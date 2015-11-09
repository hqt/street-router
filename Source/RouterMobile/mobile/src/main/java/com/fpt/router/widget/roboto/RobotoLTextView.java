package com.fpt.router.widget.roboto;

import android.content.Context;
import android.util.AttributeSet;

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
