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
    protected static final SpriteSheet SHEET = new SpriteSheet("/textures/sheet.png");
    public BufferedImage image;
    private int width, height;

    public SpriteSheet(String path) {
        try {
            this.image = ImageIO.read(this.getClass().getResource(path));
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
