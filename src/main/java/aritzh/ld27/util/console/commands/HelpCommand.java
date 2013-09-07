package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class HelpCommand implements ICommand<Game> {

    /**
     * Runs the command represented by the implementation
     *
     * @param game    The instance of {@link aritzh.ld27.Game}
     * @param command The full command (i.e. with arguments)
     * @return If a message wants to be printed, a {@code String}, else {@code null}
     */
    @Override
    public String[] run(Game game, String command) {
        String[] args = command.split(" ");
        if (args.length < 2) return new String[]{this.getHelp(), "Help"};
        else return new String[]{game.getConsole().getMatchingCommand(args[1]).getHelp(), "Help"};
    }

    /**
     * Returns a list of aliases that trigger this command.
     * <br />
     * E.g. if this commnad will be triggered by "god-mode", "godmode", "god" and "gm",
     * {@code getAliases()} should return a String array containing all these strings (i.e new String[]{"god-mode", "godmode", "god" and "gm"})
     *
     * @return A list of all aliases that will trigger this command
     */
    @Override
    public String[] getAliases() {
        return new String[]{"help", "h", "?"};
    }

    /**
     * String displayed when the user runs the help command, followed by any alias of this command
     *
     * @return A {@code String} defining the use and/or purpose of the command
     */
    @Override
    public String getHelp() {
        return "Shows help for a specific command:\n" +
                "Usage: help " + FormatHelper.ITALIC + FormatHelper.BOLD + FormatHelper.OLIVE + "<command>";
    }
}
