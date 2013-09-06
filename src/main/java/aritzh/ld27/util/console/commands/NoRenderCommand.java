package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NoRenderCommand implements ICommand {
    @Override
    public String run(Game game, String command) {
        game.getLevel().getPlayer().toggleNoRender();
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"render", "no-render", "norender"};
    }

    @Override
    public String getHelp() {
        return "Toggles rendering of the player on and off";
    }
}
