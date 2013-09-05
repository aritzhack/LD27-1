package aritzh.ld27.entity;

import aritzh.ld27.entity.ai.IAI;
import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Mobile entity, has speed, therefore moving by itself
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Mob extends Entity {

    protected final List<IAI> ais = new ArrayList<IAI>();
    protected int velX, velY;
    protected double speed = 1.0;
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
    public void update(double delta) {
        super.update(delta);
        for (IAI ai : this.ais) {
            ai.apply(this);
        }
        this.checkCollision(1, 0, delta);
        this.checkCollision(0, 1, delta);
    }

    protected void checkCollision(int x, int y, double delta) {
        int deltaX = (int) Math.round(this.velX * x * delta * this.speed);
        int deltaY = (int) Math.round(this.velY * y * delta * this.speed);

        if (this.posX + deltaX < 0 || this.posY + deltaY < 0) return;

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
