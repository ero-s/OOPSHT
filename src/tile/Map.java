package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{

    GamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        createWorldMap();

    }
    public void createWorldMap()
    {
        worldMap = new BufferedImage[gp.getMaxMap()];
        int worldMapWidth = gp.getTileSize() * gp.getMaxWorldCol();
        int worldMapHeight = gp.getTileSize() * gp.getMaxWorldRow();

        for(int i = 0; i < gp.getMaxMap(); i++)
        {
            worldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();  //Attach this g2 to worldMap Buffered image

            int col = 0;
            int row = 0;
            while(col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow())
            {
                int tileNum = mapTileNum[i][col][row];
                int x = gp.getTileSize() * col;
                int y = gp.getTileSize() * row;
                g2.drawImage(tile[tileNum].image, x, y, null);
                col++;
                if(col == gp.getMaxWorldCol())
                {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2)
    {
        //Background Color
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.getScreenWidth(), gp.getScreenHeight());

        //Draw map
        int width = 500;
        int height = 500;
        int x = gp.getScreenWidth() /2 - width/2;
        int y = gp.getScreenHeight() /2 - height/2;
        g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height, null);

        //Draw Player
        double scale = (double) (gp.getTileSize() * gp.getMaxWorldCol())/width; //Scaling from actual map
        int playerX = (int)(x + gp.getPlayer().worldX/scale);
        int playerY = (int)(y + gp.getPlayer().worldY/scale);
        int playerSize = (int)(gp.getTileSize() /scale);
        g2.drawImage(gp.getPlayer().down1,playerX,playerY,playerSize,playerSize,null);

        //Hint
        g2.setFont(gp.getUi().maruMonica.deriveFont(32f));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 750, 550);
    }
    public void drawMiniMap(Graphics2D g2)
    {
        if(miniMapOn == true)
        {
            //Draw map
            int width = 200;
            int height = 200;
            int x = gp.getScreenWidth() - width - 50;
            int y = 50;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f)); //little transparency
            g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height,null);

            //Draw Player
            double scale = (double) (gp.getTileSize() * gp.getMaxWorldCol())/width; //Scaling from actual map
            int playerX = (int)(x + gp.getPlayer().worldX/scale);
            int playerY = (int)(y + gp.getPlayer().worldY/scale);
            int playerSize = (int)(gp.getTileSize() /3); //(int)(gp.tileSize/scale);
            g2.drawImage(gp.getPlayer().down1,playerX-6,playerY-6,playerSize,playerSize,null); //playerX,playerY

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); //reset alpha
        }
    }
}
