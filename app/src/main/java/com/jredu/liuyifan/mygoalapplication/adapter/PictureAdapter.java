package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.datebase.SQLiteHelp;
import com.jredu.liuyifan.mygoalapplication.entity.Image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_DATE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_SRC;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_TITLE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_NAME;

/**
 * Created by DELL on 2016/9/22.
 */
public class PictureAdapter extends BaseAdapter {
    Context mContext;
    List<Image> mList;
    AlphaAnimation alphaAnimation;
    RequestQueue mRequestQueue;
    SQLiteHelp mSQLiteHelp;
    SQLiteDatabase mSQLiteDatabase;
    ContentValues mContentValues;

    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    public PictureAdapter(Context context, List<Image> list , RequestQueue requestQueue) {
        mContext = context;
        mList = list;
        mRequestQueue = requestQueue;
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
        ViewHolder mViewHolder = null;
        if(mViewHolder == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_picture_adapter2,null);
            mViewHolder = new ViewHolder();
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.ctime);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.text);
            mViewHolder.img = (ImageView) convertView.findViewById(R.id.img_show);
            mViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.two_img);
            mViewHolder.relativeLayout1 = (RelativeLayout) convertView.findViewById(R.id.comment);
            mViewHolder.relativeLayout2 = (RelativeLayout) convertView.findViewById(R.id.digdown);
            mViewHolder.relativeLayout3 = (RelativeLayout) convertView.findViewById(R.id.review);
            mViewHolder.mImageView1 = (ImageView) convertView.findViewById(R.id.image_view_comment);
            mViewHolder.mImageView2 = (ImageView) convertView.findViewById(R.id.image_view_digdown);
            mViewHolder.mImageView3 = (ImageView) convertView.findViewById(R.id.image_view_review);
            mViewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.text_comment);
            mViewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.text_digdown);
            mViewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.text_review);
            mViewHolder.mImageView1_add1 = (ImageView) convertView.findViewById(R.id.text_comment_add_1);
            mViewHolder.mImageView2_add1 = (ImageView) convertView.findViewById(R.id.text_digdown_add_1);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.title.setText(mList.get(position).getTitle());
        mViewHolder.textView.setText(mList.get(position).getCtime());
        int s1 = (int)(Math.random()*10000);
        int s2 = (int)(Math.random()*10000);
        int s3 = (int)(Math.random()*10000);
        mViewHolder.mTextView1.setText(s1+"");
        mViewHolder.mTextView2.setText(s2+"");
        mViewHolder.mTextView3.setText(s3+"");
        change_comment(mViewHolder.relativeLayout1,mViewHolder.mImageView1,s1,mViewHolder.mTextView1,mViewHolder.mImageView1_add1);
        change_digdown(mViewHolder.relativeLayout2,mViewHolder.mImageView2,s2,mViewHolder.mTextView2,mViewHolder.mImageView2_add1);
        change_collection(mViewHolder.checkBox,position);
        mViewHolder.img.setImageBitmap(mList.get(position).getBitmap());
        is_not(mViewHolder.checkBox,mList.get(position).getPicUrl(),mList.get(position).getTitle());
        get_item_img(mViewHolder.img, mList.get(position).getPicUrl());
        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView textView;
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
        CheckBox checkBox;
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


    //保存图片
    public void get_item_img(ImageView imageView, final String s) {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(mContext)
                        .setTitle("保存图片")
                        .setMessage("是否保存图片")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
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
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    //收藏
    public void change_collection(final CheckBox checkBox, final int i) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    checkBox.setChecked(true);
                    crate(i);
                }else {
                    checkBox.setChecked(false);
                    delete(i);
                }
            }
        });
    }

    //加入数据库
    private void crate(final int i) {
        mSQLiteHelp = new SQLiteHelp(mContext);
        mSQLiteDatabase = mSQLiteHelp.getWritableDatabase();
        mContentValues = new ContentValues();
        mContentValues.put(TBL_COL_TITLE, mList.get(i).getTitle());
        mContentValues.put(TBL_COL_SRC, mList.get(i).getPicUrl());
        mContentValues.put(TBL_COL_DATE, mList.get(i).getCtime());
        mSQLiteDatabase.insert(TBL_NAME, null, mContentValues);
        mSQLiteDatabase.close();
        Toast.makeText(mContext, "ok", Toast.LENGTH_SHORT).show();
    }

    //从数据库删除数据
    private void delete(final int i) {
        mSQLiteHelp = new SQLiteHelp(mContext);
        mSQLiteDatabase = mSQLiteHelp.getReadableDatabase();
        mSQLiteDatabase.delete(
                TBL_NAME,
                TBL_COL_SRC + " = ? ",
                new String[]{mList.get(i).getPicUrl()}
        );
        mSQLiteDatabase.close();
        Toast.makeText(mContext, "ok", Toast.LENGTH_SHORT).show();
    }

    //判断是否已经关注
    private void is_not(CheckBox checkBox , String picUrl , String title) {
        mSQLiteHelp = new SQLiteHelp(mContext);
        mSQLiteDatabase = mSQLiteHelp.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(
                TBL_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int tbl_col_title = cursor.getColumnIndex(TBL_COL_TITLE);
            int src = cursor.getColumnIndex(TBL_COL_SRC);
            int date = cursor.getColumnIndex(TBL_COL_DATE);
            String title_text = cursor.getString(tbl_col_title);
            String title_src = cursor.getString(src);
            String title_date = cursor.getString(date);
            if (picUrl.equals(title_src) && title.equals(title_text)){
                checkBox.setChecked(true);
            }
        }
    }
}
