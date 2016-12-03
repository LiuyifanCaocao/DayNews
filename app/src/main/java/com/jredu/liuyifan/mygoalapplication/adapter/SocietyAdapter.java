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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.activity.SettingActivity;
import com.jredu.liuyifan.mygoalapplication.datebase.SQLiteHelp;
import com.jredu.liuyifan.mygoalapplication.entity.SportNews;

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
 * Created by DELL on 2016/9/23.
 */
public class SocietyAdapter extends BaseAdapter {
    SQLiteHelp mSQLiteHelp;
    SQLiteDatabase mSQLiteDatabase;
    ContentValues mContentValues;

    Context mContext;
    List<SportNews> mList;
    RequestQueue mRequestQueue;
    ViewHolder mViewHolder;
    RotateAnimation animation;
    AlphaAnimation alphaAnimation;
    boolean b = true;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    public SocietyAdapter(Context context, List<SportNews> list, RequestQueue requestQueue, boolean b) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_picture_adapter, null);
            mViewHolder = new ViewHolder();
            mViewHolder.title = (TextView) convertView.findViewById(R.id.text);
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.ctime);
            mViewHolder.img_load = (ImageView) convertView.findViewById(R.id.load);
            change_size(mViewHolder.title, new SettingActivity().get_size());
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
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.checkBox.setChecked(false);
        mViewHolder.img.setImageResource(R.drawable.w2345_image_file_copy_15);
        mViewHolder.img_load.setImageResource(R.drawable.chatroom_small_loading);
        mViewHolder.title.setText(mList.get(position).getTitle());
        mViewHolder.textView.setText(mList.get(position).getTime());
        if (b) {
            img_rotate(mViewHolder.img_load);
            set_img(mViewHolder.img, mList.get(position).getPicUrl(), mViewHolder.img_load);
        }
        change_item_img(mViewHolder.img, mList.get(position).getPicUrl(),mViewHolder.img_load);
        get_item_img(mList.get(position).getPicUrl());
        int s1 = (int) (Math.random() * 10000);
        int s2 = (int) (Math.random() * 10000);
        int s3 = (int) (Math.random() * 10000);
        mViewHolder.mTextView1.setText(s1 + "");
        mViewHolder.mTextView2.setText(s2 + "");
        mViewHolder.mTextView3.setText(s3 + "");
        change_comment(mViewHolder.relativeLayout1, mViewHolder.mImageView1, s1, mViewHolder.mTextView1, mViewHolder.mImageView1_add1);
        change_digdown(mViewHolder.relativeLayout2, mViewHolder.mImageView2, s2, mViewHolder.mTextView2, mViewHolder.mImageView2_add1);
        is_not(mViewHolder.checkBox,mList.get(position).getPicUrl(),mList.get(position).getTitle());
        change_collection(mViewHolder.checkBox,position);
        return convertView;
    }

    private class ViewHolder {
        ImageView img;
        TextView title;
        TextView textView;
        ImageView img_load;
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

    //设置图片
    private void set_img(final ImageView imageView, String s, final ImageView imageView_load) {
        ImageRequest mImageRequest = new ImageRequest(
                s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView_load.clearAnimation();
                        imageView_load.setVisibility(View.GONE);
                        imageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView_load.clearAnimation();
                        imageView_load.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.w2345_image_file_copy_7);
                    }
                }
        );
        mRequestQueue.add(mImageRequest);
    }

    public void change_size(TextView textView, float f) {
        textView.setTextSize(f);
    }

    //每一项的图片
    public void change_item_img(final ImageView imageView, final String s,final ImageView imageView_load) {
        mViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequest mImageRequest = new ImageRequest(
                        s,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                imageView.setImageBitmap(response);
                                imageView_load.setVisibility(View.GONE);
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

    public void set_show_img(boolean b) {
        if (this.b != b) {
            this.b = b;
        } else {
            this.b = !b;
        }
    }

    //保存图片
    public void get_item_img(final String s) {
        mViewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
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
    public void img_rotate(ImageView imageView) {
        animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(5000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
        imageView.startAnimation(animation);
    }

    //赞加一
    public void change_comment(RelativeLayout relativeLayout, final ImageView imageView, final int i, final TextView textView, final ImageView imageView_add1) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals(i + "")) {
                    imageView.setImageResource(R.drawable.comment_like_icon_press);
                    textView.setText((i + 1) + "");
                    imageView_add1.setVisibility(View.VISIBLE);
                    alphaAnimation = new AlphaAnimation(1f, 0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setRepeatCount(0);
                    imageView_add1.startAnimation(alphaAnimation);
                } else {
                    imageView.setImageResource(R.drawable.comment_like_icon_night);
                    textView.setText(i + "");
                    imageView_add1.setVisibility(View.GONE);

                }
                /*imageView_add1.setVisibility(View.VISIBLE);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        imageView_add1.setVisibility(View.GONE);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask,1000);*/
            }
        });
    }

    //不赞减一
    public void change_digdown(RelativeLayout relativeLayout, final ImageView imageView, final int i, final TextView textView, final ImageView imageView_add1) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals(i + "")) {
                    imageView.setImageResource(R.drawable.digdown_video_pressed);
                    textView.setText((i - 1) + "");
                    imageView_add1.setVisibility(View.VISIBLE);
                    alphaAnimation = new AlphaAnimation(1f, 0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setRepeatCount(0);
                    imageView_add1.startAnimation(alphaAnimation);
                } else {
                    imageView.setImageResource(R.drawable.digdown_video_normal_night);
                    textView.setText(i + "");
                    imageView_add1.setVisibility(View.GONE);
                }
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
        mContentValues.put(TBL_COL_DATE, mList.get(i).getTime());
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

    //popupWindow
    public void big_img(View v){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popup,null);
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
}

