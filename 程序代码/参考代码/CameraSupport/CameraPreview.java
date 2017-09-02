package cn.leafspace.scanningsystem.CameraSupport;

import android.util.Log;
import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.pm.PackageManager;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "main";
    private SurfaceHolder mHolder;
    private Camera mCamera;

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
        mCamera = camera;

        // 通过SurfaceView获得SurfaceHolder
        mHolder = getHolder();
        // 为SurfaceHolder指定回调
        mHolder.addCallback(this);
        // 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面 在Android3.0之后弃用
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // 当Surface被创建之后，开始Camera的预览
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "预览失败");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Surface发生改变的时候将被调用，第一次显示到界面的时候也会被调用
        if (mHolder.getSurface() == null){
        // 如果Surface为空，不继续操作
          return;
        }

        // 停止Camera的预览
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            Log.d(TAG, "当Surface改变后，停止预览出错");
        }

        // 在预览前可以指定Camera的各项参数

        // 重新开始预览
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "预览Camera出错");
        }
    }
}