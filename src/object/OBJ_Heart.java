package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;
    public static final String objName = "Heart";

    public OBJ_Heart(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        value = 2;
        down1  = setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize()); //Entity's draw method will draw it.
        image = setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize());
        image2 = setup("/objects/heart_half", gp.getTileSize(), gp.getTileSize());
        image3 = setup("/objects/heart_blank", gp.getTileSize(), gp.getTileSize());
        price = 175;
    }
    public boolean use(Entity entity)
    {
        gp.playSE(2);
        gp.getUi().addMessage("Life +" + value);
        entity.life += value;
        return true;
    }
}
