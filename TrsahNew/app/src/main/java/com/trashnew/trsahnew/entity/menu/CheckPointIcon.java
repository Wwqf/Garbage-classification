package com.trashnew.trsahnew.entity.menu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.base.BaseImage;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

/**
 * 关卡图标
 */
public class CheckPointIcon extends BaseImage {

    private CheckPointData checkPointData;
    private Bitmap star;
    private ArrayList<RectF> starPosGroup;
    private Point textPos;
    private int fontSize;

    public CheckPointIcon(Bitmap image, CheckPointData checkPointData) {
        super(image);
        this.checkPointData = checkPointData;

        init();
    }

    public CheckPointIcon(Bitmap image, Point position, CheckPointData checkPointData) {
        super(image, position);
        this.checkPointData = checkPointData;

        init();
    }

    private void init() {
        star = ImageProcess.getImage(R.drawable.star);

        starPosGroup = new ArrayList<>();
        starPosGroup.add(new RectF(
                left() + getWidth() * 0.26f,
                bottom() - getHeight() * 0.22f,
                left() + getWidth() * 0.44f,
                bottom() - getHeight() * 0.04f
        ));

        starPosGroup.add(new RectF(
                left() + getWidth() * 0.42f,
                bottom() - getHeight() * 0.22f,
                left() + getWidth() * 0.60f,
                bottom() - getHeight() * 0.04f
        ));

        starPosGroup.add(new RectF(
                left() + getWidth() * 0.58f,
                bottom() - getHeight() * 0.22f,
                left() + getWidth() * 0.76f,
                bottom() - getHeight() * 0.04f
        ));

        fontSize = getWidth() / 4;
        textPos = new Point((int)(left() + getWidth() * 0.44f),  (int)(top() + getHeight() * 0.35f));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (int l = 0; l < checkPointData.get_star(); l++) {
            canvas.drawBitmap(star,  null, starPosGroup.get(l), null);
        }

        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        paint.setStrokeWidth(8);
        paint.setColor(0xFF070938);
        canvas.drawText(String.valueOf(checkPointData.get_id()), textPos.x, textPos.y, paint);
    }

    public boolean isPlayed() {
        return checkPointData.is_isPlayed();
    }

    public CheckPointData getCheckPointData() {
        return checkPointData;
    }
}
