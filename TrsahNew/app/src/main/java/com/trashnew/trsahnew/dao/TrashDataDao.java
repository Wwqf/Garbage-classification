package com.trashnew.trsahnew.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.model.GamePlayType;
import com.trashnew.trsahnew.model.TrashType;

import java.util.ArrayList;

/**
 * 垃圾数据存放
 */
public class TrashDataDao {

    // 表名
    public static final String TABLE_NAME = "trash_table";
    // 列名
    public static final String RES_ID = "_res_id";
    public static final String NAME = "_name";
    public static final String TRASH_TYPE = "_trash_type";
    public static final String MEDAL_NAME = "_medal_name";
    public static final String FONT_COLOR = "_font_color";
    public static final String OUTER_BORDER_COLOR = "_outer_border_color";
    public static final String INNER_BORDER_COLOR = "_inner_border_color";
    public static final String DEEP_BG_COLOR = "_deep_bg_color";
    public static final String LIGHT_BG_COLOR = "_light_bg_color";

    // 列数
    public static final int COLUMN_RES_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_TRASH_TYPE = 2;
    public static final int COLUMN_MEDAL_NAME = 3;
    public static final int COLUMN_FONT_COLOR = 4;
    public static final int COLUMN_OUTER_BORDER_COLOR = 5;
    public static final int COLUMN_INNER_BORDER_COLOR = 6;
    public static final int COLUMN_DEEP_BG_COLOR = 7;
    public static final int COLUMN_LIGHT_BG_COLOR = 8;

    ///////////////////////////// 单例 /////////////////////////////////////
    private GameSQLOpenHelper sql;
    private Context context;
    private static TrashDataDao instance;

    private TrashDataDao(Context context) {
        this.context = context;
        sql = new GameSQLOpenHelper(context);
    }

    public static TrashDataDao getInstance(Context context) {
        if (instance == null) {
            synchronized (TrashDataDao.class) {
                if (instance == null) {
                    instance = new TrashDataDao(context);
                }
            }
        }
        return instance;
    }

    public ArrayList<TrashData> getTrashData() {
        ArrayList<TrashData> result = new ArrayList<>();

        SQLiteDatabase db = sql.getReadableDatabase();
        String[] columns = {RES_ID, NAME, TRASH_TYPE, MEDAL_NAME, FONT_COLOR, OUTER_BORDER_COLOR, INNER_BORDER_COLOR, DEEP_BG_COLOR, LIGHT_BG_COLOR};

        @SuppressLint("Recycle")
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            TrashData data = new TrashData();

            data.setResId(cursor.getInt(COLUMN_RES_ID));
            data.setName(cursor.getString(COLUMN_NAME));
            data.setTrashType(TrashType.intToTrashType(cursor.getInt(COLUMN_TRASH_TYPE)));
            data.setMedalName(cursor.getString(COLUMN_MEDAL_NAME));
            data.setFontColor(cursor.getInt(COLUMN_FONT_COLOR));
            data.setOuterBorderColor(cursor.getInt(COLUMN_OUTER_BORDER_COLOR));
            data.setInnerBorderColor(cursor.getInt(COLUMN_INNER_BORDER_COLOR));
            data.setDeepBgColor(cursor.getInt(COLUMN_DEEP_BG_COLOR));
            data.setLightBgColor(cursor.getInt(COLUMN_LIGHT_BG_COLOR));
            result.add(data);
        }

        cursor.close();
        db.close();
        return result;
    }

    public static int getTrashDataLength() {
        return 12;
    }
}
