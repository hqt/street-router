package com.fpt.router.widget.roboto;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by TuanBM on 8/21/14.
 */
public class RobotoTCheckbox extends CheckBox {


    public RobotoTCheckbox(Context context) {
        super(context);
        init();
    }

    public RobotoTCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoTCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if(!this.isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "Roboto-Light.ttf");
            setTypeface(tf);
        }
    }


}
