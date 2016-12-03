package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.Logon;

import java.util.List;

public class LogonAdapter extends BaseAdapter {
    Context context;
    List<Logon> list;

    public LogonAdapter(Context context, List<Logon> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.layout_logon_adapter,
                    null
            );
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.setting_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.setting_img);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getTitle());
        viewHolder.imageView.setImageResource(list.get(position).getImg());
        return convertView;
    }
    public class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
