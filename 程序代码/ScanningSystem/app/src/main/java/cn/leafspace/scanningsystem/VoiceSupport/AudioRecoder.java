package cn.leafspace.scanningsystem.VoiceSupport;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

public class AudioRecoder {
    public static final int MAX_LENGTH = 1000 * 60 * 10;                     //最大录音时长1000*60*10;

    private String filePath;                                                 //文件路径
    private String FolderPath;                                               //文件夹路径
    private MediaRecorder mediaRecorder;
    private OnAudioStatusUpdateListener audioStatusUpdateListener;

    private long startTime;
    private long endTime;

    private int BASE = 1;
    private int SPACE = 100;                                                 //间隔取样时间

    public AudioRecoder() {                                                  //默认保存路径为/sdcard/record/下
        this(Environment.getExternalStorageDirectory() + "/record/");
    }

    public AudioRecoder(String filePath) {
        File path = new File(filePath);
        if(!path.exists()) {
            path.mkdirs();
        }
        this.FolderPath = filePath;
    }

    /**
    * 开始录音 使用amr格式
    *      录音文件
    * @return
    */
    public void startRecord() {
        // 开始录音
        //Initial：实例化MediaRecorder对象
        if (mediaRecorder == null)
            mediaRecorder = new MediaRecorder();
        try {
            //setAudioSource/setVedioSource
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);     //设置麦克风
            //设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
            * 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
            * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
            */
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            filePath = FolderPath + "recoder" + ".amr" ;
            //准备
            mediaRecorder.setOutputFile(filePath);
            mediaRecorder.setMaxDuration(MAX_LENGTH);
            mediaRecorder.prepare();
            //开始
            mediaRecorder.start();

            //获取开始时间
            startTime = System.currentTimeMillis();
            updateMicStatus();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long stopRecord() {
        if (mediaRecorder == null) {
            return 0L;
        }
        endTime = System.currentTimeMillis();
        try {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            audioStatusUpdateListener.onStop(filePath);
            filePath = "";
        } catch (RuntimeException e) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            filePath = "";
        }
        return endTime - startTime;
    }

    public void cancelRecord() {
        try {

            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

        } catch (RuntimeException e){
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        filePath = "";
    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    private void updateMicStatus() {                                         //更新麦克状态
        if (mediaRecorder != null) {
            double ratio = (double)mediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if(null != audioStatusUpdateListener) {
                    audioStatusUpdateListener.onUpdate(db,System.currentTimeMillis()-startTime);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        /**
        * 录音中...
        * @param db 当前声音分贝
        * @param time 录音时长
        */
        public void onUpdate(double db, long time);

        /**
        * 停止录音
        * @param filePath 保存路径
        */
        public void onStop(String filePath);
    }
}
