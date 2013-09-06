package aritzh.ld27.util.console;

import aritzh.ld27.Game;
import aritzh.ld27.util.console.commands.ICommand;

import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that can be used to get some user input that could (in some cases) be considered cheating
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Console {

    private final Game game;
    private final Map<String, ICommand> commands = new HashMap<String, ICommand>();

    public Console(Game game) {
        this.game = game;
    }

    /**
     * Registers a command, so that when it is run, it can be triggered
     * @param command The command to register
     */
    public void registerCommand(ICommand command) {
        if(command == null) throw new IllegalArgumentException("Cannot register null as a command");
        String[] aliases = command.getAliases();
        if (aliases == null || aliases.length == 0) return;
        String alias = command.getAliases()[0];
        if (this.commands.containsKey(alias)) {
            throw new IllegalArgumentException("Tried to register command " + command + " when alias " + alias + " was already registered");
        } else {
            for(String s : aliases){
                if(this.commands.containsKey(s)) throw new IllegalArgumentException("Alias" + s + " was already taken by " + this.commands.get(s) + " when adding " + command);
                this.commands.put(s, command);
            }
        }
    }

    /**
     * Opens the console (in a different thread) and asks the user to input a command, which is then triggered
     */
    public void openConsole() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String command = JOptionPane.showInputDialog("Enter command:");
                Console.this.game.getKeyboard().resetKey(KeyEvent.VK_F8); // TODO get rid of this here!
                if (command == null || command.equals("")) return;
                String ret = Console.this.handleCommand(command);
                if(ret != null) JOptionPane.showMessageDialog(Console.this.game, ret, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }, "Console Thread").start();
    }

    private String handleCommand(String input) {

        input = input.trim();

        ICommand command = this.getMatchingCommand(input);

        if (command == null) {
            return "Command " + command + " not found";
        } else {
            return command.run(this.game, input);
        }
    }

    /**
     * Returns the {@link ICommand} that is represented by that input
     * @param input The input you want to get the command of
     * @return The ICommand represented by the input
     */
    public ICommand getMatchingCommand(String input){
        input = input.trim();
        int spaceIndex = input.indexOf(' ');
        String command = input.substring(0, (spaceIndex != -1 ? spaceIndex : input.length())).trim();
        if (!this.commands.containsKey(command)) {
            return null;
        } else {
            return this.commands.get(command);
        }
    }
}
