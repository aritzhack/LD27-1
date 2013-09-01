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
        if (!this.elapsedTime.containsKey(section)) return 0;
        return this.elapsedTime.get(section);
    }
}
