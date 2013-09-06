package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * Represents a command that can be run through the {@link aritzh.ld27.util.console.Console}
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public interface ICommand {

    /**
     * Runs the command represented by the implementation
     * @param game The instance of {@link Game}
     * @param command The full command (i.e. with arguments)
     * @return If a message wants to be printed, a {@code String}, else {@code null}
     */
    public String run(Game game, String command);

    /**
     * Returns a list of aliases that trigger this command.
     * <br />
     * E.g. if this commnad will be triggered by "god-mode", "godmode", "god" and "gm",
     * {@code getAliases()} should return a String array containing all these strings (i.e new String[]{"god-mode", "godmode", "god" and "gm"})
     * @return A list of all aliases that will trigger this command
     */
    public String[] getAliases();

    /**
     * String displayed when the user runs the help command, followed by any alias of this command
     * @return A {@code String} defining the use and/or purpose of the command
     */
    public String getHelp();
}