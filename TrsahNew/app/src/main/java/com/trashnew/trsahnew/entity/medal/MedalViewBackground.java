package com.trashnew.trsahnew.entity.medal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.trashnew.trsahnew.consts.ConstMedalColors;
import com.trashnew.trsahnew.util.ImageProcess;

/**
 * 勋章界面背景
 */
public class MedalViewBackground {

    private static final String TAG = "MedalViewTAG";

    private RectF range;
    private Paint bgPaint;


    public MedalViewBackground() {
        range = new RectF(0, 0, ImageProcess.getScreenWidthPixels(), ImageProcess.getScreenHeightPixels());

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(ConstMedalColors.BackgroundColor);
        bgPaint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(range, bgPaint);
    }

}
