package com.trashnew.trsahnew.util;

import com.trashnew.trsahnew.entity.play.Trash;

public class DialogText {

    public static String getMistakeDialogText(int count) {
        return "还有" + count + "次机会!";
    }

    public static String checkPointNotOpen() {
        return "请先通过上一关卡哦,每个关卡需要达到两颗星才算通关!";
    }

    public static String getMedalClickDialog(Trash trash) {
        return trash.getTrashData().getName() + ": " + trash.getTrashData().getTrashType().getName();
    }


    public static String getCheckPointTip(int checkPointId) {
        if (checkPointId == 1 || checkPointId == 2 || checkPointId == 3) {
            return "一二三关 垃圾会自动在屏幕中间位置停下,你需要点击正确的垃圾桶";
        } else if (checkPointId == 4 || checkPointId == 5 || checkPointId == 6) {
            return "四五六关 你需要先点击垃圾桶,再选择正确的的垃圾";
        } else if (checkPointId == 7 || checkPointId == 8 || checkPointId == 9) {
            return "七八九关 你可以将垃圾拖至对应垃圾桶, 表示投放";
        }
        return "错误关卡";
    }
}
