package com.trashnew.trsahnew.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.trashnew.trsahnew.entity.medal.MedalViewBackground;
import com.trashnew.trsahnew.model.GameState;

/**
 * 游戏背景
 */
public class GameBackground {

    private Bitmap menuBackground;
    private Bitmap playBackground;
    private MedalViewBackground medalViewBackground;

    // 屏幕宽高
    private RectF screenRange;

    public GameBackground(int screenW, int screenH) {
        screenRange = new RectF(0, 0, screenW, screenH);
        medalViewBackground = new MedalViewBackground();
    }

    public void draw(Canvas canvas, GameState state) {
        // 根据不同的游戏状态绘制不同的背景图
        if (state == GameState.GAME_MENU) {
            canvas.drawBitmap(menuBackground, null, screenRange, null);
        } else if (state == GameState.GAME_PLAY) {
            canvas.drawBitmap(playBackground, null, screenRange, null);
        } else if (state == GameState.GAME_MEDAL) {
            medalViewBackground.draw(canvas);
        }
    }

    public void setMenuBackground(Bitmap menuBackground) {
        this.menuBackground = menuBackground;
    }

    public void setPlayBackground(Bitmap playBackground) {
        this.playBackground = playBackground;
    }
}
