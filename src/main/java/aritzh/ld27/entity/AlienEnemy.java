package aritzh.ld27.entity;

import aritzh.ld27.entity.ai.IAI;
import aritzh.ld27.level.Level;
import aritzh.ld27.render.Sprite;
import aritzh.ld27.render.SpriteSheet;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AlienEnemy extends Mob implements IEnemy {

    private static final int WIDTH = 13, HEIGHT = 10;

    private static final Sprite SPRITE = SpriteSheet.SHEET.getSprite(1, 1, WIDTH, HEIGHT);

    /**
     * Constructs an AlienEnemy at (0,0) with the specified Sprite
     */
    public AlienEnemy(Level level) {
        super(AlienEnemy.SPRITE, level);
        this.ais.add(IAI.TO_PLAYER_INTELLIGENT);
    }


}
