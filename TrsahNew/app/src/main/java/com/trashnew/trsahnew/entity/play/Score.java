package com.trashnew.trsahnew.entity.play;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 分数
 */
public class Score {

    private static final String TAG = "ScoreTAG";

    private int score;
    private Point position;

    public Score(Point position) {
        this.position = position;
    }

    public void draw(Canvas canvas, Paint paint, int score) {
        canvas.drawText("分数: " + score, position.x, position.y, paint);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
