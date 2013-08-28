package aritzh.ld27.render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render {
    private int width;
    private int height;
    private int[] pixels;

    private static int[] background;
    private static int[] backgroundFull;

    static{
        try {
            BufferedImage bgImageFull = ImageIO.read(Render.class.getResource("/textures/background2.png"));
            backgroundFull = new int[bgImageFull.getWidth() * bgImageFull.getHeight()];
            backgroundFull = bgImageFull.getRGB(0, 0, bgImageFull.getWidth(), bgImageFull.getHeight(), Render.backgroundFull, 0, bgImageFull.getWidth());

            BufferedImage bgImage = ImageIO.read(Render.class.getResource("/textures/background.png"));
            background = new int[bgImage.getWidth() * bgImage.getHeight()];
            background = bgImage.getRGB(0, 0, bgImage.getWidth(), bgImage.getHeight(), Render.background, 0, bgImage.getWidth());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Render(int width, int height, int[] pixels, int scale) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;

        if(pixels.length < width*height) throw new IllegalArgumentException("The given array doesn't hold an image as big as " + width + "x" + height);
    }

    public void renderBackgroundFull() {
        drawPixels(backgroundFull, 0, 0, width, height);
    }

    public void renderBackground() {
        drawPixels(background, 0, 0, width, height);
    }

    public void clear() {
        Arrays.fill(pixels, 0x00000000);
    }

    public void drawPixels(int[] pixels, int xp, int yp, int width, int height) {
        if (pixels.length < width * height)
            throw new ArrayIndexOutOfBoundsException("The given array doesn't hold a image as big as " + width + "x" + height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.pixels[(xp + x) + (yp + y) * this.width] = pixels[x + y * width];
            }
        }
    }

    public void drawStringCenteredAt(Graphics g, String text, int x, int y, boolean shadow) {
        FontMetrics m = g.getFontMetrics();

        int xx = (x - (m.stringWidth(text) / 2));
        int yy = m.getAscent() + y - (m.getAscent() + m.getDescent()) / 2;

        if (shadow) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.drawString(text, xx + 3, yy + 3);
            g.setColor(c);
        }
        g.drawString(text, xx, yy);

    }

    public void drawStringAt(Graphics g, String text, int x, int y) {
        FontMetrics m = g.getFontMetrics();
        g.drawString(text, x, y + m.getAscent() - m.getDescent());
    }

    public void renderSprite(Sprite s, int xp, int yp) {
        this.drawPixels(s.getPixels(), xp, yp, s.getWidth(), s.getHeight());
    }

    public void setPixel(int x, int y, int color) {
        this.pixels[x + y * width] = color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
