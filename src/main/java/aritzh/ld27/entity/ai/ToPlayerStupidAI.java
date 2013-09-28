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

        m.setVelX(p.getPosX() - m.getPosX());
        m.setVelY(p.getPosY() - m.getPosY());
    }
}
