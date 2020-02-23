package com.trashnew.trsahnew.consts;

import android.graphics.Point;

import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

/**
 * 菜单界面关卡icon坐标
 */
public class ConstMenuPosition {

    public static ArrayList<Point> getIconPositionGroup() {

        ArrayList<Point> result = new ArrayList<>();
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.2),
                (int) (ImageProcess.getScreenHeightPixels() * 0.35)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.32),
                (int) (ImageProcess.getScreenHeightPixels() * 0.55)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.45),
                (int) (ImageProcess.getScreenHeightPixels() * 0.8)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.62),
                (int) (ImageProcess.getScreenHeightPixels() * 0.9)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.67),
                (int) (ImageProcess.getScreenHeightPixels() * 0.7)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.8),
                (int) (ImageProcess.getScreenHeightPixels() * 0.42)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.92),
                (int) (ImageProcess.getScreenHeightPixels() * 0.20)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.58),
                (int) (ImageProcess.getScreenHeightPixels() * 0.3)
        ));
//
        result.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.32),
                (int) (ImageProcess.getScreenHeightPixels() * 0.1)
        ));

        return result;
    }

}
