package Entity;

import main.CollisionChecker;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Entity
{
    public GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public CollisionChecker checker;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0 , 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public boolean temp;
    public ActionMap inventory;
    String dialogues[] = new String[20];

    // state
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;


    // counter
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int dyingCounter = 0;
    int hpBarCounter = 0;

    // character attributes, status
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // item attributes
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;

    // type
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public int screenX;
    public int screenY;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }

    public void setAction() {}
    public void damageReaction() {}
    public void speak() {

    }
    public void use(Entity entity) {}
    public void checkDrop(){}
    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.getObj().length; i++) {
            if(gp.getObj()[gp.getCurrentMap()][i] == null) {
                gp.getObj()[gp.getCurrentMap()][i] = droppedItem;
                gp.getObj()[gp.getCurrentMap()][i].worldX = worldX; // dead monster's worldX
                gp.getObj()[gp.getCurrentMap()][i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife () {
        int MaxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.getParticleList().add(p1);
        gp.getParticleList().add(p2);
        gp.getParticleList().add(p3);
        gp.getParticleList().add(p4);

    }
    public void update() {

        setAction();

        collisionOn = false;
        gp.getChecker().checkTile(this);
        gp.getChecker().checkObject(this, false);
        gp.getChecker().checkEntity(this, new Entity[][]{gp.getNpc()});
        gp.getChecker().checkEntity(this, new Entity[][]{gp.getMonster()});
        gp.getChecker().checkEntity(this, new Entity[][]{gp.getiTile()});
        boolean contactPlayer = gp.getChecker().checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
        // if collision is false, player can move
        if(collisionOn == false) {

            switch(direction) {
                case "up": worldY -= speed;
                    break;
                case "down": worldY += speed;
                    break;
                case "left": worldX -= speed;
                    break;
                case "right": worldX += speed;
                    break;
            }
        }

        spriteCounter ++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1; // toggle spriteNum between 1 and 2
            spriteCounter = 1;
        }

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }
    public void damagePlayer(int attack) {
            if(gp.getPlayer().invincible == false) {
                // player can take damage
                gp.playSE(6);
                int damage = attack - gp.getPlayer().defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.getPlayer().life -= damage;
                gp.getPlayer().invincible = true;
            }
        }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

        if(worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().screenX &&
                worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().screenX &&
                worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().screenY &&
                worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().screenY)
        {
            switch(direction) {
                case "up":
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    break;
            }

            // monster hp bar
            if(type == 2 && hpBarOn == true) {
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12); // outline of the health bar

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 600) {    // 10 seconds until the health bar disappears
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if(invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F); // this adds a contrast to the character when hes during invincible mode
            }
            if(dying == true) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null);

            changeAlpha(g2, 1F);
        }
    }
    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5; // added this so I dont have to change every value manually

        if (dyingCounter <= i) {changeAlpha(g2, 0F);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1F);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0F);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1F);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0F);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1F);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0F);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1F);}
        if(dyingCounter > i*8) {
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage Image = null;

        try {
            Image = ImageIO.read(new FileInputStream(imagePath + ".png"));
            Image = uTool.scaleImage(Image, width, height);

        } catch(IOException e) {
            e.printStackTrace();
        }
        return Image;
    }

    public int getCurrentWeaponSlot() {
    }

    public int getCurrentShieldSlot() {
    }
}
