package com.trashnew.trsahnew.entity.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.trashnew.trsahnew.base.BaseImage;
import com.trashnew.trsahnew.util.ImageProcess;

/**
 * 对话框
 */
public class Dialog extends BaseImage {

    private Point position;
    private RectF okButtonRange;

    private Paint paint;
    private Point textShowPos;
    private int maxTextSize = 20;
    private float fontSize;
    private int startX, startY;

    public Dialog(Bitmap image) {
        super(image);

        position = new Point(
                ImageProcess.getScreenWidthPixels() / 2,
                ImageProcess.getScreenHeightPixels() / 2
        );

        set(position);

        okButtonRange = new RectF(
            left() + getWidth() * 0.3f,
            top() + getHeight() * 0.7f,
            right() - getWidth() * 0.3f,
            bottom() - getHeight() * 0.2f
        );

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        fontSize = ImageProcess.getScreenHeightPixels() * 0.05f;
        paint.setTextSize(fontSize);

        textShowPos = new Point();
    }

    public boolean isClickOkButton(MotionEvent event) {
        if (okButtonRange.left < event.getX() && okButtonRange.right > event.getX()) {
            return okButtonRange.top < event.getY() && okButtonRange.bottom > event.getY();
        }
        return false;
    }

    public void draw(Canvas canvas, String text) {
        super.draw(canvas);

        if (text == null) return;

        maxTextSize = (int) (getWidth() * 0.75 / fontSize);


       int startIndex = 0, endIndex = 0;
       String subString = "";

       int line = (text.length() / maxTextSize) + 1;

       for (int l = line; l > 0; l--) {
           if (endIndex + maxTextSize > text.length()) {
               startIndex = endIndex;
               endIndex = text.length();
           } else {
               startIndex = endIndex;
               endIndex += maxTextSize;
           }

           textShowPos.set(
                   (int) (centerX() - (endIndex - startIndex) / 2 * fontSize),
                   getY(l)
           );
           subString = text.substring(startIndex, endIndex);
           canvas.drawText(subString, textShowPos.x, textShowPos.y, paint);
       }

    }

    private int getY(int line) {
        int startY = 0;

        if (line == 3) startY = (int) (centerY() - fontSize * 2);
        else if (line == 2) startY = (int) (centerY() - fontSize);
        else if (line == 1) startY = (int) (centerY());
        return startY;
    }
}
