package com.jredu.liuyifan.mygoalapplication.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jredu.liuyifan.mygoalapplication.util.Constant;

/**
 * Created by DELL on 2016/9/27.
 */
public class SQLiteHelpSetting extends SQLiteOpenHelper {
    final static String DB_NAME = "set.db";
    final static int A = 1;

    public SQLiteHelpSetting(Context context) {
        super(context, DB_NAME, null, A);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = Constant.NewTable.getSettingSQL();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sq1 = " drop table " + Constant.NewTable.SET_NAME;
        db.execSQL(sq1);
        onCreate(db);
    }
}
