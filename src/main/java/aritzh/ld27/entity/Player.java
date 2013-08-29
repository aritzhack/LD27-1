package aritzh.ld27.entity;

import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;
import aritzh.ld27.util.Keyboard;

/**
 * A player. A Mob controllable by the person playing
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Player extends Mob {

    private Keyboard input;

    /**
     * Constructs an Player at (0,0) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     */
    public Player(Sprite sprite, Level level, Keyboard input) {
        super(sprite, level);
        this.input = input;
        level.setPlayer(this);
    }

    /**
     * Constructs an Player at (posX, posY) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     * @param posX   The X coordinate at which the sprite will be drawn
     * @param posY   The Y coordinate at which the sprite will be drawn
     */
    public Player(Sprite sprite, Level level, Keyboard input, int posX, int posY) {
        super(sprite, level, posX, posY);
        this.input = input;
    }

    @Override
    public void update() {

        if (input.isDown() && !input.isUp()) this.velY = 1;
        else if (input.isUp() && !input.isDown()) this.velY = -1;
        else this.velY = 0;

        if (input.isLeft() && !input.isRight()) this.velX = -1;
        else if (input.isRight() && !input.isLeft()) this.velX = 1;
        else this.velX = 0;

        super.update();
    }
}
