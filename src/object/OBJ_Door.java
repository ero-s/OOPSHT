package object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

    GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = objName;
        down1 = setup("/objects/door", gp.getTileSize(), gp.getTileSize());
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        price = 35;
        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0] = "You might need something to open this.";
    }
    public void interact() {
        startDialogue(this,0);
    }
}
