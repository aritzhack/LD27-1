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

    public static Sprite wall = new Sprite(0, 0);

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

}
