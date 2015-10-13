package com.fpt.router.utils;

import android.graphics.drawable.Drawable;

import com.fpt.router.framework.RouterApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loading resource from system
 * Created by Huynh Quang Thao on 9/19/15.
 */
public class ResourceUtils {

    public static String[] loadStringArray(int ResourceId) {
        return RouterApplication.getAppContext().getResources().getStringArray(ResourceId);
    }

    public static Drawable getDrawableFromResId(int id) {
        return RouterApplication.getAppContext().getResources().getDrawable(id);
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
