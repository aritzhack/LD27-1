package aritzh.ld27.util;

/**
 * Utility class to deal with ARGB color format
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ARGBColorUtil {

    /**
     * The mask to get the two least significant bytes of an integer;
     */
    public static final int MASK = 0xFF;
    /**
     * The shifting used to put the alpha component in position
     */
    public static final int ALPHA_SHIFT = 24;
    /**
     * The shifting used to put the red component in position
     */
    public static final int RED_SHIFT = 16;
    /**
     * The shifting used to put the green component in position
     */
    public static final int GREEN_SHIFT = 8;
    /**
     * The shifting used to put the blue component in position
     */
    public static final int BLUE_SHIFT = 0;

    /**
     * Composes two colors with the ARGB format: <br />
     * The format of the color integer is as follows: 0xAARRGGBB
     * Where:
     * <ol>
     * <li>AA is the alpha component (0-255)</li>
     * <li>RR is the red component (0-255)</li>
     * <li>GG is the green component (0-255)</li>
     * <li>BB is the blue component (0-255)</li>
     * </ol>
     *
     * @param color1 The color above
     * @param color2 The color below
     * @return A composition of both colors, in ARGB format
     */
    public static int composite(final int color1, final int color2) {

        double alpha1 = getAlpha(color1) / 256.0;
        double alpha2 = getAlpha(color2) / 256.0;

        if (alpha1 == 0xFF || alpha2 == 0) return color1;
        else if (alpha1 == 0) return color2;

        int red1 = getRed(color1);
        int red2 = getRed(color2);
        int green1 = getGreen(color1);
        int green2 = getGreen(color2);
        int blue1 = getBlue(color1);
        int blue2 = getBlue(color2);

        double doubleAlpha = (alpha1 + alpha2 * (1 - alpha1));
        int finalAlpha = (int) (doubleAlpha * 256);

        double cAlpha2 = alpha2 * (1 - alpha1) * 0.5;

        int finalRed = (int) (red1 * alpha1 + red2 * cAlpha2);
        int finalGreen = (int) (green1 * alpha1 + green2 * cAlpha2);
        int finalBlue = (int) (blue1 * alpha1 + blue2 * cAlpha2);

        return getColor(finalAlpha, finalRed, finalGreen, finalBlue);
    }

    /**
     * Gets a color composed of the specified channels. All values must be between 0 and 255, both inclusive
     *
     * @param alpha The alpha channel [0-255]
     * @param red   The red channel [0-255]
     * @param green The green channel [0-255]
     * @param blue  The blue channel [0-255]
     * @return The color composed of the specified channels
     */
    public static int getColor(final int alpha, final int red, final int green, final int blue) {
        return (alpha << ALPHA_SHIFT) | (red << RED_SHIFT) | (green << GREEN_SHIFT) | blue;
    }

    /**
     * Returns the red channel of the color
     * @param color The color to get the red channel of
     * @return The red channel of the color
     */
    public static int getRed(final int color) {
        return (color >> RED_SHIFT) & MASK;
    }

    /**
     * Returns the green channel of the color
     * @param color The color to get the green channel of
     * @return The green channel of the color
     */
    public static int getGreen(final int color) {
        return (color >> GREEN_SHIFT) & MASK;
    }

    /**
     * Returns the blue channel of the color
     * @param color The color to get the blue channel of
     * @return The blue channel of the color
     */
    public static int getBlue(final int color) {
        return (color & MASK);
    }

    /**
     * Returns the alpha channel of the color
     * @param color The color to get the alpha channel of
     * @return The alpha channel of the color
     */
    public static int getAlpha(final int color) {
        return (color >> ALPHA_SHIFT) & MASK;
    }
}
