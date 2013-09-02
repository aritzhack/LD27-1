package aritzh.ld27.entity;

import aritzh.ld27.level.Level;
import aritzh.ld27.render.Render;
import aritzh.ld27.render.Sprite;

import java.awt.Rectangle;

/**
 * Static Entity. (Movement is specified explicitly, does not move by itself)
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Entity {

    protected final int MAX_HEALTH;
    protected Level level;
    protected int posX, posY;
    protected int width, height;
    protected Sprite sprite;
    protected boolean noRender;
    protected int health;
    protected boolean dead;
    protected boolean god;
    protected int damageCooldown = 0;

    /**
     * Constructs an Entity at (0,0) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     */
    public Entity(Sprite sprite, Level level) {
        this(sprite, level, 0, 0, 10);
    }

    /**
     * Constructs an Entity at (posX, posY) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     * @param posX   The X coordinate at which the sprite will be drawn
     * @param posY   The Y coordinate at which the sprite will be drawn
     */
    public Entity(Sprite sprite, Level level, int posX, int posY, int maxHealth) {
        this.sprite = sprite;
        this.level = level;
        this.posX = posX;
        this.posY = posY;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.health = this.MAX_HEALTH = maxHealth;

    }

    /**
     * Returns the X coordinate of the entity
     *
     * @return the X coordinate of the entity
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Sets the X coordinate of the Entity
     *
     * @param posX the new X coordinate of the entity
     */
    public Entity setPosX(int posX) {
        this.posX = posX;
        return this;
    }

    /**
     * Returns the Y coordinate of the entity
     *
     * @return the Y coordinate of the entity
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     * Sets the Y coordinate of the Entity
     *
     * @param posY the new Y coordinate of the entity
     */
    public Entity setPosY(int posY) {
        this.posY = posY;
        return this;
    }

    /**
     * Returns the sprite this Entity represents
     *
     * @return the sprite this Entity represents
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**
     * Get the rectangle used to handle collision with walls
     * @return the rectangle representing the "feet" of the sprite
     */
    public Rectangle getCollisionBox() {
        return this.getBoundingBox();
    }

    /**
     * Get the rectangle used to handle collision with other entities
     * @return the rectangle representing the whole entity
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(this.posX, this.posY, this.width, this.height);
    }

    /**
     * Called in the game's update method.
     * By default does nothing
     */
    public void update(double delta) {
        Entity entity = this.level.collidesWithEntity(this);
        if (this.damageCooldown == 0 && entity != null && entity instanceof IEnemy && !this.dead && !entity.dead) {
            this.getHurtBy(entity);
            this.damageCooldown = 2;
        }
    }

    /**
     * Hurt an entity, from another
     *
     * @param entity The entity that hurts
     */
    public void getHurtBy(Entity entity) {
        this.setHealth(this.getHealth() - 1);
        System.out.println("Entity " + this + " was hurt by " + entity + ". Remaining health: " + this.getHealth());
    }

    public int getHealth() {
        return this.health;
    }

    public Entity setHealth(int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
            this.dead = true;
        }
        return this;
    }

    public void updatePerSecond() {
        if (this.damageCooldown > 0) this.damageCooldown--;
        if (this.damageCooldown < 0) this.damageCooldown = 0;
    }

    /**
     * Renders the Entity using the {@link Render} provided
     *
     * @param render The Render used to render the Entity
     */
    public void render(Render render) {
        if (!this.noRender) render.renderSprite(this.sprite, this.posX, this.posY);
    }

    public void toggleNoRender() {
        this.noRender = !this.noRender;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void toggleGod() {
        this.god = !this.god;
        if (this.god) this.health = this.MAX_HEALTH;
    }

    public Level getLevel() {
        return this.level;
    }
}
