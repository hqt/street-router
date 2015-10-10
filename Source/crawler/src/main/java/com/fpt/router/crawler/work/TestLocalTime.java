package com.fpt.router.crawler.work;

import com.fpt.router.artifacter.utils.TimeUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * Created by datnt on 10/4/2015.
 */
public class TestLocalTime {

    public static void main(String args[]) {
        Date date = DateUtil.getJavaDate(0.20833333333333334);

        TimeUtils timeUtils = new TimeUtils();
        LocalTime localTime = timeUtils.convertExcelDate(date);
        System.out.println(localTime);
    }

}
