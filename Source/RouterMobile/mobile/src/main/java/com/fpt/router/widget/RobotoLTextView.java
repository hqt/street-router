package com.fpt.router.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fpt.router.widget.RobotoView;

/**
 * Created by TuanBM on 8/21/14.
 */
public class RobotoLTextView extends RobotoView {


    public RobotoLTextView(Context context) {
        super(context,"Roboto-Thin.ttf");
    }

    public RobotoLTextView(Context context, AttributeSet attrs) {
        super(context, attrs,"Roboto-Thin.ttf");
    }

    public RobotoLTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle,"Roboto-Thin.ttf");
    }
}
