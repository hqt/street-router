package com.fpt.router.web.action;

import org.hibernate.annotations.SourceType;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by datnt on 11/3/2015.
 */
public class asd {


    public String convertLocalTimeToString(LocalTime localTime) {

        localTime = new LocalTime(20, 30);
        Date date = localTime.toDateTimeToday().toDate();
        String patternTime = "h:mm a";
        SimpleDateFormat simpleTime = new SimpleDateFormat(patternTime);
        String result = simpleTime.format(date);

        if (result != null) {
            return result;
        }

        return "";
    }


    public static class MyThread implements Runnable {
        String thrName;

        public MyThread(String thrName) {
            this.thrName = thrName;
        }

        /*
            Entry point of thread
         */
        @Override
        public void run() {
            System.out.println(thrName + " starting.");
            try {
                for (int count = 0; count < 10; count++) {
                    Thread.sleep(10000);
                    System.out.println("In " + thrName + ", count is " + count);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        System.out.println("Main Thread starting.");

        // first, constructor a MyThread object.
        MyThread mt = new MyThread("Child #1");

        // next, constructor a thread from that object
        Thread newThr = new Thread(mt);

        // finally, start execution of the thread
        newThr.start();

        for (int i = 0; i < 50; i++) {
            System.out.print(".");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main thread ending.");
    }

}
