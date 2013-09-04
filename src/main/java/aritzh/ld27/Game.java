package aritzh.ld27;

import aritzh.ld27.entity.Player;
import aritzh.ld27.level.Level;
import aritzh.ld27.render.Render;
import aritzh.ld27.util.Console;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends Canvas implements Runnable {
    private final boolean applet;
    private final Keyboard keyboard;
    private final boolean isFullscreenSupported;
    private final Font font32;
    private final Font font64;
    private final Font font100;
    private JFrame frame;
    private Thread thread;
    private Profiler profiler = Profiler.getInstance();
    private boolean running;
    private Render currRender;
    private long timeInSeconds = 0, timer;
    private int frames, updates;
    private Dimension size;
    private boolean isFullscreen;
    private int fullScreenErrorTimeout;
    private Level level;
    private Player player;
    private Console console;


    public Game(int width, int height, boolean applet, int scale) {
        this.size = new Dimension(width * scale, height * scale);

        this.keyboard = new Keyboard();
        this.applet = applet;

        this.setFont(this.font32 = new Font("Consola", Font.PLAIN, 32));
        this.font64 = new Font("Consola", Font.PLAIN, 64);
        this.font100 = new Font("Consola", Font.PLAIN, 100);
        this.setFont(this.font100);

        this.setSize(this.size);
        this.setMaximumSize(this.size);
        this.setPreferredSize(this.size);
        this.addKeyListener(this.keyboard);
        this.addFocusListener(this.keyboard);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game.this.level.clicked(e);
            }
        });

        Render.init(width, height, scale);

        this.isFullscreenSupported = !applet && GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isFullScreenSupported();

        if (!applet) this.createWindow();

        this.reload();
    }

    private void reload() {
        Render.init();
        Level.init(this);
        this.currRender = (this.isFullscreen ? Render.fullRender : Render.normalRender);
        this.level = Level.getLEVEL_1();
        this.player = new Player(this.level, this.keyboard);
        this.level.setPlayer(this.player);
        this.console = new Console(this);
    }

    private void createWindow() {

        this.frame = new JFrame();
        this.frame.setResizable(false);
        this.frame.setTitle("LD27 Late Entry");
        this.frame.add(this);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setSize(this.size);
        this.frame.setMaximumSize(this.size);
        this.frame.setPreferredSize(this.size);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.this.stop();
            }
        });
    }

    public synchronized void stop() {
        this.running = false;
        if (!this.applet) this.frame.dispose();
        try {
            this.thread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exiting...");
    }

    public static void main(String[] args) {
        Game g = new Game(400, 225, false, 2);
        g.start();
    }

    protected synchronized void start() {
        this.running = true;
        this.thread = new Thread(this, "Main Game Thread");
        this.thread.start();
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
        this.timer = System.currentTimeMillis();

        final double NS = 1000000000.0 / 60.0;
        double delta = 0;

        if (!this.hasFocus()) this.requestFocus();

        while (this.running) {
            this.profiler.startSection("MainLoop");

            long now = System.nanoTime();
            delta += (now - lastTime) / NS;
            lastTime = now;
            if (delta >= 1) {
                this.update(delta);
                delta--;
                this.updates++;
            }
            this.render();

            this.frames++;
            if (System.currentTimeMillis() - this.timer > 1000) {
                this.updatePerSecond();
                System.out.println(this.updates + "ups\t|\t" + this.frames + "fps\t|\tUpdateTime: " + this.profiler.getSectionTime("update") + "\t|\tRenderTime: " + this.profiler.getSectionTime("render") + "\t|\tMainLoopTime: " + this.profiler.getSectionTime("mainloop") + "\t|\tTotalTime: " + this.timeInSeconds + "\t|\tLast A*: " + this.profiler.getSectionTime("A* AI"));
                this.timer += 1000;
                this.updates = 0;
                this.frames = 0;
                if (this.fullScreenErrorTimeout > 0) this.fullScreenErrorTimeout--;
                this.timeInSeconds++;
            }

            this.profiler.endSection();
        }
    }

    private void updatePerSecond() {
        this.profiler.startSection("UpdatePS");

        if (!this.keyboard.hasFocus()) {
            this.profiler.endSection();
            return;
        }

        this.level.updatePS();

        this.profiler.endSection();
    }

    private void render() {
        if (!this.running) return;
        this.profiler.startSection("Render");
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            this.initFonts();
        } else {
            // Init
            Graphics g = bs.getDrawGraphics();
            g.setClip(0, 0, this.getWidth(), this.getHeight());
            g.setFont(this.font32);

            // Clear
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.currRender.clear();

            // Draw
            this.currRender.renderBackground(this.isFullscreen);

            this.level.render(this.currRender);

            // Render
            g.drawImage(this.currRender.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

            // Text
            g.setColor(Color.WHITE);
            if (!this.keyboard.hasFocus()) {
                g.setFont(this.font100);
                g.setColor(Color.WHITE);
                this.currRender.drawStringCenteredAt(g, "Click to focus!!", this.getWidth() / 2, this.getHeight() / 2 + (this.fullScreenErrorTimeout > 0 ? 50 : 0), true);
                g.setFont(this.font32);
            }

            if (this.fullScreenErrorTimeout > 0) {
                g.setColor(Color.RED);
                g.setFont(this.font64);
                this.currRender.drawStringCenteredAt(g, "Cannot go Fullscreen :(", this.getWidth() / 2, this.getHeight() / 2 - 50, true);
                g.setFont(this.font32);
            }

            g.dispose();
            if (this.running) bs.show();
        }
        this.profiler.endSection();
    }

    /**
     * Used to remove the time used to load the font metrics (Slowed down over 2 seconds in some PCs)
     */
    private void initFonts() {
        final Graphics g = this.getBufferStrategy().getDrawGraphics();
        g.setFont(this.font100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                g.getFontMetrics();
            }
        }).start();
    }

    private void update(double delta) {
        this.profiler.startSection("Update");

        if (!this.keyboard.hasFocus()) {
            this.profiler.endSection();
            return;
        }

        this.level.update(delta);

        if (this.keyboard.isKeyTyped(KeyEvent.VK_ESCAPE)) {
            this.stop();
        }

        if (this.keyboard.isKeyTyped(KeyEvent.VK_R)) {
            System.out.println("Reloading...");
            this.reload();
            System.out.println("Reloaded");
        }

        if (this.keyboard.isKeyTyped(KeyEvent.VK_F8)) {
            this.openConsole();
        }

        if (this.keyboard.isKeyTyped(KeyEvent.VK_F11)) {
            if (this.isFullscreenSupported) this.toggleFullscreen();
            else this.fullScreenErrorTimeout = 4;
        }

        this.profiler.endSection();
    }

    private void toggleFullscreen() {
        if (!this.isFullscreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this.frame);
            this.currRender = Render.fullRender;
        } else {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
            this.currRender = Render.normalRender;
        }

        this.isFullscreen = !this.isFullscreen;
    }

    private void openConsole() {
        this.console.openConsole();
    }

    public Level getLevel() {
        return this.level;
    }

    public Keyboard getKeyboard() {
        return this.keyboard;
    }
}
