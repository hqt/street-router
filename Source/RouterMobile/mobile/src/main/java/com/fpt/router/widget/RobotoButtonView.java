package com.fpt.router.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.fpt.router.R;

/**
 * Created by TuanBM on 8/21/14.
 */
public class RobotoButtonView extends Button {


    public RobotoButtonView(Context context) {
        super(context);
        init();
    }

    public RobotoButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (!this.isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "Roboto-Light.ttf");
            setTypeface(tf);
           // setBackground(getResources().getDrawable(R.drawable.button_bg));
        }
    }

}
