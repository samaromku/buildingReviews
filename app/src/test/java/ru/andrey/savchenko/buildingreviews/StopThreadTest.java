package ru.andrey.savchenko.buildingreviews;

import org.junit.Test;

/**
 * Created by savchenko on 23.05.18.
 */

public class StopThreadTest {
    @Test
    public void stopThread(){
        Thread2 thread2 = new Thread2();
        Thread1 thread1 = new Thread1(thread2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Thread1 extends Thread{
        Thread2 thread2;

        public Thread1(Thread2 thread2) {
            this.thread2 = thread2;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("thread 1");
                if(i==2){
                    thread2.stop();
                }
            }
        }
    }

    class Thread2 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if(!Thread.interrupted()) {
                    System.out.println("thread 2");
                }
            }
        }
    }
}
