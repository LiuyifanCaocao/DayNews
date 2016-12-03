package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.Image;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/10/11.
 */
public class PhotoAdapter extends BaseAdapter{
    Context mContext;
    ArrayList<Image> mList;
    ViewHolder mViewHolder;

    public PhotoAdapter(Context mContext, ArrayList<Image> mlist) {
        this.mContext = mContext;
        this.mList = mlist;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_photo,null);
            mViewHolder = new ViewHolder();
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.text_view);
            mViewHolder.imgView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        set_rotate(mViewHolder.imgView);
        mViewHolder.textView.setText(mList.get(position).getPicUrl());
        mViewHolder.imgView.setImageBitmap(mList.get(position).getBitmap());

        return convertView;
    }

    private class ViewHolder{
        ImageView imgView;
        TextView textView;
    }

    public void set_rotate( ImageView imageView){
        RotateAnimation animation = new RotateAnimation(0f, 8f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(0);//设定转一圈的时间
        animation.setRepeatCount(1);//设定循环
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }
}
