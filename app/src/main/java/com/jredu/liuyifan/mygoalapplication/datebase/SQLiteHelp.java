package com.jredu.liuyifan.mygoalapplication.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jredu.liuyifan.mygoalapplication.util.Constant;

/**
 * Created by DELL on 2016/9/19.
 */
public class SQLiteHelp extends SQLiteOpenHelper {
    public final static String DB_NAME = "new.db";
    public final static int VERSION = 1;

    public SQLiteHelp(Context context) {
        super(context, DB_NAME , null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sq1 = Constant.NewTable.getCrateTableSQL();
        db.execSQL(sq1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sq1 = " drop table " + Constant.NewTable.TBL_NAME;
        db.execSQL(sq1);
        onCreate(db);
    }
}
