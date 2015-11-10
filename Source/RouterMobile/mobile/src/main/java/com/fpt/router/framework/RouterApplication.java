package com.fpt.router.framework;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.NUTITEQ;
import com.fpt.router.library.model.message.MapPackageMessage;
import com.fpt.router.service.GPSServiceOld;
import com.nutiteq.datasources.PackageManagerTileDataSource;
import com.nutiteq.packagemanager.NutiteqPackageManager;
import com.nutiteq.packagemanager.PackageAction;
import com.nutiteq.packagemanager.PackageErrorType;
import com.nutiteq.packagemanager.PackageManagerListener;
import com.nutiteq.packagemanager.PackageStatus;
import com.nutiteq.ui.MapView;

import java.io.File;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class RouterApplication extends Application {

    public static Context mContext;

    private EventBus bus;

    /**
     *  SDK contains PackageManagerTileDataSource that will automatically display all imported or downloaded packages. PackageManagerTileDataSource
     * needs PackageManager instance, so it is best to create a PackageManager instance at application level
     * share this instance between activities.
     *
     */
    public static NutiteqPackageManager packageManager;

    // data source for offline map
    public static PackageManagerTileDataSource dataSource;

    /** this is just application context. Use this function carefully to avoid error */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //JodaTimeAndroid.init(this);
        mContext = getApplicationContext();

        bus = EventBus.getDefault();

        // build realm default configuration
        RealmConfiguration config = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(config);

        // set some system variable
        AppConstants.SERVER_ADDRESS = "http://" + PrefStore.getServerIp() + ":" + PrefStore.getServerPort();
        AppConstants.buildAPILink();

        // GPSServiceOld gpsService = new GPSServiceOld(mContext);
        Intent intent = new Intent(mContext, GPSServiceOld.class);
        startService(intent);

        setUpNutiteq();

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        packageManager.stop(false);
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    // set up nutiteq package manager. so can use for loading offline map
    private void setUpNutiteq() {
        // Register license
        MapView.registerLicense(NUTITEQ.NUTITEQ_KEY, getApplicationContext());

        // Create package manager
        File packageFolder = new File(getApplicationContext().getExternalFilesDir(null), "mappackages");
        if (!(packageFolder.mkdirs() || packageFolder.isDirectory())) {
            Log.e("hqthao", "Could not create package folder!");
        }

        // set up Nutiteq Package Manager
        packageManager = new NutiteqPackageManager(NUTITEQ.GLOBAL_STREET_MAP, packageFolder.getAbsolutePath());
        packageManager.setPackageManagerListener(new PackageListener());

        packageManager.start();

        dataSource = new PackageManagerTileDataSource(packageManager);
    }

    /**
     * Listener for package manager events.
     */
    class PackageListener extends PackageManagerListener {
        @Override
        public void onPackageListUpdated() {
            Log.e("hqthao", "onPackageListUpdated");
            // do thing now :)
            if (!PrefStore.getIsMapDownloaded()) {
                packageManager.startPackageDownload(NUTITEQ.NOTITEQ_VN_CODE);
            }

        }

        @Override
        public void onPackageListFailed() {
            Log.e("hqthao", "onPackageListFailed");
            // do nothing now
        }

        // update status package
        @Override
        public void onPackageStatusChanged(String id, int version, PackageStatus status) {
            String action = null;
            if (!id.equals(NUTITEQ.NOTITEQ_VN_CODE)) {
                Log.e("hqthao", "Wrong Country Code. So fatal error");
            }

            // finish download. or already contain in system
            if (status.getCurrentAction() == PackageAction.PACKAGE_ACTION_READY) {
                action = NUTITEQ.PackageStatus.PACKAGE_ACTION_READY;
                PrefStore.setIsMapDownloaded(true);
            } else if (status.getCurrentAction() == PackageAction.PACKAGE_ACTION_WAITING) {
                action = NUTITEQ.PackageStatus.PACKAGE_ACTION_WAITING;
            } else if (status.getCurrentAction() == PackageAction.PACKAGE_ACTION_COPYING) {
                action = NUTITEQ.PackageStatus.PACKAGE_ACTION_COPYING;
            } else if (status.getCurrentAction() == PackageAction.PACKAGE_ACTION_DOWNLOADING) {
                action = NUTITEQ.PackageStatus.PACKAGE_ACTION_DOWNLOADING;
            } else if (status.getCurrentAction() == PackageAction.PACKAGE_ACTION_REMOVING) {
                action = NUTITEQ.PackageStatus.PACKAGE_ACTION_REMOVING;
            }

            MapPackageMessage mapPackage = new MapPackageMessage();
            mapPackage.packageId = id;
            mapPackage.packageStatus = action;
            mapPackage.progress = status.getProgress();
            Log.e("hqthao", "id: " + mapPackage.packageId +
                    "\tstatus: " + mapPackage.packageStatus +
                    "\tprogress:" + mapPackage.progress);

            bus.post(mapPackage);
        }

        @Override
        public void onPackageCancelled(String id, int version) {
            Log.e("hqthao", "onPackageCancelled");
            if (!id.equals(NUTITEQ.NOTITEQ_VN_CODE)) {
                Log.e("hqthao", "Wrong Country Code. So fatal error");
            }
        }

        @Override
        public void onPackageUpdated(String id, int version) {
            Log.e("hqthao", "onPackageUpdated");
            PrefStore.setIsMapDownloaded(true);

            MapPackageMessage mapPackage = new MapPackageMessage();
            mapPackage.packageId = id;
            mapPackage.packageStatus = NUTITEQ.PackageStatus.PACKAGE_ACTION_READY;
            bus.post(mapPackage);
        }

        @Override
        public void onPackageFailed(String id, int version, PackageErrorType errorType) {
            PrefStore.setIsMapDownloaded(false);
        }
    }
}
