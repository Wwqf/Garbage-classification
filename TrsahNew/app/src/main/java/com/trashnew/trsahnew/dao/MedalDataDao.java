package com.trashnew.trsahnew.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.dao.data.MedalData;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.model.GamePlayType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * 勋章数据存放
 */
public class MedalDataDao {

    // 表名
    public static final String TABLE_NAME = "medal_table";
    // 列名
    public static final String RES_ID = "_res_id";
    public static final String MAX_VAL = "_max_val";
    public static final String CURRENT_VAL = "_current_val";

    // 列数
    public static final int COLUMN_RES_ID = 0;
    public static final int COLUMN_MAX_VAL = 1;
    public static final int COLUMN_CURRENT_VAL = 2;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> addCountMaps = new HashMap<>();


    ///////////////////////////// 单例 /////////////////////////////////////
    private GameSQLOpenHelper sql;
    private Context context;
    private static MedalDataDao instance;

    private MedalDataDao(Context context) {
        this.context = context;
        sql = new GameSQLOpenHelper(context);
    }


    public static MedalDataDao getInstance(Context context) {
        if (instance == null) {
            synchronized (MedalDataDao.class) {
                if (instance == null) {
                    instance = new MedalDataDao(context);
                }
            }
        }
        return instance;
    }

    public HashMap<Integer, MedalData> getMedalData() {
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, MedalData> result = new HashMap<>();

        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = {RES_ID, MAX_VAL, CURRENT_VAL};

        @SuppressLint("Recycle")
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            MedalData data = new MedalData();

            data.setResId(cursor.getInt(COLUMN_RES_ID));
            data.setMaxVal(cursor.getInt(COLUMN_MAX_VAL));
            data.setCurrentVal(cursor.getInt(COLUMN_CURRENT_VAL));

            result.put(data.getResId(), data);
        }

        cursor.close();
        db.close();
        return result;
    }

    public void addCount(int resId) {
        addCount(resId, 1);
    }

    public void addCount(int resId, int count) {
        if (addCountMaps.containsKey(resId)) {
            int val = addCountMaps.get(resId);
            addCountMaps.put(resId, val + count);
        } else {
            addCountMaps.put(resId, count);
        }
    }

    public long update() {
        SQLiteDatabase db = sql.getReadableDatabase();

        long update_len = 0;

        for (int keys : addCountMaps.keySet()) {
            ContentValues values = new ContentValues();

            int update_val = addCountMaps.get(keys);
            values.put(CURRENT_VAL, update_val);

            String whereClause = RES_ID + "=?";
            int size = db.update(TABLE_NAME, values, whereClause, new String[]{String.valueOf(keys)});
            if (size > 0) update_len++;

            values.clear();
        }

        addCountMaps.clear();
        db.close();
        return update_len;
    }
}
