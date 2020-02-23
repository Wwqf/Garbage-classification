package com.trashnew.trsahnew.entity.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.trashnew.trsahnew.base.DualStateImage;
import com.trashnew.trsahnew.model.TrashType;

/**
 * 垃圾桶
 */
public class TrashBucket extends DualStateImage {

    private TrashType trashType; // 代表的垃圾类型

    public TrashBucket(Bitmap sourceImage, Bitmap clickImage) {
        super(sourceImage, clickImage);
    }

    public TrashBucket(Bitmap sourceImage, Bitmap clickImage, Point position) {
        super(sourceImage, clickImage, position);
    }

    public TrashType getTrashType() {
        return trashType;
    }

    public void setTrashType(TrashType trashType) {
        this.trashType = trashType;
    }
}
