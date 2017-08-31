import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class AudioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void recoder(View view) {
        //当前布局文件的根layout
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        
                mButton = (Button) findViewById(R.id.button);
        
                //PopupWindow的布局文件
                final View view = View.inflate(this, R.layout.layout_microphone, null);
        
                final PopupWindowFactory mPop = new PopupWindowFactory(this,view);
        
                //PopupWindow布局文件里面的控件
                mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
                mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        
                mAudioRecoderUtils = new AudioRecoder();
        
                //录音回调
                mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
        
                    //录音中....db为声音分贝，time为录音时长
                    @Override
                    public void onUpdate(double db, long time) {
                        //根据分贝值来设置录音时话筒图标的上下波动，下面有讲解
                        mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                        mTextView.setText(TimeUtils.long2String(time));
                    }
        
                    //录音结束，filePath为保存路径
                    @Override
                    public void onStop(String filePath) {
                        Toast.makeText(AudioActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                        mTextView.setText(TimeUtils.long2String(0));
                    }
                });
        
                //Button的touch监听
                mButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
        
                        switch (event.getAction()){
        
                            case MotionEvent.ACTION_DOWN:
        
                                mPop.showAtLocation(rl,Gravity.CENTER,0,0);
        
                                mButton.setText("松开保存");
                                mAudioRecoderUtils.startRecord();
        
        
                                break;
        
                            case MotionEvent.ACTION_UP:
        
                                mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
                                mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                                mPop.dismiss();
                                mButton.setText("按住说话");
        
                                break;
                        }
                        return true;
                    }
                });
    }
}
