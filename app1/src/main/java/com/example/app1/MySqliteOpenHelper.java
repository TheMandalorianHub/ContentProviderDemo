package com.example.app1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类
 * created by xiaochen on 2018/8/24
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    //数据库版本
    private static final int dbVersion = 1;
    //数据库名称 -》人
    private static final String dbName = "person.db";
    //男性 表名
    public static final String MAN_TABLE_NAME = "man";
    //女性 表名
    public static final String WOMEN_TABLE_NAME = "women";
    //创建 表 男
    private static final String CREATE_MAN_TABLE = "create table " + MAN_TABLE_NAME + "(_id integer primary key autoincrement,name text,age text,sex text)";
    //创建 表 女
    private static final String CREATE_WOMEN_TABLE = "create table " + WOMEN_TABLE_NAME + "(_id integer primary key autoincrement,name text,age text,sex text)";

    public MySqliteOpenHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建对应的俩张表
        db.execSQL(CREATE_MAN_TABLE);
        db.execSQL(CREATE_WOMEN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
