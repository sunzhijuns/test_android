package com.example.sunzhijun.szjgames;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    private static int REQ_1=1;
    private static int REQ_2=2;
    private ImageView mImageView;

    private String mFilePath;
    private String pngPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mImageView = (ImageView) findViewById(R.id.iv_camera);
        mFilePath = getRootPath()+File.separator + "SZJ_NOTES";
        File fileDir = new File(mFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    public void startCamera1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQ_1);

    }
    public void startCamera2(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pngPath = mFilePath + File.separator +  System.currentTimeMillis() +".png";
        Log.i("时间",pngPath);
//        //第一种方法
//        ContentValues contentValues = new ContentValues(1);
//        contentValues.put(MediaStore.Images.Media.DATA, pngPath);
//        Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);

        //第二种方法
        File file = new File(pngPath);
        if (file.exists()) {
            file.delete();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(this, file));

        startActivityForResult(intent,REQ_2);


    }


    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.sunzhijun.szjgames", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public void customCamera(View view){
        startActivity(new Intent(this,CustomCamera.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==REQ_1){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
            }else if (requestCode == REQ_2){
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(pngPath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if (fis != null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
