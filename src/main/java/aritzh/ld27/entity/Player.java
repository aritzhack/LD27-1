package aritzh.ld27.entity;

import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;
import aritzh.ld27.util.Keyboard;

import java.awt.Rectangle;

/**
 * A player. A Mob controllable by the person playing
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Player extends Mob {

    private static final int WIDTH = 16, HEIGHT = 31;
    private static final Sprite SPRITE = new Sprite(2, 1, WIDTH, HEIGHT);
    private Keyboard input;

    /**
     * Constructs an Player at (0,0) with the specified Sprite
     *
     * @param level The level to which the player will be added
     * @param input The keyboard class that will be used to move the player
     */
    public Player(Level level, Keyboard input) {
        this(level, input, 0, 0, 10);
    }

    /**
     * Constructs an Player at (posX, posY) with the specified Sprite
     *
     * @param level The level to which the player will be added
     * @param input The keyboard class that will be used to move the player
     * @param posX  The X coordinate at which the sprite will be drawn
     * @param posY  The Y coordinate at which the sprite will be drawn
     */
    public Player(Level level, Keyboard input, int posX, int posY, int maxHealth) {
        super(Player.SPRITE, level, posX, posY, maxHealth);
        this.input = input;
        this.speed = 1.5;
    }

    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(this.posX + 6, this.posY + 27, this.width - 11, 2);
    }

    @Override
    public Entity setHealth(int health) {
        if (!this.god) super.setHealth(health);
        return this;
    }

    @Override
    public void update(double delta) {

        if (this.input.isDown() && !this.input.isUp()) this.velY = 1;
        else if (this.input.isUp() && !this.input.isDown()) this.velY = -1;
        else this.velY = 0;

        if (this.input.isLeft() && !this.input.isRight()) this.velX = -1;
        else if (this.input.isRight() && !this.input.isLeft()) this.velX = 1;
        else this.velX = 0;

        super.update(delta);
    }
}
