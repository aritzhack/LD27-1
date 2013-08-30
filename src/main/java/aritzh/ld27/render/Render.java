package aritzh.ld27.render;

import aritzh.ld27.util.ColorUtil;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render {
    private static int nWidth;
    private static int nHeight;
    private int width;
    private int height;
    private static int scale;

    private int[] pixels;
    private BufferedImage image;

    private static int[] background;
    private static int[] backgroundFull;

    public static Render fullRender;
    public static Render normalRender;

    public Render(int width, int height) {


        this.width = width;
        this.height = height;

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();

        if (pixels.length < width * height)
            throw new IllegalArgumentException("The given array doesn't hold an image as big as " + width + "x" + height);
    }

    public void renderBackground(boolean fullScreen) {
        drawPixels((fullScreen ? backgroundFull : background), 0, 0, width, height);
    }

    public void clear() {
        Arrays.fill(pixels, 0x00000000);
    }

    public void drawPixels(int[] pixels, int xp, int yp, int width, int height) {
        if (pixels.length < width * height)
            throw new ArrayIndexOutOfBoundsException("The given array doesn't hold a image as big as " + width + "x" + height);
        if (xp + width < 0 || yp + height < 0 || xp > this.width || yp > this.height) return;
        int maxX = Math.min(width, this.width - xp);
        int maxY = Math.min(height, this.height - yp);

        for (int y = 0; y < maxY; y++) {
            if (yp + y < 0) continue;
            for (int x = 0; x < maxX; x++) {
                if (xp + x < 0) continue;

                int index = (xp + x) + (yp + y) * this.width;
                int newPixel = pixels[x + y * width];
                int oldPixel = this.pixels[index];
                if (newPixel >>> 24 == 0) continue;
                this.pixels[index] = ColorUtil.composite(newPixel, oldPixel);
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

    public BufferedImage getImage() {
        return image;
    }

    public static void init(int width, int height, int scale) {
        Render.nWidth = width;
        Render.nHeight = height;
        Render.scale = scale;
    }

    public static void init() {
        normalRender = new Render(nWidth, nHeight);
        fullRender = new Render(nWidth * scale, nHeight * scale);

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

}
