package com.trashnew.trsahnew.entity.play;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.trashnew.trsahnew.callback.TimeEndCallback;
import com.trashnew.trsahnew.util.ImageProcess;

/**
 * 倒计时
 */
public class CountDown {
    private static final String TAG = "CountDownTAG";

    private int time;
    private Point position;

    private TimeEndCallback endCallback;

    public CountDown(Point position) {
        this.position = position;
    }

    public void setTimeEndCallback(TimeEndCallback endCallback) {
        this.endCallback = endCallback;
    }

    public Thread updateTime() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    // 游戏结束回调
                    if (time < 0) {
                        endCallback.timeEnd();
                        break;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    time--;
                }
            }
        };

        return new Thread(runnable);
    }

    public void draw(Canvas canvas, Paint paint) {
        if (time <= 0) {
            Point point = new Point(
                    position.x + (int) (ImageProcess.getScreenWidthPixels() * 0.01),
                    position.y
            );
            canvas.drawText("0", point.x, point.y, paint);
        } else if (time < 10) {
            canvas.drawText("0" + String.valueOf(time), position.x, position.y, paint);
        } else {
            canvas.drawText(String.valueOf(time), position.x, position.y, paint);
        }
    }

    public void reset(int time) {
        this.time = time;
    }
}
