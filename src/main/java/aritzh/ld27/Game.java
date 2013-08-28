package aritzh.ld27;

import aritzh.ld27.render.Render;
import aritzh.ld27.render.Sprite;
import aritzh.ld27.util.Keyboard;
import aritzh.ld27.util.Profiler;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends Canvas implements Runnable {
    private JFrame frame;
    private Thread thread;
    private Profiler profiler = new Profiler();
    private boolean running;
    private final boolean applet;

    private BufferedImage renderImage;
    private final Render render;
    private final Keyboard keyboard;
    private long timeInSeconds = 0;
    private Dimension size;

    private final boolean isFullscreenSupported;
    private boolean isFullscreen;
    private int fullScreenErrorTimeout;

    private final Font font32;
    private final Font font64;
    private final Font font100;


    public Game(int width, int height, boolean applet, int scale) {
        size = new Dimension(width * scale, height * scale);

        keyboard = new Keyboard();
        this.applet = applet;

        this.setFont(font32 = new Font("Consola", Font.PLAIN, 32));
        font64 = new Font("Consola", Font.PLAIN, 64);
        font100 = new Font("Consola", Font.PLAIN, 100);
        this.setSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.addKeyListener(keyboard);
        this.addFocusListener(keyboard);

        renderImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        render = new Render(width, height, ((DataBufferInt) renderImage.getRaster().getDataBuffer()).getData());

        isFullscreenSupported = !applet && GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isFullScreenSupported();
        
        if (!applet) this.createWindow();
    }

    public static void main(String[] args) {
        //---------------------------------------
        //-- TODO Disable scaling in fullscreen--
        //---------------------------------------
        Game g = new Game(400, 300, false, 2);
        g.start();
    }

    private void createWindow() {

        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("LD27 Late Entry");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(size);
        frame.setMaximumSize(size);
        frame.setPreferredSize(size);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.this.stop();
            }
        });

    }

    protected synchronized void start() {
        this.running = true;
        this.thread = new Thread(this, "Main Game Thread");

        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        if (!applet) this.frame.dispose();
        try {
            this.thread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exiting...");
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        int frames = 0, updates = 0;
        final double NS = 1000000000.0 / 60.0;
        double delta = 0;

        if (!this.hasFocus()) this.requestFocus();

        while (running) {
            profiler.startSection("MainLoop");

            long now = System.nanoTime();
            delta += (now - lastTime) / NS;
            lastTime = now;
            if (delta >= 1) {
                update();
                delta--;
                updates++;
            }
            render();

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + "ups\t|\t" + frames + "fps\t|\tUpdateTime: " + profiler.getSectionTime("update") + "\t|\tRenderTime: " + profiler.getSectionTime("render") + "\t|\tMainLoopTime: " + profiler.getSectionTime("mainloop") + "\t|\tTotalTime: " + timeInSeconds);
                updates = 0;
                frames = 0;
                if (fullScreenErrorTimeout > 0) fullScreenErrorTimeout--;
                timeInSeconds++;
            }

            profiler.endSection("MainLoop");
        }
    }

    private void update() {
        profiler.startSection("Update");

        if (this.keyboard.isKeyTyped(KeyEvent.VK_ESCAPE)) {
            this.stop();
        }

        if (this.keyboard.isKeyTyped(KeyEvent.VK_F11)) {
            if (this.isFullscreenSupported) {
                if (!isFullscreen)
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
                else
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);

                this.isFullscreen = !this.isFullscreen;
            } else {
                this.fullScreenErrorTimeout = 4;
            }
        }

        profiler.endSection();
    }

    private void render() {
        if (!running) return;
        profiler.startSection("Render");
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
        } else {
            // Init
            Graphics g = bs.getDrawGraphics();
            g.setClip(0, 0, getWidth(), getHeight());
            g.setFont(this.font32);

            // Clear
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
            render.clear();

            // Draw
            render.renderBackground();
            render.renderSprite(Sprite.wall, render.getWidth() / 2 - 55, render.getHeight() / 2);

            // Render
            g.drawImage(renderImage, 0, 0, getWidth(), getHeight(), null);

            // Text
            g.setColor(Color.WHITE);
            if (!keyboard.hasFocus()) {
                g.setFont(this.font100);
                g.setColor(Color.WHITE);
                render.drawStringCenteredAt(g, "Click to focus!!", getWidth() / 2, getHeight() / 2 + (fullScreenErrorTimeout > 0 ? 50 : 0), true);
                g.setFont(this.font32);
            }

            if (fullScreenErrorTimeout > 0) {
                g.setColor(Color.RED);
                g.setFont(this.font64);
                render.drawStringCenteredAt(g, "Cannot go Fullscreen :(", getWidth() / 2, getHeight() / 2 - 50, true);
                g.setFont(this.font32);
            }

            g.dispose();
            bs.show();
        }
        profiler.endSection("Render");
    }
}
