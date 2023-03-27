package org.ssp.server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public final class ExecutableRecursiveTimer extends Timer {

    private final Consumer<Integer> consumer;
    private final Runnable lose;

    private final int[] sec;

    public ExecutableRecursiveTimer(Consumer<Integer> consumer, Runnable lose, int[] sec) {
        this.consumer = consumer;
        this.lose = lose;
        this.sec = sec;
    }

    public void start() {
        int i = 0;
        consumer.accept(sec[0]);
        schedule(i + 1);
    }

    private void schedule(int i) {
        if (i < sec.length) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    consumer.accept(sec[i]);
                    schedule(i + 1);
                }
            };
            schedule(task, getDelay(i));
        } else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    lose.run();
                }
            };
            schedule(task, 1000);
        }
    }

    private int getDelay(int i) {
        return 1000 * (sec[i - 1] - sec[i]);
    }
}
