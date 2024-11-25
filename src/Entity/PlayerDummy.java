package Entity;

import main.GamePanel;

public class PlayerDummy extends Entity{
    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp)
    {
        super(gp);

        name = npcName;
        getImage();
    }

    public void getImage()
    {
        up1 = setup("/player/boy_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/player/boy_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/player/boy_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/player/boy_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/player/boy_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/player/boy_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/player/boy_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/player/boy_right_2", gp.getTileSize(), gp.getTileSize());
    }
}
