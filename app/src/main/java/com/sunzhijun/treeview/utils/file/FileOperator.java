package com.sunzhijun.treeview.utils.file;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sunzhijun on 2017/12/12.
 */

public class FileOperator {
    /**
     * 复制文件或目录
     * @param srcDir 源目录 or 文件 eg:/mnt/sdcard/file or directory
     * @param destDir 目标目录 eg:/mnt/sdcard/destDir/
     * @return
     */
    public static boolean copyDir(String srcDir,String destDir){
        File srcFile = new File(srcDir);
        //判断文件或目录是否存在
        if (!srcFile.exists()){
            return false;
        }
        //判断是否是目录
        if (srcFile.isDirectory()){
            File[] files = srcFile.listFiles();
            File destFile = new File(destDir);
            //创建目标目录
            if (!destFile.exists()){
                destFile.mkdirs();
            }
            //遍历要复制该目录下的全部文件
            for (File file : files) {
                if (file.isDirectory()){
                    Log.i("是目录，递归src",file.getPath()+File.separator);
                    Log.i("是目录，递归des",destDir + file.getName() + File.separator);
                    if(!copyDir(file.getPath()+File.separator,
                            destDir + file.getName() + File.separator))
                        return false;
                }else{//进行文件复制
                    Log.i("同目录，文件复制：文件src",file.getPath());
                    Log.i("同目录，文件复制：文件des",destDir + file.getName());
                    if(!copyFile(file.getPath(),destDir + file.getName()))
                        return false;
                }
            }
        }else{
            Log.i("文件复制路径srcDir:",srcDir);
            Log.i("文件复制路径destDir:",destDir);
            if(!copyFileToDir(srcDir,destDir))
                return false;
        }
        return true;
    }

    /**
     * 测试复制文件
     * @param srcDir 源目录 or 文件 eg:/mnt/sdcard/file or directory
     * @param destDir 目标目录 eg:/mnt/sdcard/destDir/
     */
    private static void copyTest(String srcDir, String destDir){
        Log.i("文件操作：","开始复制");
        if (! copyDir(srcDir,destDir)){
            Log.i("文件操作：","复制失败");
        }else{
            Log.i("文件操作：","复制成功");
        }
    }




    /**
     * 把文件拷贝到某目录
     * @param srcFile 文件 eg:/mnt/sdcard/file
     * @param destDir 目标目录 eg:/mnt/sdcard/destDir/
     * @return
     */
    public static boolean copyFileToDir(String srcFile, String destDir){
        File fileDir = new File(destDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        Log.i("拷贝文件没名字",destDir);
        String destFile = destDir + new File(srcFile).getName();
        Log.i("拷贝文件有名字",destFile);

        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = streamFrom.read(buffer)) > 0){
                streamTo.write(buffer,0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制文件
     * @param srcFileName 文件 eg:/mnt/sdcard/a/name.xxx
     * @param destFileName 文件 eg:/mnt/sdcard/b/name.xxx
     * @return
     */
    private static boolean copyFile(String srcFileName, String destFileName){
        try {
            InputStream streamFrom = new FileInputStream(srcFileName);
            OutputStream streamTo = new FileOutputStream(destFileName);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer))>0){
                streamTo.write(buffer,0,len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移动文件目录到某一路径下
     * @param srcFile 源目录 or 文件 eg:/mnt/sdcard/file or directory
     * @param destDir 目标目录 eg:/mnt/sdcard/destDir/
     * @return
     */
    public static boolean moveFile(String srcFile, String destDir){
        // 复制后删除原目录
        if (!copyDir(srcFile,destDir)){
            Log.e("复制","失败:" + destDir);
            return false;
        }
        if(!deleteFile(new File(srcFile))){
            Log.e("删除","失败:" + srcFile);
            return false;
        }
        return true;

    }


    /**
     * 删除文件，包含目录
     * @param delFile 源目录 or 文件 eg:/mnt/sdcard/file or directory 生成的文件
     * @return
     */
    public static boolean deleteFile(File delFile) {
        //如果是目录递归删除
        if (delFile == null || !delFile.exists()){
            Log.e("未删除：","文件不存在");
            return false;
        }
        if (delFile.isDirectory()){
            File[] files = delFile.listFiles();
            for (File file: files){
                if (!deleteFile(file)){
                    Log.e("删除失败：","位置：" + file.getPath());
                    return false;
                }
            }
        }else{
            if(!delFile.delete()){
                Log.e("删除失败：","位置：" + delFile.getPath());
                return false;
            }
        }
        // 如果不执行下面这句，目录下所有文件都删除了， 但是还剩下子目录空文件夹
        delFile.delete();
        return true;
    }

    /**
     * 源目录 or 文件 eg:/mnt/sdcard/file or directory
     * @param delFileDir
     */
    public static boolean deleteFile(String delFileDir){
        if (delFileDir == null || "".equals(delFileDir)){
            Log.e("删除失败","文件名为空");
            return false;
        }
        if(!deleteFile(new File(delFileDir)))
            return false;
        return true;
    }

    /**
     * 测试删除文件
     * @param delFileDir 源目录 or 文件 eg:/mnt/sdcard/file or directory
     */
    private static boolean deleteTest(String delFileDir){
        if(!deleteFile(delFileDir))
            return false;
        return true;
    }

    public static void test(){
        String appPath = FileUtils.getAppRootPath();
        String destDir = FileUtils.getRootPath() + File.separator + "aaaaaaa/cc/" + File.separator;
        String srcDir = appPath;
        copyTest(srcDir,destDir);
        String delDir = FileUtils.getRootPath() + File.separator + "aaaaaaa/cc/dd";
        if (!deleteTest(delDir)){
            Log.e("删除：","失败");
        }else{
            Log.e("删除：","成功");
        }


    }


}
