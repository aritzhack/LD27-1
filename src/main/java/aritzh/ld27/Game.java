package aritzh.ld27;

import aritzh.ld27.util.Keyboard;
import aritzh.ld27.util.Profiler;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
    JFrame frame;
    private Thread thread;
    private Profiler profiler = new Profiler();
    private boolean running;

    private BufferedImage image;
    private int[] pixels;
    Render render;
    Keyboard keyboard;
    Font font;
    long timeInSeconds = 0;

    public Game(int width, int height, boolean applet) {
        Dimension size = new Dimension(width, height);

        keyboard = new Keyboard();

        this.setFont(font = new Font("Consola", Font.PLAIN, 32));
        this.setSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.addKeyListener(keyboard);
        this.addFocusListener(keyboard);

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        render = new Render(width, height);

        if (!applet) this.createWindow();
    }

    public static void main(String[] args) {
        Game g = new Game(800, 600, false);
        g.start();
    }

    private void createWindow() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("LD27 Late Entry");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
        this.frame.dispose();
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
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
                System.out.println(updates + "ups, " + frames + "fps, UpdateTime: " + profiler.getSectionTime("update") + ", RenderTime: " + profiler.getSectionTime("render"));
                updates = 0;
                frames = 0;
                timeInSeconds++;
            }

            profiler.endSection("MainLoop");
        }
    }

    private void update() {
        profiler.startSection("Update");

        profiler.endSection();
    }

    private void render() {
        if (!running) return;
        profiler.startSection("Render");
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
        } else {
            Graphics g = bs.getDrawGraphics();
            g.setFont(this.font);
            render.clear(pixels);
            render.render(pixels);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

            render.drawStringAt(g, "Hoola!!!", 0, 0);


            if (!keyboard.hasFocus()) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Consola", Font.PLAIN, 100));
                render.drawStringCenteredAt(g, "Click to focus!!", getWidth() / 2, getHeight() / 2);
            }

            g.dispose();
            bs.show();
        }
        profiler.endSection("Render");
    }
}
