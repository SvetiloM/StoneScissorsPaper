package org.ssp.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ssp.server.TimerManager;

import java.util.function.Consumer;

public class TimerManagerTest {

    @Test
    public void commonTest() throws InterruptedException {
        TimerManager manager = new TimerManager();
        final int[] consumerCounter = {0};
        final int[] runCounter = {0};
        Consumer<Integer> consumer = o -> consumerCounter[0]++;
        Runnable runnable = () -> runCounter[0]++;

        manager.start(consumer,runnable, 0);
        manager.stop(0);

        Thread.sleep(30000);

        Assertions.assertEquals(consumerCounter[0], 1);
        Assertions.assertEquals(runCounter[0], 0);
    }
}
