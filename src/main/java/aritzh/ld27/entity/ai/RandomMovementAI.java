package aritzh.ld27.entity.ai;

import aritzh.ld27.entity.Mob;

import java.util.Random;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RandomMovementAI implements IAI {

    Random random = new Random();

    @Override
    public void apply(Mob m) {
        int x = this.random.nextInt(3) - 1;
        int y = this.random.nextInt(3) - 1;

        m.setVelX(x);
        m.setVelY(y);
    }
}
