package monster;

import Entity.Entity;
import data.Progress;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp; // cuz of different package
    public static final String monName = "Skeleton Lord";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        attack = 16;
        defense = 3;
        exp = 40;
        knockBackPower = 5;
        sleep = true;

        int size = gp.getTileSize() * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48 ;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage()
    {

        int i = 5;
        if(inRage == false)
        {
            up1 = setup("/monster/skeletonlord_up_1", gp.getTileSize() * i, gp.getTileSize() * i);
            up2 = setup("/monster/skeletonlord_up_2", gp.getTileSize() * i, gp.getTileSize() * i);
            down1 = setup("/monster/skeletonlord_down_1", gp.getTileSize() * i, gp.getTileSize() * i);
            down2 = setup("/monster/skeletonlord_down_2", gp.getTileSize() * i, gp.getTileSize() * i);
            left1 = setup("/monster/skeletonlord_left_1", gp.getTileSize() * i, gp.getTileSize() * i);
            left2 = setup("/monster/skeletonlord_left_2", gp.getTileSize() * i, gp.getTileSize() * i);
            right1 = setup("/monster/skeletonlord_right_1", gp.getTileSize() * i, gp.getTileSize() * i);
            right2 = setup("/monster/skeletonlord_right_2", gp.getTileSize() * i, gp.getTileSize() * i);
        }
        if(inRage == true)
        {
            up1 = setup("/monster/skeletonlord_phase2_up_1", gp.getTileSize() * i, gp.getTileSize() * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2", gp.getTileSize() * i, gp.getTileSize() * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1", gp.getTileSize() * i, gp.getTileSize() * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2", gp.getTileSize() * i, gp.getTileSize() * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1", gp.getTileSize() * i, gp.getTileSize() * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2", gp.getTileSize() * i, gp.getTileSize() * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1", gp.getTileSize() * i, gp.getTileSize() * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2", gp.getTileSize() * i, gp.getTileSize() * i);
        }
    }
    public void getAttackImage()
    {

        int i = 5;

        if(inRage == false)
        {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
        }
        if(inRage == true)
        {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i);
        }
    }
    public void setDialogue()
    {
        dialogues[0][0] = "No one can shall steal my treasure. \nGaze upon me, oh chosen one, and \nbear witness to your ultimate trial.";
        dialogues[0][1] = "Your fate is sealed. for you are but a mere \nmortal facing a powerful creature of the afterlife.";
        dialogues[0][2] = "Embrace your inevitable fate, and let your \njourney come to a thrilling end! \nPREPARE TO MEET YOUR DOOM!";

    }
    public void setAction()
    {

        if(inRage==false && life < maxLife/2)
        {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;

        }
        if(getTileDistance(gp.getPlayer()) < 10)
        {
            moveTowardPlayer(60);
        }
        else
        {
            getRandomDirection(120);
        }

        //Check if it is attacks
        if(attacking == false)
        {
            checkAttackOrNot(60, gp.getTileSize() *7, gp.getTileSize() *5); //Small rate = More agressive
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }
    public void checkDrop()
    {
        gp.setBossBattleOn(false);
        Progress.skeletonLordDefeated = true;

        //Restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        // Remove the iron doors
        for(int i = 0; i < gp.getObj()[1].length; i++)
        {
            if(gp.getObj()[gp.getCurrentMap()][i] != null && gp.getObj()[gp.getCurrentMap()][i].name.equals(OBJ_Door_Iron.objName))
            {
                gp.playSE(21);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
        }

        //CAST A DIE
        int i = new Random().nextInt(100)+1;

        //SET THE MONSTER DROP
        if(i < 50)
        {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75)
        {
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100)
        {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
