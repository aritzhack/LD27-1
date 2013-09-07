package aritzh.ld27.util.console.commands;

/**
 * Represents a command that can be run through the {@link aritzh.ld27.util.console.Console}
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public interface ICommand<T> {

    /**
     * Runs the command represented by the implementation
     *
     * @param t       The instance that is passed, so that the command can perform what it needs to perform
     * @param command The full command (i.e. with arguments)
     * @return If a message wants to be printed, a {@code String} array with either of the following formats:
     *         <ul>
     *         <li>[MESSAGE, TITLE]</li>
     *         <li>[MESSAGE]</li>
     *         </ul>
     *         <p/>
     *         If no message wants to be printed, return null (empty array, or with size bigger than 2, will be omitted too)
     *         <p/>
     *         To format the message (not the title, see {@link FormatHelper#format(String)})
     */
    public String[] run(T t, String command);

    /**
     * Returns a list of aliases that trigger this command.
     * <br />
     * E.g. if this commnad will be triggered by "god-mode", "godmode", "god" and "gm",
     * {@code getAliases()} should return a String array containing all these strings (i.e new String[]{"god-mode", "godmode", "god" and "gm"})
     *
     * @return A list of all aliases that will trigger this command
     */
    public String[] getAliases();

    /**
     * String displayed when the user runs the help command, followed by any alias of this command
     *
     * @return A {@code String} defining the use and/or purpose of the command
     */
    public String getHelp();
}