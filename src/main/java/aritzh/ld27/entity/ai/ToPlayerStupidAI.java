package aritzh.ld27.entity.ai;

import aritzh.ld27.entity.Mob;
import aritzh.ld27.entity.Player;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ToPlayerStupidAI implements IAI {
    @Override
    public void apply(Mob m) {
        Player p = m.getLevel().getPlayer();
        int vX = m.getPosX() < p.getPosX() ? 1 : -1;
        int vY = m.getPosY() < p.getPosY() ? 1 : -1;

        m.setVelX(vX);
        m.setVelY(vY);
    }
}
