package com.fpt.router.widget.roboto;

import android.content.Context;
import android.util.AttributeSet;

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
