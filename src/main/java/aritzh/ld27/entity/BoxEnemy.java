package aritzh.ld27.entity;

import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BoxEnemy extends Mob implements IEnemy {

    /**
     * Constructs an Mob at (0,0) with the specified Sprite
     *
     * @param sprite The sprite that will be drawn when rendering
     */
    public BoxEnemy(Sprite sprite, Level level) {
        super(sprite, level);
    }


}
