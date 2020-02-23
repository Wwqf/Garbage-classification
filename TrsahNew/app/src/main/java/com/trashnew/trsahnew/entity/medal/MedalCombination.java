package com.trashnew.trsahnew.entity.medal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.activity.MainActivity;
import com.trashnew.trsahnew.consts.ConstMedalColors;
import com.trashnew.trsahnew.dao.MedalDataDao;
import com.trashnew.trsahnew.dao.data.MedalData;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.entity.play.Trash;

import java.util.Objects;

/**
 * 勋章icon组合
 */
public class MedalCombination {

    private RoundBox roundBox;
    private SquareBox squareBox;
    private Progress progress;
    private String text;
    private int count;

    private Trash trash;
    private TrashData trashData;

    Point roundPoint;
    Point squarePoint;
    Point progressPoint;
    Point textPoint;


    public MedalCombination(Point roundPoint, TrashData trashData) {
        this.roundPoint = roundPoint;
        this.trashData = trashData;

        trash = new Trash(trashData);
        roundBox = new RoundBox(roundPoint, trash);

        squarePoint = new Point(
                roundPoint.x,
                roundPoint.y + RoundBox.outerRadius + 30
        );
        squareBox = new SquareBox(squarePoint, RoundBox.outerRadius + 10, trashData);

        progressPoint = new Point(
                squareBox.getPosition().x,
                squareBox.getPosition().y + squareBox.getHeight() + 8
        );
        progress = new Progress(progressPoint, 80, 20);
        progress.maxVal = Objects.requireNonNull(MedalDataDao.getInstance(MainActivity.main).getMedalData().get(trashData.getResId())).getMaxVal();
        progress.currentVal = Objects.requireNonNull(MedalDataDao.getInstance(MainActivity.main).getMedalData().get(trashData.getResId())).getCurrentVal();

        textPoint = new Point(
            progress.getPosition().x - progress.getRadius(),
                progress.getPosition().y + progress.getHeight() + 10
        );
    }

    public boolean ouTouchEvent(MotionEvent event) {
        return roundBox.onTouchEvent(event);
    }

    public void draw(Canvas canvas) {
        count = Objects.requireNonNull(MedalDataDao.getInstance(MainActivity.main).getMedalData().get(trashData.getResId())).getCurrentVal();
        progress.currentVal = count;


        roundBox.draw(canvas);
        squareBox.draw(canvas);
        progress.draw(canvas);


        text = "连续清理 " + trashData.getName() + count + " 次";

        Paint paint = new Paint();
        paint.setColor(ConstMedalColors.fontColor);
        paint.setTextSize(18);
        paint.setStrokeWidth(3);

        canvas.drawText(text, textPoint.x, textPoint.y, paint);
    }

    public RoundBox getRoundBox() {
        return roundBox;
    }
}
