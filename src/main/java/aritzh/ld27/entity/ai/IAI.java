package aritzh.ld27.entity.ai;

import aritzh.ld27.entity.Mob;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public interface IAI {

    public static final IAI RANDOM_MOVEMENT = new RandomMovementAI();
    public static final IAI TO_PLAYER_STUPID = new ToPlayerStupidAI();

    public void apply(Mob m);
}
