package com.example.chenjy.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by chenjy on 2019/4/3.
 */


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public static final String TEST_DB = "test.db";
    public static final String USER_TABLE = "user";

    public MySqliteOpenHelper(Context context) {
        super(context, TEST_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (_id integer primary key autoincrement," + TableVO.USER_ID + " varchar(20)," + TableVO.USER_NAME + " varchar(20)," + TableVO.USER_DEPT + " varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
