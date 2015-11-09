package com.fpt.router.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by TuanBM on 8/21/14.
 */
public class RobotoMTextView extends RobotoView {


    public RobotoMTextView(Context context) {
        super(context,"Roboto-Medium.ttf");
    }

    public RobotoMTextView(Context context, AttributeSet attrs) {
        super(context, attrs,"Roboto-Medium.ttf");
    }

    public RobotoMTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle,"Roboto-Medium.ttf");
    }
}
