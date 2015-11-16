package com.fpt.router.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.fpt.router.R;

/**
 * Created by ngoan on 11/13/2015.
 */
public class CustomerSearchTextView extends TextView {
    private static final int MAX_LENGTH = 100;
    public CustomerSearchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.setBackgroundResource(android.R.drawable.edit_text);
        }

        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public synchronized boolean onEditorAction(TextView v,
                                                       int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        String value = "";
        final String viewMode = "editing";
        final String viewSide = "right";
        final Drawable x = getResources().getDrawable(R.drawable.ic_close_black_24dp);

        // The height will be set the same with [X] icon
        setHeight(x.getBounds().height());

        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        Drawable x2 = viewMode.equals("never") ? null : viewMode
                .equals("always") ? x : viewMode.equals("editing") ? (value
                .equals("") ? null : x)
                : viewMode.equals("unlessEditing") ? (value.equals("") ? x
                : null) : null;
        // Display search icon in text field
        final Drawable searchIcon = getResources().getDrawable(
                android.R.drawable.ic_search_category_default);
        searchIcon.setBounds(0, 0, x.getIntrinsicWidth(),
                x.getIntrinsicHeight());

        setCompoundDrawables(searchIcon, null, viewSide.equals("right") ? x2
                : null, null);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawables()[viewSide.equals("left") ? 0 : 2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                // x pressed
                if ((viewSide.equals("left") && event.getX() < getPaddingLeft()
                        + x.getIntrinsicWidth())
                        || (viewSide.equals("right") && event.getX() > getWidth()
                        - getPaddingRight() - x.getIntrinsicWidth())) {
                    Drawable x3 = viewMode.equals("never") ? null : viewMode
                            .equals("always") ? x
                            : viewMode.equals("editing") ? null : viewMode
                            .equals("unlessEditing") ? x : null;
                    setText("");
                    setCompoundDrawables(searchIcon, null,
                            viewSide.equals("right") ? x3 : null, null);
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Drawable x4 = viewMode.equals("never") ? null : viewMode
                        .equals("always") ? x
                        : viewMode.equals("editing") ? (getText().toString()
                        .equals("") ? null : x) : viewMode
                        .equals("unlessEditing") ? (getText()
                        .toString().equals("") ? x : null) : null;
                setCompoundDrawables(searchIcon, null,
                        viewSide.equals("right") ? x4 : null, null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > MAX_LENGTH) {
                    setText(s.subSequence(0, MAX_LENGTH));
                   // Selection.setSelection(s,MAX_LENGTH);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
    }
}
