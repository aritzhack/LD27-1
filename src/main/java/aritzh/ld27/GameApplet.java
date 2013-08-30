package aritzh.ld27;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.HeadlessException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GameApplet extends Applet {

    Game game;

    /**
     * Constructs a new Applet.
     * <p/>
     * Note: Many methods in <code>java.applet.Applet</code>
     * may be invoked by the applet only after the applet is
     * fully constructed; applet should avoid calling methods
     * in <code>java.applet.Applet</code> in the constructor.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    public GameApplet() throws HeadlessException {
        super();
        Dimension size = new Dimension(800, 450);
        this.setSize(size);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it has been loaded into the system. It is always
     * called before the first time that the <code>start</code> method is
     * called.
     * <p/>
     * A subclass of <code>Applet</code> should override this method if
     * it has initialization to perform. For example, an applet with
     * threads would use the <code>init</code> method to create the
     * threads and the <code>destroy</code> method to kill them.
     * <p/>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * @see Applet#destroy()
     * @see Applet#start()
     * @see Applet#stop()
     */
    @Override
    public void init() {
        super.init();    //To change body of overridden methods use File | Settings | File Templates.
        this.game = new Game(400, 225, true, 2);
        this.add(game);
        game.start();
    }

    /**
     * Called by the browser or applet viewer to inform
     * this applet that it should stop its execution. It is called when
     * the Web page that contains this applet has been replaced by
     * another page, and also just before the applet is to be destroyed.
     * <p/>
     * A subclass of <code>Applet</code> should override this method if
     * it has any operation that it wants to perform each time the Web
     * page containing it is no longer visible. For example, an applet
     * with animation might want to use the <code>start</code> method to
     * resume animation, and the <code>stop</code> method to suspend the
     * animation.
     * <p/>
     * The implementation of this method provided by the
     * <code>Applet</code> class does nothing.
     *
     * @see Applet#destroy()
     * @see Applet#init()
     */
    @Override
    public void stop() {
        super.stop();
        game.stop();
    }
}
