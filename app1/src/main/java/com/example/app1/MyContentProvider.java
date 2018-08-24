package com.example.app1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 内容提供者
 * created by xiaochen on 2018/8/24
 */
public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";
    private Context context;
    //用于数据操作
    private MySqliteOpenHelper sqliteOpenHelper;
    //到时候需要用到的uri 匹配类，参数中的code为如果没有匹配到的话返回的默认code值
//    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static UriMatcher uriMatcher = new UriMatcher(-1000);
    //验证需要的一个地址，待会需要拼接到uri中
    private static String authority = "com.example.app1.MyContentProvider";
    //一些在uri匹配到时urimatch给你回的int值，用于区别
    private static final int PEOPLE_MAN_CODE = 1;
    private static final int PEOPLE_WOMEN_CODE = 2;
    static {
        //添加对应的uri规则,最后一个参数为在匹配到时返回的int值
        uriMatcher.addURI(authority, MySqliteOpenHelper.MAN_TABLE_NAME, PEOPLE_MAN_CODE);
        uriMatcher.addURI(authority, MySqliteOpenHelper.WOMEN_TABLE_NAME, PEOPLE_WOMEN_CODE);
    }
    @Override
    public boolean onCreate() {
        context = getContext();
        sqliteOpenHelper = new MySqliteOpenHelper(context);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: ");
        Cursor cursor = sqliteOpenHelper.getReadableDatabase().query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long insert = sqliteOpenHelper.getWritableDatabase().insert(getTableName(uri), null, values);
        Log.d(TAG, "insert: "+insert);
        //如果resolver注册了observer，则会收到数据库的变动通知
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete = sqliteOpenHelper.getWritableDatabase().delete(getTableName(uri), selection, selectionArgs);
        Log.d(TAG, "delete: " + delete);
        context.getContentResolver().notifyChange(uri, null);
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int update = sqliteOpenHelper.getWritableDatabase().update(getTableName(uri), values, selection, selectionArgs);
        Log.d(TAG, "update: " + update);
        return update;
    }

    //根据传递过来的uri 判断是需要操作的哪个数据库的表
    public String getTableName(Uri uri) {
        int matchCode = uriMatcher.match(uri);
        String tableNam = null;
        Log.d(TAG, "getTableName,matchCode:" + matchCode);
        switch (matchCode) {
            case PEOPLE_MAN_CODE:
                tableNam = sqliteOpenHelper.MAN_TABLE_NAME;
                break;
            case PEOPLE_WOMEN_CODE:
                tableNam = sqliteOpenHelper.WOMEN_TABLE_NAME;
                break;
        }
        return tableNam;
    }
}
