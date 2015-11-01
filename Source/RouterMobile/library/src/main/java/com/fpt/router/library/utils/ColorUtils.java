package com.fpt.router.library.utils;

import android.graphics.Color;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * Created by Huynh Quang Thao on 10/31/15.
 */
public class ColorUtils {

    public static void setImageColor(ImageView image, int color) {
        DrawableCompat.setTint(image.getDrawable(), color);
    }

    public static int convertHexaColorToInt(String hexa) {
        return Color.parseColor(hexa.trim().toLowerCase());
    }

}
