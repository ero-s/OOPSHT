package tiles_interactive;
import Entity.Entity;
import main.GamePanel;

import java.awt.*;

public class interactiveTile extends Entity
{
    GamePanel gp;
    public boolean destructible = false;

    public interactiveTile(GamePanel gp, int col, int row)
    {
        super(gp);
        this.gp = gp;
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
        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

        if(worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY)
        {
            g2.drawImage(down1, screenX, screenY, null);
        }
    }
}
