package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    GamePanel gp;
    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gp)
    {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("/objects/tent", gp.getTileSize(), gp.getTileSize());
        description = "[Tent]\nYou can use this to sleep \nuntil the next morning.";
        price = 100;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.setGameState(gp.getSleepState());
        gp.playSE(14);
        gp.getPlayer().life = gp.getPlayer().maxLife;
        gp.getPlayer().mana = gp.getPlayer().maxMana;
        gp.getPlayer().getSleepingImage(down1);
        return true;
    }
}
