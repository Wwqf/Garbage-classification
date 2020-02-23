package com.trashnew.trsahnew.dao.data;

import com.trashnew.trsahnew.model.TrashType;

public class TrashData {

    private String name;
    private int resId;
    private TrashType trashType;

    private String medalName;
    private int fontColor;
    private int outerBorderColor;
    private int innerBorderColor;
    private int deepBgColor;
    private int lightBgColor;

    public TrashData() { }

    public TrashData(int resId, String name, TrashType trashType, String medalName, int fontColor, int outerBorderColor, int innerBorderColor, int deepBgColor, int lightBgColor) {
        this.resId = resId;
        this.name = name;
        this.trashType = trashType;
        this.medalName = medalName;
        this.fontColor = fontColor;
        this.outerBorderColor = outerBorderColor;
        this.innerBorderColor = innerBorderColor;
        this.deepBgColor = deepBgColor;
        this.lightBgColor = lightBgColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public TrashType getTrashType() {
        return trashType;
    }

    public void setTrashType(TrashType trashType) {
        this.trashType = trashType;
    }

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getOuterBorderColor() {
        return outerBorderColor;
    }

    public void setOuterBorderColor(int outerBorderColor) {
        this.outerBorderColor = outerBorderColor;
    }

    public int getInnerBorderColor() {
        return innerBorderColor;
    }

    public void setInnerBorderColor(int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
    }

    public int getDeepBgColor() {
        return deepBgColor;
    }

    public void setDeepBgColor(int deepBgColor) {
        this.deepBgColor = deepBgColor;
    }

    public int getLightBgColor() {
        return lightBgColor;
    }

    public void setLightBgColor(int lightBgColor) {
        this.lightBgColor = lightBgColor;
    }
}
