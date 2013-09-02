package aritzh.ld27.level;

import aritzh.ld27.entity.AlienEnemy;
import aritzh.ld27.entity.Entity;
import aritzh.ld27.entity.Player;
import aritzh.ld27.render.Render;
import aritzh.ld27.render.SpriteSheet;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Level {

    private static Level LEVEL_1;
    private final int width;
    private final int height;
    public List<Entity> entities = new ArrayList<Entity>();
    private Tile[][] tiles;
    private int[][] nodes;
    private Player player;

    public Level(String path) throws IOException {
        this(Level.class.getResourceAsStream(path));
    }

    public Level(InputStream iStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, Charset.defaultCharset()));

        List<String> lines = new ArrayList<String>();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] firstLineTokens = lines.get(0).split(":");
        this.width = Integer.parseInt(firstLineTokens[0]);
        this.height = Integer.parseInt(firstLineTokens[1]);

        this.tiles = new Tile[this.width][this.height];

        for (int lineN = 0; lineN < lines.size() - 1; lineN++) {
            String line = lines.get(lineN + 1);
            String[] tiles = line.split(",");
            for (int tileN = 0; tileN < tiles.length; tileN++) {
                Integer i = Integer.valueOf(tiles[tileN]);
                if (i == null) throw new IllegalArgumentException("File could not be parsed");
                if (!Tile.idToTile.containsKey(i))
                    throw new IllegalArgumentException("Tile ID " + i + " in line " + lineN + " isn't valid!");
                this.tiles[tileN][lineN] = Tile.idToTile.get(i);
            }
        }

        reader.close();
        iStream.close();
    }

    public Level(File file) throws IOException {
        this(new FileInputStream(file));
    }

    public static void init() {
        try {
            Level.LEVEL_1 = new Level("/levels/level1.lvl");
            Entity e = new AlienEnemy(Level.LEVEL_1).setPosX(50);
            Level.LEVEL_1.addEntity(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public static Level getLEVEL_1() {
        return LEVEL_1;
    }

    public void render(Render render) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                render.renderSprite(this.tiles[x][y].getSprite(), x * SpriteSheet.SPRITE_SIZE, y * SpriteSheet.SPRITE_SIZE);
            }
        }
        this.player.render(render);

        for (Entity e : this.entities) {
            e.render(render);
        }
    }

    public void update(double delta) {
        Iterator<Entity> iterator = this.entities.iterator();
        while (iterator.hasNext()) {
            Entity e = iterator.next();
            if (e.isDead() && !(e instanceof Player)) iterator.remove();
            else if (e.isDead()) System.out.println("You DIED!");
            else e.update(delta);
        }
    }

    public void updatePS() {
        for (Entity e : this.entities) {
            e.updatePerSecond();
        }
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return Arrays.deepToString(this.tiles);
    }

    public boolean collides(Rectangle r) {
        if(r.getMaxX()>= this.width*SpriteSheet.SPRITE_SIZE || r.getMaxY() >= this.height*SpriteSheet.SPRITE_SIZE) return true;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.tiles[x][y].isSolid()) {
                    if (r.intersects(new Rectangle(x * SpriteSheet.SPRITE_SIZE, y * SpriteSheet.SPRITE_SIZE, SpriteSheet.SPRITE_SIZE, SpriteSheet.SPRITE_SIZE)))
                        return true;
                }
            }
        }
        return false;
    }

    public void clicked(MouseEvent e) {
        int x = (int) Math.floor((double) e.getX() / ((double) SpriteSheet.SPRITE_SIZE * 2));
        int y = (int) Math.floor((double) e.getY() / ((double) SpriteSheet.SPRITE_SIZE * 2));
        if (x > this.width - 1 || y > this.height - 1) System.out.println("Clicked out of the world");
        else System.out.println("Clicked tile at (" + x + ", " + y + ") with ID: " + this.tiles[x][y].getID());
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        if (this.entities.contains(this.player)) this.entities.remove(this.player);
        this.player = player;
        this.entities.add(this.player);
    }

    public Entity collidesWithEntity(Entity entity) {
        for (Entity e : this.entities) {
            if (e != entity && e.getBoundingBox().intersects(entity.getBoundingBox())) return e;
        }
        return null;
    }

    private void recalculateNodes() {
        this.nodes = new int[this.width][this.height];
        for(int y = 0; y<this.height; y++){
            for(int x = 0; x<this.width; x++){
                this.nodes[x][y] = this.tiles[x][y].isSolid()?1:0;
            }
        }
    }

    public Point getTile(Entity e){
        int x = (int) Math.floor((double) (e.getPosX() + e.getSprite().getWidth()/2) / (double) SpriteSheet.SPRITE_SIZE);
        int y = (int) Math.floor((double) (e.getPosY()  + e.getSprite().getHeight()/2)/ (double) SpriteSheet.SPRITE_SIZE);
        return new Point(x, y);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[][] getNodes() {
        if(this.nodes == null) this.recalculateNodes();
        return this.nodes;
    }
}
