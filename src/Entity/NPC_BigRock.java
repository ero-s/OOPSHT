package Entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class NPC_BigRock extends Entity{


    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp)
    {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;


        dialogueSet = -1; //For first dialogueSet(= 0)

        getImage();
        setDialogue();
    }
    public void getImage()
    {
        up1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        up2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        down1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        down2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        left1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        left2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        right1 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
        right2 = setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize());
    }
    public void setDialogue()
    {
        dialogues[0][0] = "It's a giant rock.";
    }
    public void setAction()
    {

    }
    public void update()
    {

    }
    public void speak()
    {
        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;
        if(dialogues[dialogueSet][0] == null)
        {
            //dialogueSet = 0;
            dialogueSet--; //displays last set
        }
    }
    public void move(String d)
    {
        this.direction = d;

        checkCollision();

        if(collisionOn == false)
        {
            switch(direction)
            {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        detectPlate();
    }
    public void detectPlate()
    {
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        //Create a plate list
        for(int i = 0; i < gp.getiTile()[1].length; i++)
        {
            if(gp.getiTile()[gp.getCurrentMap()][i] != null && gp.getiTile()[gp.getCurrentMap()][i].name != null && gp.getiTile()[gp.getCurrentMap()][i].name.equals(IT_MetalPlate.itName))
            {
                plateList.add(gp.getiTile()[gp.getCurrentMap()][i]);
            }
        }

        //Create a rock list
        for(int i = 0; i < gp.getNpc()[1].length; i++)
        {
            if(gp.getNpc()[gp.getCurrentMap()][i] != null && gp.getNpc()[gp.getCurrentMap()][i].name.equals(NPC_BigRock.npcName))
            {
                rockList.add(gp.getNpc()[gp.getCurrentMap()][i]);
            }
        }

        int count = 0;

        //Scan the plate list
        for(int i = 0; i < plateList.size(); i++)
        {
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            if(distance < 8)
            {
                if(linkedEntity == null)
                {
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                }
            }
            else
            {
                if(linkedEntity == plateList.get(i)) //checking if linked before then u move rock again somewhere else
                {
                    linkedEntity = null;
                }
            }
        }

        //Scan the rock list
        for(int i = 0; i < rockList.size(); i++)
        {
            //Count the rock on the plate
            if(rockList.get(i).linkedEntity != null)
            {
                count++;
            }
        }

        //If all the rocks are on the plates, the iron door opens
        if(count == rockList.size())
        {
            for(int i = 0; i < gp.getObj()[1].length; i++)
            {
                if(gp.getObj()[gp.getCurrentMap()][i] != null && gp.getObj()[gp.getCurrentMap()][i].name.equals(OBJ_Door_Iron.objName))
                {
                    gp.getObj()[gp.getCurrentMap()][i] = null;
                    gp.playSE(21);
                }
            }
        }
    }
}
