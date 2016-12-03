package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.activity.AttentionActivity;
import com.jredu.liuyifan.mygoalapplication.adapter.AttentionAdapter;
import com.jredu.liuyifan.mygoalapplication.datebase.SQLiteHelp;
import com.jredu.liuyifan.mygoalapplication.entity.Attention;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_DATE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_SRC;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_TITLE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttentionFragment extends Fragment {

    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";

    RequestQueue mRequestQueue;
    ImageView mImageView;
    Bundle mBundle;
    String url;
    String title;
    String ctime;
    ArrayList<Attention> mList;
    ListView mListView;
    ImageView mImageView_add;
    TextView mTextView_text_view_add;
    ImageView imageView_search;
    AttentionAdapter attentionAdapter;
    SQLiteHelp mSQLiteHelp;
    SQLiteDatabase mSQLiteDatabase;
    ContentValues mContentValues;


    public AttentionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attention, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        imageView_search = (ImageView) v.findViewById(R.id.search);
        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(getActivity(), AttentionActivity.class));
            }
        });
        mList = new ArrayList<>();
        mListView = (ListView) v.findViewById(R.id.list_view);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position);
                return false;
            }
        });

        //加号
        mImageView_add = (ImageView) v.findViewById(R.id.add);
        mImageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AttentionActivity.class));
            }
        });

        mTextView_text_view_add = (TextView) v.findViewById(R.id.text_view_add);

        mImageView = (ImageView) v.findViewById(R.id.image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
            }
        });
        show_list();
        return v;
    }

    //获得图片
    public void get_list() {
        attentionAdapter = new AttentionAdapter(getActivity(), mList,mRequestQueue);
        mListView.setAdapter(attentionAdapter);
        //跟新列表

        //显示隐藏加号
        if (mList.size() > 1) {
            mImageView_add.setVisibility(View.GONE);
            mTextView_text_view_add.setVisibility(View.GONE);
        }else {
            mImageView_add.setVisibility(View.VISIBLE);
            mTextView_text_view_add.setVisibility(View.VISIBLE);
        }
    }

    //获得广播数据
    public void get_broadcast(String s1, String s2, String s3) {

        /*url = s1;
        title = s2;
        ctime = s3;*/
    }

    //可存贮图片
    public void get_img(Bitmap bitmap1){
        File dirFile = new File(ALBUM_PATH);
                        if (!dirFile.exists()) {
                            dirFile.mkdir();
                        }
                        File myCaptureFile = new File(ALBUM_PATH + "test.png");
                        BufferedOutputStream bos = null;
                        BufferedInputStream bis = null;
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                        try {
                            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                            bitmap1.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            bis = new BufferedInputStream(new FileInputStream(myCaptureFile));
                            Bitmap bitmap = BitmapFactory.decodeStream(bis);
                            Attention attention = new Attention(bitmap, title, ctime);
                            mList.add(attention);
                            Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
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
    }

    //删除
    private void delete(final int i) {
        mSQLiteHelp = new SQLiteHelp(getActivity());
        mSQLiteDatabase = mSQLiteHelp.getReadableDatabase();
        mSQLiteDatabase.delete(
                TBL_NAME,
                TBL_COL_SRC + " = ? ",
                new String[]{mList.get(i).getPicUrl()}
        );
        mSQLiteDatabase.close();
        mList.remove(i);
        attentionAdapter.notifyDataSetChanged();
    }

    //展示数据
    public void show_list() {
        mList.clear();
        mListView.setAdapter(null);
        mSQLiteHelp = new SQLiteHelp(getActivity());
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
            int title = cursor.getColumnIndex(TBL_COL_TITLE);
            int src = cursor.getColumnIndex(TBL_COL_SRC);
            int date = cursor.getColumnIndex(TBL_COL_DATE);
            String title_text = cursor.getString(title);
            String title_src = cursor.getString(src);
            String title_date = cursor.getString(date);
            Attention attention = new Attention(title_src,title_text,title_date);
            mList.add(0,attention);
        }
        get_list();
    }

    @Override
    public void onResume() {
        super.onResume();
        show_list();
        attentionAdapter.notifyDataSetChanged();
    }
}
