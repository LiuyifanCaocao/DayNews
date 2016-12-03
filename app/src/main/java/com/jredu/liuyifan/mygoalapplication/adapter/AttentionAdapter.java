package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.Attention;
import com.jredu.liuyifan.mygoalapplication.util.Back_Ground_Bmp;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/10/10.
 */
public class AttentionAdapter extends BaseAdapter{
    Context context;
    ArrayList<Attention> mList;
    RequestQueue mRequestQueue;
    ViewHolder mViewHolder = null;

    public AttentionAdapter(Context context, ArrayList<Attention> mList, RequestQueue requestQueue) {
        this.context = context;
        this.mList = mList;
        this.mRequestQueue = requestQueue;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_attention_adapter,null);
            mViewHolder = new ViewHolder();
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            mViewHolder.textView_title = (TextView) convertView.findViewById(R.id.text_view_title);
            mViewHolder.textView_ctime = (TextView) convertView.findViewById(R.id.text_view_ctime);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        set_img(mViewHolder.imageView,mList.get(position).getPicUrl());
        mViewHolder.textView_title.setText(mList.get(position).getTitle());
        mViewHolder.textView_ctime.setText(mList.get(position).getTime());
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView_title;
        TextView textView_ctime;
    }

    //设置图片
    private void set_img(final ImageView imageView, String s ){
        ImageRequest mImageRequest = new ImageRequest(
                s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(Back_Ground_Bmp.toRoundBitmap(response));

                    }
                },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.w2345_image_file_copy_7);
                    }
                }
        );
        mRequestQueue.add(mImageRequest);
    }

}
