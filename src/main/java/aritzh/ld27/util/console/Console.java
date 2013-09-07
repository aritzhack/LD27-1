package aritzh.ld27.util.console;

import aritzh.ld27.util.console.commands.FormatHelper;
import aritzh.ld27.util.console.commands.ICommand;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that can be used to get some user input that could (in some cases) be considered cheating
 * Generified so that commands get an instance of what you need to perform it. (Usually an instance of the game)
 *
 * @param <G> The type of the object that will be passed to the commands, so that they are able to perform what they need
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Console<G> {

    private final G game;
    private final Map<String, ICommand<G>> commands = new HashMap<String, ICommand<G>>();

    private Thread thread;

    public Console(G game) {
        this.game = game;
    }

    /**
     * Registers a command, so that when it is run, it can be triggered
     *
     * @param command The command to register
     */
    public void registerCommand(ICommand<G> command) {
        if (command == null) throw new IllegalArgumentException("Cannot register null as a command");
        String[] aliases = command.getAliases();
        if (aliases == null || aliases.length == 0) return;
        String alias = command.getAliases()[0];
        if (this.commands.containsKey(alias)) {
            throw new IllegalArgumentException("Tried to register command " + command + " when alias " + alias + " was already registered");
        } else {
            for (String s : aliases) {
                if (this.commands.containsKey(s))
                    throw new IllegalArgumentException("Alias" + s + " was already taken by " + this.commands.get(s) + " when adding " + command);
                this.commands.put(s, command);
            }
        }
    }

    /**
     * Opens the console (in a different thread) and asks the user to input a command, which is then triggered
     */
    public void openConsole() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = JOptionPane.showInputDialog("Enter command:");
                if (command == null || command.equals("")) return;
                String[] ret = Console.this.handleCommand(command);
                if (ret != null && ret.length == 2)
                    JOptionPane.showMessageDialog(null, FormatHelper.format(ret[0]), ret[1], JOptionPane.INFORMATION_MESSAGE);
                else if (ret != null && ret.length == 1)
                    JOptionPane.showMessageDialog(null, FormatHelper.format(ret[0]), "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }, "Console Thread");
        this.thread.start();
    }

    private String[] handleCommand(String input) {
        input = input.trim();
        ICommand<G> command = this.getMatchingCommand(input);

        if (command == null)
            return new String[]{FormatHelper.RED + "Command " + FormatHelper.BOLD + FormatHelper.DBLUE + input + FormatHelper.RESET + FormatHelper.RESET + " not found", "Error"};
        return command.run(this.game, input);
    }

    /**
     * Returns the {@link ICommand} that is represented by that input
     *
     * @param input The input you want to get the command of
     * @return The ICommand represented by the input
     */
    public ICommand<G> getMatchingCommand(String input) {
        input = input.trim();
        int spaceIndex = input.indexOf(' ');
        String command = input.substring(0, (spaceIndex != -1 ? spaceIndex : input.length())).trim();
        if (!this.commands.containsKey(command)) {
            return null;
        } else {
            return this.commands.get(command);
        }
    }

    /**
     * Returns true if the console thread is currently running
     *
     * @return true if the console thread is currently running
     */
    public boolean isOpen() {
        return this.thread != null && this.thread.isAlive();
    }
}
