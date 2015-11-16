package com.fpt.router.web.action.staff.comparer;

import com.fpt.router.artifacter.model.entity.PathInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by datnt on 11/14/2015.
 */
public class ComparePathInfo {

    private List<PathInfo> listPathInfoDB;
    private List<PathInfo> listPathInfoSource;
    public List pathInfoNof; // wait for thao define PahtInfo Notification entity.

    public ComparePathInfo(List<PathInfo> listPathInfoDB, List<PathInfo> listPathInfoSource) {
        this.listPathInfoDB = listPathInfoDB;
        this.listPathInfoSource = listPathInfoSource;
        this.pathInfoNof = new ArrayList();
    }

    public void run() {
        processComparePathThread();
    }

    public void processComparePathThread() {

    }

    private class ComparePathInfoThread implements Runnable {

        private PathInfo pathInfoDB;
        private PathInfo pathInfoSource;

        public ComparePathInfoThread(PathInfo pathInfoDB, PathInfo pathInfoSource) {
            this.pathInfoDB = pathInfoDB;
            this.pathInfoSource = pathInfoSource;
        }

        @Override
        public void run() {
            comparePathInfo();
        }

        public void comparePathInfo() {
            int routeIdDB = this.pathInfoDB.getRoute().getRouteNo();
            int routeIdSource = this.pathInfoSource.getRoute().getRouteNo();
            if (routeIdDB != routeIdSource) {
                return;
            }

            if (this.pathInfoDB.getPathInfoNo() != this.pathInfoSource.getPathInfoNo()) {
                return;
            }

            boolean canAdd = false;
            String codeStationDB = this.pathInfoDB.getFrom().getCodeId();
            String codeStationSource = this.pathInfoDB.getFrom().getCodeId();
            if (!codeStationDB.equals(codeStationSource)) {
                // here for create PathInfo Notification.
                System.out.println("Vary PathInfo");
                pathInfoNof.add(this.pathInfoSource);
            }
        }
    }

    public void sort(List<PathInfo> pathInfos) {
       Collections.sort(pathInfos, new Comparator<PathInfo>() {
           @Override
           public int compare(PathInfo o1, PathInfo o2) {
               return o1.getPathInfoNo() - o2.getPathInfoNo();
           }
       });
    }
}
