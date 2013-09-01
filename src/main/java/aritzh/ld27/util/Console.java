package aritzh.ld27.util;

import aritzh.ld27.Game;

import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Console {

    final Game game;

    public Console(Game game) {
        this.game = game;
    }

    public void openConsole() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Console.this.showConsole();
            }
        }, "Command Input Thread").start();
    }

    private void showConsole() {
        String command = JOptionPane.showInputDialog("Enter command:");
        this.game.getKeyboard().resetKey(KeyEvent.VK_F8); // reset the key, so that you can type it again
        if (command == null || command.equals("")) return;
        handleCommand(Command.matches(command), command);
    }

    private void handleCommand(Command command, String args) {
        if (command == null) return;
        switch (command) {
            case NO_CLIP:
                this.game.getLevel().getPlayer().toggleNoClip();
                break;
            case NO_RENDER:
                this.game.getLevel().getPlayer().toggleNoRender();
                break;
            case GOD_MODE:
                this.game.getLevel().getPlayer().toggleGod();
                break;

        }
    }

    private enum Command {

        NO_CLIP("no-clip", "clip", "noclip"),
        NO_RENDER("render", "no-render", "norender"),
        GOD_MODE("god", "godmode", "god-mode", "gm");
        private String[] validInputs;

        private Command(String... args) {
            this.validInputs = args;
        }

        public static Command matches(String s) {
            s = s.trim();
            int spaceIndex = s.indexOf(' ');
            s = s.substring(0, (spaceIndex != -1 ? spaceIndex : s.length())).trim();
            for (Command c : values()) {
                for (String valid : c.validInputs) {
                    if (valid.equalsIgnoreCase(s)) {
                        return c;
                    }
                }
            }
            return null;
        }
    }
}
