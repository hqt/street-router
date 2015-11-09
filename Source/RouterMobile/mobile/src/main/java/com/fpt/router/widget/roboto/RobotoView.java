package com.fpt.router.widget.roboto;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by TuanBM on 10/3/2014.
 */
public abstract class RobotoView extends TextView {
    String typeFacePath;

    public RobotoView(Context context, String typeFacePath) {
        super(context);
        this.typeFacePath = typeFacePath;
    }

    public RobotoView(Context context, AttributeSet attrs, String typeFacePath) {
        super(context, attrs);
        this.typeFacePath = typeFacePath;
    }

    public RobotoView(Context context, AttributeSet attrs, int defStyleAttr, String typeFacePath) {
        super(context, attrs, defStyleAttr);
        this.typeFacePath = typeFacePath;
    }

    private void init() {
        if (!this.isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    this.typeFacePath);
            setTypeface(tf);
        }
    }
}
