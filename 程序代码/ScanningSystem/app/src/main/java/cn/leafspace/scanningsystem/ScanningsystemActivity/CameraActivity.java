package cn.leafspace.scanningsystem.ScanningsystemActivity;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;
import cn.leafspace.scanningsystem.CameraSupport.CameraPreview;

public class CameraActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;

    private Camera getCameraInstance() {                                                         //打开一个Camera
        try {
            return Camera.open();
        } catch (Exception e) {
            Toast.makeText(this, "无法打开摄像头, 请检查设置!", Toast.LENGTH_LONG);
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //创建预览类，并与Camera关联，最后添加到界面布局中
        this.camera = this.getCameraInstance();
        if (this.camera == null) {
            final Intent intent = new Intent(CameraActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            this.cameraPreview = new CameraPreview(this, this.camera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(this.cameraPreview);
        }
    }

    @Override
    protected void onDestroy() {
        if(this.camera != null){
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
        super.onDestroy();
    }
    
}