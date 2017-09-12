package cn.leafspace.scanningsystem.ScanningsystemActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.support.v7.app.AppCompatActivity;
import cn.leafspace.scanningsystem.Adapter.MainlistitemAdapter;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ListView listView1;
    private TextView textView1, textView2;
    private String[] textList = {"西瓜", "柑橘", "橙子", "苹果", "木瓜"};
    private String[] fileList = {"fruits_1.txt", "fruits_2.txt", "fruits_3.txt", "fruits_4.txt", "fruits_5.txt"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView1 = (ListView) findViewById(R.id.listView1);
        this.textView1 = (TextView) findViewById(R.id.textView1);
        this.textView2 = (TextView) findViewById(R.id.textView2);

        this.listView1.setAdapter(new MainlistitemAdapter(this, R.layout.mainlistitem_layout));
        this.listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setShowText(position);
            }
        });

        this.setShowText(0);
    }

    private String getFileContent(String name) {
        String str = null;
        InputStream inputStream;
        try {
            inputStream = getResources().getAssets().open(name);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            str = new String(buffer,"utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void setShowText(int position) {
        this.textView1.setText(this.textList[position]);
        this.textView2.setText(this.getFileContent(this.fileList[position]));
    }

    public void BeginCheck(View view) {
        final Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}
