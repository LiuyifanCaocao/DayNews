package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.entity.Joke;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by DELL on 2016/10/12.
 */
public class JokeAdapter  extends BaseAdapter{
    Context mContext;
    List<Joke> mList;
    RequestQueue mRequestQueue;
    ViewHolder mViewHolder = null;
    ViewHolder2 mViewHolder2 = null;
    RotateAnimation animation;
    AlphaAnimation alphaAnimation;
    boolean b = true;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    public JokeAdapter(Context context, List<Joke> list,RequestQueue requestQueue,boolean b) {
        mContext = context;
        mList = list;
        mRequestQueue = requestQueue;
        this.b = b;
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
        if(convertView == null){
            switch (type){
                case 0:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_joke,null);
                    mViewHolder = new ViewHolder();
                    mViewHolder.title = (TextView) convertView.findViewById(R.id.text);
                    mViewHolder.textView = (TextView) convertView.findViewById(R.id.name);
                    mViewHolder.img_load = (ImageView) convertView.findViewById(R.id.load);
                    mViewHolder.img = (WebView) convertView.findViewById(R.id.img_show);
                    convertView.setTag(mViewHolder);
                    break;
                case 1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_joke_2,null);
                    mViewHolder2 = new ViewHolder2();
                    mViewHolder2.title = (TextView) convertView.findViewById(R.id.title);
                    mViewHolder2.textView = (TextView) convertView.findViewById(R.id.content);
                    mViewHolder2.ct = (TextView) convertView.findViewById(R.id.ctime);
                    mViewHolder2.mImageView_two = (ImageView) convertView.findViewById(R.id.two_img);
                    mViewHolder2.relativeLayout1 = (RelativeLayout) convertView.findViewById(R.id.comment);
                    mViewHolder2.relativeLayout2 = (RelativeLayout) convertView.findViewById(R.id.digdown);
                    mViewHolder2.relativeLayout3 = (RelativeLayout) convertView.findViewById(R.id.review);
                    mViewHolder2.mImageView1 = (ImageView) convertView.findViewById(R.id.image_view_comment);
                    mViewHolder2.mImageView2 = (ImageView) convertView.findViewById(R.id.image_view_digdown);
                    mViewHolder2.mImageView3 = (ImageView) convertView.findViewById(R.id.image_view_review);
                    mViewHolder2.mTextView1 = (TextView) convertView.findViewById(R.id.text_comment);
                    mViewHolder2.mTextView2 = (TextView) convertView.findViewById(R.id.text_digdown);
                    mViewHolder2.mTextView3 = (TextView) convertView.findViewById(R.id.text_review);
                    mViewHolder2.mImageView1_add1 = (ImageView) convertView.findViewById(R.id.text_comment_add_1);
                    mViewHolder2.mImageView2_add1 = (ImageView) convertView.findViewById(R.id.text_digdown_add_1);

                    convertView.setTag(mViewHolder2);
                    break;
            }
        }else {
            switch (type){
                case 0:
                    mViewHolder = (ViewHolder) convertView.getTag();
                    break;
                case 1:
                    mViewHolder2 = (ViewHolder2) convertView.getTag();
            }
        }
        switch (type){
            case 0:
                mViewHolder.title.setText(mList.get(position).getTitle());
                if (b){
                    img_rotate(mViewHolder.img_load);
                    set_img(mViewHolder.img,mList.get(position).getImg(),mViewHolder.img_load);
                }
                // change_item_img(mViewHolder.img,mList.get(position).getImg());
                get_item_img(mList.get(position).getImg());
                break;
            case 1:
                mViewHolder2.title.setText(mList.get(position).getTitle());
                mViewHolder2.textView.setText(mList.get(position).getImg());
                mViewHolder2.ct.setText(mList.get(position).getId());
                int s1 = (int)(Math.random()*10000);
                int s2 = (int)(Math.random()*10000);
                int s3 = (int)(Math.random()*10000);
                mViewHolder2.mTextView1.setText(s1+"");
                mViewHolder2.mTextView2.setText(s2+"");
                mViewHolder2.mTextView3.setText(s3+"");
                change_comment(mViewHolder2.relativeLayout1,mViewHolder2.mImageView1,s1,mViewHolder2.mTextView1,mViewHolder2.mImageView1_add1);
                change_digdown(mViewHolder2.relativeLayout2,mViewHolder2.mImageView2,s2,mViewHolder2.mTextView2,mViewHolder2.mImageView2_add1);
                change_collection(mViewHolder2.mImageView_two,s3);
                break;
        }
        return convertView;
    }
    private class ViewHolder{
        WebView img;
        TextView title;
        TextView textView;
        ImageView img_load;
    }
    private class ViewHolder2{
        TextView title;
        TextView textView;
        TextView ct;
        RelativeLayout relativeLayout1;
        RelativeLayout relativeLayout2;
        RelativeLayout relativeLayout3;
        ImageView mImageView1;
        ImageView mImageView1_add1;
        ImageView mImageView2;
        ImageView mImageView2_add1;
        ImageView mImageView3;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        ImageView mImageView_two;

    }


    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType() == "1"){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //设置图片
    private void set_img(final WebView mWebView, String s, final ImageView imageView_load ){
        StringRequest mStringRequest = new StringRequest(
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imageView_load.clearAnimation();
                        imageView_load.setVisibility(View.GONE);
                        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                        mWebView.getSettings().setJavaScriptEnabled(true);
                        mWebView.loadDataWithBaseURL(null,response,"text/html","utf-8",null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String s = new String(new String(response.data, "utf-8"));
                    return Response.success(s, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mRequestQueue.add(mStringRequest);

    }

    public void change_size(TextView textView,float f){
        textView.setTextSize(f);
    }

    //每一项的图片
    public void change_item_img(final ImageView imageView, final String s){
        mViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequest mImageRequest = new ImageRequest(
                        s,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                imageView.setImageBitmap(response);
                            }
                        },
                        0,
                        0,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                imageView.setImageResource(R.drawable.small_loadpic_empty_listpage);
                            }
                        }
                );
                mRequestQueue.add(mImageRequest);
            }
        });
    }

    public void set_show_img(boolean b){
        if (this.b != b){
            this.b = b;
        }else {
            this.b = !b;
        }
    }

    //保存图片
    public void get_item_img(final String s){
        mViewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageRequest mImageRequest = new ImageRequest(
                        s,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {

                                File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = System.currentTimeMillis() + ".jpg";
                                File file = new File(appDir, fileName);
                                BufferedOutputStream bos = null;
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bos = new BufferedOutputStream(fos);
                                    response.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        bos.flush();
                                        bos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // 其次把文件插入到系统图库
                                try {
                                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                                            file.getAbsolutePath(), fileName, null);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                // 最后通知图库更新
                                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "Boohee")));






                                File dirFile = new File(ALBUM_PATH);
                                if (!dirFile.exists()) {
                                    dirFile.mkdir();
                                }
                                File myCaptureFile = new File(ALBUM_PATH + "test.png");
                                BufferedOutputStream bfos = null;
                                // BufferedInputStream bis = null;
                                try {
                                    bfos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                                    response.compress(Bitmap.CompressFormat.JPEG, 100, bfos);
                                    Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
                                    // bis = new BufferedInputStream(new FileInputStream(myCaptureFile));
                                    // Bitmap bitmap = BitmapFactory.decodeStream(bis);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        bfos.flush();
                                        bfos.close();
                                        //bis.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        0,
                        0,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                mRequestQueue.add(mImageRequest);
                return false;
            }
        });
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "图库")));
    }

    //图片旋转
    public void img_rotate(ImageView imageView){
        animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(5000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
        imageView.startAnimation(animation);
    }

    //赞加一
    public void change_comment(RelativeLayout relativeLayout, final ImageView imageView, final int i, final TextView textView, final ImageView imageView_add1){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals(i+"")){
                    imageView.setImageResource(R.drawable.comment_like_icon_press);
                    textView.setText((i+1)+"");
                    imageView_add1.setVisibility(View.VISIBLE);
                    alphaAnimation = new AlphaAnimation(1f,0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setRepeatCount(0);
                    imageView_add1.startAnimation(alphaAnimation);
                }else {
                    imageView.setImageResource(R.drawable.comment_like_icon_night);
                    textView.setText(i+"");
                    imageView_add1.setVisibility(View.GONE);

                }
            }
        });
    }
    //不赞减一
    public void change_digdown(RelativeLayout relativeLayout, final ImageView imageView, final int i, final TextView textView, final ImageView imageView_add1){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals(i+"")){
                    imageView.setImageResource(R.drawable.digdown_video_pressed);
                    textView.setText((i-1)+"");
                    imageView_add1.setVisibility(View.VISIBLE);
                    alphaAnimation = new AlphaAnimation(1f,0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setRepeatCount(0);
                    imageView_add1.startAnimation(alphaAnimation);
                }else {
                    imageView.setImageResource(R.drawable.digdown_video_normal_night);
                    textView.setText(i+"");
                    imageView_add1.setVisibility(View.GONE);
                }
            }
        });
    }
    //收藏
    public void change_collection(final ImageView imageView,final int i){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b = i;
                if (b == i){
                    imageView.setImageResource(R.drawable.love_video_press);
                }
            }
        });
    }

}
