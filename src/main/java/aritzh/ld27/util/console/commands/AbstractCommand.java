package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AbstractCommand implements ICommand {

    @Override
    public String run(Game game, String command) { return null;};

    @Override
    public String[] getAliases() {return new String[0];}

    @Override
    public String getHelp() {return null;}
}
