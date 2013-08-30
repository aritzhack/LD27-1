package aritzh.ld27.util;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ColorUtil {

    private static final int MASK = 0xFF;

    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

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

        int finalRed = (int) (red1 * alpha1 + red2 * alpha2 * (1 - alpha1) * 0.5);
        int finalGreen = (int) (green1 * alpha1 + green2 * alpha2 * (1 - alpha1) * 0.5);
        int finalBlue = (int) (blue1 * alpha1 + blue2 * alpha2 * (1 - alpha1) * 0.5);

        return getColor(finalAlpha, finalRed, finalGreen, finalBlue);
    }

    public static int getColor(final int alpha, final int red, final int green, final int blue) {
        //if(alpha>MASK || red>MASK || green>MASK || blue>MASK) throw new IllegalArgumentException("All arguments must be less than or equal to 255 (0xFF)");
        return (alpha << ALPHA_SHIFT) | (red << RED_SHIFT) | (green << GREEN_SHIFT) | blue;
    }

    public static int getRed(final int color) {
        return (color >> RED_SHIFT) & MASK;
    }

    public static int getGreen(final int color) {
        return (color >> GREEN_SHIFT) & MASK;
    }

    public static int getBlue(final int color) {
        return (color & MASK);
    }

    public static int getAlpha(final int color) {
        return (color >> ALPHA_SHIFT) & MASK;
    }
}
