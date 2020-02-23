package com.trashnew.trsahnew.model;

public enum TrashType {
    NONE(0, "无"),
    DRY_GARBAGE(1, "干垃圾"), // 干垃圾
    WET_GARBAGE(2, "湿垃圾"), // 湿垃圾
    HARMFUL_WASTE(3, "有害垃圾"), // 有害垃圾
    RECYCLABLE_WASTE(4, "可回收垃圾"); // 可回收垃圾

    int val;
    String name;
    TrashType(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TrashType intToTrashType(int type) {
        if (type == NONE.getVal()) return NONE;
        else if (type == DRY_GARBAGE.getVal()) return DRY_GARBAGE;
        else if (type == WET_GARBAGE.getVal()) return WET_GARBAGE;
        else if (type == HARMFUL_WASTE.getVal()) return HARMFUL_WASTE;
        else if (type == RECYCLABLE_WASTE.getVal()) return RECYCLABLE_WASTE;
        return null;
    }
}
