package com.fpt.router.widget.roboto;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by TuanBM on 8/21/14.
 */
public class RobotoTTextView extends RobotoView {

    public RobotoTTextView(Context context) {
        super(context, "Roboto-Light.ttf");
    }

    public RobotoTTextView(Context context, AttributeSet attrs) {
        super(context, attrs, "Roboto-Light.ttf");
    }

    public RobotoTTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, "Roboto-Light.ttf");
    }
}
