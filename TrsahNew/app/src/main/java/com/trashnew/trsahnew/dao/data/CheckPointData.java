package com.trashnew.trsahnew.dao.data;

import android.graphics.Point;

import com.trashnew.trsahnew.model.GamePlayType;

public class CheckPointData {

    private int _id; // 第几关
    private int _star; // 几颗星
    private int _score; // 关卡的分数
    private boolean _isPlayed; // 是否玩过
    private GamePlayType _type;

    public CheckPointData() { }

    public CheckPointData(int id, int star, int score, boolean isPlayed, GamePlayType type) {
        _id = id;
        _star = star;
        _score = score;
        _isPlayed = isPlayed;
        _type = type;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_star() {
        return _star;
    }

    public void set_star(int _star) {
        this._star = _star;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public boolean is_isPlayed() {
        return _isPlayed;
    }

    public void set_isPlayed(boolean _isPlayed) {
        this._isPlayed = _isPlayed;
    }

    public GamePlayType get_type() {
        return _type;
    }

    public void set_type(GamePlayType _type) {
        this._type = _type;
    }
}
