package aritzh.ld27.render;

import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Sprite {
    public static final Sprite floor = SpriteSheet.SHEET.getSprite(0, 0);
    public static final Sprite borderB = SpriteSheet.SHEET.getSprite(1, 0);
    public static final Sprite borderL = SpriteSheet.SHEET.getSprite(7, 0);
    public static final Sprite borderR = SpriteSheet.SHEET.getSprite(0, 1);
    public static final Sprite borderT = SpriteSheet.SHEET.getSprite(4, 0);
    public static final Sprite cornerLB = SpriteSheet.SHEET.getSprite(2, 0);
    public static final Sprite cornerRB = SpriteSheet.SHEET.getSprite(3, 0);
    public static final Sprite cornerLT = SpriteSheet.SHEET.getSprite(5, 0);
    public static final Sprite cornerRT = SpriteSheet.SHEET.getSprite(6, 0);

    private final int width;

    private final int height;
    private final int[] pixels;

    public Sprite(int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.pixels = new int[this.width * this.height];
        Arrays.fill(this.pixels, color);
    }

    public Sprite(int width, int height, int[] colors) {
        this.width = width;
        this.height = height;
        this.pixels = colors;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getPixels() {
        return this.pixels;
    }

    public int getWidth() {
        return this.width;
    }
}
