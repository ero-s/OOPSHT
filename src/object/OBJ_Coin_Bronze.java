package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gp;
    public static final String objName = "Bronze Coin";
    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 30;
        down1 = setup("/objects/coin_bronze", gp.getTileSize(), gp.getTileSize());
        price = 25;
    }
    public boolean use(Entity entity)
    {
        gp.playSE(1);
        gp.getUi().addMessage("Coin +" + value);
        entity.coin += value;
        return true;
    }
}
