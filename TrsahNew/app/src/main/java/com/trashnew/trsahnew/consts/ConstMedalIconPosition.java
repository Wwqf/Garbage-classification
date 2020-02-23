package com.trashnew.trsahnew.consts;

import android.graphics.Point;

import com.trashnew.trsahnew.dao.TrashDataDao;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

/**
 * 勋章界面图标坐标
 */
public class ConstMedalIconPosition {


    public static ArrayList<Point> getMedalIconPos() {

        ArrayList<Point> result = new ArrayList<>();

        double startX = 0.25;
        double startY = 0.1;
        double upDown = 0.32;
        double leftRight = 0.18;

        for (int l = 0; l < 4; l++) {
            for (int r = 0; r < TrashDataDao.getTrashDataLength() / 4; r++) {
                result.add(new Point(
                        (int) (ImageProcess.getScreenWidthPixels() * (startX + l * leftRight)),
                        (int) (ImageProcess.getScreenHeightPixels() * (startY + r * upDown))
                ));
            }
        }
        return result;
    }
}
