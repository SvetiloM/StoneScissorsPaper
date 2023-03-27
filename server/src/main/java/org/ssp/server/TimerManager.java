package org.ssp.server;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class TimerManager {

    private final Map<Integer, ExecutableRecursiveTimer> timers = new HashMap<>();
    private final int[] sec = {30, 15, 10, 5, 3, 1};

    public void start(Consumer<Integer> consumer, Runnable lose, int id) {
        ExecutableRecursiveTimer oldTimer = timers.get(id);
        if (oldTimer != null) {
            oldTimer.cancel();
            timers.remove(id);
        }
        ExecutableRecursiveTimer timer = new ExecutableRecursiveTimer(consumer, lose, sec);
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
