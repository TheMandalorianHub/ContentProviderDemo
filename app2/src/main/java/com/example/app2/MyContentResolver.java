package com.example.app2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

/**
 * 数据的监听
 * created by xiaochen on 2018/8/24
 */
class MyContentObserver extends ContentObserver {
    private static final String TAG = "MyContentObserver";
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MyContentObserver(Handler handler) {
        super(handler);
    }
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d(TAG, "onChange: ");
    }
}
