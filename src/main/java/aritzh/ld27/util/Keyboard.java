package aritzh.ld27.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Keyboard implements KeyListener, FocusListener {

    boolean focus = true;
    boolean[] keys = new boolean[Character.MAX_VALUE];
    ArrayList<Integer> checked = new ArrayList<Integer>();

    /**
     * Invoked when a component gains the keyboard focus.
     */
    @Override
    public void focusGained(FocusEvent e) {
        this.focus = true;
    }

    /**
     * Invoked when a component loses the keyboard focus.
     */
    @Override
    public void focusLost(FocusEvent e) {
        this.focus = false;
        Arrays.fill(this.keys, false);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        this.keys[code] = true;
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
        for (int i = 0; i < this.checked.size(); i++) if (this.checked.get(i).equals(e.getKeyCode())) this.checked.remove(i);
    }

    public boolean isKeyTyped(int key) {
        if (this.keys[key] && !this.checked.contains(key)) {
            this.checked.add(key);
            return true;
        }
        return false;
    }

    public void resetChecked() {
        this.checked.clear();
    }

    public boolean isUp() {
        return this.keys[KeyEvent.VK_W] || this.keys[KeyEvent.VK_UP];
    }

    public boolean isDown() {
        return this.keys[KeyEvent.VK_S] || this.keys[KeyEvent.VK_DOWN];
    }

    public boolean isLeft() {
        return this.keys[KeyEvent.VK_A] || this.keys[KeyEvent.VK_LEFT];
    }

    public boolean isRight() {
        return this.keys[KeyEvent.VK_D] || this.keys[KeyEvent.VK_RIGHT];
    }

    public boolean hasFocus() {
        return this.focus;
    }

    public void resetKey(int keyCode) {
        this.checked.remove(Integer.valueOf(keyCode));
    }
}
