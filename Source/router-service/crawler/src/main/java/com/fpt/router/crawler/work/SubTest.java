package com.fpt.router.crawler.work;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by datnt on 10/9/2015.
 */
public class SubTest {

    public static void main(String args[]) {
        String fileNameDemo = "50-Depart.json";
        Pattern p = Pattern.compile("^(\\d+)-(\\w+).(\\w+)");

        Matcher m = p.matcher(fileNameDemo);
        if (m.find()) {
            System.out.println(m.group(0));

            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
        }
    }


}
