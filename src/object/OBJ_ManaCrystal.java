package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gp;
    public static final String objName = "Mana Crystal";

    public OBJ_ManaCrystal(GamePanel gp)
    {
        super(gp);

        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 1;
        down1 = setup("/objects/manacrystal_full", gp.getTileSize(), gp.getTileSize());
        image = setup("/objects/manacrystal_full", gp.getTileSize(), gp.getTileSize());
        image2 = setup("/objects/manacrystal_blank", gp.getTileSize(), gp.getTileSize());
        price = 105;
    }
    public boolean use(Entity entity)
    {
        gp.playSE(2);
        gp.getUi().addMessage("Mana +" + value);
        entity.mana += value;
        return true;
    }
}
