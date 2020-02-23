package com.trashnew.trsahnew.view;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.activity.MainActivity;
import com.trashnew.trsahnew.consts.ConstMedalIconPosition;
import com.trashnew.trsahnew.dao.MedalDataDao;
import com.trashnew.trsahnew.dao.TrashDataDao;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.entity.medal.MedalCombination;
import com.trashnew.trsahnew.entity.medal.ReturnButton;
import com.trashnew.trsahnew.model.GameState;
import com.trashnew.trsahnew.util.DialogText;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

/**
 * 勋章界面
 */
public class MedalView {

    ArrayList<Point> points;    // 勋章icon的坐标
    ArrayList<TrashData> trashData; // 每个勋章的垃圾数据
    ArrayList<MedalCombination> medalCombinations;  // 勋章icon
    ReturnButton returnButton;  // 返回按钮

    private MedalCombination currentMedal;  // 当前点击的勋章

    public MedalView() {
        points = ConstMedalIconPosition.getMedalIconPos();
        trashData = TrashDataDao.getInstance(MainActivity.main).getTrashData();

        medalCombinations = new ArrayList<>();

        for (int l = 0; l < points.size(); l++) {
            medalCombinations.add(new MedalCombination(points.get(l), trashData.get(l)));
        }

        returnButton = new ReturnButton(ImageProcess.getImage(R.drawable.return_button));
        returnButton.set(returnButton.getWidth() / 2 + 10, returnButton.getHeight() / 2 + 10);
    }

    public boolean onTouchEvent(MotionEvent event) {

        for (MedalCombination item : medalCombinations) {
            if (item.ouTouchEvent(event)) {
                currentMedal = item;
                return true;
            }
        }

        if (returnButton.onTouchEvent(event)) {
            GameView.gameState = GameState.GAME_MENU;
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (MedalCombination item : medalCombinations) {
            item.draw(canvas);
        }
        returnButton.draw(canvas);

        if (currentMedal != null && currentMedal.getRoundBox().isDialogState()) {
            currentMedal.getRoundBox().getDialog().draw(canvas, DialogText.getMedalClickDialog(currentMedal.getRoundBox().getTrash()));
        }
    }
}
