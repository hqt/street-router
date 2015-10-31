package com.fpt.router.library.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.fpt.router.library.config.AppConstants;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.fpt.router.library.config.AppConstants.*;

/**
 * Created by Nguyen Trung Nam on 10/30/2015.
 */
public class SoundUtils {
    static MediaPlayer mp = new MediaPlayer();

    public static void playSound(Context context, String message) {

        FileDescriptor fileDescriptor = null;
        try {
            String key = StringUtils.normalizeFileCache(message);
            DiskLruSoundCache soundCache = new DiskLruSoundCache(context, FileCache.FOLDER_NAME, FileCache.SYSTEM_SIZE);

            if (!soundCache.containsKey(key)) {
                Log.e("hqthao", "Shit Happen here. No File Key Found");
                return;
            }

            byte[] sound = soundCache.getData(key);

            // create temporary mp3 file
            File tempMp3 = File.createTempFile("kurchina", "mp3", context.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(sound);
            fos.close();

            // play this temporary file
            FileInputStream fis = new FileInputStream(tempMp3);
            fileDescriptor = fis.getFD();

            mp.reset();

            mp.setDataSource(fileDescriptor);
            mp.prepare();

            mp.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
