package cn.leafspace.scanningsystem.ScanningsystemActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import cn.leafspace.scanningsystem.VoiceSupport.AudioRecoder;

public class AudioActivity extends AppCompatActivity {
    private Button button;
    private LinearLayout linearLayout;
    private AudioRecoder audioRecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);

        this.button = (Button) findViewById(R.id.button);
        this.linearLayout = (LinearLayout) findViewById(R.id.linerLayout);

        this.audioRecoder = new AudioRecoder();
        this.audioRecoder.setOnAudioStatusUpdateListener(new AudioRecoder.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {                     //录音中....db为声音分贝，time为录音时长
            }

            @Override
            public void onStop(String filePath) {                            //录音结束，filePath为保存路径
            }
        });


        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        button.setText("松开保存");
                        audioRecoder.startRecord();
                        break;

                    case MotionEvent.ACTION_UP:
                        audioRecoder.stopRecord();                           //结束录音（保存录音文件）
                        button.setText("按住说话");
                        break;
                }
                return true;
            }
        });
    }
}
