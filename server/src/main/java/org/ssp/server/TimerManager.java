package org.ssp.server;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.function.Consumer;

@Component
public class TimerManager {

    private HashMap<Integer, ExecutableRecursiveTimer> timers = new HashMap();

    public void start(Consumer<Byte> consumer, int id) {
        ExecutableRecursiveTimer oldTimer = timers.get(id);
        if (oldTimer != null) {
            oldTimer.cancel();
            timers.remove(id);
        }
        ExecutableRecursiveTimer timer = new ExecutableRecursiveTimer(consumer);
        timer.start();
        timers.put(id, timer);
    }

    public void stop(int id) {
        ExecutableRecursiveTimer oldTimer = timers.get(id);
        if (oldTimer != null) {
            oldTimer.cancel();
            timers.remove(id);
        }
    }

}
