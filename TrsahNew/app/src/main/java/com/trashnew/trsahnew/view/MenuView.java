package com.trashnew.trsahnew.view;

import android.graphics.Canvas;
import android.graphics.Point;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.activity.MainActivity;
import com.trashnew.trsahnew.consts.ConstMenuPosition;
import com.trashnew.trsahnew.dao.CheckPointDataDao;
import com.trashnew.trsahnew.dao.MedalDataDao;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.entity.medal.SquareBox;
import com.trashnew.trsahnew.entity.menu.CheckPointIcon;
import com.trashnew.trsahnew.entity.menu.MedalButton_Menu;
import com.trashnew.trsahnew.entity.play.Dialog;
import com.trashnew.trsahnew.model.GameState;
import com.trashnew.trsahnew.util.DialogText;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;

public class MenuView {

    private ArrayList<CheckPointIcon> checkPointIcons;
    private MedalButton_Menu medalButtonMenu;

    private CheckPointData currentCheckPoint;
    private CheckPointData nextCheckPoint;

    private SquareBox handBoxIcon;
    private Dialog dialog;

    private boolean isDialog = false;

    private GameView father;
    public MenuView(GameView father) {
        this.father = father;

        CheckPointDataDao.getInstance(MainActivity.main).update(4, true);
        CheckPointDataDao.getInstance(MainActivity.main).update(9, true);

        initCheckPoint();
        initMedalButton();
        initHandBoxIcon();
        initDialog();
    }

    private void initCheckPoint() {
        checkPointIcons = new ArrayList<>();
        ArrayList<Point> checkPointPositions = ConstMenuPosition.getIconPositionGroup();
        ArrayList<CheckPointData> checkPointData = CheckPointDataDao.getInstance(MainActivity.main).getCheckPointData();

        for (int l = 0; l < checkPointPositions.size(); l++) {
            checkPointIcons.add(new CheckPointIcon(
                ImageProcess.getImage(R.drawable.level),
                checkPointPositions.get(l), checkPointData.get(l)
            ));
        }
    }

    private void initMedalButton() {
        medalButtonMenu = new MedalButton_Menu(
          ImageProcess.getImage(R.drawable.medal),
          ImageProcess.getImage(R.drawable.medal_choice)
        );

        medalButtonMenu.set(new Point(
                (int) (ImageProcess.getScreenWidthPixels() - medalButtonMenu.getWidth() + ImageProcess.getScreenWidthPixels() * 0.1),
                (int) (ImageProcess.getScreenHeightPixels() - medalButtonMenu.getHeight() + ImageProcess.getScreenHeightPixels() * 0.05)
        ));
    }

    private void initHandBoxIcon() {
        handBoxIcon = new SquareBox(
                new Point(90, 30), 60,
                0xFF070938, 0xFFFFFFFF, 0xFF5a479f,
                0x666a59a8,0xFFf8ca74, "图鉴"
        );
    }

    private void initDialog() {
        dialog = new Dialog(ImageProcess.getImage(R.drawable.dialog));
    }

    public void draw(Canvas canvas) {
        for (CheckPointIcon icon : checkPointIcons) {
            icon.draw(canvas);
        }
        medalButtonMenu.draw(canvas);
        handBoxIcon.draw(canvas);

        if (isDialog) {
            dialog.draw(canvas, DialogText.checkPointNotOpen());
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        // 点击dialog
        if (dialog.isClickOkButton(event)) {
            isDialog = false;
            return true;
        }

        // 打开勋章界面
        if  (medalButtonMenu.onTouchEvent(event)) {
            GameView.gameState = GameState.GAME_MEDAL;
            return true;
        }

        // 打开游戏界面
        for (CheckPointIcon icon : checkPointIcons) {
            if (icon.onTouchEvent(event)) {
                // 可玩
                if (icon.isPlayed()) {
                    currentCheckPoint = icon.getCheckPointData();
                    father.setPlayView(new PlayView(this, icon.getCheckPointData()));
                    GameView.gameState = GameState.GAME_PLAY;
                } else {
                    isDialog = true;
                }

                return true;
            }
        }

        return false;
    }

    public void openNextCheckPoint() {
        for (int l = 0; l < checkPointIcons.size() - 1; l++) {
            if (currentCheckPoint == checkPointIcons.get(l).getCheckPointData()) {
                nextCheckPoint = checkPointIcons.get(l + 1).getCheckPointData();
            }
        }
        Log.e(getClass().getName() + "___", nextCheckPoint.get_id() + " is played.");

        if (currentCheckPoint.get_star() >= 2) {
            nextCheckPoint.set_isPlayed(true);
            CheckPointDataDao.getInstance(MainActivity.main).update(nextCheckPoint.get_id(), true);
        }
    }
}
