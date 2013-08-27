package aritzh.ld27;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render {
    int width;
    int height;

    public Render(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void render(int[] pixels) {
        Arrays.fill(pixels, 0xff00ff55);
    }

    public void clear(int[] pixels) {
        Arrays.fill(pixels, 0x00000000);
    }

    public void drawStringCenteredAt(Graphics g, String text, int x, int y) {
        FontMetrics m = g.getFontMetrics();

        int xx = (int) (x - (m.getStringBounds(text, g).getWidth() / 2));
        int yy = y + (m.getAscent() - m.getDescent()) / 2;

        g.drawString(text, xx, yy);
    }

    public void drawStringAt(Graphics g, String text, int x, int y) {
        FontMetrics m = g.getFontMetrics();

        g.drawString(text, x, y + m.getAscent() - m.getDescent());
    }
}
