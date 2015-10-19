package com.fpt.router.library.utils;

import com.fpt.router.library.utils.string.AccentRemoval;

/**
 * Created by Huynh Quang Thao on 10/19/15.
 */
public class StringUtils {
    public static String normalizeString(String s) {
        s = s.toLowerCase().trim().replaceAll("\\s+", " ");
        s = s.replaceAll("\\.", "");
        s = AccentRemoval.removeAccent(s);
        return s;
    }
}
