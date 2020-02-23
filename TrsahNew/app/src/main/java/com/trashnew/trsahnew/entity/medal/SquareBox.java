package com.trashnew.trsahnew.entity.medal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.trashnew.trsahnew.dao.data.TrashData;

import java.util.ArrayList;

/**
 * 方框
 */
public class SquareBox {

    // 以传入的点为坐标, 为box的中心点坐标
    // 则左上角的点为(px - radius, py - tilt_len - height_len / 2), 右上角的点为(px + radius, , py - tilt_len - height_len / 2)

    public static final int tilt_len = 8;
    public static final int height_len = 16;
    public static final int outer_dis_inner = 10;

    private Point position;
    private int radius;

    private Paint paint;
    private TrashData trashData;
    private ArrayList<Point> outer_octagon, inner_octagon;
    private ArrayList<Path> bgLine;

    //////////////////////颜色////////////////////////////////////////
    private int outerBorderColor;
    private int innerBorderColor;
    private int lightBgColor;
    private int deepBgColor;
    private int showTextColor;
    private String showText;

    public SquareBox(Point position, int radius, TrashData trashData) {
        this.position = position;
        this.radius = radius;
        this.trashData = trashData;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        initOuterOctagon();
        initInnerOctagon();
        initBgLine();

        outerBorderColor = trashData.getOuterBorderColor();
        innerBorderColor = trashData.getInnerBorderColor();
        lightBgColor = trashData.getLightBgColor();
        deepBgColor = trashData.getDeepBgColor();
        showTextColor = trashData.getFontColor();
        showText = trashData.getMedalName();
    }

    public SquareBox(Point position, int radius, int outerBorderColor, int innerBorderColor, int lightBgColor, int deepBgColor, int showTextColor, String showText) {
        this.position = position;
        this.radius = radius;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        initOuterOctagon();
        initInnerOctagon();
        initBgLine();

        this.outerBorderColor = outerBorderColor;
        this.innerBorderColor = innerBorderColor;
        this.lightBgColor = lightBgColor;
        this.deepBgColor = deepBgColor;
        this.showTextColor = showTextColor;
        this.showText = showText;
    }


    public void initOuterOctagon() {
        outer_octagon = new ArrayList<>();
        outer_octagon.add(new Point(
                position.x - radius,
                position.y - tilt_len - height_len / 2
        )); // 起始点
        outer_octagon.add(new Point(
                position.x + radius,
                position.y - tilt_len - height_len / 2
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(outer_octagon.size() - 1).x + tilt_len,
                outer_octagon.get(outer_octagon.size() - 1).y + tilt_len
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(outer_octagon.size() - 1).x,
                outer_octagon.get(outer_octagon.size() - 1).y + height_len
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(outer_octagon.size() - 1).x - tilt_len,
                outer_octagon.get(outer_octagon.size() - 1).y + tilt_len
        ));
        outer_octagon.add(new Point(
                position.x - radius,
                outer_octagon.get(outer_octagon.size() - 1).y
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(outer_octagon.size() - 1).x - tilt_len,
                outer_octagon.get(outer_octagon.size() - 1).y - tilt_len
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(outer_octagon.size() - 1).x,
                outer_octagon.get(outer_octagon.size() - 1).y - height_len
        ));
        outer_octagon.add(new Point(
                outer_octagon.get(0).x, outer_octagon.get(0).y
        ));
    }

    public void initInnerOctagon() {
        inner_octagon = new ArrayList<>();
    }

    public void initBgLine() {
        bgLine = new ArrayList<>();
        int len = radius * 2;

        int upX = position.x - radius;
        int upY = position.y - tilt_len - height_len / 2;

        int downX = position.x - radius;
        int downY = position.y + tilt_len + height_len / 2;

        int share = len / 5;
        Path path1 = new Path();
        path1.moveTo(outer_octagon.get(0).x, outer_octagon.get(0).y);
        path1.lineTo(upX + share, upY);
        path1.lineTo(outer_octagon.get(outer_octagon.size() - 3).x, outer_octagon.get(outer_octagon.size() - 3).y);
        path1.lineTo(outer_octagon.get(outer_octagon.size() - 2).x, outer_octagon.get(outer_octagon.size() - 2).y);
        path1.close();

        bgLine.add(path1);

        Path path2 = new Path();
        path2.moveTo(upX + share, upY);
        path2.lineTo(upX + 2 * share, upY);
        path2.lineTo(outer_octagon.get(outer_octagon.size() - 4).x, outer_octagon.get(outer_octagon.size() - 4).y);
        path2.lineTo(outer_octagon.get(outer_octagon.size() - 3).x, outer_octagon.get(outer_octagon.size() - 3).y);
        path2.lineTo(upX + share, upY);
        path2.close();

        bgLine.add(path2);

        Path path3 = new Path();
        path3.moveTo(upX + 2 * share, upY);
        path3.lineTo(upX + 3 * share, upY);
        path3.lineTo(downX + share, downY);
        path3.lineTo(downX, downY);
        path3.lineTo(upX + 2 * share, upY);
        path3.close();

        bgLine.add(path3);

        Path path4 = new Path();
        path4.moveTo(upX + 3 * share, upY);
        path4.lineTo(upX + 4 * share, upY);
        path4.lineTo(downX + 2 * share, downY);
        path4.lineTo(downX + share, downY);
        path4.lineTo(upX + 3 * share, upY);
        path4.close();

        bgLine.add(path4);

        Path path5 = new Path();
        path5.moveTo(upX + 4 * share, upY);
        path5.lineTo(upX + 5 * share, upY);
        path5.lineTo(downX + 3 * share, downY);
        path5.lineTo(downX + 2 * share, downY);
        path5.lineTo(upX + 4 * share, upY);
        path5.close();

        bgLine.add(path5);

        Path path6 = new Path();
        path6.moveTo(outer_octagon.get(1).x, outer_octagon.get(1).y);
        path6.lineTo(outer_octagon.get(2).x, outer_octagon.get(2).y);
        path6.lineTo(downX + 4 * share, downY);
        path6.lineTo(downX + 3 * share, downY);
        path6.lineTo(outer_octagon.get(1).x, outer_octagon.get(1).y);
        path6.close();

        bgLine.add(path6);

        Path path7 = new Path();
        path7.moveTo(outer_octagon.get(2).x, outer_octagon.get(2).y);
        path7.lineTo(outer_octagon.get(3).x, outer_octagon.get(3).y);
        path7.lineTo(outer_octagon.get(4).x, outer_octagon.get(4).y);
        path7.lineTo(downX + 4 * share, downY);
        path7.lineTo(outer_octagon.get(2).x, outer_octagon.get(2).y);
        path7.close();

        bgLine.add(path7);
    }

    public void draw(Canvas canvas) {
        Path outer_path = new Path();

        outer_path.moveTo(outer_octagon.get(0).x, outer_octagon.get(0).y);
        for (int l = 1; l < outer_octagon.size(); l++) {
            outer_path.lineTo(outer_octagon.get(l).x, outer_octagon.get(l).y);
        }
        outer_path.close();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(outerBorderColor);
        canvas.drawPath(outer_path, paint);

//        Path inner_path = new Path();
//        inner_path.moveTo(inner_octagon.get(0).x, inner_octagon.get(0).y);
//        for (int l = 1; l < inner_octagon.size(); l++) {
//            inner_path.lineTo(inner_octagon.get(l).x, inner_octagon.get(l).y);
//        }
//        inner_path.close();
//
//        paint.setColor(trashMedalModel.get_inner_border());
//        canvas.drawPath(inner_path, paint);

        paint.setStyle(Paint.Style.FILL);


        for (int l = 0; l < bgLine.size(); l++) {
            if ( l % 2 == 0 ) {
                paint.setColor(lightBgColor);
            } else {
                paint.setColor(deepBgColor);
            }

            canvas.drawPath(bgLine.get(l), paint);
        }

        int size = radius / 3;
        paint.setTextSize(size);

        paint.setColor(showTextColor);
        canvas.drawText(showText, position.x - size * 2, position.y + size * 0.38f, paint);
    }


    public Point getPosition() {
        return position;
    }

    public int getHeight() {
        return tilt_len + tilt_len + height_len;
    }
}
