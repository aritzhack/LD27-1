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

    @Override
    public void apply(Mob m) {
        Profiler.getInstance().startSection("A* AI");
        try {
            this.level = m.getLevel();
            this.me = this.level.getTile(m);
            this.player = this.level.getPlayer();
            this.p = this.level.getTile(this.player);

            if (this.calculatePath() == null) {
//                IAI.TO_PLAYER_STUPID.apply(m);
//                m.getLevel().getGame().setAIMode(false);
            } else {
                Node next = this.path.get(1);

                final double targetX = (next.getX() + .5) * SpriteSheet.SPRITE_SIZE;
                final double targetY = (next.getY() + .5) * SpriteSheet.SPRITE_SIZE;

                System.out.println("Center of target node: (" + targetX + ", " + targetY + ")");

                m.setVelX((int) (targetX - m.getPosX() - m.getSprite().getWidth() / 2));
                m.setVelY((int) (targetY - m.getPosY() - m.getSprite().getHeight() / 2));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            m.setVelX(0);
            m.setVelY(0);
        }
        Profiler.getInstance().endSection();
    }

    private List<Node> calculatePath() {
//        if (this.map == null)
//        else this.map.update(this.level.getNodes(), this.me.x, this.me.y, this.p.x, this.p.y);
        return this.path = new NodeMap(this.level.getNodes(), this.me.x, this.me.y, this.p.x, this.p.y, true).calculatePath();
    }
}
