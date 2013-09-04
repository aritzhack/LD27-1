package aritzh.ld27.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Profiler {

    Map<String, Long> startTime = new HashMap<String, Long>();
    Map<String, Long> elapsedTime = new HashMap<String, Long>();
    Stack<String> trace = new Stack<String>();

    private static final Profiler DEFAULT;
    private static final Map<String, Profiler> profilers = new HashMap<String, Profiler>();

    static {
        DEFAULT = new Profiler();
    }

    private Profiler() {

    }

    public static Profiler getInstance() {
        return Profiler.DEFAULT;
    }

    public static Profiler getInstance(String name) {
        if (!Profiler.profilers.containsKey(name)) {
            Profiler p = new Profiler();
            Profiler.profilers.put(name, p);
            return p;
        } else return Profiler.profilers.get(name);
    }

    public synchronized void startSection(String section) {
        section = section.toLowerCase();

        if (this.startTime.containsKey(section))
            throw new IllegalArgumentException("Section \"" + section + "\" was been started!");

        this.startTime.put(section, System.nanoTime());
        this.trace.push(section);
    }

    public synchronized void endSection() {
        if (this.trace.empty()) throw new IllegalStateException("There are no open sections to close!");
        this.endSection(this.trace.pop());
    }

    public synchronized void endSection(String section) {
        section = section.toLowerCase();
        if (!this.startTime.containsKey(section))
            throw new IllegalArgumentException("Section \"" + section + "\" hasn't been started!");

        long before = this.startTime.remove(section);
        this.elapsedTime.put(section, System.nanoTime() - before);

    }

    public synchronized long getSectionTime(String section) {
        section = section.toLowerCase();
        if (!this.elapsedTime.containsKey(section)) return -1;
        return this.elapsedTime.get(section);
    }
}
