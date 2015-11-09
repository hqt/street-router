package com.fpt.router.widget.roboto;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ThaoHQ on 9/12/2014.
 */
public class RobotoEditText extends EditText {

    public RobotoEditText(Context context) {
        super(context);
        init();
    }

    public RobotoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (!this.isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "Roboto-Light.ttf");
            setTypeface(tf);
        }
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(32);
        this.setFilters(filterArray);
    }
}
