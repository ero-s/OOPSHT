package tiles_interactive;
import Entity.Entity;
import main.panel;

import java.awt.*;

public class interactiveTile extends Entity
{
    panel panel;
    public boolean destructible = false;

    public interactiveTile(main.panel panel, int col, int row)
    {
        super(panel);
        this.panel = panel;
    }
    public boolean isCorrectWeapon(Entity entity) {
        boolean isCorrectWeapon = false;
        return isCorrectWeapon;
    }
    public void playSE() {

    }
    public interactiveTile getDestroyedForm() {
        interactiveTile tile = null;
        return tile;
    }
    public void update() {
        if(invincible == true) {
            invincibleCounter++;
            if(invincible == true) {
                invincibleCounter++;
                if(invincibleCounter > 20) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }
    public void draw (Graphics2D g2) {
        int screenX = worldX - panel.player.worldX + panel.player.screenX;
        int screenY = worldY - panel.player.worldY + panel.player.screenY;

        if(worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY - panel.tileSize < panel.player.worldY + panel.player.screenY)
        {
            g2.drawImage(down1, screenX, screenY, null);
        }
    }
}
