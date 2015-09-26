package com.fpt.router.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class ScreenUtils {
    public static final int NUMBER_IMAGE_VERTICAL_NORMAL_SCREEN = 3;
    public static final int NUMBER_IMAGE_HORIZONTAL_NORMAL_SCREEN = 2;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Activity activity) {
        if (UIUtils.hasHoneycomb()) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size;
        } else {
            Display display = ((WindowManager) activity.getSystemService(activity.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            return new Point(width, height);
        }
    }
}
