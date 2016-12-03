package com.jredu.liuyifan.mygoalapplication.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.datebase.SQLiteHelp;
import com.jredu.liuyifan.mygoalapplication.entity.LogonNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_COMM;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_DATE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_SRC;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_COL_TITLE;
import static com.jredu.liuyifan.mygoalapplication.util.Constant.NewTable.TBL_NAME;

public class ElseActivity extends AppCompatActivity {

    EditText mEditText_title;
    EditText mEditText_src;
    EditText mEditText_comm;
    EditText mEditText_date;
    Button mButton_crate;
    Button mButton_delete;
    Button mButton_update;
    Button mButton_queryDate;
    Button mButton_upNews;
    SQLiteHelp mSQLiteHelp;
    SQLiteDatabase mSQLiteDatabase;
    ContentValues mContentValues;
    SimpleAdapter mSimpleAdapter;
    ListView mListView;
    List<LogonNews> mLogonNewsList;
    List<Map<String, String>> mList;

    public ElseActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_else_activitye);
        mEditText_title = (EditText) findViewById(R.id.title);
        mEditText_src = (EditText) findViewById(R.id.src);
        mEditText_comm = (EditText) findViewById(R.id.comm);
        mEditText_date = (EditText) findViewById(R.id.date);
        mButton_crate = (Button) findViewById(R.id.crate);
        mButton_delete = (Button) findViewById(R.id.delete);
        mButton_update = (Button) findViewById(R.id.update);
        mButton_queryDate = (Button) findViewById(R.id.queryDate);
        mButton_upNews = (Button) findViewById(R.id.upNews);
        mListView = (ListView) findViewById(R.id.list_view);
        mList = new ArrayList<Map<String, String>>();
        mButton_crate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crate();
            }
        });
        mButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        mButton_queryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryDate();
            }
        });
        mButton_upNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNews();
            }
        });
    }

    private void crate() {
        mSQLiteHelp = new SQLiteHelp(ElseActivity.this);
        mSQLiteDatabase = mSQLiteHelp.getWritableDatabase();
        mContentValues = new ContentValues();
        mContentValues.put(TBL_COL_TITLE, mEditText_title.getText().toString());
        mContentValues.put(TBL_COL_SRC, mEditText_src.getText().toString());
        mContentValues.put(TBL_COL_COMM, mEditText_comm.getText().toString());
        mContentValues.put(TBL_COL_DATE, mEditText_date.getText().toString());
        mSQLiteDatabase.insert(TBL_NAME, null, mContentValues);
        mSQLiteDatabase.close();
        Toast.makeText(ElseActivity.this, "ok", Toast.LENGTH_SHORT).show();
    }

    private void delete() {
        mSQLiteHelp = new SQLiteHelp(ElseActivity.this);
        mSQLiteDatabase = mSQLiteHelp.getReadableDatabase();
        mSQLiteDatabase.delete(
                TBL_NAME,
                TBL_COL_TITLE + " = ? ",
                new String[]{mEditText_title.getText().toString()}
        );
        mSQLiteDatabase.close();
        Toast.makeText(ElseActivity.this, "ok", Toast.LENGTH_SHORT).show();
    }

    private void update() {
        mSQLiteHelp = new SQLiteHelp(ElseActivity.this);
        mSQLiteDatabase = mSQLiteHelp.getWritableDatabase();
        mContentValues = new ContentValues();
        mContentValues.put(TBL_COL_TITLE, mEditText_title.getText().toString());
        mContentValues.put(TBL_COL_SRC, mEditText_src.getText().toString());
        mContentValues.put(TBL_COL_COMM, mEditText_comm.getText().toString());
        mContentValues.put(TBL_COL_DATE, mEditText_date.getText().toString());
        mSQLiteDatabase.update(
                TBL_NAME,
                mContentValues,
                TBL_COL_TITLE + " = ? ",
                new String[]{mEditText_title.getText().toString()}
        );
        mSQLiteDatabase.close();
    }

    private void queryDate() {
        mSQLiteHelp = new SQLiteHelp(ElseActivity.this);
        mSQLiteDatabase = mSQLiteHelp.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(
                TBL_NAME,
                new String[]{TBL_COL_TITLE, TBL_COL_SRC, TBL_COL_COMM, TBL_COL_DATE},
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int title = cursor.getColumnIndex(TBL_COL_TITLE);
            String title_text = cursor.getString(title);
            if (mEditText_title.getText().toString().equals(title_text)) {
                int src = cursor.getColumnIndex(TBL_COL_SRC);
                int comm = cursor.getColumnIndex(TBL_COL_COMM);
                int date = cursor.getColumnIndex(TBL_COL_DATE);
                String src_text = cursor.getString(src);
                String comm_text = cursor.getString(comm);
                String date_text = cursor.getString(date);
                mEditText_title.setText(title_text);
                mEditText_src.setText(src_text);
                mEditText_comm.setText(comm_text);
                mEditText_date.setText(date_text);
            }
        }
        cursor.close();
        mSQLiteDatabase.close();
    }

    private void showNews() {
        mList.clear();
        mListView.setAdapter(null);
        mSQLiteHelp = new SQLiteHelp(ElseActivity.this);
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
            int comm = cursor.getColumnIndex(TBL_COL_COMM);
            int date = cursor.getColumnIndex(TBL_COL_DATE);
            String title_text = cursor.getString(title);
            String title_src = cursor.getString(src);
            String title_comm = cursor.getString(comm);
            String title_date = cursor.getString(date);
            Map<String, String> text = new HashMap<String, String>();
            text.put("title", title_text);
            text.put("src", title_src);
            text.put("comm", title_comm);
            text.put("date", title_date);
            mList.add(text);
        }
        mSimpleAdapter = new SimpleAdapter(
                ElseActivity.this,
                mList,
                R.layout.layout_logon_fragment_listview,
                new String[]{"title", "src", "comm", "date"},
                new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4}
        );
        mListView.setAdapter(mSimpleAdapter);
    }

}
