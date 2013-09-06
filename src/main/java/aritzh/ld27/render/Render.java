package aritzh.ld27.render;

import aritzh.ld27.util.ARGBColorUtil;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class used to aid rendering to the screen
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render {
    
    /**
     * @deprecated Will be removed when separating the engine and the game
     */
    public static Render fullRender;
    /**
     * @deprecated Will be removed when separating the engine and the game
     */
    public static Render normalRender;
    private static int nWidth;
    private static int nHeight;
    private static int scale;
    private static int[] background;
    private static int[] backgroundFull;
    private int width;
    private int height;
    private int[] pixels;
    private BufferedImage image;

    /**
     * Creates a render with the specified size
     *
     * @param width  The width of the renderer
     * @param height The height of the renderer
     */
    public Render(int width, int height) {

        this.width = width;
        this.height = height;

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
    }

    /**
     * @deprecated Will be removed when separating the engine and the game
     */
    public static void init(int width, int height, int scale) {
        Render.nWidth = width;
        Render.nHeight = height;
        Render.scale = scale;
    }

    /**
     * @deprecated Will be removed when separating the engine and the game
     */
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

    public void renderBackground(boolean fullScreen) {
        this.drawPixels((fullScreen ? backgroundFull : background), 0, 0, this.width, this.height);
    }

    /**
     * Composites the pixels inside {@code pixels} with the current pixels. <br />
     * There is an offset of {@code xp} horizontally and {@code yp} vertically.
     * The amount of pixels drawn is cut off at {@code width} and {@code height} <br />
     * The pixels will be taken as follows: {@code pixels[x + (y * this.width)]} where x and y are the iteration indexes
     *
     * @param pixels The integer array containing the colors
     * @param xp     The horizontal offset from the left
     * @param yp     The vertical offset from the top
     * @param width  The maximum amount of pixels composited horizontally
     * @param height The maximum amount of pixels composited vertically
     */
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
                this.pixels[index] = ARGBColorUtil.composite(newPixel, oldPixel);
            }
        }
    }

    /**
     * Sets all pixels to 0 (Transparent black)
     */
    public void clear() {
        Arrays.fill(this.pixels, 0x00000000);
    }

    /**
     * Draws a String centered at the specified location
     *
     * @param g      The {@link Graphics} instance into which the String will be drawn
     * @param text   The String to render
     * @param x      The X coordinate to which the String will be centered at
     * @param y      The Y coordinate to which the String will be centered at
     * @param shadow Whether the string should be rendered with shadow or not
     */
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

    /**
     * Draws a String with the top-left corner at the specified location
     *
     * @param g      The {@link Graphics} instance into which the String will be drawn
     * @param text   The String to render
     * @param x      The X coordinate of the top-left corner at which the String will be drawn
     * @param y      The Y coordinate of the top-left corner at which the String will be drawn
     * @param shadow Whether the string should be rendered with shadow or not
     */
    public void drawStringAt(Graphics g, String text, int x, int y, boolean shadow) {
        FontMetrics m = g.getFontMetrics();

        int yy = y + m.getAscent() - m.getDescent();

        if (shadow) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.drawString(text, x + 3, yy + 3);
            g.setColor(c);
        }

        g.drawString(text, x, yy);
    }

    /**
     * Draws a sprite at the specified location
     *
     * @param s  The sprite to draw
     * @param xp The X position
     * @param yp The Y position
     */
    public void renderSprite(Sprite s, int xp, int yp) {
        this.drawPixels(s.getPixels(), xp, yp, s.getWidth(), s.getHeight());
    }

    /**
     * Sets a particular pixel to the specified color.
     * @param x     The X coordinate of the pixel
     * @param y     The Y coordinate of the pixel
     * @param color The color to set the pixel to.
     * @see aritzh.ld27.util.ARGBColorUtil ARGBColorUtil - Utility class, specific to this format
     */
    public void setPixel(int x, int y, int color) {
        this.pixels[x + y * this.width] = color;
    }

    /**
     * Returns the width of the renderer
     *
     * @return the width of the renderer
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the renderer
     *
     * @return the height of the renderer
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the image behind this renderer. Useful to get the {@link java.awt.image.BufferedImage#getGraphics() image graphics}
     * in order to render rectangles, instead of drawing pixel-per-pixel
     *
     * @return the image behind this renderer.
     */
    public BufferedImage getImage() {
        return this.image;
    }

}
