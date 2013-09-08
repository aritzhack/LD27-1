package aritzh.ld27.render;

import aritzh.ld27.util.ARGBColorUtil;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render implements IRender {

    private final int width;
    private final int height;
    private final BufferedImage image;
    private final int[] pixels;

    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
    }

    public void setPixel(int x, int y, int color, boolean composite) {
        if (x > this.width || y > this.height || x < 0 || y < 0) return;

        this.pixels[x + y * this.width] = (composite ? ARGBColorUtil.composite(color, this.pixels[x + y * this.width]) : color);
    }

    public void draw(Sprite s, int x, int y) {
        this.draw(x, y, s.getWidth(), s.getHeight(), s.getPixels());

    }

    public void draw(int x, int y, int width, int height, int pixels[]) {
        if (x + width < 0 || y + height < 0 || x > this.width || y > this.height) return;
        int maxX = Math.min(width, this.width - x);
        int maxY = Math.min(height, this.height - y);

        for (int yp = 0; yp < maxY; yp++) {
            if (yp + y < 0) continue;
            int beforeY = y + yp;
            for (int xp = 0; xp < maxX; xp++) {
                this.pixels[((x + xp) + beforeY * this.width)] = ARGBColorUtil.composite(pixels[xp + yp * width], this.pixels[((x + xp) + beforeY * this.width)]);
            }
        }
    }

    public void draw(int x, int y, BufferedImage image) {
        this.draw(x, y, image.getWidth(), image.getHeight(), image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()));
    }

    public void clear() {
        Arrays.fill(this.pixels, 0x00000000);
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

    public int getHeight() {
        return this.height;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getWidth() {
        return this.width;
    }
}
