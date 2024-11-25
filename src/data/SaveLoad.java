package data;

import main.GamePanel;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp)
    {
        this.gp = gp;
    }

    public void save()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            //PLAYER STATS
            ds.level = gp.getPlayer().level;
            ds.maxLife = gp.getPlayer().maxLife;
            ds.life = gp.getPlayer().life;
            ds.maxMana = gp.getPlayer().maxMana;
            ds.mana = gp.getPlayer().mana;
            ds.strength = gp.getPlayer().strength;
            ds.dexterity = gp.getPlayer().dexterity;
            ds.exp = gp.getPlayer().exp;
            ds.nextLevelExp = gp.getPlayer().nextLevelExp;
            ds.coin = gp.getPlayer().coin;

            //PLAYER INVENTORY
            for(int i = 0; i < gp.getPlayer().inventory.size(); i++)
            {
                ds.itemNames.add(gp.getPlayer().inventory.get(i).name);
                ds.itemAmounts.add(gp.getPlayer().inventory.get(i).amount);
            }

            //PLAYER EQUIPMENT
            ds.currentWeaponSlot = gp.getPlayer().getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.getPlayer().getCurrentShieldSlot();

            //OBJECTS ON MAP
            ds.mapObjectNames = new String[gp.getMaxMap()][gp.getObj()[1].length]; //2nd dimension of obj array
            ds.mapObjectWorldX = new int[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectWorldY = new int[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectLootNames = new String[gp.getMaxMap()][gp.getObj()[1].length];
            ds.mapObjectOpened = new boolean[gp.getMaxMap()][gp.getObj()[1].length];

            for(int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++)
            {
                for(int i = 0; i < gp.getObj()[1].length; i++)
                {
                    if(gp.getObj()[mapNum][i] == null)
                    {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else
                    {
                        ds.mapObjectNames[mapNum][i] = gp.getObj()[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.getObj()[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.getObj()[mapNum][i].worldY;
                        if(gp.getObj()[mapNum][i].loot != null)
                        {
                            ds.mapObjectLootNames[mapNum][i] = gp.getObj()[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.getObj()[mapNum][i].opened;
                    }
                }
            }

            //Write the DataStorage object
            oos.writeObject(ds);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void load()
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //Read the DataStorage object
            DataStorage ds =  (DataStorage)ois.readObject();

            //PLAYER STATS
            gp.getPlayer().level = ds.level;
            gp.getPlayer().maxLife = ds.maxLife;
            gp.getPlayer().life = ds.life;
            gp.getPlayer().maxMana = ds.maxMana;
            gp.getPlayer().mana = ds.mana;
            gp.getPlayer().strength = ds.strength;
            gp.getPlayer().dexterity = ds.dexterity;
            gp.getPlayer().exp = ds.exp;
            gp.getPlayer().nextLevelExp = ds.nextLevelExp;
            gp.getPlayer().coin = ds.coin;

            //PLAYER INVENTORY
            gp.getPlayer().inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++)
            {
                gp.getPlayer().inventory.add(gp.geteGenerator().getObject(ds.itemNames.get(i)));
                gp.getPlayer().inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            //PLAYER EQUIPMENT
            gp.getPlayer().currentWeapon = gp.getPlayer().inventory.get(ds.currentWeaponSlot);
            gp.getPlayer().currentShield = gp.getPlayer().inventory.get(ds.currentShieldSlot);
            gp.getPlayer().getAttack();
            gp.getPlayer().getDefense();
            gp.getPlayer().getAttackImage();

            //OBJECTS ON MAP
            for(int mapNum = 0; mapNum < gp.getMaxMap(); mapNum++)
            {
                for(int i = 0; i < gp.getObj()[1].length; i++)
                {
                    if(ds.mapObjectNames[mapNum][i].equals("NA"))
                    {
                        gp.getObj()[mapNum][i] = null;
                    }
                    else
                    {
                        gp.getObj()[mapNum][i] = gp.geteGenerator().getObject(ds.mapObjectNames[mapNum][i]);
                        gp.getObj()[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.getObj()[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if(ds.mapObjectLootNames[mapNum][i] != null)
                        {
                            gp.getObj()[mapNum][i].setLoot(gp.geteGenerator().getObject(ds.mapObjectLootNames[mapNum][i]));
                        }
                        gp.getObj()[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if(gp.getObj()[mapNum][i].opened == true)
                        {
                            gp.getObj()[mapNum][i].down1 = gp.getObj()[mapNum][i].image2;
                        }
                        gp.getObj()[mapNum][i].setDialogue(); // added this line
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
