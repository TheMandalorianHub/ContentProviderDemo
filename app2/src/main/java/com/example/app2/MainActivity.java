package com.example.app2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView msg;
    private StringBuilder msgStr;
    private ScrollView scrollView;
    //构建需要进行操作的对应的uri
    private Uri manUri = Uri.parse("content://com.example.app1.MyContentProvider/man");
    private Uri womenUri = Uri.parse("content://com.example.app1.MyContentProvider/women");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg = findViewById(R.id.msg);
        msgStr = new StringBuilder();
        scrollView = findViewById(R.id.scrollView);
        //这里选择对womenuri的更改执行监听
        getContentResolver().registerContentObserver(womenUri, true, new MyContentObserver(new Handler()));

    }

    public void insertMan(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "男一号");
        values.put("age", "23");
        values.put("sex", "男性");
        getContentResolver().insert(manUri, values);
        addMsg("插入数据：‘男一号，23岁，男性’ 到 app1 中");
    }

    public void deleteMan(View view) {
        //第二个参数指的是sqlie的一部分查询语句，后续更上对应的值
        int count = getContentResolver().delete(manUri, "name = ? or name =?", new String[]{"男一号", "男二号"});
        addMsg("删除所有男性数据，删除条数：" + count);
    }

    public void updateMan(View view) {
        //values 代表的是 需要更改成的数据 todo 待会回来还需要再继续测试一下
        ContentValues values = new ContentValues();
        values.put("name", "男二号");
        //后续俩个条件用于确认某一条的数据进行更改
        getContentResolver().update(manUri, values, "name = ? and age =?", new String[]{"男一号", "23"});
        addMsg("更改男一号的名称为男二号");
    }

    public void queryManFirst(View view) {
        //根据条件查男一号 第二个参数代表查询要返回的列
        Cursor query = getContentResolver().query(manUri, new String[]{"name","age","sex"}, "name =?", new String[]{"男一号"}, null);
        String cursorString = getCursorString(query);
        addMsg("查询到的数据：" + cursorString);
    }

    public void queryManAll(View view) {
        Cursor query = getContentResolver().query(manUri, new String[]{"name","age","sex"}, null, null, null);
        String cursorStr = getCursorString(query);
        addMsg("查询到的数据：" + cursorStr);
    }

    public void insertWomen(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "女一号");
        values.put("age", "23");
        values.put("sex", "女性");
        getContentResolver().insert(womenUri, values);
        addMsg("插入数据：‘女一号，23岁，女性’ 到 app1 中");
    }

    public void deleteWomen(View view) {
        //第二个参数指的是sqlie的一部分查询语句，后续更上对应的值
        int count = getContentResolver().delete(womenUri, "name = ? or name =?", new String[]{"女一号", "女二号"});
        addMsg("删除所有女性数据，删除条数：" + count);
    }

    public void updateWomen(View view) {
//values 代表的是 需要更改成的数据 todo 待会回来还需要再继续测试一下
        ContentValues values = new ContentValues();
        values.put("name", "女二号");
        //后续俩个条件用于确认某一条的数据进行更改
        getContentResolver().update(womenUri, values, "name = ? and age =?", new String[]{"女一号", "23"});
        addMsg("更改女一号的名称为女二号");
    }

    public void queryWomenFirst(View view) {
//根据条件查女一号
        Cursor query = getContentResolver().query(womenUri, new String[]{"name","age","sex"}, "name =?", new String[]{"女一号"}, null);
        String cursorString = getCursorString(query);
        addMsg("查询到的数据：" + cursorString);
    }

    public void queryWomenAll(View view) {
        Cursor query = getContentResolver().query(womenUri, new String[]{"name","age","sex"}, null, null, null);
        String cursorStr = getCursorString(query);
        addMsg("查询到的数据：" + cursorStr);
    }

    public void addMsg(String msgs) {
        msgStr.append(msgs + "\r\n\r\n");
        msg.setText(msgStr);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    /**
     * 将cursor的数据转换成字符串
     *
     * @param cursor
     * @return
     */
    public String getCursorString(Cursor cursor) {
        if (cursor.getCount() <= 0) {
            return "没有数据";
        }
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            sb.append("name:" + name + ",age:" + age + ",sex:" + sex + "\r\n");
        }
        cursor.close();
        return sb.toString();
    }
}
