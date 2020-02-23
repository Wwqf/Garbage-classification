package com.trashnew.trsahnew.entity.menu;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.trashnew.trsahnew.base.DualStateImage;

/**
 * 勋章入口按钮
 */
public class MedalButton_Menu extends DualStateImage {


    public MedalButton_Menu(Bitmap sourceImage, Bitmap clickImage) {
        super(sourceImage, clickImage);
    }

    public MedalButton_Menu(Bitmap sourceImage, Bitmap clickImage, Point position) {
        super(sourceImage, clickImage, position);
    }
}
