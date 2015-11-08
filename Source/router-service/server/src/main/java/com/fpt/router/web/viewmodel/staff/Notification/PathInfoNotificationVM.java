package com.fpt.router.web.viewmodel.staff.Notification;

import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.web.viewmodel.staff.PathinfoListVM;
import com.fpt.router.web.viewmodel.staff.StationVM;

import java.util.List;

/**
 * Created by datnt on 11/1/2015.
 */
public class PathInfoNotificationVM extends NotificationVM {

    public PathinfoListVM newPath;
    public PathinfoListVM oldPath;

    public PathInfoNotificationVM(List<PathInfo> pathServer, List<PathInfo> pathDB) {
        this.newPath = new PathinfoListVM(pathServer);
        this.oldPath = new PathinfoListVM(pathDB);
    }
}
