package aritzh.ld27.render;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static aritzh.ld27.render.SpriteSheet.SPRITE_SIZE;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Sprite {

    public static final Sprite floor = new Sprite(0, 0);
    public static final Sprite borderB = new Sprite(1, 0);
    public static final Sprite borderL = new Sprite(7, 0);
    public static final Sprite borderR = new Sprite(0, 1);
    public static final Sprite borderT = new Sprite(4, 0);
    public static final Sprite cornerLB = new Sprite(2, 0);
    public static final Sprite cornerRB = new Sprite(3, 0);
    public static final Sprite cornerLT = new Sprite(5, 0);
    public static final Sprite cornerRT = new Sprite(6, 0);
    public static final Sprite player = new Sprite(2, 1);
    private int width, height;
    private int[] pixels;

    public Sprite(int sx, int sy) {
        this(sx, sy, SpriteSheet.SPRITE_SIZE, SpriteSheet.SPRITE_SIZE);
    }

    public Sprite(int sx, int sy, int width, int height) {
        sx *= SPRITE_SIZE;
        sy *= SPRITE_SIZE;

        this.width = width;
        this.height = height;

        this.pixels = new int[width * height];

        BufferedImage image = SpriteSheet.SHEET.image.getSubimage(sx, sy, SPRITE_SIZE, SPRITE_SIZE);
        this.pixels = image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public Sprite(int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
        createSprite(color);
    }

    private void createSprite(int color) {
        Arrays.fill(pixels, color);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return Arrays.copyOf(this.pixels, this.pixels.length);
    }
}
