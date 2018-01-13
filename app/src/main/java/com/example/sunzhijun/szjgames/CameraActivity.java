package com.example.sunzhijun.szjgames;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sunzhijun.treeview.utils.file.FileOperator;
import com.sunzhijun.treeview.utils.file.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    private static int REQ_1=1;
    private static int REQ_2=2;
    private static int REQ_3=3;
    private ImageView mImageView;

    private String mFilePath;
    private String pngPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileOperator.test();
        setContentView(R.layout.activity_camera);
        mImageView = (ImageView) findViewById(R.id.iv_camera);
        mFilePath = FileUtils.getAppRootPath();
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

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public void customCamera(View view){
        startActivityForResult(new Intent(this,CustomCamera.class),REQ_3);
//        startActivity(new Intent(this,CustomCamera.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==REQ_1){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
            }else if (requestCode == REQ_2 || requestCode ==  REQ_3){
                FileInputStream fis = null;
                if (requestCode ==  REQ_3){
                    pngPath = (String) data.getExtras().get("picPath");
                }
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
