package com.trashnew.trsahnew.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.dao.data.MedalData;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.model.GamePlayType;
import com.trashnew.trsahnew.model.TrashType;

public class GameSQLOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "gso.db";
    private static final int DB_VERSION = 4;

    public GameSQLOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_CHECK_POINT_DATA);
            db.execSQL(CREATE_MEDAL_DATA);
            db.execSQL(CREATE_TRASH_DATA);
        } catch (Exception e) {
            Log.e(getClass().getName() + "__", "create table is failed.");
            e.printStackTrace();
        }

        try {

            for (String sql : INSERT_CHECK_POINT_DATA) {
                db.execSQL(sql);
            }

            for (String sql : INSERT_MEDAL_DATA) {
                db.execSQL(sql);
            }

            for (String sql : INSERT_TRASH_DATA) {
                db.execSQL(sql);
            }

        } catch (Exception e) {
            Log.e(getClass().getName() + "__", "insert data is failed.");
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CheckPointDataDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MedalDataDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrashDataDao.TABLE_NAME);

        onCreate(db);
    }

    private static final String CREATE_CHECK_POINT_DATA = "CREATE TABLE " + CheckPointDataDao.TABLE_NAME + "(" +
            CheckPointDataDao.ID + " INTEGER PRIMARY KEY, " +
            CheckPointDataDao.STAR + " INTEGER  DEFAULT 0, " +
            CheckPointDataDao.SCORE + " INTEGER, " +
            CheckPointDataDao.IS_PLAYED + " INTEGER DEFAULT 0, " +
            CheckPointDataDao.TYPE + " INTEGER" + ");";


    private static final String CREATE_MEDAL_DATA = "CREATE TABLE " + MedalDataDao.TABLE_NAME + "(" +
            MedalDataDao.RES_ID + " INTEGER PRIMARY KEY, " +
            MedalDataDao.MAX_VAL + " INTEGER, " +
            MedalDataDao.CURRENT_VAL + " INTEGER" + ");";

    private static final String CREATE_TRASH_DATA = "CREATE TABLE " + TrashDataDao.TABLE_NAME + "(" +
            TrashDataDao.RES_ID + " INTEGER PRIMARY KEY, " +
            TrashDataDao.NAME + " TEXT, " +
            TrashDataDao.TRASH_TYPE + " INTEGER, " +
            TrashDataDao.MEDAL_NAME + " TEXT, " +
            TrashDataDao.FONT_COLOR + " INTEGER, " +
            TrashDataDao.OUTER_BORDER_COLOR + " INTEGER, " +
            TrashDataDao.INNER_BORDER_COLOR + " INTEGER, " +
            TrashDataDao.DEEP_BG_COLOR + " INTEGER, " +
            TrashDataDao.LIGHT_BG_COLOR + " INTEGER" + ");";

    private static final String[] INSERT_CHECK_POINT_DATA = {
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (1, 0, 0, 1, 1);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (2, 0, 0, 0, 1);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (3, 0, 0, 0, 1);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (4, 0, 0, 0, 2);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (5, 0, 0, 0, 2);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (6, 0, 0, 0, 2);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (7, 0, 0, 0, 3);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (8, 0, 0, 0, 3);",
            "INSERT INTO " + CheckPointDataDao.TABLE_NAME + " VALUES (9, 0, 0, 0, 3);",
    };

    private static final String[] INSERT_MEDAL_DATA = {
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.shuye + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.zhenzhu + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.dabian + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.xieke + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.niunai + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.feizhao + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.huaban + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.zhusheqi + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.suliaoping + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.chepiao + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.bangbangtang + ", 50, 0);",
            "INSERT INTO " + MedalDataDao.TABLE_NAME + " VALUES (" + R.drawable.jienengdeng + ", 50, 0);"
    };

    private static final String[] INSERT_TRASH_DATA = {
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.shuye + ", \"花瓣\", 2, \"朝花夕拾\", 0xFFc35185, 0xFFc35185, 0xFFffffff, 0xFFf899c3, 0xFFf9aecf);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.zhenzhu + ", \"珍珠\", 2, \"发现珍珠\", 0xFF377e90, 0xFF377e90, 0xFFffffff, 0xFFfed5b7, 0xFFffddc4);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.dabian + ", \"大便\", 1, \"有机肥料\", 0xFF70482e, 0xFF70482e, 0xFFffffff, 0xFFc07945, 0xFFcc926a);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.xieke + ", \"蟹壳\", 2, \"坚不可摧\", 0xFF6f2a09, 0xFF6f2a09, 0xFFffffff, 0xFFc84614, 0xFFcf6035);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.niunai + ", \"牛奶\", 4, \"快高长大\", 0xFF2493b1, 0xFF2493b1, 0xFFffffff, 0xFF63d0ef, 0xFF82daf2);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.feizhao + ", \"肥皂\", 1, \"兄弟情深\", 0xFFd89916, 0xFFd89916, 0xFFffffff, 0xFFfec866, 0xFFfed384);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.huaban + ", \"画板\", 2, \"美术大师\", 0xFF357e8f, 0xFF357e8f, 0xFF98cad1, 0xFFdaecf0, 0xFFfeffff);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.zhusheqi + ", \"注射器\", 4, \"屁股好疼\", 0xFF3e5155, 0xFF3e5155, 0xFFffffff, 0xFF657477, 0xFF848f93);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.suliaoping + ", \"塑料瓶\", 4, \"致富能手\", 0xFF0a5992, 0xFF0a5992, 0xFFffffff, 0xFF1c8bdd, 0xFF49a2e4);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.chepiao + ", \"车票\", 1, \"归去来兮\", 0xFF2a3263, 0xFF2a3263, 0xFFffffff, 0xFF4f5a92, 0xFF727aa8);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.bangbangtang + ", \"棒棒糖\", 2, \"甜心宝贝\", 0xFFbe223a, 0xFFbe223a, 0xFFffffff, 0xFFfe405a, 0xFFff667b);",
            "INSERT INTO " + TrashDataDao.TABLE_NAME + " VALUES (" + R.drawable.jienengdeng + ", \"节能灯\", 3, \"环保达人\", 0xFF7f881b, 0xFF7f881b, 0xFFffffff, 0xFFcfda4d, 0xFFd8e26f);"
    };

}
