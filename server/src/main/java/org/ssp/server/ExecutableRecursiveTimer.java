package org.ssp.server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public final class ExecutableRecursiveTimer extends Timer {

    private final Consumer<Integer> consumer;

    private final int[] delays = {0, 15, 5, 5, 2, 1};
    private final int[] sec = {30, 15, 10, 5, 3, 1};

    public ExecutableRecursiveTimer(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    public void start() {
        int i = 0;
        consumer.accept(sec[0]);
        schedule(i + 1);
    }

    private void schedule(int i) {
        if (i < delays.length) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    consumer.accept(sec[i]);
                    schedule(i + 1);
                }
            };
            schedule(task, delays[i] * 1000);
        }
    }
}
