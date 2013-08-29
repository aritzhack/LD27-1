package aritzh.ld27.render;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static aritzh.ld27.render.SpriteSheet.SPRITE_SIZE;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Sprite {

    private int width, height;
    private int[] pixels;

    public static Sprite floor;

    public static Sprite borderB;
    public static Sprite borderL;
    public static Sprite borderR;
    public static Sprite borderT;

    public static Sprite cornerLB;
    public static Sprite cornerRB;
    public static Sprite cornerLT;
    public static Sprite cornerRT;

    public static Sprite player;

    public Sprite(int sx, int sy) {
        sx *= SPRITE_SIZE;
        sy *= SPRITE_SIZE;

        this.width = SPRITE_SIZE;
        this.height = SPRITE_SIZE;

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
        return pixels;
    }

    public static void init() {
        Sprite.floor = new Sprite(0, 0);

        Sprite.borderB = new Sprite(1, 0);
        Sprite.cornerLB = new Sprite(2, 0);
        Sprite.cornerRB = new Sprite(3, 0);

        Sprite.borderT = new Sprite(4, 0);
        Sprite.cornerLT = new Sprite(5, 0);
        Sprite.cornerRT = new Sprite(6, 0);

        Sprite.borderL = new Sprite(7, 0);
        Sprite.borderR = new Sprite(0, 1);

        Sprite.player = new Sprite(1, 1);
    }

}
