package com.trashnew.trsahnew.entity.medal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.view.MotionEvent;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.consts.ConstMedalColors;
import com.trashnew.trsahnew.entity.play.Dialog;
import com.trashnew.trsahnew.entity.play.Trash;
import com.trashnew.trsahnew.util.DialogText;
import com.trashnew.trsahnew.util.ImageProcess;

/**
 * 圆框
 */
public class RoundBox {

    public static int outerRadius = 50;
    public static int innerRadius = 45;
    private Point position;
    private Paint paint;
    private Trash trash;

    private boolean isDialog = false;
    private Dialog dialog;

    public RoundBox(Point position) {
        this.position = position;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        dialog = new Dialog(ImageProcess.getImage(R.drawable.dialog));
    }

    public RoundBox(Point position, Trash trash) {
        this(position);
        this.trash = trash;
    }

    private boolean isRange(MotionEvent event) {
        int left = position.x - innerRadius;
        int right =  position.x + innerRadius;
        int top = position.y - innerRadius;
        int bottom = position.y + innerRadius;

        if (event.getX() > left && event.getX() < right) {
            return event.getY() > top && event.getY() < bottom;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isRange(event)) {
            if (isDialog) {
                return false;
            }

            isDialog = true;
            return true;
        }

        if (dialog.isClickOkButton(event)) {
            isDialog = false;
            return true;
        }

        return false;
    }

    public void draw(Canvas canvas) {
        paint.setColor(ConstMedalColors.circleBorderColor);
        canvas.drawCircle(position.x, position.y, outerRadius, paint);
        paint.setColor(ConstMedalColors.circleInnerBgColor);
        canvas.drawCircle(position.x, position.y, innerRadius, paint);

        trash.set(new Point(position.x, position.y - 10));
        trash.draw(canvas);

    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public boolean isDialogState() {
        return isDialog;
    }

    public Trash getTrash() {
        return trash;
    }
}
