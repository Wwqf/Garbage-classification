package com.trashnew.trsahnew.manager;

import android.graphics.Bitmap;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.activity.MainActivity;
import com.trashnew.trsahnew.dao.TrashDataDao;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.entity.play.Trash;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

/**
 * 加载资源
 */
public class LoadResources {

    private float menu_dsw, menu_dsh;
    private float play_dsw, play_dsh;
    private int width, height;

    public LoadResources() {
        width = ImageProcess.getScreenWidthPixels();
        height = ImageProcess.getScreenHeightPixels();

        // 加载背景
        Bitmap menuBg = load(R.drawable.timg3);
        Bitmap playBg = load(R.drawable.game_bg);

        menu_dsw = 1.0f * width / menuBg.getWidth();
        menu_dsh = 1.0f * height / menuBg.getHeight();

        play_dsw = 1.0f * width / playBg.getWidth();
        play_dsh = 1.0f * height / playBg.getHeight();

        // 关卡的图标和得星
        load(R.drawable.star, play_dsw, play_dsh);
        load(R.drawable.level, play_dsw, play_dsh);

        // 勋章按钮
        load(R.drawable.medal, play_dsw, play_dsh);
        load(R.drawable.medal_choice, play_dsw, play_dsh);

        // 四个垃圾桶
        load(R.drawable.gan, play_dsw, play_dsh);
        load(R.drawable.shi, play_dsw, play_dsh);
        load(R.drawable.kehuishou, play_dsw, play_dsh);
        load(R.drawable.youhai, play_dsw, play_dsh);

        // 垃圾
        ArrayList<TrashData> trashes = TrashDataDao.getInstance(MainActivity.main).getTrashData();
        for (int l = 0; l < trashes.size(); l++) {
            load(trashes.get(l).getResId(), play_dsw * 0.25f, play_dsh * 0.25f);
        }

        // 对话框
        load(R.drawable.dialog, play_dsw, play_dsh);

        load(R.drawable.return_button, play_dsw * 0.4f, play_dsh * 0.4f);
    }

    public Bitmap load(int resId) {
        return ImageProcess.getImage(resId);
    }

    public void load(int resId, float dsw, float dsh) {
        ImageProcess.getImage(resId, dsw, dsh);
    }
}
