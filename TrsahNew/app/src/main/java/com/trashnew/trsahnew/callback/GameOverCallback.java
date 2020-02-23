package com.trashnew.trsahnew.callback;

import com.trashnew.trsahnew.model.GameOverType;

/**
 * 游戏结束回调方法
 */
public interface GameOverCallback {
    void gameOver(GameOverType type);
}
