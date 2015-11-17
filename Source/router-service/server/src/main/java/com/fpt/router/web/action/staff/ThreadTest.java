package com.fpt.router.web.action.staff;

/**
 * Created by datnt on 11/13/2015.
 */
public class ThreadTest {

    // Create multiple theads.
    static class MyThread implements Runnable {

        Thread thread;

        MyThread(String name) {
            thread = new Thread(this, name);
            thread.start();
        }

        // begin execution thread
        @Override
        public void run() {
            System.out.println(thread.getName() + " starting.");

            try {
                for (int count = 0; count < 50; count++) {
                    Thread.sleep(4000);
                    System.out.println("In " + thread.getName() + ", count is " + count);
                }
            } catch (InterruptedException ex) {
                System.out.println(thread.getName() + " is interrupted!");
            }

            System.out.println(thread.getName() + " terminating.");
        }
    }

    public static void main(String args[]) {
        System.out.println("Main Thread starting.");

        MyThread mt1 = new MyThread("Child #1");
        MyThread mt2 = new MyThread("Child #2");
        MyThread mt3 = new MyThread("Child #3");

        try {
            for (int i = 0; i < 50; i++) {
                System.out.print(".");

                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            System.out.println("Main Thread is interrupted.");
        }

        System.out.println("Main Thread is ending.");
    }


}
