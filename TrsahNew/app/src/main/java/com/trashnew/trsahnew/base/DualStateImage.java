package com.trashnew.trsahnew.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

public abstract class DualStateImage extends BaseImage {

    // 点击前的image, 点击后的image
    protected Bitmap sourceImage, clickImage;
    protected boolean clickState = false;

    public DualStateImage(Bitmap sourceImage, Bitmap clickImage) {
        super(sourceImage);
        this.sourceImage = sourceImage;
        this.clickImage = clickImage;
    }

    public DualStateImage(Bitmap sourceImage, Bitmap clickImage, Point position) {
        super(sourceImage, position);
        this.sourceImage = sourceImage;
        this.clickImage = clickImage;
    }

    @Override
    public void draw(Canvas canvas) {

        if (clickState) {
            image = clickImage;
        } else image = sourceImage;

        super.draw(canvas);
    }

    public void changeStated() {
        clickState = !clickState;
    }

    public void setState(boolean state) {
        clickState = state;
    }

    public boolean getState() {
        return clickState;
    }

}
