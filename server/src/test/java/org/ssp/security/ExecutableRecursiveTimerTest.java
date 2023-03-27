package org.ssp.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ssp.server.ExecutableRecursiveTimer;

import java.util.function.Consumer;

public class ExecutableRecursiveTimerTest {

    @Test
    public void start() throws InterruptedException {
        final int[] consumerCounter = {0};
        final int[] runCounter = {0};
        Consumer<Integer> consumer = o -> consumerCounter[0]++;
        Runnable runnable = () -> runCounter[0]++;
        int[] sec = {3, 2, 1};
        ExecutableRecursiveTimer timer = new ExecutableRecursiveTimer(consumer, runnable, sec);

        timer.start();

        Thread.sleep(4000);

        Assertions.assertEquals(consumerCounter[0], 3);
        Assertions.assertEquals(runCounter[0], 1);
    }
}
