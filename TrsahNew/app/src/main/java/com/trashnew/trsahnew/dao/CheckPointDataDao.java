package com.trashnew.trsahnew.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.model.GamePlayType;

import java.util.ArrayList;

/**
 * 关卡数据存放
 */
public class CheckPointDataDao {

    // 表名
    public static final String TABLE_NAME = "check_point_table";
    // 列名
    public static final String ID = "_id";
    public static final String STAR = "_star";
    public static final String SCORE = "_score";
    public static final String IS_PLAYED = "_is_played";
    public static final String TYPE = "type";

    // 列数
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_STAR = 1;
    public static final int COLUMN_SCORE = 2;
    public static final int COLUMN_IS_PLAYED = 3;
    public static final int COLUMN_TYPE = 4;

    ///////////////////////////// 单例 /////////////////////////////////////
    private GameSQLOpenHelper sql;
    private Context context;
    private static CheckPointDataDao instance;

    private CheckPointDataDao(Context context) {
        this.context = context;
        sql = new GameSQLOpenHelper(context);
    }

    public static CheckPointDataDao getInstance(Context context) {
        if (instance == null) {
            synchronized (CheckPointDataDao.class) {
                if (instance == null) {
                    instance = new CheckPointDataDao(context);
                }
            }
        }
        return instance;
    }

    public ArrayList<CheckPointData> getCheckPointData() {
        ArrayList<CheckPointData> result = new ArrayList<>();

        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = {ID, STAR, SCORE, IS_PLAYED, TYPE};

        @SuppressLint("Recycle")
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            CheckPointData data = new CheckPointData();
            data.set_id(cursor.getInt(COLUMN_ID));
            data.set_star(cursor.getInt(COLUMN_STAR));
            data.set_score(cursor.getInt(COLUMN_SCORE));
            int is_played = cursor.getInt(COLUMN_IS_PLAYED);
            data.set_isPlayed(is_played == 1);
            data.set_type(GamePlayType.intToPlayType(cursor.getInt(COLUMN_TYPE)));
            result.add(data);
        }

        cursor.close();
        db.close();
        return result;
    }

    public void update(int id, int star, int score, boolean isPlayed) {
        ArrayList<CheckPointData> receive = getCheckPointData();

        Log.e(getClass().getName() + "__", "start star = " + star);

        for (int l = 0; l < receive.size(); l++) {
            if (receive.get(l).get_id() == id) {
                if (receive.get(l).get_star() > star) {
                    return;
                }
            }
        }

        Log.e(getClass().getName() + "__", "end star = " + star);

        SQLiteDatabase db = sql.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(STAR, star);
        values.put(SCORE, score);
        values.put(IS_PLAYED, (isPlayed ? 1 : 0));

        String whereClause = ID + "=?";
        db.update(TABLE_NAME, values, whereClause, new String[]{String.valueOf(id)});

        values.clear();
        db.close();
    }

    public void update(int id, boolean isPlayed) {
        SQLiteDatabase db = sql.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(IS_PLAYED, (isPlayed ? 1 : 0));

        String whereClause = ID + "=?";
        db.update(TABLE_NAME, values, whereClause, new String[]{String.valueOf(id)});

        values.clear();

        db.close();
    }
}
