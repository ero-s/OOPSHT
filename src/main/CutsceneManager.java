package main;

import Entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

import java.awt.*;

public class CutsceneManager {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    //Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;


    public CutsceneManager(GamePanel gp)
    {
        this.gp = gp;
        endCredit = "Developed by\n"
                + "André Oliveira"
                + "\n\n\n\n\n\n\n\n\n\n\n"
                + "Special Thanks\n\n"
                + "Ryi Snow\n\n"
                + "Check his tutorials from RyiSnow YouTube Channel!\n"
                + "\n\n\n\n\n\n\n"
                + "Thank you for playing!";
    }
    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        switch(sceneNum)
        {
            case skeletonLord: scene_skeletonLord(); break;
            case ending: scene_ending(); break;
        }

    }
    public void scene_skeletonLord()
    {
        if(scenePhase == 0)
        {
            gp.setBossBattleOn(true);

            //Shut the iron door to trap player
            for(int i = 0; i < gp.getObj()[1].length; i++) //Search a vacant slot for the iron door
            {
                if(gp.getObj()[gp.getCurrentMap()][i] == null)
                {
                    gp.getObj()[gp.getCurrentMap()][i] = new OBJ_Door_Iron(gp);
                    gp.getObj()[gp.getCurrentMap()][i].worldX = gp.getTileSize() * 25;
                    gp.getObj()[gp.getCurrentMap()][i].worldY = gp.getTileSize() * 28;
                    gp.getObj()[gp.getCurrentMap()][i].temp = true; //only need during the boss fight
                    gp.playSE(21);
                    break;
                }
            }
            for(int i = 0; i < gp.getNpc()[1].length; i++) //Search a vacant slot for the player dummy
            {
                if(gp.getNpc()[gp.getCurrentMap()][i] == null)
                {
                    gp.getNpc()[gp.getCurrentMap()][i] = new PlayerDummy(gp);
                    gp.getNpc()[gp.getCurrentMap()][i].worldX = gp.getPlayer().worldX;
                    gp.getNpc()[gp.getCurrentMap()][i].worldY = gp.getPlayer().worldY;
                    gp.getNpc()[gp.getCurrentMap()][i].direction = gp.getPlayer().direction;
                    break;
                }
            }
            gp.getPlayer().drawing = false;

            scenePhase++;
        }
        if(scenePhase == 1)
        {
            gp.getPlayer().worldY -= 2;
            if(gp.getPlayer().worldY < gp.getTileSize() * 16) //stop camera
            {
                scenePhase++;
            }
        }
        if(scenePhase == 2)
        {
            //Search for the boss
            for(int i = 0; i < gp.getMonster()[1].length; i++)
            {
                if(gp.getMonster()[gp.getCurrentMap()][i] != null && gp.getMonster()[gp.getCurrentMap()][i].name.equals(MON_SkeletonLord.monName))
                {
                    gp.getMonster()[gp.getCurrentMap()][i].sleep = false;
                    gp.getUi().npc = gp.getMonster()[gp.getCurrentMap()][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3)
        {
            // The boss speaks
            gp.getUi().drawDialogueScreen(); // increases scenePhase

        }
        if(scenePhase == 4)
        {
            // Return to the player

            //Search for the dummy
            for(int i = 0; i < gp.getNpc()[1].length; i++)
            {
                if(gp.getNpc()[gp.getCurrentMap()][i] != null && gp.getNpc()[gp.getCurrentMap()][i].name.equals(PlayerDummy.npcName))
                {
                    //Restore the player position
                    gp.getPlayer().worldX = gp.getNpc()[gp.getCurrentMap()][i].worldX;
                    gp.getPlayer().worldY = gp.getNpc()[gp.getCurrentMap()][i].worldY;
                    gp.getPlayer().direction = gp.getNpc()[gp.getCurrentMap()][i].direction;
                    //Delete the dummy
                    gp.getNpc()[gp.getCurrentMap()][i] = null;
                    break;
                }
            }
            //Start drawing the player
            gp.getPlayer().drawing = true;

            //Reset
            sceneNum = NA;
            scenePhase = 0;
            gp.setGameState(gp.getPlayState());

            //Change the music
            gp.stopMusic();
            gp.playMusic(22);
        }
    }
    public void scene_ending()
    {
        if(scenePhase == 0)
        {
            gp.stopMusic();
            gp.getUi().npc = new OBJ_BlueHeart(gp);
            scenePhase++;
        }
        if(scenePhase == 1)
        {
            //Display dialogues
            gp.getUi().drawDialogueScreen();
        }
        if(scenePhase == 2)
        {
            //Play the fanfare
            gp.playSE(4);
            scenePhase++;
        }
        if(scenePhase == 3)
        {
            //Wait until the sound effect ends
            if(counterReached(300) == true) // 5 sec delay
            {
                scenePhase++;
            }
        }
        if(scenePhase == 4)
        {
            //The screen gets darker
            /*alpha += 0.005f;  // after 200 frames alpha becomes 1
            if(alpha > 1f)
            {
                alpha = 1f;
            }*/
            alpha = graduallyAlpha(alpha, 0.005f);

            drawBlackBackground(alpha);

            if(alpha == 1f)
            {
                alpha = 0;
                scenePhase++;
            }
        }
        if(scenePhase == 5)
        {
            drawBlackBackground(1f);

            //Show message gradually
           /* alpha += 0.005f;  // after 200 frames alpha becomes 1
            if(alpha > 1f)
            {
                alpha = 1f;
            }*/
            alpha = graduallyAlpha(alpha, 0.005f);

            String text = "After the fierce battle with the Skeleton Lord,\n"
                    + "the warrior finally found the legendary treasure.\n"
                    + "But this is not the end of his journey.\n"
                    + "The adventure has just begun.";

            drawString(alpha, 38f, 200, text, 70);

            if(counterReached(600) == true && alpha == 1f)
            {
                gp.playMusic(0);
                alpha = 0;
                scenePhase++;
            }
        }
        if(scenePhase == 6)
        {
            drawBlackBackground(1f);

            alpha = graduallyAlpha(alpha, 0.01f);

            drawString(alpha,120f, gp.getScreenHeight() /2, "2D Adventure", 40);

            if(counterReached(480) == true && alpha == 1f)
            {
                scenePhase++;
                alpha = 0;
            }
        }
        if(scenePhase == 7)
        {
            //First Credits
            drawBlackBackground(1f);

            alpha = graduallyAlpha(alpha, 0.01f);

            y = gp.getScreenHeight() /2;

            drawString(alpha, 38f,  y, endCredit, 40);

            if(counterReached(240) == true && alpha == 1f)
            {
                scenePhase++;
                alpha = 0;
            }
        }
        if(scenePhase == 8)
        {
            drawBlackBackground(1f);

            //Scrolling the credit
            y--;
            drawString(1f, 38f,  y, endCredit, 40);
            if(counterReached(1320) == true) //22sec
            {
                //Reset
                sceneNum = NA;
                scenePhase = 0;

                //Transition to game again
                gp.setGameState(gp.getPlayState());
                gp.resetGame(false);

            }
        }
    }

    public boolean counterReached(int target)
    {
        boolean counterReached = false;
        counter++;
        if(counter > target)
        {
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }
    public void drawBlackBackground(float alpha)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.getScreenWidth(), gp.getScreenHeight());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line: text.split("\n"))
        {
            int x = gp.getUi().getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public float graduallyAlpha(float alpha, float grade)
    {
        alpha += grade;  // after 200 frames alpha becomes 1
        if(alpha > 1f)
        {
            alpha = 1f;
        }
        return alpha;
    }
}
