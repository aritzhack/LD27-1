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
    protected boolean noclip = false;

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
    public Mob(Sprite sprite, Level level, int posX, int posY, int maxHealth) {
        super(sprite, level, posX, posY, maxHealth);
    }

    /**
     * Returns the horizontal velocity
     *
     * @return the horizontal velocity
     */
    public int getVelX() {
        return this.velX;
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
        return this.velY;
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
        this.checkCollision(1, 0);
        this.checkCollision(0, 1);
    }

    protected void checkCollision(int x, int y) {
        int deltaX = this.velX * x;
        int deltaY = this.velY * y;

        Rectangle r = this.getCollisionBox();
        r.translate(deltaX, deltaY);

        if (!this.level.collides(r) || this.noclip) {
            this.posX += deltaX;
            this.posY += deltaY;
        }
    }

    public void toggleNoClip() {
        this.noclip = !this.noclip;
    }
}
