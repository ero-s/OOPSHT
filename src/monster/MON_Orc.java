package monster;

import Entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp; // cuz of different package
    public MON_Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 8;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }

    public void getImage()
    {
        up1 = setup("/monster/orc_up_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/monster/orc_up_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/monster/orc_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/monster/orc_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/monster/orc_left_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/monster/orc_left_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/monster/orc_right_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/monster/orc_right_2", gp.getTileSize(), gp.getTileSize());
    }
    public void getAttackImage()
    {
        attackUp1 = setup("/monster/orc_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2);
        attackUp2 = setup("/monster/orc_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2);
        attackDown1 = setup("/monster/orc_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2);
        attackDown2 = setup("/monster/orc_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2);
        attackLeft1 = setup("/monster/orc_attack_left_1", gp.getTileSize() * 2, gp.getTileSize());
        attackLeft2 = setup("/monster/orc_attack_left_2", gp.getTileSize() * 2, gp.getTileSize());
        attackRight1 = setup("/monster/orc_attack_right_1", gp.getTileSize() * 2, gp.getTileSize());
        attackRight2 = setup("/monster/orc_attack_right_2", gp.getTileSize() * 2, gp.getTileSize());
    }
    public void setAction()
    {
        if(onPath == true)
        {
            //Check if it stops chasing
            checkStopChasingOrNot(gp.getPlayer(),15,100);

            //Search the direction to go
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
        }
        else
        {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);

            //Get a random direction
            getRandomDirection(120);
        }

        //Check if it is attacks
        if(attacking == false)
        {
            checkAttackOrNot(30, gp.getTileSize() *4, gp.getTileSize()); //Small rate = More agressive
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true; // gets aggro
    }
    public void checkDrop()
    {
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
