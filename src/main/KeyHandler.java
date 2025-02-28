package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public String godModeOn;
    public boolean showDebugText;
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    // debug
    boolean checkDrawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode(); // returns the integer keycode associated with the key that was pressed

        // title state
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }

        // play state
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        // pause state
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // dialogue state
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        // option state
        else if (gp.gameState == gp.characterState) {
            optionsState(code);
        }
        // character state
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }
    }
    public void titleState(int code) {
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                // add later (this one is for the load game)
            }
            if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState (int code) {
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;
        }
        // debug
        if(code == KeyEvent.VK_T){
            if(checkDrawTime == false) {
                checkDrawTime = true;
            }
            else if(checkDrawTime) {
                checkDrawTime = false;
            }
        }
    }
    public void pauseState (int code) {
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState (int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState (int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W) {
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }
    public void optionsState(int code)
    {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0: maxCommandNum = 4; break;
            case 3: maxCommandNum = 1; break;
        }
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    gp.sound.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.SE.volumeScale > 0) {
                    gp.SE.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    gp.sound.checkVolume();
                    gp.playSE(9);
                }
            }
            if(gp.ui.commandNum == 2 && gp.SE.volumeScale < 5) {
                gp.SE.volumeScale++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
        upPressed = false;
    }
        if(code == KeyEvent.VK_A){
        leftPressed = false;
    }
        if(code == KeyEvent.VK_S){
        downPressed = false;
    }
        if(code == KeyEvent.VK_D){
        rightPressed = false;
    }
        if(code == KeyEvent.VK_F){
        shotKeyPressed = false;
        }
    }
}

