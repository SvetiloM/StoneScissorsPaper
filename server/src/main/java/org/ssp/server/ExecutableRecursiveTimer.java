package org.ssp.server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public final class ExecutableRecursiveTimer extends Timer {

    private final Consumer<Byte> consumer;

    private final byte[] delays = {30, 15, 10, 5, 3, 1};

    public ExecutableRecursiveTimer(Consumer<Byte> consumer) {
        this.consumer = consumer;
    }

    public void start() {
        int i = 0;
        consumer.accept(delays[0]);
        schedule(i + 1);
    }

    private void schedule(int i) {
        if (i < delays.length) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    consumer.accept(delays[i]);
                    schedule(i + 1);
                }
            };
            schedule(task, delays[i] * 1000);
        }
    }
}
