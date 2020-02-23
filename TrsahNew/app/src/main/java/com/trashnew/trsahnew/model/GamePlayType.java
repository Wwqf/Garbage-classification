package com.trashnew.trsahnew.model;

public enum GamePlayType {
    // 1.2.3
    CHOICE_TRASH(1), // 出现向下的箭头, 然后选择
    // 4.5.6
    CLICK_TRASH(2), // 点击垃圾, 放入垃圾桶
    // 7.8.9
    DRAG_TRASH(3); // 拖动垃圾


    int val;
    GamePlayType(int _val) {
        val = _val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public static GamePlayType intToPlayType(int type) {
        if (type == CHOICE_TRASH.getVal()) return CHOICE_TRASH;
        else if (type == CLICK_TRASH.getVal()) return CLICK_TRASH;
        else if (type == DRAG_TRASH.getVal()) return DRAG_TRASH;
        return null;
    }
}
