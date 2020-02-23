package com.trashnew.trsahnew.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.trashnew.trsahnew.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 图片资源处理
 */
public class ImageProcess {

    private static final String TAG = "ImageProcessTAG";

    private static Img currentImage; // 维护一个当前使用的image

    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, Img> imageGroup = new HashMap<>();

    public static Bitmap getImage(int resId) {
        if (!imageGroup.containsKey(resId)) {
            loadingImage(resId,1f, 1f);
        }
        currentImage = imageGroup.get(resId);
        if (currentImage == null) {
            Log.e(TAG, "currentImage is null by id = " + resId);
        }
        return getCurrentImage();
    }

    public static Bitmap getImage(int resId, float dsw, float dsh) {
        if (!imageGroup.containsKey(resId)) {
            loadingImage(resId, dsw, dsh);
        }
        currentImage = imageGroup.get(resId);
        return getCurrentImage();
    }

    private static void loadingImage(int resId, float dsw, float dsh) {
        int imageStoredMaxCount = 100;

        if (imageGroup.size() > imageStoredMaxCount) {
            long minTime = 0;
            int removeResId = -1;

            long cTime = 0;
            for (Integer item : imageGroup.keySet()) {
                if (minTime == 0) {
                    cTime = Objects.requireNonNull(imageGroup.get(item)).date;
                }

                if (minTime > cTime) {
                    minTime = cTime;
                    removeResId = item;
                }
            }

            if (removeResId != -1) {
                imageGroup.remove(removeResId);
            }
        }
        putImage(resId, dsw, dsh);
    }

    private static void putImage(int resId, float dsw, float dsh) {
        Img img = new Img();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        img.bitmap = BitmapFactory.decodeResource(MainActivity.main.getResources(), resId);
        if (dsw != 1f && dsh != 1f) {
            img.bitmap = scaleImage(img.bitmap, dsw, dsh);
        }
        img.date = System.nanoTime();
        imageGroup.put(resId, img);
    }


    public static Bitmap getCurrentImage() {
        currentImage.date = System.nanoTime();
        return currentImage.bitmap;
    }

    /*Todo 分割图片 --------------------------------------------------------------------*/

    /**
     * 图片切片
     * @param bitmap 图片资源
     * @param blockCount 分割成几份
     * @param isCol 是否竖着分割
     * @return 切割完成后的图像数据
     */
    public static List<Bitmap> imgSegment(Bitmap bitmap, int blockCount, boolean isCol) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float singleLen = 0;

        Bitmap segmentBitmap = null;
        List<Bitmap> imgGroup = new ArrayList<>();

        if (isCol) {
            singleLen = width / blockCount;

            for (int i = 0; i < blockCount; i++) {
                segmentBitmap = Bitmap.createBitmap(bitmap, (int) (i * singleLen), 0, (int) singleLen, (int) height);
                imgGroup.add(segmentBitmap);
            }
        } else {
            singleLen = height / blockCount;

            for (int i = 0; i < blockCount; i++) {
                segmentBitmap = Bitmap.createBitmap(bitmap, 0, (int) (i * singleLen), (int) width, (int) (i * singleLen));
                imgGroup.add(segmentBitmap);
            }
        }

        return imgGroup;
    }


    /**
     * 图片切片
     * @param bitmap 图片资源
     * @param colsCount 列分割成几份
     * @param rowsCount 行分割成几份
     * @return 返回图片组
     */
    public static HashMap<Integer, List<Bitmap>> imgSegment(Bitmap bitmap, int colsCount, int rowsCount) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float singleWidth = width / colsCount;
        float singleHeight = height / rowsCount;

        @SuppressLint("UseSparseArrays")
        HashMap<Integer, List<Bitmap>> imgGroup = new HashMap<>();

        Bitmap segmentBitmap;

        for (int y = 0; y < rowsCount; y++) {
            List<Bitmap> imgSubGroup = new ArrayList<>();
            for (int x = 0; x < colsCount; x++) {
                segmentBitmap = Bitmap.createBitmap(bitmap, (int) (x * singleWidth), (int) (y * singleHeight), (int) singleWidth, (int) singleHeight);
                imgSubGroup.add(segmentBitmap);
            }
            imgGroup.put(y, imgSubGroup);
        }

        return imgGroup;
    }

    /*Todo 缩放图片---------------------------------------------------------------------*/

    public static Bitmap scaleImage(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static Bitmap scaleImage(Bitmap bitmap, float dsw, float dsh) {
        int width = (int) (dsw * bitmap.getWidth());
        int height = (int) (dsh * bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static Bitmap scaleImage(Bitmap bitmap, float ds) {
        int width = (int) (ds * bitmap.getWidth());
        int height = (int) (ds * bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /*Todo 获取屏幕相关参数 ----------------------------------------------------------------------------*/

    /* 获取屏幕宽度 */
    public static int getScreenWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /* 获取屏幕高度 */
    public static int getScreenHeightPixels() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /*Todo 转换函数 ----------------------------------------------------------------------------*/

    /* dip to px */
    public static int dip2px(int dip) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dip / density + 0.5f);
    }

    public static Bitmap decodeResource(int resId) {
        return BitmapFactory.decodeResource(MainActivity.main.getResources(), resId);
    }
}
