package com.fpt.router.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by asus on 10/7/2015.
 */
public class LockableListView extends ListView {

    private boolean mScrollable = true;

    public LockableListView(Context context) {
        super(context);
    }

    public LockableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (mScrollable) {
                    return super.onTouchEvent(ev);
                }
                // only continue to handle the touch event if scrolling enabled
                return mScrollable; // mScrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (!mScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

}
