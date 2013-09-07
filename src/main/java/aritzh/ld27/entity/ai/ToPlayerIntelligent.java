package aritzh.ld27.entity.ai;

import aritzh.ld27.entity.Mob;
import aritzh.ld27.entity.Player;
import aritzh.ld27.entity.ai.astar.Node;
import aritzh.ld27.entity.ai.astar.NodeMap;
import aritzh.ld27.level.Level;
import aritzh.ld27.render.SpriteSheet;
import aritzh.ld27.util.Profiler;

import java.awt.Point;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ToPlayerIntelligent implements IAI {

    List<Node> path;
    Level level;
    Point me, p;
    Player player;

    NodeMap map;

    @Override
    public void apply(Mob m) {
        Profiler.getInstance().startSection("A* AI");
        try {
            this.level = m.getLevel();
            this.me = this.level.getTile(m);
            this.player = this.level.getPlayer();
            this.p = this.level.getTile(this.player);

            if (this.p.equals(this.me)) {
                m.setVelX(Integer.signum(this.player.getPosX() - m.getPosX()));
                m.setVelY(Integer.signum(this.player.getPosY() - m.getPosY()));
            } else if (this.calculatePath() != null && this.path.size() >= 2) {
                Node next = this.path.get(1);
                m.setVelX(Integer.signum((int) (((next.getX() + .5) * SpriteSheet.SPRITE_SIZE) - m.getPosX() - m.getSprite().getWidth() / 2)));
                m.setVelY(Integer.signum((int) (((next.getY() + .5) * SpriteSheet.SPRITE_SIZE) - m.getPosY() - m.getSprite().getHeight() / 2)));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            m.setVelX(0);
            m.setVelY(0);
        }
        Profiler.getInstance().endSection();
    }

    private List<Node> calculatePath() {
        if (this.map == null)
            this.map = new NodeMap(this.level.getNodes(), this.me.x, this.me.y, this.p.x, this.p.y, true);
        else this.map.update(this.level.getNodes(), this.me.x, this.me.y, this.p.x, this.p.y);
        return this.path = this.map.calculatePath();
    }
}
