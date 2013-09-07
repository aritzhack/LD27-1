package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NoClipCommand implements ICommand<Game> {

    @Override
    public String[] run(Game game, String command) {
        game.getLevel().getPlayer().toggleNoClip();
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"no-clip", "clip", "noclip"};
    }

    @Override
    public String getHelp() {
        return "Toggles no-clipping in the current player. (Removes collision)";
    }
}
