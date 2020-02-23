package com.trashnew.trsahnew.entity.play;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.TableRow;

import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.util.ImageProcess;

/**
 * 游戏前三关的提示文字
 */
public class TipText {

    private static final String TAG = "TipTextTAG";

    private Point startPos;
    private Point fontPos;
    private Point endPos;

    private Paint normalPaint;
    private Paint trashNamePaint;

    private int normalPaintFontSize;
    private int trashNamePaintFontSize;

    private String leftText = "正在被处理的物品是";
    private String rightText = ",  请问它属于什么类型的垃圾呢?";

    public TipText() {
        normalPaintFontSize = (int) (ImageProcess.getScreenHeightPixels() * 0.03);
        normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK);
        normalPaint.setAntiAlias(true);
        normalPaint.setTextSize(normalPaintFontSize);
        trashNamePaintFontSize = (int) (ImageProcess.getScreenHeightPixels() * 0.04);
        trashNamePaint = new Paint();
        trashNamePaint.setColor(Color.RED);
        trashNamePaint.setAntiAlias(true);
        trashNamePaint.setTextSize(trashNamePaintFontSize);


        startPos = new Point();
        fontPos = new Point();
        endPos = new Point();


        calculationPos(2);
    }

    public void draw(Canvas canvas, TrashData data) {
        calculationPos(data.getName().length());

        canvas.drawText(leftText, startPos.x, startPos.y, normalPaint);
        canvas.drawText(data.getName(), fontPos.x, fontPos.y, trashNamePaint);
        canvas.drawText(rightText, endPos.x, endPos.y, normalPaint);
    }

    private void calculationPos(int textLen) {
        int sy = (int) (ImageProcess.getScreenHeightPixels() * 0.8);
        int sx = (int) (ImageProcess.getScreenWidthPixels() - normalPaintFontSize * (leftText.length() + rightText.length()) - trashNamePaintFontSize * textLen) / 2;

        startPos.set(sx, sy);

        sx = sx + normalPaintFontSize * leftText.length();

        fontPos.set(sx, sy);

        sx = sx + trashNamePaintFontSize * textLen;

        endPos.set(sx, sy);
    }
}
