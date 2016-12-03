package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/10/24.
 */
public class TalkingAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<com.jredu.liuyifan.mygoalapplication.entity.Talking> mList ;
    ViewHolder_0 mViewHolder_0 = null;
    ViewHolder_1 mViewHolder_1 = null;

    public TalkingAdapter(Context mContext, ArrayList<com.jredu.liuyifan.mygoalapplication.entity.Talking> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case 0:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_talking_0,null);
                    mViewHolder_0 = new ViewHolder_0();
                    mViewHolder_0.imageView = (ImageView) convertView.findViewById(R.id.img_view);
                    mViewHolder_0.mTextView_content = (TextView) convertView.findViewById(R.id.text_content);
                    mViewHolder_0.mTextView_date = (TextView) convertView.findViewById(R.id.date);
                    convertView.setTag(mViewHolder_0);
                    break;
                case 1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_talking_1,null);
                    mViewHolder_1 = new ViewHolder_1();
                    mViewHolder_1.imageView = (ImageView) convertView.findViewById(R.id.img_view);
                    mViewHolder_1.mTextView_content = (TextView) convertView.findViewById(R.id.text_content);
                    mViewHolder_1.mTextView_date = (TextView) convertView.findViewById(R.id.date);
                    convertView.setTag(mViewHolder_1);
                    break;
            }
        }else {
            switch (type){
                case 0:
                    mViewHolder_0 = (ViewHolder_0) convertView.getTag();
                    break;
                case 1:
                    mViewHolder_1 = (ViewHolder_1) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case 0:
                //mViewHolder_0.imageView.setImageBitmap(mList.get(position).getBitmap());
                mViewHolder_0.mTextView_content.setText(mList.get(position).getContent());
                mViewHolder_0.mTextView_date.setText(mList.get(position).getDate());
                break;
            case 1:
               // mViewHolder_1.imageView.setImageBitmap(mList.get(position).getBitmap());
                mViewHolder_1.mTextView_content.setText(mList.get(position).getContent());
                mViewHolder_1.mTextView_date.setText(mList.get(position).getDate());
                break;
        }
        return convertView;
    }

    public class ViewHolder_0{
        ImageView imageView;
        TextView mTextView_content;
        TextView mTextView_date;
    }

    public class ViewHolder_1{
        ImageView imageView;
        TextView mTextView_content;
        TextView mTextView_date;
    }




    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType() == "0"){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
