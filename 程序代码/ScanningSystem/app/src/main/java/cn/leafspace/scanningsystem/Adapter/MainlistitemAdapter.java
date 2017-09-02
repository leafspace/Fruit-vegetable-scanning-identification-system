package cn.leafspace.scanningsystem.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import cn.leafspace.scanningsystem.ScanningsystemActivity.R;

public class MainlistitemAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    protected LayoutInflater inflater;
    private int[] imageList = {R.drawable.fruits_1, R.drawable.fruits_2, R.drawable.fruits_3, R.drawable.fruits_4, R.drawable.fruits_5};

    public MainlistitemAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public Object getItem(int position) {
        return this.imageList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(this.resource, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageResource(this.imageList[position]);
        return convertView;
    }
}