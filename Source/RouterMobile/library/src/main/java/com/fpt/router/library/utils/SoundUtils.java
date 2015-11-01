package com.fpt.router.library.utils;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Nguyen Trung Nam on 10/30/2015.
 */
public class SoundUtils {
    static MediaPlayer mp = new MediaPlayer();

    public static void playSound (String soundName) {
        Uri uri;
        File fileSMAC;
        FileDescriptor fd = null;

        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/smac");
        dir.mkdirs();
        fileSMAC = new File(dir, soundName+".wav");
        uri = Uri.fromFile(fileSMAC);
        Log.e("QUYYY111", "--" + fileSMAC.getAbsolutePath() + "--");

        try {
            FileInputStream fis = new FileInputStream(fileSMAC);
            fd = fis.getFD();

            mp.reset();

            try {
                mp.setDataSource(fd);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
