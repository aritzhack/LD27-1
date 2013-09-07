package aritzh.ld27.util.console.commands;

import aritzh.ld27.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GodModeCommand implements ICommand<Game> {
    @Override
    public String[] run(Game game, String command) {
        game.getLevel().getPlayer().toggleGod();
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"god", "godmode", "god-mode", "gm"};
    }

    @Override
    public String getHelp() {
        return "Toggles god-mode for the current player. (Makes it invincible, and restores all it's health)";
    }
}
