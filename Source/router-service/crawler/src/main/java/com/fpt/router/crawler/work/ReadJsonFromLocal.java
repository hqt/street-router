package com.fpt.router.crawler.work;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by datnt on 10/8/2015.
 */
public class ReadJsonFromLocal {

    private int busId;
    private boolean isgo;

    public File getFolderJson() {
        ClassLoader classLoader = getClass().getClassLoader();
        File f = new File(classLoader.getResource("json").getFile());

        return f;
    }

    public InputStream readJsonFile(File folder, int busId, boolean isgo) {

        if (folder.isDirectory()) {
            for (File f : folder.listFiles()) {
                String fileName = f.getName();
                int idFile = Integer.parseInt(fileName);
                if (idFile == busId) {

                }
            }
        }

        InputStream in = null;/*
        try {
            in = new FileInputStream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        return in;
    }

    public static void main(String args[]) {

    }

}
