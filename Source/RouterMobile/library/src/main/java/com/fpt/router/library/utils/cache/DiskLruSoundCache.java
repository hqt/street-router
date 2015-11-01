package com.fpt.router.library.utils.cache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.fpt.router.library.BuildConfig;
import com.fpt.router.library.utils.FileUtils;
import com.fpt.router.library.utils.cache.DiskLruCache;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Huynh Quang Thao on 10/31/15.
 */
public class DiskLruSoundCache {

    private DiskLruCache mDiskCache;
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    private static final String TAG = "DiskLruSoundCache";

    public DiskLruSoundCache(Context context, String uniqueName, int diskCacheSize) {
        try {
            final File diskCacheDir = getDiskCacheDir(context, uniqueName);
            mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeDataToFile(byte[] data, DiskLruCache.Editor editor)
            throws FileNotFoundException, IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), FileUtils.IO_BUFFER_SIZE);
            out.write(data);
            return true;
        } finally {
            if (out != null) {
                // we don't need to call flush because it has been already called in close.
                // out.flush();
                out.close();
            }
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {

        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !FileUtils.isExternalStorageRemovable() ?
                        FileUtils.getExternalCacheDir(context).getPath() :
                        context.getCacheDir().getPath();

        Log.e("hqthao", "FUCK_QUY: " + cachePath);

        return new File(cachePath + File.separator + uniqueName);
    }

    public void put(String key, byte[] data) {

        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(key);
            if (editor == null) {
                return;
            }

            if (writeDataToFile(data, editor)) {
                mDiskCache.flush();
                editor.commit();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "image put on disk cache " + key);
                }
            } else {
                editor.abort();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
                }
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
            }
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }

    }

    public byte[] getData(String key) {

       byte[] data = null;
        DiskLruCache.Snapshot snapshot = null;
        try {

            snapshot = mDiskCache.get(key);
            if (snapshot == null) {
                return null;
            }
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                // http://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int nRead;
                byte[] buffer = new byte[16384];
                while ((nRead = in.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, nRead);
                }
                bos.flush();
                data = bos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        if (BuildConfig.DEBUG) {
            Log.d("cache_test_DISK_", data == null ? "" : "image read from disk " + key);
        }

        return data;

    }

    public boolean containsKey(String key) {

        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;

    }

    public void clearCache() {
        if (BuildConfig.DEBUG) {
            Log.d("cache_test_DISK_", "disk cache CLEARED");
        }
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }

}