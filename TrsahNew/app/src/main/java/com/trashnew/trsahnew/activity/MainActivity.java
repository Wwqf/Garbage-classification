package com.trashnew.trsahnew.activity;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.trashnew.trsahnew.dao.CheckPointDataDao;
import com.trashnew.trsahnew.dao.GameSQLOpenHelper;
import com.trashnew.trsahnew.dao.TrashDataDao;
import com.trashnew.trsahnew.view.GameView;


public class MainActivity extends AppCompatActivity {

    public static MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉标题栏
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 屏幕全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置手机方向
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        main = this;
        GameView view = new GameView(this);

        setContentView(view);

        GameSQLOpenHelper sqlOpenHelper = new GameSQLOpenHelper(this);
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TrashDataDao.TABLE_NAME, null);
        Log.e(getClass().getName() + "__", "len = " + cursor.getCount());
    }
}
