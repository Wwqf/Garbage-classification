package com.trashnew.trsahnew.entity.medal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.trashnew.trsahnew.consts.ConstMedalColors;


/**
 * 进度条
 */
public class Progress {

    private static final String TAG = "ProgressTAG";

    public int maxVal = 100;
    public int currentVal = 0;

    private Point position;
    private int radius = 110;
    private int height = 30;
    private RectF leftRect, rightRect, middleRect;
    private RectF showRect;

    public Progress(Point position, int radius, int height) {

        this.position = position;
        this.radius = radius;
        this.height = height;

        leftRect = new RectF(
                position.x - radius - height / 2, position.y - height / 2,
                position.x - radius + height / 2, position.y + height / 2
        );

        rightRect = new RectF(
                position.x + radius - height / 2, position.y - height / 2,
                position.x + radius + height / 2, position.y + height / 2
        );

        middleRect = new RectF(
                position.x - radius, position.y - height / 2,
                position.x + radius, position.y + height / 2
        );

        showRect = new RectF(
                position.x - radius, position.y - height / 2,
                position.x - radius, position.y + height / 2
        );

    }

    public void draw(Canvas canvas) {

        Path path = new Path();

        path.addArc(leftRect, 90, 180);
        path.close();

        Paint paint = new Paint();
        paint.setColor(ConstMedalColors.progressBgColor);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        if (currentVal > maxVal * 0.1) {
            paint.setColor(ConstMedalColors.progressColor);
        } else {
            paint.setColor(ConstMedalColors.progressBgColor);
        }
        canvas.drawPath(path, paint);


        paint.setColor(ConstMedalColors.progressBgColor);
        canvas.drawRect(middleRect, paint);

        if (currentVal > maxVal * 0.1) {
            paint.setColor(ConstMedalColors.progressColor);
            if (currentVal > maxVal - 10) {
                currentVal = maxVal - 10;
            }
            float pos = 1.0f * radius * 2 / 80 * (currentVal - 10);
            showRect.set(middleRect.left, middleRect.top, middleRect.left + pos, middleRect.bottom);
            canvas.drawRect(showRect, paint);
        }

        if (currentVal >= maxVal * 0.9) {
            paint.setColor(ConstMedalColors.progressColor);
        } else {
            paint.setColor(ConstMedalColors.progressBgColor);
        }

        path.reset();
        path.addArc(rightRect, -90, 180);
        canvas.drawPath(path, paint);
    }

    public Point getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }
}
