package com.jredu.liuyifan.mygoalapplication.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.datebase.SQLiteHelp;
import com.jredu.liuyifan.mygoalapplication.entity.SportNews;
import com.jredu.liuyifan.mygoalapplication.util.Back_Ground_Bmp;

import java.util.List;

import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_DATE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_SRC;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_TITLE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_NAME;


/**
 * Created by DELL on 2016/9/23.
 */
public class Attention_add_Adapter extends BaseAdapter {
    SQLiteHelp mSQLiteHelp;
    SQLiteDatabase mSQLiteDatabase;
    ContentValues mContentValues;

    Context mContext;
    List<SportNews> mList;
    RequestQueue mRequestQueue;
    ViewHolder mViewHolder;
    boolean b = true;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    public Attention_add_Adapter(Context context, List<SportNews> list, RequestQueue requestQueue, boolean b) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_attention_add_adapter,null);
            mViewHolder = new ViewHolder();
            mViewHolder.title = (TextView) convertView.findViewById(R.id.text_view_title);
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.ctime);
            mViewHolder.img = (ImageView) convertView.findViewById(R.id.image_view);
            mViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.checkBox.setChecked(false);
        mViewHolder.title.setText(mList.get(position).getTitle());
        mViewHolder.textView.setText(mList.get(position).getTime());
        set_img(mViewHolder.img,mList.get(position).getPicUrl());
        send_broadcast(mViewHolder.checkBox,position);
        is_not(mViewHolder.checkBox,mList.get(position).getPicUrl(),mList.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView textView;
        CheckBox checkBox;
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

    //发送广播
    public void send_broadcast(final CheckBox checkBox, final int i){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checkBox.isChecked())){
                    crate(i);
                    /*Intent intent = new Intent("attention");
                    Bundle bundle = new Bundle();
                    bundle.putString("title", mList.get(i).getTitle());
                    bundle.putString("user", mList.get(i).getPicUrl());
                    bundle.putString("ctime",mList.get(i).getTime());
                    intent.putExtras(bundle);
                    mContext.sendBroadcast(intent);*/
                }else {
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


}
