package aritzh.ld27.level;

import aritzh.ld27.render.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Tile {

    public static final Map<Integer, Tile> idToTile = new HashMap<Integer, Tile>();
    public static final Tile TILE_FLOOR = new Tile(Sprite.floor, 0, false);
    public static final Tile TILE_BORDER_B = new Tile(Sprite.borderB, 1, true);
    public static final Tile TILE_CORNER_LB = new Tile(Sprite.cornerLB, 2, true);
    public static final Tile TILE_CORNER_RB = new Tile(Sprite.cornerRB, 3, true);
    public static final Tile TILE_BORDER_T = new Tile(Sprite.borderT, 4, true);
    public static final Tile TILE_CORNER_LT = new Tile(Sprite.cornerLT, 5, true);
    public static final Tile TILE_CORNER_RT = new Tile(Sprite.cornerRT, 6, true);
    public static final Tile TILE_BORDER_L = new Tile(Sprite.borderL, 7, true);
    public static final Tile TILE_BORDER_R = new Tile(Sprite.borderR, 8, true);
    public static final Tile TILE_CLOSED = new Tile(Sprite.floor, 9, true);
    private final Sprite sprite;
    private int id;
    private boolean solid;

    public Tile(Sprite sprite, int id, boolean isSolid) {
        this.sprite = sprite;
        this.id = id;
        this.solid = isSolid;

        if (idToTile.containsKey(id)) throw new IllegalArgumentException("Chosen ID " + id + " was already used");
        idToTile.put(id, this);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    public boolean isSolid() {
        return this.solid;
    }

    public int getID() {
        return this.id;
    }
}
