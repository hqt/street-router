package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.PathInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/28/2015.
 */
public class PathinfoListVM {

    private List<PathInfoVM> pathInfoVMs;

    public PathinfoListVM(List<PathInfo> pathInfos) {
        pathInfoVMs = new ArrayList<>();
        for (PathInfo p : pathInfos) {
            pathInfoVMs.add(new PathInfoVM(p));
        }
    }

    public List<PathInfoVM> getPathInfoVMs() {
        return pathInfoVMs;
    }

    public void setPathInfoVMs(List<PathInfoVM> pathInfoVMs) {
        this.pathInfoVMs = pathInfoVMs;
    }
}
