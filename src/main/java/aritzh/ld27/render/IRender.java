package aritzh.ld27.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Interface defining basic methods to draw 2D graphics using Java2D
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public interface IRender {

    /**
     * Sets a particular pixel to the specified color.
     *
     * @param x         The X coordinate of the pixel
     * @param y         The Y coordinate of the pixel
     * @param color     The color to set the pixel to.
     * @param composite Whether the color should be just set, or mixed with the color that was there before
     * @see aritzh.ld27.util.ARGBColorUtil ARGBColorUtil - Utility class, specific to this color format
     */
    public void setPixel(int x, int y, int color, boolean composite);

    /**
     * Draws a sprite at the specified location
     *
     * @param s The sprite to draw
     * @param x The X position
     * @param y The Y position
     */
    public void draw(Sprite s, int x, int y);

    /**
     * Composites the pixels inside {@code pixels} with the current pixels. <br />
     * There is an offset of {@code xp} horizontally and {@code yp} vertically.
     * The amount of pixels drawn is cut off at {@code width} and {@code height} <br />
     * The pixels will be taken as follows: {@code pixels[x + (y * this.width)]} where x and y are the iteration indexes
     *
     * @param x      The horizontal offset from the left
     * @param y      The vertical offset from the top
     * @param width  The maximum amount of pixels composited horizontally
     * @param height The maximum amount of pixels composited vertically
     * @param pixels The integer array containing the colors
     */
    public void draw(int x, int y, int width, int height, int pixels[]);

    /**
     * Draws a {@link BufferedImage} at the specified location
     *
     * @param x     The x position
     * @param y     The y position
     * @param image The image to be drawn
     */
    public void draw(int x, int y, BufferedImage image);

    /**
     * Sets all pixels to 0 (Transparent black)
     */
    public void clear();

    /**
     * Draws a String centered at the specified location
     *
     * @param g      The {@link Graphics} instance into which the String will be drawn
     * @param text   The String to render
     * @param x      The X coordinate to which the String will be centered at
     * @param y      The Y coordinate to which the String will be centered at
     * @param shadow Whether the string should be rendered with shadow or not
     */
    public void drawStringCenteredAt(Graphics g, String text, int x, int y, boolean shadow);

    /**
     * Draws a String with the top-left corner at the specified location
     *
     * @param g      The {@link Graphics} instance into which the String will be drawn
     * @param text   The String to render
     * @param x      The X coordinate of the top-left corner at which the String will be drawn
     * @param y      The Y coordinate of the top-left corner at which the String will be drawn
     * @param shadow Whether the string should be rendered with shadow or not
     */
    public void drawStringAt(Graphics g, String text, int x, int y, boolean shadow);

    /**
     * Returns the image behind this renderer. Useful to get the {@link java.awt.image.BufferedImage#getGraphics() image graphics}
     * in order to render rectangles, instead of drawing pixel-per-pixel
     *
     * @return the image behind this renderer.
     */
    public BufferedImage getImage();

    /**
     * Returns the height of the renderer
     *
     * @return the height of the renderer
     */
    public int getHeight();

    /**
     * Returns the width of the renderer
     *
     * @return the width of the renderer
     */
    public int getWidth();

}
