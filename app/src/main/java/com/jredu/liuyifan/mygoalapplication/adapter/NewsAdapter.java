package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.News;

import java.util.List;

/**
 * Created by DELL on 2016/9/9.
 */
public class NewsAdapter extends BaseAdapter {
    Context context;
    List<News> list;
    public final static int ZERO = 0;
    public final static int ONE = 1;
    public final static int TWO = 2;

    public NewsAdapter(Context context, List<News> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        ViewHolder3 viewHolder3 = null;
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case ZERO:
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.layout_news_adapter,
                            null
                    );
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.textView1 = (TextView) convertView.findViewById(R.id.none_text);
                    viewHolder1.textView2 = (TextView) convertView.findViewById(R.id.none_text_user);
                    viewHolder1.imageView1 = (ImageView) convertView.findViewById(R.id.none_img);
                    viewHolder1.mImageView = (ImageView) convertView.findViewById(R.id.exit);
                    convertView.setTag(viewHolder1);
                    break;
                case ONE:
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.layout_news_adapter_two,
                            null
                    );
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.textView1 = (TextView) convertView.findViewById(R.id.none_text);
                    viewHolder2.textView2 = (TextView) convertView.findViewById(R.id.none_text_user);
                    viewHolder2.imageView1 = (ImageView) convertView.findViewById(R.id.none_img);
                    viewHolder2.imageView3 = (ImageView) convertView.findViewById(R.id.one);
                    viewHolder2.mImageView = (ImageView) convertView.findViewById(R.id.exit);
                    convertView.setTag(viewHolder2);
                    break;
                case TWO:
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.layout_news_adapter_three,
                            null
                    );
                    viewHolder3 = new ViewHolder3();
                    viewHolder3.textView1 = (TextView) convertView.findViewById(R.id.none_text);
                    viewHolder3.textView2 = (TextView) convertView.findViewById(R.id.none_text_user);
                    viewHolder3.imageView1 = (ImageView) convertView.findViewById(R.id.none_img);
                    viewHolder3.imageView2 = (ImageView) convertView.findViewById(R.id.one);
                    viewHolder3.imageView3 = (ImageView) convertView.findViewById(R.id.two);
                    viewHolder3.imageView4 = (ImageView) convertView.findViewById(R.id.three);
                    viewHolder3.mImageView = (ImageView) convertView.findViewById(R.id.exit);
                    convertView.setTag(viewHolder3);
                    break;
            }
        }else{
            switch (type){
                case ZERO:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case ONE:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
                case TWO:
                    viewHolder3 = (ViewHolder3) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case ZERO:
                viewHolder1.textView1.setText(list.get(position).getTitle());
                viewHolder1.textView2.setText(list.get(position).getUser());
                viewHolder1.imageView1.setImageResource(list.get(position).getImg4());
                viewHolder1.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss(v);
                    }
                });
                break;
            case ONE:
                viewHolder2.textView1.setText(list.get(position).getTitle());
                viewHolder2.textView2.setText(list.get(position).getUser());
                viewHolder2.imageView1.setImageResource(list.get(position).getImg4());
                viewHolder2.imageView3.setImageResource(list.get(position).getImg3());
                viewHolder2.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss(v);
                    }
                });
                break;
            case TWO:
                viewHolder3.textView1.setText(list.get(position).getTitle());
                viewHolder3.textView2.setText(list.get(position).getUser());
                viewHolder3.imageView1.setImageResource(list.get(position).getImg4());
                viewHolder3.imageView2.setImageResource(list.get(position).getImg2());
                viewHolder3.imageView3.setImageResource(list.get(position).getImg3());
                viewHolder3.imageView4.setImageResource(list.get(position).getImg1());
                viewHolder3.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss(v);
                    }
                });
                break;
        }
        return convertView;
    }
    public class ViewHolder1{
        TextView textView1;
        TextView textView2;
        ImageView imageView1;
        ImageView mImageView;
    }
    public class ViewHolder2{
        TextView textView1;
        TextView textView2;
        ImageView imageView1;
        ImageView imageView3;
        ImageView mImageView;
    }
    public class ViewHolder3{
        TextView textView1;
        TextView textView2;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView mImageView;
    }
    public void dismiss(View v){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popup,null);
        final PopupWindow popupWindow = new PopupWindow(
                view,
                100,
                100
        );
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(v);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.exit);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.line1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getNum() == ZERO){
            return ZERO;
        }
        if (list.get(position).getNum() == ONE){
            return ONE;
        }else {
            return TWO;
        }
    }
}
