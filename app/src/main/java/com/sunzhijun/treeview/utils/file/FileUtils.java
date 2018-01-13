package com.sunzhijun.treeview.utils.file;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunzhijun on 2017/12/12.
 */

public class FileUtils {

    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }
    public static String getAppRootPath(){
        String path = getRootPath()+ File.separator + "SZJ_NOTES";
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return path;
    }
}
