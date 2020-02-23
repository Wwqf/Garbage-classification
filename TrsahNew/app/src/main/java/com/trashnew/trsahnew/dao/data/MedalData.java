package com.trashnew.trsahnew.dao.data;

public class MedalData {

    private int resId; // resId
    private int maxVal; // 最大次数
    private int currentVal; // 当前正确次数

    public MedalData() { }

    public MedalData(int resId, int maxVal, int currentVal) {
        this.resId = resId;
        this.maxVal = maxVal;
        this.currentVal = currentVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public int getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(int currentVal) {
        this.currentVal = currentVal;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
