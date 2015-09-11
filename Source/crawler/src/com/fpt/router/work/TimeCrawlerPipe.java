package com.fpt.router.work;

import java.util.HashMap;
import java.util.Map;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class TimeCrawlerPipe {

    public Map<Integer, String> busTimeExcelLinks;

    public TimeCrawlerPipe() {
        busTimeExcelLinks = new HashMap<Integer, String>();
    }

    private void parseAllExcelLinks() {

    }

    public void run() {
        parseAllExcelLinks();
    }
}
