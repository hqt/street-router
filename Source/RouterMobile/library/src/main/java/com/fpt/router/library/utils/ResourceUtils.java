package com.fpt.router.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loading resource from system
 * Created by Huynh Quang Thao on 9/19/15.
 */
public class ResourceUtils {

    public static String[] loadStringArray(Context context, int ResourceId) {
        return context.getResources().getStringArray(ResourceId);
    }

    public static Drawable getDrawableFromResId(Context context, int id) {
        return context.getResources().getDrawable(id);
    }


    public static String readFile(String path) {
        //Get the text file
        File file = new File(path);

        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }
        return text.toString();
    }

}
