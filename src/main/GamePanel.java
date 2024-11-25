package main;
import Entity.Entity;
import ai.PathFinder;
import data.SaveLoad;
import Entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    private final int originalTileSize = 16; // 16*16  tile. default
    private final int scale = 3; // 16*3 scale

    private final int tileSize = getOriginalTileSize() * getScale(); // 48*48 tile // public cuz we use it in Player Class
    private final int maxScreenCol = 20; // 4:3 window
    private final int maxScreenRow = 12;
    private final int screenWidth = getTileSize() * getMaxScreenCol();  //48*20 = 960 pixels
    private final int screenHeight = getTileSize() * getMaxScreenRow();  //48*12 = 576 pixels  // GAME SCREEN SIZE

    //WORLD SETTINGS
    private int maxWorldCol;
    private int maxWorldRow;
    private final int maxMap = 10;
    private int currentMap = 0;
    private CollisionChecker checker;

    //FOR FULLSCREEN
    private int screenWidth2 = getScreenWidth();
    private int screenHeight2 = getScreenHeight();
    private BufferedImage tempScreen;
    private Graphics2D g2;
    private boolean fullScreenOn = false;


    //FPS
    private int FPS = 60;

    //SYSTEM
    private TileManager tileM = new TileManager(this);
    private KeyHandler keyH = new KeyHandler(this);
    private EventHandler eHandler = new EventHandler(this);
    private Sound music = new Sound(); // Created 2 different objects for Sound Effect and Music. If you use 1 object SE or Music stops sometimes.
    private Sound se = new Sound();
    private CollisionChecker cChecker = new CollisionChecker(this);
    private AssetSetter  aSetter = new AssetSetter(this);
    private UI ui = new UI(this);
    private Config config = new Config(this);
    private PathFinder pFinder = new PathFinder(this);
    private EnvironmentManager eManager = new EnvironmentManager(this);
    private Map map = new Map(this);
    private SaveLoad saveLoad = new SaveLoad(this);
    private EntityGenerator eGenerator = new EntityGenerator(this);
    private CutsceneManager csManager = new CutsceneManager(this);
    private Thread gameThread;

    //ENTITY AND OBJECT
    private Player player = new Player(this, getKeyH());
    private Entity[][] obj = new Entity[getMaxMap()][20]; // display 10 objects same time
    private Entity[][] npc = new Entity[getMaxMap()][10];
    private Entity[][] monster = new Entity[getMaxMap()][20];
    private InteractiveTile[][] iTile = new InteractiveTile[getMaxMap()][50];
    private Entity[][] projectile = new Entity[getMaxMap()][20]; // cut projectile
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    private ArrayList<Entity> particleList = new ArrayList<>();
    private ArrayList<Entity> entityList = new ArrayList<>();


    //GAME STATE
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;
    private final int optionsState = 5;
    private final int gameOverState = 6;
    private final int transitionState = 7;
    private final int tradeState = 8;
    private final int sleepState = 9;
    private final int mapState = 10;
    private final int cutsceneState = 11;

    //OTHERS
    private boolean bossBattleOn = false;

    //AREA
    private int currentArea;
    private int nextArea;
    private final int outside = 50;
    private final int indoor = 51;
    private final int dungeon = 52;


    public GamePanel() // constructor
    {
        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight())); // JPanel size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering performance
        this.addKeyListener(getKeyH());
        this.setFocusable(true);
    }
    public void setupGame()
    {
        getaSetter().setObject();
        getaSetter().setNPC();
        getaSetter().setMonster();
        getaSetter().setInteractiveTile();
        geteManager().setup();

        /*playMusic(0);   // 0 = BlueBoyAdventure.wav
        stopMusic();*/
        setGameState(getTitleState());
        //FOR FULLSCREEN
        setTempScreen(new BufferedImage(getScreenWidth(), getScreenHeight(),BufferedImage.TYPE_INT_ARGB)); //blank screen
        setG2((Graphics2D) getTempScreen().getGraphics()); // g2 attached to this tempScreen. g2 will draw on this tempScreen buffered image.
        if(isFullScreenOn() == true)
        {
            setFullScreen();
        }
    }
    public void resetGame(boolean restart)
    {
        stopMusic();
        setCurrentArea(getOutside());
        removeTempEntity();
        setBossBattleOn(false);
        getPlayer().setDefaultPositions();
        getPlayer().restoreStatus();
        getaSetter().setMonster();
        getaSetter().setNPC();
        getPlayer().resetCounter();

        if(restart == true)
        {
            getPlayer().setDefaultValues();
            getaSetter().setObject();
            getaSetter().setInteractiveTile();
            geteManager().lighting.resetDay();
        }

    }
    public void setFullScreen()
    {
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        setScreenWidth2(Main.window.getWidth());
        setScreenHeight2(Main.window.getHeight());
    }

    public void startGameThread()
    {
        setGameThread(new Thread(this));
        getGameThread().start(); // run'ı cagirir
    }

    @Override
    public void run()
    {
        double drawInterval = 1000000000/ getFPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        //long timer = 0;
        //int drawCount = 0;


        while(getGameThread() != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            //timer += currentTime - lastTime;
            lastTime = currentTime;
            if(delta >= 1)
            {
                update();
                /*repaint(); COMMENTED FOR FULL SCREEN*/
                drawToTempScreen(); //FOR FULL SCREEN - Draw everything to the buffered image
                drawToScreen();     //FOR FULL SCREEN - Draw the buffered image to the screen
                delta--;
                //drawCount++;
            }
            //SHOW FPS
            /*if(timer >= 1000000000)
            {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }*/
        }
    }

    public void update()
    {
        if(getGameState() == getPlayState())
        {
            //PLAYER
            getPlayer().update();

            //NPC
            for(int i = 0; i < getNpc()[1].length; i++) //[1] means second dimension's length!!!
            {
                if(getNpc()[getCurrentMap()][i] != null)
                {
                    getNpc()[getCurrentMap()][i].update();
                }
            }

            //MONSTER
            for(int i = 0; i < getMonster()[1].length; i++)
            {
                if(getMonster()[getCurrentMap()][i] != null)
                {
                    if(getMonster()[getCurrentMap()][i].alive == true && getMonster()[getCurrentMap()][i].dying == false)
                    {
                        getMonster()[getCurrentMap()][i].update();
                    }
                    if(getMonster()[getCurrentMap()][i].alive == false)
                    {
                        getMonster()[getCurrentMap()][i].checkDrop(); //when monster dies, i check its drop
                        getMonster()[getCurrentMap()][i] = null;
                    }
                }
            }

            //PROJECTILE
            for(int i = 0; i < getProjectile()[1].length; i++)
            {
                if(getProjectile()[getCurrentMap()][i] != null)
                {
                    if(getProjectile()[getCurrentMap()][i].alive == true)
                    {
                        getProjectile()[getCurrentMap()][i].update();
                    }
                    if(getProjectile()[getCurrentMap()][i].alive == false)
                    {
                        getProjectile()[getCurrentMap()][i] = null;
                    }
                }
            }

            //PARTICLE
            for(int i = 0; i < getParticleList().size(); i++)
            {
                if(getParticleList().get(i)!= null)
                {
                    if(getParticleList().get(i).alive == true)
                    {
                        getParticleList().get(i).update();
                    }
                    if(getParticleList().get(i).alive == false)
                    {
                        getParticleList().remove(i);
                    }
                }
            }

            //INTERACTIVE TILE
            for(int i = 0; i < getiTile()[1].length; i++)
            {
                if(getiTile()[getCurrentMap()][i] != null)
                {
                    getiTile()[getCurrentMap()][i].update();
                }
            }

            geteManager().update();
        }

        if(getGameState() == getPauseState())
        {
            //nothing, just pause screen
        }
    }

    //FOR FULL SCREEN (FIRST DRAW TO TEMP SCREEN INSTEAD OF JPANEL)
    public void drawToTempScreen()
    {
        //DEBUG
        long drawStart = 0;
        if(getKeyH().showDebugText == true)
        {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(getGameState() == getTitleState())
        {
            getUi().draw(getG2());
        }
        //MAP SCREEN
        else if(getGameState() == getMapState())
        {
            getMap().drawFullMapScreen(getG2());
        }
        //OTHERS
        else
        {
            //TILE
            getTileM().draw(getG2());

            //INTERACTIVE TILE
            for(int i = 0; i < getiTile()[1].length; i++)
            {
                if(getiTile()[getCurrentMap()][i] != null)
                {
                    getiTile()[getCurrentMap()][i].draw(getG2());
                }
            }

            //ADD ENTITIES TO THE LIST
            //PLAYER
            getEntityList().add(getPlayer());

            //NPCs
            for(int i = 0; i < getNpc()[1].length; i++)
            {
                if(getNpc()[getCurrentMap()][i] != null)
                {
                    getEntityList().add(getNpc()[getCurrentMap()][i]);
                }
            }

            //OBJECTS
            for(int i = 0; i < getObj()[1].length; i++)
            {
                if(getObj()[getCurrentMap()][i] != null)
                {
                    getEntityList().add(getObj()[getCurrentMap()][i]);
                }
            }

            //MONSTERS
            for(int i = 0; i < getMonster()[1].length; i++)
            {
                if(getMonster()[getCurrentMap()][i] != null)
                {
                    getEntityList().add(getMonster()[getCurrentMap()][i]);
                }
            }

            //PROJECTILES
            for(int i = 0; i < getProjectile()[1].length; i++)
            {
                if(getProjectile()[getCurrentMap()][i] != null)
                {
                    getEntityList().add(getProjectile()[getCurrentMap()][i]);
                }
            }

            //PARTICLES
            for(int i = 0; i < getParticleList().size(); i++)
            {
                if(getParticleList().get(i) != null)
                {
                    getEntityList().add(getParticleList().get(i));
                }
            }

            //SORT
            Collections.sort(Collections.unmodifiableList(getEntityList()), new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);   // result returns : (x=y : 0, x>y : >0, x<y : <0)
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i = 0; i < getEntityList().size(); i++)
            {
                getEntityList().get(i).draw(getG2());
            }

            //EMPTY ENTITY LIST
            getEntityList().clear();

            //ENVIRONMENT
            geteManager().draw(getG2());

            //MINI MAP
            getMap().drawMiniMap(getG2());

            //CUTSCENE
            getCsManager().draw(getG2());

            //UI
            getUi().draw(getG2());

            //DEBUG

            if(getKeyH().showDebugText == true)
            {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                getG2().setFont(new Font("Arial", Font.PLAIN,20));
                getG2().setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                getG2().drawString("WorldX " + getPlayer().worldX,x,y);
                y+= lineHeight;
                getG2().drawString("WorldY " + getPlayer().worldY,x,y);
                y+= lineHeight;
                getG2().drawString("Col " + (getPlayer().worldX + getPlayer().solidArea.x) / getTileSize(),x,y);
                y+= lineHeight;
                getG2().drawString("Row " + (getPlayer().worldY + getPlayer().solidArea.y) / getTileSize(),x,y);
                y+= lineHeight;
                getG2().drawString("Map " + getCurrentMap(),x,y);
                y+= lineHeight;
                getG2().drawString("Draw time: " + passed,x,y);
                y+= lineHeight;
                getG2().drawString("God Mode: " + getKeyH().godModeOn, x, y);

            }
        }
    }
    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(getTempScreen(), 0, 0, getScreenWidth2(), getScreenHeight2(),null);
        g.dispose();
    }
    //COMMENTED FOR FULLSCREEN
    /*public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // Graphics2D extends Graphics class

        //DEBUG
        long drawStart = 0;
        if(keyH.showDebugText == true)
        {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        //OTHERS
        else
        {
            //TILE
            tileM.draw(g2);

            //INTERACTIVE TILE
            for(int i = 0; i < iTile.length; i++)
            {
                if(iTile[i] != null)
                {
                    iTile[i].draw(g2);
                }
            }

            //ADD ENTITIES TO THE LIST
            //PLAYER
            entityList.add(player);

            //NPCs
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    entityList.add(npc[i]);
                }
            }

            //OBJECTS
            for(int i = 0; i < obj.length; i++)
            {
                if(obj[i] != null)
                {
                    entityList.add(obj[i]);
                }
            }

            //MONSTERS
            for(int i = 0; i < monster.length; i++)
            {
                if(monster[i] != null)
                {
                    entityList.add(monster[i]);
                }
            }

            //PROJECTILES
            for(int i = 0; i < projectileList.size(); i++)
            {
                if(projectileList.get(i) != null)
                {
                    entityList.add(projectileList.get(i));
                }
            }

            //PARTICLES
            for(int i = 0; i < particleList.size(); i++)
            {
                if(particleList.get(i) != null)
                {
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);   // result returns : (x=y : 0, x>y : >0, x<y : <0)
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++)
            {
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            //UI
            ui.draw(g2);

            //DEBUG

            if(keyH.showDebugText == true)
            {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN,20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX " + player.worldX,x,y);
                y+= lineHeight;
                g2.drawString("WorldY " + player.worldY,x,y);
                y+= lineHeight;
                g2.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize,x,y);
                y+= lineHeight;
                g2.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize,x,y);
                y+= lineHeight;

                g2.drawString("Draw time : " + passed,x,y);
            }
            g2.dispose();
        }
    }*/

    public void playMusic(int i)
    {
        getMusic().setFile(i);
        getMusic().play();
        getMusic().loop();
    }
    public void stopMusic()
    {
        getMusic().stop();
    }
    public void playSE(int i) // Sound effect, dont need loop
    {
        getSe().setFile(i);
        getSe().play();
    }
    public void changeArea()
    {
        if(getNextArea() != getCurrentArea())
        {
            stopMusic();

            if(getNextArea() == getOutside())
            {
                playMusic(0);
            }
            if(getNextArea() == getIndoor())
            {
                playMusic(18);
            }
            if(getNextArea() == getDungeon())
            {
                playMusic(19);
            }
            getaSetter().setNPC(); //reset for at the dungeon puzzle's stuck rocks.
        }

        setCurrentArea(getNextArea());
        getaSetter().setMonster();
    }
    public void removeTempEntity()
    {
        for(int mapNum = 0; mapNum < getMaxMap(); mapNum++)
        {
            for(int i = 0; i < getObj()[1].length; i++)
            {
                if(getObj()[mapNum][i] != null && getObj()[mapNum][i].temp == true)
                {
                    getObj()[mapNum][i] = null;
                }
            }
        }
    }

    public int getOriginalTileSize() {
        return originalTileSize;
    }

    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public CollisionChecker getChecker() {
        return checker;
    }

    public void setChecker(CollisionChecker checker) {
        this.checker = checker;
    }

    public int getScreenWidth2() {
        return screenWidth2;
    }

    public void setScreenWidth2(int screenWidth2) {
        this.screenWidth2 = screenWidth2;
    }

    public int getScreenHeight2() {
        return screenHeight2;
    }

    public void setScreenHeight2(int screenHeight2) {
        this.screenHeight2 = screenHeight2;
    }

    public BufferedImage getTempScreen() {
        return tempScreen;
    }

    public void setTempScreen(BufferedImage tempScreen) {
        this.tempScreen = tempScreen;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public TileManager getTileM() {
        return tileM;
    }

    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public EventHandler geteHandler() {
        return eHandler;
    }

    public void seteHandler(EventHandler eHandler) {
        this.eHandler = eHandler;
    }

    public Sound getMusic() {
        return music;
    }

    public void setMusic(Sound music) {
        this.music = music;
    }

    public Sound getSe() {
        return se;
    }

    public void setSe(Sound se) {
        this.se = se;
    }

    public CollisionChecker getcChecker() {
        return cChecker;
    }

    public void setcChecker(CollisionChecker cChecker) {
        this.cChecker = cChecker;
    }

    public AssetSetter getaSetter() {
        return aSetter;
    }

    public void setaSetter(AssetSetter aSetter) {
        this.aSetter = aSetter;
    }

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public PathFinder getpFinder() {
        return pFinder;
    }

    public void setpFinder(PathFinder pFinder) {
        this.pFinder = pFinder;
    }

    public EnvironmentManager geteManager() {
        return eManager;
    }

    public void seteManager(EnvironmentManager eManager) {
        this.eManager = eManager;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public SaveLoad getSaveLoad() {
        return saveLoad;
    }

    public void setSaveLoad(SaveLoad saveLoad) {
        this.saveLoad = saveLoad;
    }

    public EntityGenerator geteGenerator() {
        return eGenerator;
    }

    public void seteGenerator(EntityGenerator eGenerator) {
        this.eGenerator = eGenerator;
    }

    public CutsceneManager getCsManager() {
        return csManager;
    }

    public void setCsManager(CutsceneManager csManager) {
        this.csManager = csManager;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = (Player) player;
    }

    public Entity[][] getObj() {
        return obj;
    }

    public void setObj(Entity[][] obj) {
        this.obj = obj;
    }

    public Entity[][] getNpc() {
        return npc;
    }

    public void setNpc(Entity[][] npc) {
        this.npc = npc;
    }

    public Entity[][] getMonster() {
        return monster;
    }

    public void setMonster(Entity[][] monster) {
        this.monster = monster;
    }

    public InteractiveTile[][] getiTile() {
        return iTile;
    }

    public void setiTile(InteractiveTile[][] iTile) {
        this.iTile = iTile;
    }

    public Entity[][] getProjectile() {
        return projectile;
    }

    public void setProjectile(Entity[][] projectile) {
        this.projectile = projectile;
    }

    public ArrayList<Entity> getParticleList() {
        return particleList;
    }

    public void setParticleList(ArrayList<Entity> particleList) {
        this.particleList = particleList;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getTitleState() {
        return titleState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getCharacterState() {
        return characterState;
    }

    public int getOptionsState() {
        return optionsState;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public int getTransitionState() {
        return transitionState;
    }

    public int getTradeState() {
        return tradeState;
    }

    public int getSleepState() {
        return sleepState;
    }

    public int getMapState() {
        return mapState;
    }

    public int getCutsceneState() {
        return cutsceneState;
    }

    public boolean isBossBattleOn() {
        return bossBattleOn;
    }

    public void setBossBattleOn(boolean bossBattleOn) {
        this.bossBattleOn = bossBattleOn;
    }

    public int getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(int currentArea) {
        this.currentArea = currentArea;
    }

    public int getNextArea() {
        return nextArea;
    }

    public void setNextArea(int nextArea) {
        this.nextArea = nextArea;
    }

    public int getOutside() {
        return outside;
    }

    public int getIndoor() {
        return indoor;
    }

    public int getDungeon() {
        return dungeon;
    }
}
