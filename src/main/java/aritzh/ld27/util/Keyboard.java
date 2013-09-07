package aritzh.ld27.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Keyboard and focus handler.
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Keyboard implements KeyListener, FocusListener {

    private boolean focus = true;
    private boolean[] keys = new boolean[Character.MAX_VALUE];
    private ArrayList<Integer> checked = new ArrayList<Integer>();

    // region ...Listener methods...
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
        for (int i = 0; i < this.checked.size(); i++)
            if (this.checked.get(i).equals(e.getKeyCode())) this.checked.remove(i);
    }

    // endregion

    /**
     * Checks whether the specified key was typed (pressed, but not checked if it was pressed)
     * <br />
     * Example: {@code isKeyTyped(KeyEvent.VK_F)} to check whether the key F was pressed since the last call to this method
     * @param key The key code to check.
     * @return true if the key hasn't been checked since it was last pressed, false otherwise
     * @see KeyEvent KeyEvent for the keycodes
     */
    public boolean isKeyTyped(int key) {
        if (this.keys[key] && !this.checked.contains(key)) {
            this.checked.add(key);
            return true;
        }
        return false;
    }

    /**
     * Returns true if the key is currently pressed
     * @param key The key code
     * @return true if the key is currently pressed
     * @see KeyEvent KeyEvent for the keycodes
     */
    public boolean isKeyDown(int key){
        return this.keys[key];
    }

    /**
     * Whether {@link KeyEvent#VK_UP} or {@link KeyEvent#VK_KP_UP} or {@link KeyEvent#VK_W} are currently pressed
     * @return true if any up-meaning key are pressed
     */
    public boolean isUp() {
        return this.keys[KeyEvent.VK_W] || this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_KP_UP];
    }

    /**
     * Whether {@link KeyEvent#VK_DOWN} or {@link KeyEvent#VK_KP_DOWN} or {@link KeyEvent#VK_S} are currently pressed
     * @return true if any down-meaning key are pressed
     */
    public boolean isDown() {
        return this.keys[KeyEvent.VK_S] || this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_KP_DOWN];
    }

    /**
     * Whether {@link KeyEvent#VK_LEFT} or {@link KeyEvent#VK_KP_LEFT} or {@link KeyEvent#VK_A} are currently pressed
     * @return true if any left-meaning key are pressed
     */
    public boolean isLeft() {
        return this.keys[KeyEvent.VK_A] || this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_KP_LEFT];
    }

    /**
     * Whether {@link KeyEvent#VK_RIGHT} or {@link KeyEvent#VK_KP_RIGHT} or {@link KeyEvent#VK_D} are currently pressed
     * @return true if any right-meaning key are pressed
     */
    public boolean isRight() {
        return this.keys[KeyEvent.VK_D] || this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_KP_RIGHT];
    }

    /**
     * True if the window this was registered to has currently focus
     * @return true if the window has focus
     */
    public boolean hasFocus() {
        return this.focus;
    }

    /**
     * Resets all the checked keys. Effectively making all keys unchecked, so if the key is pressed, calling {@link Keyboard#isKeyTyped(int)} will return true
     */
    public void resetChecked() {
        this.checked.clear();
    }

    /**
     * Resets a specific key. For later use with {@link Keyboard#isKeyTyped(int)}
     * @param keyCode The key code to reset
     * @see aritzh.ld27.util.Keyboard#resetChecked()
     * @see KeyEvent KeyEvent for the keycodes
     */
    public void resetKey(int keyCode) {
        this.checked.remove(Integer.valueOf(keyCode));
    }
}
