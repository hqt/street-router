package com.fpt.router.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fpt.router.library.model.entity.Route;

import static com.fpt.router.utils.LogUtils.makeLogTag;

/**
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class RouteDatabase extends SQLiteOpenHelper {

    private static String TAG = makeLogTag(SQLiteOpenHelper.class);

    private Context mContext;

    public static final String DATABASE_NAME = "street_router.db";
    public static final int DATABASE_VERSION = 1;

    public RouteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public RouteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
