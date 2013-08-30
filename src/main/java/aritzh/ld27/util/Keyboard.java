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
        focus = true;
    }

    /**
     * Invoked when a component loses the keyboard focus.
     */
    @Override
    public void focusLost(FocusEvent e) {
        focus = false;
        Arrays.fill(keys, false);
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
        keys[code] = true;
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        for (int i = 0; i < checked.size(); i++) if (checked.get(i).equals(e.getKeyCode())) checked.remove(i);
    }

    public boolean isKeyTyped(int key) {
        if (keys[key] && !checked.contains(key)) {
            checked.add(key);
            return true;
        }
        return false;
    }

    public void resetChecked() {
        this.checked.clear();
    }

    public boolean isUp() {
        return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
    }

    public boolean isDown() {
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    }

    public boolean isLeft() {
        return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    }

    public boolean isRight() {
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    public boolean hasFocus() {
        return focus;
    }

    public void resetKey(int keyCode) {
        this.checked.remove(Integer.valueOf(keyCode));
    }
}
