package monster;

import Entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {

    GamePanel gp; // cuz of different package
    public MON_RedSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Red Slime";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 4;
        defense = 0;
        exp = 4;
        projectile = new OBJ_Rock(gp);


        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage()
    {
        up1 = setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize());
    }
    public void setAction()
    {
        if(onPath == true)
        {

            //Check if it stops chasing
            checkStopChasingOrNot(gp.getPlayer(),15,100);

            //Search the direction to go
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));

            //Check if it shoots a projectile
            checkShootOrNot(200, 30);
        }
        else
        {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);

            //Get a random direction
            getRandomDirection(120);
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
