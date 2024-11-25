package Entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;

        dialogueSet = -1; //For first dialogueSet(= 0)

        getImage();
        setDialogue();
    }
    public void getImage()
    {
        up1 = setup("/npc/oldman_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/npc/oldman_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/npc/oldman_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/npc/oldman_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/npc/oldman_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/npc/oldman_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/npc/oldman_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/npc/oldman_right_2", gp.getTileSize(), gp.getTileSize());
    }
    public void setDialogue()
    {
        dialogues[0][0] = "Hello, lad.";
        dialogues[0][1] = "So you've come to this island to find the treasure?";
        dialogues[0][2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogues[0][4] = "Well, good luck on you.";
        dialogues[0][3] = "You can talk with me again when you're stuck.";

        dialogues[1][0] = "If you become tired, rest at the water.";
        dialogues[1][1] = "However, the monsters reappear if you rest.\nI don't know why but that's how it works.";
        dialogues[1][2] = "In any case, don't push yourself too hard.";

        dialogues[2][0] = "I wonder how to open that door...";
    }
    public void setAction()
    {
        if(onPath == true)
        {
//            int goalCol = 12;
//            int goalRow = 9;

            int goalCol = (gp.getPlayer().worldX + gp.getPlayer().solidArea.x) / gp.getTileSize();
            int goalRow = (gp.getPlayer().worldY + gp.getPlayer().solidArea.y) / gp.getTileSize();
            searchPath(goalCol, goalRow);

        }
        else
        {
            actionLockCounter++;

            if(actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100) + 1;  // pick up  a number from 1 to 100
                if(i <= 25)
                {
                    direction = "up";
                }
                if(i>25 && i <= 50)
                {
                    direction = "down";
                }
                if(i>50 && i <= 75)
                {
                    direction = "left";
                }
                if(i>75 && i <= 100)
                {
                    direction = "right";
                }
                actionLockCounter = 0; // reset
            }
        }
    }
    public void speak()
    {
        //ENTITY CLASS SPEAK()
        //Do this character specific stuff

        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;
        if(dialogues[dialogueSet][0] == null)
        {
            //dialogueSet = 0;
            dialogueSet--; //displays last set
        }

        /*if(gp.player.life < gp.player.maxLife/3)
        {
            dialogueSet = 1;
        }*/
        //follow me, come with me  or something like that
        //onPath = true;
    }
}
