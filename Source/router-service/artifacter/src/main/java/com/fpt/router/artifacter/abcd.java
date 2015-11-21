package com.fpt.router.artifacter;

import com.fpt.router.artifacter.algorithm.MultiPointAlgorithm;
import com.fpt.router.artifacter.dao.StaffDAO;

/**
 * Created by datnt on 10/21/2015.
 */
public class abcd {

    public static void main(String args[]) {


        StaffDAO dao = new StaffDAO();
        dao.findStaffByEmail("huynhquangthao@gmail.com", "123456");
        int a = 3;
    }

}
