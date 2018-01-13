package com.example.sunzhijun.szjgames;


import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.sunzhijun.treeview.utils.file.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sunzhijun on 2017/12/12.
 */

public class CustomCamera extends AppCompatActivity implements SurfaceHolder.Callback{
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private Camera.PictureCallback mPicCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String path = FileUtils.getAppRootPath() + File.separator + System.currentTimeMillis()+".png";
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
                Intent intent = CustomCamera.this.getIntent();
                intent.putExtra("picPath",file.getAbsolutePath());
                Log.i("路径：",file.getAbsolutePath());
//                startActivity(intent);
                CustomCamera.this.setResult(RESULT_OK,intent);
                CustomCamera.this.finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {


            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG","-----------onCreate-----------");
        mCamera = getCamera();

        setContentView(R.layout.custom_camera);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG","-----------onResume-----------");
        if (mCamera == null){
            mCamera = getCamera();
            if (mHolder != null){
                setStartPreView(mCamera,mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG","-----------onPause-----------");
        releaseCamera();
    }

    public void capture(View view){
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(800,400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success){
                    mCamera.takePicture(null,null,mPicCallback);
                }
            }
        });
    }

    private Camera getCamera(){
        Log.i("TAG","-----------getCamera-----------");
        Camera camera;
        try {
            Log.i("TAG","-------try----getCamera-----------");



            int numberOfCameras = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < numberOfCameras; i++) {
            }
            Log.i("TAG","-------try----getCamera-----------" + numberOfCameras);
            camera = Camera.open();
            Log.i("创建camera：",String.valueOf(camera));
        } catch (Exception e) {
            Log.i("TAG","---------catch--Exception-----------");
            camera = null;
            e.printStackTrace();
        }

        return camera;

    }

    private void setStartPreView(Camera camera, SurfaceHolder holder){
        try {
            Log.i("空camera：",String.valueOf(camera));
            Log.i("空holder：",String.valueOf(holder));
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseCamera(){

        if (mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("TAG","-----------surfaceCreated-----------");
        if (mCamera == null){
            mCamera = getCamera();
        }
        if (mHolder != null){
            setStartPreView(mCamera,mHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("TAG","-----------surfaceChanged-----------");
        if (mCamera == null){
            mCamera = getCamera();
        }
        mCamera.stopPreview();
        setStartPreView(mCamera,mHolder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("TAG","-----------surfaceDestroyed-----------");
        releaseCamera();
    }
}
