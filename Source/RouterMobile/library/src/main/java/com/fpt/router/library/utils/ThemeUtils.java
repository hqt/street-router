package com.fpt.router.library.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class ThemeUtils {
    /**
     * Resolves the given attribute id of the theme to a resource id
     */
    public static int getAttribute(Resources.Theme theme, int attrId) {
        final TypedValue outValue = new TypedValue();
        theme.resolveAttribute(attrId, outValue, true);
        return outValue.resourceId;
    }

    /**
     * Returns the resource id of the background used for buttons to show pressed and focused state
     */
    public static int getSelectableItemBackground(Resources.Theme theme) {
        return getAttribute(theme, android.R.attr.selectableItemBackground);
    }

    /**
     * Returns the resource id of the background used for list items that show activated background
     */
    public static int getActivatedBackground(Resources.Theme theme) {
        return getAttribute(theme, android.R.attr.activatedBackgroundIndicator);
    }
}
