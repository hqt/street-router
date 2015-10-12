package com.fpt.router.artifacter.utils;

import com.fpt.router.artifacter.config.Config;

/**
 * Created by datnt on 10/11/2015.
 */
public class PaginationUtils {

    public static int getOffset(int pageNum) {
        return (pageNum-1) * Config.WEB.ITEM_PER_PAGE;
    }

    public static int getLimit(int pageNum) {
        return pageNum * Config.WEB.ITEM_PER_PAGE - 1;
    }
}
