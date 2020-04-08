package com.example.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Button button;
    SurfaceView surfaceView;
    android.hardware.Camera camera;
    SurfaceHolder surfaceHolder;
    android.hardware.Camera.PictureCallback pictureCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        getSupportActionBar().setTitle("Camera");
        this.button = findViewById(R.id.button);
        this.surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

        pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                pic = Bitmap.createBitmap(pic, 0,0, pic.getWidth(), pic.getHeight(), null, true);
                String path = getPath();
                storePhoto(pic, path);
                Toast.makeText(getApplicationContext(), "Saved pic!", Toast.LENGTH_LONG).show();
                CameraActivity.this.camera.startPreview();
            }
        };
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        } catch (Exception e) {

        }

        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewFrameRate(20);
        parameters.setPreviewSize(352, 288);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch(Exception e) {

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private String getPath() {
        return "MyFile.jpg";
    }

    private void storePhoto(Bitmap pic, String path) {
        File file = new File(Environment.getExternalStorageDirectory(), "/DCIM/" + path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch(Exception e) {

        }
    }
}
