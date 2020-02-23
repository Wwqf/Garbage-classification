package com.trashnew.trsahnew.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public abstract class BaseImage {
    protected Bitmap image; // 图片资源
    protected Point position; // 图片的宽高
    protected int width, height; // 绘制图片的位置需要的点坐标

    public boolean isDrawRange = false;

    public BaseImage() { }

    public BaseImage(Bitmap bitmap) {
        this.image = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.position = new Point();
    }

    public BaseImage(Bitmap bitmap, Point point) {
        this(bitmap);

        if (point != null)
            this.position = point;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, left(), top(), null);

        if (isDrawRange) {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(3);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(left(), top(), right(), bottom(), paint);
        }
    }

    public boolean isClicked(float x, float y) {
        return isInRange(x, y);
    }

    // (x, y) 是否在image内
    private boolean isInRange(float x, float y) {
        if (x > left() && x < right()) {
            return y > top() && y < bottom();
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return isClicked(event.getX(), event.getY());
    }

    public boolean collisions(BaseImage otherImage) {
        return Math.abs(otherImage.centerX() - this.centerX()) < otherImage.getWidth() / 2 + this.getWidth() / 2 &&
                Math.abs(otherImage.centerY() - this.centerY()) < otherImage.getHeight() / 2 + this.getHeight() / 2;
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.image = bitmap;
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
        }
    }

    public Bitmap getBitmap() {
        return image;
    }

    public Point getPoint() {
        return position;
    }

    public float left() {
        return position.x - width / 2;
    }

    public void leftTop(int x, int y) {
        position.x = x + width / 2;
        position.y = y + height / 2;
    }

    public float right() {
        return position.x + width / 2;
    }

    public float top() {
        return position.y - height / 2;
    }

    public float bottom() {
        return position.y + height / 2;
    }

    public float centerX() {
        return position.x;
    }

    public float centerY() {
        return position.y;
    }

    public void offset(int dx, int dy) {
        position.offset(dx, dy);
    }

    public void set(Point point) {
        this.position = point;
    }

    public void set(int x, int y) {
        this.position.set(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDrawRange() {
        return isDrawRange;
    }

    public void setDrawRange(boolean drawRange) {
        isDrawRange = drawRange;
    }
}
