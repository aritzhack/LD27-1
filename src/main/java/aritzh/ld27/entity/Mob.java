package aritzh.ld27.entity;

import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;

import java.awt.Rectangle;

/**
 * Mobile entity, has speed, therefore moving by itself
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Mob extends Entity {

    protected int velX, velY;

    /**
     * Constructs an Mob at (0,0) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     */
    public Mob(Sprite sprite, Level level) {
        super(sprite, level);
    }

    /**
     * Constructs an Mob at (posX, posY) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     * @param posX   The X coordinate at which the sprite will be drawn
     * @param posY   The Y coordinate at which the sprite will be drawn
     */
    public Mob(Sprite sprite, Level level, int posX, int posY) {
        super(sprite, level, posX, posY);
    }

    /**
     * Returns the horizontal velocity
     *
     * @return the horizontal velocity
     */
    public int getVelX() {
        return velX;
    }

    /**
     * Sets the horizontal velocity
     *
     * @param velX The new horizontal velocity
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }

    /**
     * Returns the vertical velocity
     *
     * @return the vertical velocity
     */
    public int getVelY() {
        return velY;
    }

    /**
     * Sets the vertical velocity
     *
     * @param velY The new vertical velocity
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }

    /**
     * Called in the game's update method.
     * By default does nothing
     */
    @Override
    public void update() {
        super.update();
        checkCollision(1, 0);
        checkCollision(0, 1);
    }

    private void checkCollision(int x, int y) {
        int newPosX = this.posX + velX * x;
        int newPosY = this.posY + velY * y;

        Rectangle r = new Rectangle(newPosX, newPosY, this.sprite.getWidth(), this.sprite.getHeight());

        if (!this.level.collides(r)) {
            this.posX = newPosX;
            this.posY = newPosY;
        }
    }
}
