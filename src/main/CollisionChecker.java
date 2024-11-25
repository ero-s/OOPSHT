package main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp)
    {
        this.gp = gp;
    }
    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;                                // solidArea.x = 8
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;      // solidArea.y = 16
        int entityTopWorldY = entity.worldY + entity.solidArea.y;                                 // solidArea.width = 32
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;    // solidArea.height = 32

        int entityLeftCol = entityLeftWorldX/ gp.getTileSize();
        int entityRightCol = entityRightWorldX/ gp.getTileSize();
        int entityTopRow = entityTopWorldY/ gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/ gp.getTileSize();

        int tileNum1 , tileNum2;

        //Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack == true)
        {
            direction = entity.knockBackDirection;
        }

        switch (direction)
        {
            case "up" :
                entityTopRow = (entityTopWorldY - entity.speed)/ gp.getTileSize();
                tileNum1 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityTopRow];  //Check Left Hand
                tileNum2 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityTopRow]; //Check Right Hand
                if(gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;

            case "down" :
                entityBottomRow = (entityBottomWorldY + entity.speed)/ gp.getTileSize();
                tileNum1 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityBottomRow];  //Check Left Hand
                tileNum2 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityBottomRow]; //Check Right Hand
                if(gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;

            case "left" :
                entityLeftCol = (entityLeftWorldX - entity.speed)/ gp.getTileSize();
                tileNum1 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityTopRow];  //Check Left Hand
                tileNum2 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityLeftCol][entityBottomRow]; //Check Right Hand
                if(gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;

            case "right" :
                entityRightCol = (entityRightWorldX + entity.speed)/ gp.getTileSize();
                tileNum1 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityTopRow];  //Check Left Hand
                tileNum2 =  gp.getTileM().mapTileNum[gp.getCurrentMap()][entityRightCol][entityBottomRow]; //Check Right Hand
                if(gp.getTileM().tile[tileNum1].collision == true || gp.getTileM().tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player)
    {
        int index = 999;

        //Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack == true)
        {
            direction = entity.knockBackDirection;
        }

        for(int i = 0; i < gp.getObj()[1].length; i++)
        {
            if(gp.getObj()[gp.getCurrentMap()][i] != null)
            {
                // get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the object's solid area position
                gp.getObj()[gp.getCurrentMap()][i].solidArea.x = gp.getObj()[gp.getCurrentMap()][i].worldX + gp.getObj()[gp.getCurrentMap()][i].solidArea.x;       //entity's solid area and obj's solid area is different.
                gp.getObj()[gp.getCurrentMap()][i].solidArea.y = gp.getObj()[gp.getCurrentMap()][i].worldY + gp.getObj()[gp.getCurrentMap()][i].solidArea.y;

                switch (direction)
                {
                    case "up" :
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down" :
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left" :
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right" :
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if(entity.solidArea.intersects(gp.getObj()[gp.getCurrentMap()][i].solidArea)) //Checking if Entity rectangle and Object rectangle intersects.
                {
                    if(gp.getObj()[gp.getCurrentMap()][i].collision == true) //Collision (Player can't enter through a door.)
                    {
                        entity.collisionOn = true;
                    }
                    if(player == true) // Checking this because no one can receive items except the player.
                    {
                        index = i;   // Non-player characters cannot pickup objects.
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX; // Reset
                entity.solidArea.y = entity.solidAreaDefaultY;

                gp.getObj()[gp.getCurrentMap()][i].solidArea.x = gp.getObj()[gp.getCurrentMap()][i].solidAreaDefaultX;     // Reset
                gp.getObj()[gp.getCurrentMap()][i].solidArea.y = gp.getObj()[gp.getCurrentMap()][i].solidAreaDefaultY;
            }
        }
         return index;
    }

    //NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[][] target)
    {
        int index = 999;   // no collision returns 999;
        //Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack == true)
        {
            direction = entity.knockBackDirection;
        }

        for(int i = 0;i < target[1].length; i++)
        {
            if(target[gp.getCurrentMap()][i] != null)
            {
                // get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the object's solid area position
                target[gp.getCurrentMap()][i].solidArea.x = target[gp.getCurrentMap()][i].worldX + target[gp.getCurrentMap()][i].solidArea.x;       //entity's solid area and obj's solid area is different.
                target[gp.getCurrentMap()][i].solidArea.y = target[gp.getCurrentMap()][i].worldY + target[gp.getCurrentMap()][i].solidArea.y;

                switch (direction)
                {
                    case "up" :
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down" :
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left" :
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right" :
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if(entity.solidArea.intersects(target[gp.getCurrentMap()][i].solidArea))
                {
                    if(target[gp.getCurrentMap()][i] != entity) // avoid entity includes itself as a collision target
                    {
                        entity.collisionOn = true;
                        index = i;   // Non-player characters cannot pickup objects.
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX; //Reset
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[gp.getCurrentMap()][i].solidArea.x = target[gp.getCurrentMap()][i].solidAreaDefaultX;     //Reset
                target[gp.getCurrentMap()][i].solidArea.y = target[gp.getCurrentMap()][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity)
    {
        boolean contactPlayer = false;
        // get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // get the object's solid area position
        gp.getPlayer().solidArea.x = gp.getPlayer().worldX + gp.getPlayer().solidArea.x;       //entity's solid area and obj's solid area is different.
        gp.getPlayer().solidArea.y = gp.getPlayer().worldY + gp.getPlayer().solidArea.y;

        switch (entity.direction)
        {
            case "up" :
                entity.solidArea.y -= entity.speed;
                break;
            case "down" :
                entity.solidArea.y += entity.speed;
                break;
            case "left" :
                entity.solidArea.x -= entity.speed;
                break;
            case "right" :
                entity.solidArea.x += entity.speed;
                break;
        }
        if(entity.solidArea.intersects(gp.getPlayer().solidArea))
        {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX; ////Reset
        entity.solidArea.y = entity.solidAreaDefaultY;

        gp.getPlayer().solidArea.x = gp.getPlayer().solidAreaDefaultX;     ////Reset
        gp.getPlayer().solidArea.y = gp.getPlayer().solidAreaDefaultY;

        return contactPlayer;
    }
}
