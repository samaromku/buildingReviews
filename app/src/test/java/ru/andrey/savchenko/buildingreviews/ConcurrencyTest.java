package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

/**
 * Created by Andrey on 22.05.2018.
 */

public class ConcurrencyTest {
    @Test
    public void testConcurrency() {
        Increase increase = new Increase();
        Thread thread1 = new Thread(new Counter(increase));
        Thread thread2 = new Thread(new Counter(increase));

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(increase.number);
    }

    class Increase{
        public int number;
        final Object lock = new Object();

        private void increase() {
            synchronized (lock) {
                number++;
            }
        }
    }


    class Counter implements Runnable {
        private Increase increase;

        public Counter(Increase increase) {
            this.increase = increase;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                increase.increase();
            }
        }
    }
}
