package org.ssp.server;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class TimerManager {

    //todo lose
    private final Map<Integer, ExecutableRecursiveTimer> timers = new HashMap<>();

    public void start(Consumer<Integer> consumer, Runnable lose, int id) {
        ExecutableRecursiveTimer oldTimer = timers.get(id);
        if (oldTimer != null) {
            oldTimer.cancel();
            timers.remove(id);
        }
        ExecutableRecursiveTimer timer = new ExecutableRecursiveTimer(consumer, lose);
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
