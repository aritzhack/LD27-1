package aritzh.ld27.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SpriteSheet {

    public static final int SPRITE_SIZE = 32;
    public static final SpriteSheet SHEET = new SpriteSheet("/textures/sheet.png");
    private final BufferedImage image;
    private int width, height;

    public SpriteSheet(String path) {
        try {
            this.image = ImageIO.read(this.getClass().getResource(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading sprite", e);
        }
        this.width = this.getImage().getWidth();
        this.height = this.getImage().getHeight();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Sprite getSprite(int x, int y) {
        return this.getSprite(x, y, SPRITE_SIZE, SPRITE_SIZE);
    }

    public Sprite getSprite(int x, int y, int width, int height) {
        int[] pixels = new int[width * height];
        this.image.getSubimage(x * SPRITE_SIZE, y * SPRITE_SIZE, width, height).getRGB(0, 0, width, height, pixels, 0, width);
        return new Sprite(width, height, pixels);
    }
}
