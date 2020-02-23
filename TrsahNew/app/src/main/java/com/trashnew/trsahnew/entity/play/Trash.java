package com.trashnew.trsahnew.entity.play;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.trashnew.trsahnew.base.BaseImage;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.model.TrashType;
import com.trashnew.trsahnew.util.ImageProcess;

import java.net.PortUnreachableException;

/**
 * 垃圾
 */
public class Trash extends BaseImage {
    public static int rate = 2; //速率
    public static int shallowColor = 0x33F0F0F0; //阴影颜色

    private TrashData trashData;
    private Paint paint;

    public Trash(TrashData trashData) {
        super(ImageProcess.getImage(trashData.getResId()));
        this.trashData = trashData;

        init();
    }

    public Trash(TrashData trashData, Point position) {
        super(ImageProcess.getImage(trashData.getResId()), position);
        this.trashData = trashData;

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(shallowColor);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // 绘制阴影
        canvas.drawOval(left(), top() + getHeight() + 3, right(), top() + getHeight() + 5 + 15, paint);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setRate(int rate) {
        offset(rate, 0);
    }

    public TrashType getType() {
        return trashData.getTrashType();
    }

    public TrashData getTrashData() {
        return trashData;
    }
}
