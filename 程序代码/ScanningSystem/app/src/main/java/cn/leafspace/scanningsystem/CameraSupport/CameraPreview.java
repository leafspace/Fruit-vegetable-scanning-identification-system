package cn.leafspace.scanningsystem.CameraSupport;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.pm.PackageManager;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "main";
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    /* 检测设备是否存在Camera硬件 */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;                                                     //存在
        } else {
            return false;                                                    //不存在
        }
    }
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.surfaceHolder = getHolder();                                    //通过SurfaceView获得SurfaceHolder
        this.surfaceHolder.addCallback(this);                                //为SurfaceHolder指定回调
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //设置Surface不维护自己的缓冲区
    }

    public void surfaceCreated(SurfaceHolder holder) {                       //当Surface被创建之后，开始Camera的预览
        try {
            this.camera.setPreviewDisplay(holder);
            this.camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {}

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (this.surfaceHolder.getSurface() == null){
            return;
        }

        try {
            this.camera.stopPreview();                                       //停止Camera的预览
        } catch (Exception e){
            e.printStackTrace();
        }

        this.surfaceCreated(this.surfaceHolder);
    }
}