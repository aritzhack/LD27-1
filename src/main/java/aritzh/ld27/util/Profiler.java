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

    public synchronized void startSection(String section){
        section = section.toLowerCase();

        if(startTime.containsKey(section)) throw new IllegalArgumentException("Section \"" + section + "\" was been started!");

        startTime.put(section, System.nanoTime());
        trace.push(section);
    }

     public synchronized void endSection(String section){
         section = section.toLowerCase();
         if(!startTime.containsKey(section)) throw new IllegalArgumentException("Section \"" + section + "\" hasn't been started!");

         long before = startTime.remove(section);
         elapsedTime.put(section, System.nanoTime() - before);

    }

    public synchronized void endSection(){
        if(trace.empty()) throw new IllegalStateException("There are no open sections to close!");
        this.endSection(trace.pop());
    }

    public synchronized long getSectionTime(String section){
        section = section.toLowerCase();
        if(!elapsedTime.containsKey(section)) return 0;
        return elapsedTime.get(section);
    }
}
