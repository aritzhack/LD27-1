package aritzh.ld27.util;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Timer {

    private long lastTime, timer;
    private int frames, updates;
    private double delta;

    private boolean shouldUpdate = false;

    private static final double NS = 1000000000.0/60.0;

    public Timer(){
        this.lastTime = System.nanoTime();
        this.timer = System.currentTimeMillis();

        this.frames = 0;
        this.updates = 0;
        this.delta = 0;
    }

    public void updateBefore(){
        long now = System.nanoTime();
        delta += (now - lastTime) / NS;
        lastTime = now;
        if(delta >= 1){
            shouldUpdate = true;
            delta--;
            updates++;
        } else shouldUpdate = false;
    }

    public void updateAfter(){
        frames++;
        if(System.currentTimeMillis() - timer > 1000){
            timer+=1000;
            System.out.println(updates + "ups, " + frames + "fps");
            updates = 0;
            frames = 0;
        }
    }

    private boolean shouldUpdate(){
        return shouldUpdate;
    }
}
