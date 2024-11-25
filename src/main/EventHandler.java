package main;

import Entity.Entity;
import data.Progress;

public class EventHandler{
    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;


    public EventHandler(GamePanel gp)
    {
        //Set event's interact 2x2 pixels
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.getMaxMap()][gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.getMaxMap() && col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow())
        {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.getMaxWorldCol())
            {
                col = 0;
                row++;

                if(row == gp.getMaxWorldRow())
                {
                    row = 0;
                    map++; // create eventRectangles for each map
                }
            }
        }
        setDialogue();
    }
    public void setDialogue()
    {
        eventMaster.dialogues[0][0] = "You fall into a pit!";

        eventMaster.dialogues[1][0] = "You drank some water.\nYour life and mana has been recovered.\n"+ "(The progress has been saved)";
        eventMaster.dialogues[1][1] = "Frick, this is some good water.";
    }
    public void checkEvent()
    {
        //Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.getPlayer().worldX - previousEventX);  //pure distance
        int yDistance = Math.abs(gp.getPlayer().worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);                //returns greater value
        if(distance > gp.getTileSize())
        {
            canTouchEvent = true;
        }

        if(canTouchEvent == true)
        {
            if(hit(0,23,12, "up") == true) {healingPool(gp.getDialogueState());}
            else if(hit(0,27,16, "right") == true) {damagePit(gp.getDialogueState());}
            else if(hit(0,10,39, "any") == true) {teleport(1,12,13, gp.getIndoor());} //to merchant's house
            else if(hit(1,12,13, "any") == true) {teleport(0,10,39, gp.getOutside());} //to outside
            else if(hit(1,12,9, "up") == true) {speak(gp.getNpc()[1][0]);} //merchant

            else if(hit(0,12,9, "any") == true) {teleport(2,9,41, gp.getDungeon());} //to the dungeon
            else if(hit(2,9,41, "any") == true) {teleport(0,12,9, gp.getOutside());} //to outside
            else if(hit(2,8,7, "any") == true) {teleport(3,26,41, gp.getDungeon());} //to B2
            else if(hit(3,26,41, "any") == true) {teleport(2,8,7, gp.getDungeon());} //to B1
            else if(hit(3,25,27, "any") == true) {skeletonLord();} //BOSS

        }

    }
    public boolean hit(int map, int col, int row, String reqDirection)
    {
        boolean hit = false;
        if(map == gp.getCurrentMap())
        {
            //Getting player's current solidArea positions
            gp.getPlayer().solidArea.x = gp.getPlayer().worldX + gp.getPlayer().solidArea.x;
            gp.getPlayer().solidArea.y = gp.getPlayer().worldY + gp.getPlayer().solidArea.y;
            //Getting eventRect's current solidArea positions
            eventRect[map][col][row].x = col * gp.getTileSize() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.getTileSize() + eventRect[map][col][row].y;
            //Checking if player's solidArea is colliding with eventRect's solidArea
            if(gp.getPlayer().solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone)
            {
                if(gp.getPlayer().direction.contentEquals(reqDirection) || reqDirection.equals("any"))
                {
                    hit = true;

                    previousEventX = gp.getPlayer().worldX;
                    previousEventY = gp.getPlayer().worldY;
                }
            }
            //RESET
            gp.getPlayer().solidArea.x = gp.getPlayer().solidAreaDefaultX;
            gp.getPlayer().solidArea.y = gp.getPlayer().solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }
        return hit;
    }
    public void teleport(int map, int col, int row, int area)
    {
        gp.setGameState(gp.getTransitionState());
        gp.setNextArea(area);
        tempMap = map;
        tempCol = col;
        tempRow = row;
        //DRAW TRANSITION IN UI
        canTouchEvent = false;
        gp.playSE(13);
    }
    public void damagePit(int gameState)
    {
        gp.setGameState(gameState);
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.getPlayer().life -= 2;
        canTouchEvent = false;
        //eventRect[col][row].eventDone = true;
    }
    public void healingPool(int gameState)
    {
        if(gp.getKeyH().enterPressed == true)
        {
            gp.setGameState(gameState);
            gp.getPlayer().attackCanceled = true;
            gp.playSE(2);
            eventMaster.startDialogue(eventMaster,1);
            gp.getPlayer().life = gp.getPlayer().maxLife;
            gp.getPlayer().mana = gp.getPlayer().maxMana;
            //when you rest at the healing pool monsters will respawn
            gp.getaSetter().setMonster();
            gp.getSaveLoad().save();
        }
    }
    public void speak(Entity entity)
    {
        if(gp.getKeyH().enterPressed == true)
        {
            gp.setGameState(gp.getDialogueState());
            gp.getPlayer().attackCanceled = true;
            entity.speak();
        }
    }
    public void skeletonLord()
    {
        if(gp.isBossBattleOn() == false && Progress.skeletonLordDefeated == false)
        {
            gp.setGameState(gp.getCutsceneState());
            gp.getCsManager().sceneNum = gp.getCsManager().skeletonLord;
        }
    }
}
