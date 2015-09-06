package com.fpt.router.work;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class Work {
    public void run() {
        BusCrawlerPipe crawler = new BusCrawlerPipe();
        crawler.run();


    }

    public static void main(String[] args) {
        Work work = new Work();
        work.run();
    }
}
