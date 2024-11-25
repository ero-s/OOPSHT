package object;
import Entity.Entity;
import main.Panel;

public class OBJ_Coin extends Entity
{
    Panel panel;
    public OBJ_Coin(Panel panel)
    {
        super(panel);
        this.panel = panel;

        type = type_pickupOnly;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("res/objects/coin_bronze", panel.tileSize, panel.tileSize);
    }
    public void use(Entity entity)
    {
        panel.playSE(1);
        panel.ui.addMessage("Coin +" + value);
        panel.player.coin += value;
    }
}
