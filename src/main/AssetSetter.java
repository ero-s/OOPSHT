package main;

import data.Progress;
import Entity.NPC_BigRock;
import Entity.NPC_Merchant;
import Entity.NPC_OldMan;
import monster.*;
import object.*;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setObject()
    {
        int mapNum = 0;
        int i = 0;

        gp.getObj()[mapNum][i] = new OBJ_Axe(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 33;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 7;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Lantern(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 31;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 12;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Tent(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 26;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 16;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 14;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 28;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 12;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 12;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 22;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 41;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 38;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 40;
        i++;

        mapNum = 1;//adding object to second map
        i=0;
        gp.getObj()[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 10;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 10;
        i++;

        mapNum = 2;
        i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 40;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 41;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 13;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 16;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 26;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 34;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 27;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 15;
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 18;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 23;
        i++;

        mapNum = 3;
        i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 25;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 15;
        i++;

        gp.getObj()[mapNum][i] = new OBJ_BlueHeart(gp);
        gp.getObj()[mapNum][i].worldX = gp.getTileSize() * 25;
        gp.getObj()[mapNum][i].worldY = gp.getTileSize() * 8;
        i++;
    }

    public void setNPC()
    {
        int mapNum = 0;
        int i = 0;

        //MAP = 0
        gp.getNpc()[mapNum][i] = new NPC_OldMan(gp);
        gp.getNpc()[mapNum][i].worldX = gp.getTileSize() *21;
        gp.getNpc()[mapNum][i].worldY = gp.getTileSize() *21;
        i++;

        //MAP = 1
        mapNum = 1;
        i = 0;

        gp.getNpc()[mapNum][i] = new NPC_Merchant(gp);
        gp.getNpc()[mapNum][i].worldX = gp.getTileSize() *12;
        gp.getNpc()[mapNum][i].worldY = gp.getTileSize() *7;
        i++;

        mapNum = 2;
        i = 0;

        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].worldX = gp.getTileSize() *20;
        gp.getNpc()[mapNum][i].worldY = gp.getTileSize() *25;
        i++;
        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].worldX = gp.getTileSize() *11;
        gp.getNpc()[mapNum][i].worldY = gp.getTileSize() *18;
        i++;
        gp.getNpc()[mapNum][i] = new NPC_BigRock(gp);
        gp.getNpc()[mapNum][i].worldX = gp.getTileSize() *23;
        gp.getNpc()[mapNum][i].worldY = gp.getTileSize() *14;
        i++;

    }
    public void setMonster()
    {
        int mapNum = 0;
        int i = 0;
        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *23;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *36;
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *23;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *37;
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *24;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *37;
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *34;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *42;
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *38;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *42;
        i++;

        gp.getMonster()[mapNum][i] = new MON_Orc(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *12;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *33;
        i++;


        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *34;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *11;
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *38;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *7;
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *37;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *9;
        i++;

        mapNum = 2;
        i = 0;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *34;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *39;
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *36;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *25;
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *39;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *26;
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *28;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *11;
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *10;
        gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *19;
        i++;

        mapNum = 3;
        i = 0;

        if(Progress.skeletonLordDefeated == false)
        {
            gp.getMonster()[mapNum][i] = new MON_SkeletonLord(gp);
            gp.getMonster()[mapNum][i].worldX = gp.getTileSize() *23;
            gp.getMonster()[mapNum][i].worldY = gp.getTileSize() *16;
            i++;
        }


    }
    public void setInteractiveTile()
    {
        int mapNum = 0;
        int i = 0;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,27,12);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,28,12);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,29,12);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,30,12);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,32,12);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,33,12);i++;

        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,18,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,17,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,16,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,15,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,14,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,13,40);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,10,40);i++;

        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,13,41);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,12,41);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,11,41);i++;
        gp.getiTile()[mapNum][i] = new IT_DryTree(gp,10,41);i++;

        mapNum = 2;
        i = 0;

        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,18,30);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,17,31);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,17,32);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,17,34);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,18,34);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,10,33);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,10,22);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,38,24);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,38,18);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,38,19);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,38,21);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,18,13);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,18,14);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,22,28);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,30,28);i++;
        gp.getiTile()[mapNum][i] = new IT_DestructibleWall(gp,32,28);i++;

        gp.getiTile()[mapNum][i] = new IT_MetalPlate(gp,20,22);i++;
        gp.getiTile()[mapNum][i] = new IT_MetalPlate(gp,8,17);i++;
        gp.getiTile()[mapNum][i] = new IT_MetalPlate(gp,39,31);i++;


    }
}
