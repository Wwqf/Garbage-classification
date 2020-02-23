package com.trashnew.trsahnew.view;

import android.bluetooth.BluetoothGatt;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.activity.MainActivity;
import com.trashnew.trsahnew.callback.GameOverCallback;
import com.trashnew.trsahnew.callback.TimeEndCallback;
import com.trashnew.trsahnew.dao.CheckPointDataDao;
import com.trashnew.trsahnew.dao.MedalDataDao;
import com.trashnew.trsahnew.dao.TrashDataDao;
import com.trashnew.trsahnew.dao.data.CheckPointData;
import com.trashnew.trsahnew.dao.data.TrashData;
import com.trashnew.trsahnew.entity.medal.ReturnButton;
import com.trashnew.trsahnew.entity.play.BlutProgress;
import com.trashnew.trsahnew.entity.play.CountDown;
import com.trashnew.trsahnew.entity.play.Dialog;
import com.trashnew.trsahnew.entity.play.Score;
import com.trashnew.trsahnew.entity.play.Star;
import com.trashnew.trsahnew.entity.play.TipText;
import com.trashnew.trsahnew.entity.play.Trash;
import com.trashnew.trsahnew.entity.play.TrashBucket;
import com.trashnew.trsahnew.model.GameOverType;
import com.trashnew.trsahnew.model.GamePlayType;
import com.trashnew.trsahnew.model.GameState;
import com.trashnew.trsahnew.model.TrashType;
import com.trashnew.trsahnew.util.DialogText;
import com.trashnew.trsahnew.util.ImageProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * choice 结束条件
 *      -- 时间结束
 *      -- 没有垃圾
 *      -- 错误次数用完
 * click 结束条件
 *      -- 时间结束
 *      -- 没有垃圾
 *      -- 错误次数用完
 * drag 结束条件
 *      -- 时间结束
 *      -- 没有垃圾
 *      -- 错误次数用完
 *
 * // 出屏的垃圾需要清除
 */

public class PlayView {

    private static final String TAG = "PlayViewTAG";

    public static GamePlayType playType = GamePlayType.CLICK_TRASH;
    public static final int singleTrashScore = 10;
    public static final int generatorTrashCount = 20;

    // 四个垃圾桶
    private ArrayList<TrashBucket> trashBuckets;
    // 分数
    private Score score;
    // 倒计时
    private CountDown countDown;
    // 星星
    private ArrayList<Star> stars;
    // 分数和倒计时的绘制
    private Paint scorePaint, countDownPaint;
    // 得分和当前得星
    private int trashScore = 0;
    private int currentStar = 0;
    // 星星的缩放比例(显示)
    private float starScale = 0.8f;

    // 垃圾
    private ArrayList<Trash> trashes;
    // 垃圾数据
    private ArrayList<TrashData> trashData;

    // 关卡数据
    private CheckPointData checkPointData;

    // 点击垃圾桶
    private boolean hasClickedTrashBucket = false; // 是否有选择的垃圾桶
    private TrashBucket currentClickTrashBucket = null; // 当前选中的垃圾桶, 没选择为null
    private TrashType currentTrashType = TrashType.NONE; // 当前点击的垃圾类型

    private boolean playTypeIsChoiceTrash_StopDraw = false; // 是否停止绘制
    private TipText tipText; // 文字对象
    private Dialog dialog; // 对话框对象
    private TrashData currentTrashData; // 当前垃圾
    private boolean choiceState = true; // 选择是否正确
    private boolean isDialog = false; // 是否弹出对话框
    private String dialogText;

    // 在choice trash玩法中, 当前第几个Id
    // 按下对话框的确定和选择正确时递增
    private int currentTrashId = 0;

    // 选择正确的垃圾个数
    private int correctTrashCount = 0;
    // 选择错误的垃圾个数
    private int mistakeTrashCount = 0;

    private Trash currentTrash = null; //当前点击的垃圾(当没有点击的垃圾时为null)
    private Point currentTrashPoint = null; // 当前点击的垃圾坐标

    private GameOverCallback gameOverCallback; // 游戏结束回调

    private BlutProgress blutProgress;  // 血量条

    private ReturnButton returnButton;  // 返回按钮(替代了血量条)

    private boolean gameOver = false;   // 游戏结束状态

    private int maxMistake = 4; // 最大的错误个数

    private int clickTrashId = 0;   // 当前点击几个垃圾

    /**
     * TODO 在这里很关键,为什么我会用一个list专门存放清除垃圾
     * 你可以搜索当前的trashes.remove(), 有三处地方移除垃圾(现在注释了)
     * 如果不用, 多个地方清除垃圾, 会导致ConcurrentModificationException异常
     * 我试着用了多线程和单线程的移除方法,但还是会报异常, 所以我专门用了一个清除垃圾list
     */
    private ArrayList<Trash> clearTrashGroup = new ArrayList<>();   // 清除垃圾

    private boolean isStart = true;

    private MenuView father;
    public PlayView(MenuView father, CheckPointData checkPointData) {
        this.father = father;
        this.checkPointData = checkPointData;

        if (checkPointData.get_type() == GamePlayType.CHOICE_TRASH) {
            Trash.rate = 4;
        }

        updatePlayType();

        initTrashBucket();
        initScore();
        initCountDown();
        initStar();
        initTrash();
        initTipText();
        initDialog();
        initBlutProgress();
        initReturnButton();

        gameOverCallback();

        MedalDataDao.getInstance(MainActivity.main).getMedalData();

        if (checkPointData.get_id() == 1) {
            playTypeIsChoiceTrash_StopDraw = true;
            dialogText = DialogText.getCheckPointTip(checkPointData.get_id());
            isDialog = true;
        } else if (checkPointData.get_id() == 4) {
            playTypeIsChoiceTrash_StopDraw = true;
            dialogText = DialogText.getCheckPointTip(checkPointData.get_id());
            isDialog = true;
        } else if (checkPointData.get_id() == 7) {
            playTypeIsChoiceTrash_StopDraw = true;
            dialogText = DialogText.getCheckPointTip(checkPointData.get_id());
            isDialog = true;
        }
    }

    /////////////////////////////////初始化/////////////////////////////////////////

    private void initTrashBucket() {
        trashBuckets = new ArrayList<>();
        trashBuckets.add(new TrashBucket(
                ImageProcess.getImage(R.drawable.gan),
                ImageProcess.scaleImage(ImageProcess.getImage(R.drawable.gan), 1.1f)));
        trashBuckets.get(trashBuckets.size() - 1).setTrashType(TrashType.DRY_GARBAGE);

        trashBuckets.add(new TrashBucket(
                ImageProcess.getImage(R.drawable.shi),
                ImageProcess.scaleImage(ImageProcess.getImage(R.drawable.shi), 1.1f)));
        trashBuckets.get(trashBuckets.size() - 1).setTrashType(TrashType.WET_GARBAGE);

        trashBuckets.add(new TrashBucket(
                ImageProcess.getImage(R.drawable.kehuishou),
                ImageProcess.scaleImage(ImageProcess.getImage(R.drawable.kehuishou), 1.1f)));
        trashBuckets.get(trashBuckets.size() - 1).setTrashType(TrashType.RECYCLABLE_WASTE);

        trashBuckets.add(new TrashBucket(
                ImageProcess.getImage(R.drawable.youhai),
                ImageProcess.scaleImage(ImageProcess.getImage(R.drawable.youhai), 1.1f)));
        trashBuckets.get(trashBuckets.size() - 1).setTrashType(TrashType.HARMFUL_WASTE);


        int dis = 50;
        int totalW = trashBuckets.get(0).getWidth() * 4 + dis * 3;
        int startX = (ImageProcess.getScreenWidthPixels() - totalW) / 2;
        int startY = ImageProcess.getScreenHeightPixels() - trashBuckets.get(0).getHeight();

        for (int l = 0; l < trashBuckets.size(); l++) {
            trashBuckets.get(l).leftTop(
                    startX, startY
            );
            startX = startX + trashBuckets.get(l).getWidth() + dis;
        }
    }

    private void initScore() {
        int startX = (int) (ImageProcess.getScreenWidthPixels() * 0.29);
        int startY = (int) (ImageProcess.getScreenHeightPixels() * 0.07);

        score = new Score(new Point(startX, startY));

        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(0xFFfee002);
        scorePaint.setTextSize(35);
        scorePaint.setStrokeWidth(5);
    }

    private void initCountDown() {
        int startX = (int) (ImageProcess.getScreenWidthPixels() * 0.48);
        int startY = (int) (ImageProcess.getScreenHeightPixels() * 0.1);

        countDown = new CountDown(new Point(startX, startY));

        countDownPaint = new Paint();
        countDownPaint.setAntiAlias(true);
        countDownPaint.setColor(0xFFdedde3);
        countDownPaint.setTextSize(50);
        countDownPaint.setStrokeWidth(15);

        timeEndCallback();
        countDown.reset(60);
        countDown.updateTime().start();
    }

    private void initStar() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.595),
                (int) (ImageProcess.getScreenHeightPixels() * 0.058)
        ));
        points.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.642),
                (int) (ImageProcess.getScreenHeightPixels() * 0.056)
        ));
        points.add(new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.688),
                (int) (ImageProcess.getScreenHeightPixels() * 0.058)
        ));
        stars = new ArrayList<>();
        for (Point item : points) {
            stars.add(new Star(ImageProcess.scaleImage(ImageProcess.decodeResource(R.drawable.star), starScale), item));
        }
    }

    private void initTrash() {
        trashData = TrashDataDao.getInstance(MainActivity.main).getTrashData();
        trashes = new ArrayList<>();
        addTrash(trashes, generatorTrashCount);
    }

    private void addTrash(ArrayList<Trash> trashes, int count) {
        for (int l = 0; l < count; l++)
            trashes.add(generatorTrash());
    }

    private Trash generatorTrash() {
        int index = new Random().nextInt(trashData.size());
        Trash trash = new Trash(trashData.get(index));
        int sx = 0, sy = (int) (ImageProcess.getScreenHeightPixels() * 0.675);

        if (trashes.size() > 0) {
            sx = (int) (trashes.get(trashes.size() - 1).left() - trash.getWidth() - 10);
        }

        trash.set(sx, sy);
        return trash;
    }

    private void initTipText() {
        tipText = new TipText();
    }

    private void initDialog() {
        dialog = new Dialog(ImageProcess.getImage(R.drawable.dialog));
    }

    private void initBlutProgress() {
        blutProgress = new BlutProgress(
//          new Point((int) (ImageProcess.getScreenWidthPixels() * 0.15),
//                  (int) (ImageProcess.getScreenHeightPixels() * 0.052)),
//                (int) (ImageProcess.getScreenWidthPixels() * 0.1),
//                (int) (ImageProcess.getScreenHeightPixels() * 0.05)

                new Point(-100, -100), 10, 2
        );
    }

    private void initReturnButton() {
        returnButton = new ReturnButton(ImageProcess.getImage(R.drawable.return_button));
        returnButton.set(returnButton.getWidth() / 2 + 10, returnButton.getHeight() / 2 + 10);
    }
    /////////////////////////////////方法/////////////////////////////////////////

    // 计时结束回调
    private void timeEndCallback() {
        // TODO 计时器结束
        countDown.setTimeEndCallback(new TimeEndCallback() {
            @Override
            public void timeEnd() {
                gameOverCallback.gameOver(GameOverType.TIME_END);
            }
        });
    }

    // 游戏结束回调
    private void gameOverCallback() {
        gameOverCallback = new GameOverCallback() {
            @Override
            public void gameOver(GameOverType type) {

                Log.e(TAG, "call game over.");
                gameOver = true;
                if (type == GameOverType.NO_BLUT) {
                    dialogText = "错误次数已用完";
                    isDialog = true;
                } else if (type == GameOverType.TIME_END) {
                    dialogText = "时间结束";
                    isDialog = true;
                } else if (type == GameOverType.NO_TRASH) {
                    dialogText = "恭喜你, 你已清理完所有垃圾";
                    isDialog = true;
                } else if (type == GameOverType.RETURN_BUTTON) {
                    GameView.gameState = GameState.GAME_MENU;
                }

            }
        };
    }

    // 不同关卡不同的玩法
    private void updatePlayType() {
        playType = checkPointData.get_type();
    }

    // 判断等级 得星
    private void judgeLevel_setStar() {
        if (correctTrashCount >= generatorTrashCount * 0.3) currentStar = 1;
        if (correctTrashCount >= generatorTrashCount * 0.7) currentStar = 2;
        if (correctTrashCount == generatorTrashCount) currentStar = 3;
    }

    // 获取游戏状态
    public GamePlayType getPlayType() {
        return playType;
    }

    // 清除垃圾桶的状态
    private void clearTrashBucketState() {
        for (TrashBucket item : trashBuckets) {
            item.setState(false);
        }
    }

    // 跳转到菜单栏
    private void startViewToMenu() {
        isDialog = true;
        playTypeIsChoiceTrash_StopDraw = true;

        // 设置当前关卡的分数和星星
        checkPointData.set_score(trashScore);
        checkPointData.set_star(currentStar);

        MedalDataDao.getInstance(MainActivity.main).update();

        CheckPointDataDao.getInstance(MainActivity.main).update(checkPointData.get_id(), checkPointData.get_star(), checkPointData.get_score(), checkPointData.is_isPlayed());

        // 如果当前的得星大于等于2颗星, 则可以进入下一关
        if (currentStar >= 2) {
            father.openNextCheckPoint();
        }

        GameView.gameState = GameState.GAME_MENU;
    }

    // 垃圾投放是否正确
    private void garbageIsPlacedCorrectly(boolean correctState) {
        clickTrashId++;
        if (clickTrashId == generatorTrashCount) gameOverCallback.gameOver(GameOverType.NO_TRASH);

        if (correctState) {
            trashScore += singleTrashScore; // 计分
            correctTrashCount++; // 正确+1
            judgeLevel_setStar(); // 判断得星
            MedalDataDao.getInstance(MainActivity.main).addCount(currentTrash.getTrashData().getResId());
        } else {
            mistakeTrashCount++; // 错误+1
            Log.e(TAG, "call one");
            // 弹出对话框
            dialogText = DialogText.getMistakeDialogText(maxMistake - mistakeTrashCount);
            isDialog = true;
            // 根据错误设置血量
            if (mistakeTrashCount == 1) blutProgress.currentVal = 70;
            else if (mistakeTrashCount == 2) blutProgress.currentVal = 40;
            else if (mistakeTrashCount == 3) blutProgress.currentVal = 10;
            else if (mistakeTrashCount == 4) gameOverCallback.gameOver(GameOverType.NO_BLUT);
        }
    }

    /////////////////////////////////事件/////////////////////////////////////////
    public boolean onTouchEvent(MotionEvent event) {
        if (returnButton.onTouchEvent(event)) gameOverCallback.gameOver(GameOverType.RETURN_BUTTON);

        if (isDialog && dialog.isClickOkButton(event)) {
            isDialog = false;

            if (isStart) {
                playTypeIsChoiceTrash_StopDraw = false;
                isStart = false;
            }
            return true;
        }

        if (gameOver && dialog.onTouchEvent(event)) {
            startViewToMenu();
        }

        if (getPlayType() == GamePlayType.CHOICE_TRASH) {
            return choiceTrashOnTouchEvent(event);
        } else if (getPlayType() == GamePlayType.CLICK_TRASH) {
            return clickTrashOnTouchEvent(event);
        } else if (getPlayType() == GamePlayType.DRAG_TRASH) {
            return dragTrashOnTouchEvent_Down(event);
        }
        return false;
    }

    private boolean choiceTrashOnTouchEvent(MotionEvent event) {
        if (isDialog) {
            if (dialog.isClickOkButton(event)) {

                clearTrashBucketState();
                playTypeIsChoiceTrash_StopDraw = false;
                isDialog = false;

                currentClickTrashBucket = null;
                currentTrash = null;
                currentTrashData = null;
            }
            return true;
        }

        if (clickTrashBucket(event)) {

            if (currentClickTrashBucket == null) {
                Log.e(TAG, "当前没有选择垃圾桶");
                return false;
            }

            if (currentTrashData == null) {
                currentClickTrashBucket.setState(false);
                Log.e(TAG, "data is null");
                return false;
            }

            if (currentClickTrashBucket.getTrashType() == currentTrashData.getTrashType()) {
                choiceState = true;
                playTypeIsChoiceTrash_StopDraw = false;
                isDialog = false;
                Log.e(TAG, "call ?");
                clearTrashBucketState();
                garbageIsPlacedCorrectly(true);

                clearTrashGroup.add(currentTrash);
//                trashes.remove(currentTrash);
            } else {
                choiceState = false;
                playTypeIsChoiceTrash_StopDraw = true;
                isDialog = true;
                currentTrashId++;// 如果没有答对, 则下一个
                garbageIsPlacedCorrectly(false);
            }

            return true;
        }
        return false;
    }

    public boolean upOnTouchEvent(MotionEvent event) {
//        currentClickTrashBucket = null;
//        currentTrash = null;
//        currentTrashData = null;
        return true;
    }

    private boolean clickTrashOnTouchEvent(MotionEvent event) {
        // 需要选择一个垃圾桶, 然后点击垃圾, 如果trashType匹配, 则从垃圾列表清除

        if (clickTrashBucket(event)) return true;

        if (currentClickTrashBucket != null) {
            Trash removeTrash = null;

            for (Trash item : trashes) {
                // 如果当前垃圾被点击
                if (item.onTouchEvent(event)) {
                    currentTrash = item;
                    // 如果类型匹配
                    if (item.getType() == currentTrashType) {
                        garbageIsPlacedCorrectly(true);
                        removeTrash = item;
                    } else {
                        garbageIsPlacedCorrectly(false);
                    }
                }
            }


            if (removeTrash != null) {
                clearTrashGroup.add(currentTrash);
//                trashes.remove(removeTrash);
                return true;
            }
        }
        return false;
    }

    public boolean dragTrashOnTouchEvent_Down(MotionEvent event) {

        // 获取触摸的点, 判断点击到哪个垃圾, 然后调用move
        for (Trash item : trashes) {
            if (item.onTouchEvent(event)) {
                currentTrash = item;
                currentTrashPoint = new Point(currentTrash.getPoint());
                return true;
            }
        }
        currentTrash = null;
        return false;
    }

    public boolean dragTrashOnTouchEvent_Move(MotionEvent event) {

        // 在移动的过程中, 如果碰到垃圾桶, 垃圾桶changeState, 如果在这时eventUp,
        // 则代表当前垃圾投入到当前垃圾桶, 匹配type是否一致, 如果一致, 则加分, 如果错误则记录

        if (currentTrash != null) {
            currentTrash.set((int) event.getX(), (int) event.getY());
            for (TrashBucket item : trashBuckets) {
                if (item.collisions(currentTrash)) {
                    currentClickTrashBucket = item;
                }
            }


            if (currentClickTrashBucket != null) {
                if (!currentClickTrashBucket.collisions(currentTrash)) {
                    currentClickTrashBucket.setState(false);
                    currentClickTrashBucket = null;
                } else {
                    currentClickTrashBucket.setState(true);
                    for (TrashBucket item : trashBuckets) {
                        if (item != currentClickTrashBucket) {
                            item.setState(false);
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean dragTrashOnTouchEvent_Up(MotionEvent event) {
        if (currentTrash != null) {
            currentTrash.set(currentTrashPoint);

            if (currentClickTrashBucket != null) {
                if (currentTrash.getType() == currentClickTrashBucket.getTrashType()) {
                    garbageIsPlacedCorrectly(true);
                    clearTrashGroup.add(currentTrash);
//                    trashes.remove(currentTrash);
                } else {
                    garbageIsPlacedCorrectly(false);
                }
            }
            // 如果type一致

            currentTrash = null;
            currentTrashPoint = null;

            clearTrashBucketState();
        }


        return true;
    }

    // 垃圾桶的点击事件, 包含更改状态
    public boolean clickTrashBucket(MotionEvent event) {
        // 保证只会选中一个, 也保证点击空处不会使得效果消失
        boolean isClick = false;
        for (TrashBucket item : trashBuckets) {
            if (item.onTouchEvent(event)) {
                hasClickedTrashBucket = true;
                isClick = true;

                currentClickTrashBucket = item;
                currentTrashType = currentClickTrashBucket.getTrashType();
            }
        }

        if (hasClickedTrashBucket) {
            currentClickTrashBucket.changeStated();
            if (!currentClickTrashBucket.getState()) {
                currentClickTrashBucket = null;
                currentTrashType = TrashType.NONE;
            }

            for (TrashBucket item : trashBuckets) {
                if (currentClickTrashBucket != item) {
                    item.setState(false);
                }
            }

            hasClickedTrashBucket = false;
        }

        return isClick;
    }
    /////////////////////////////////绘制/////////////////////////////////////////

    public void draw(Canvas canvas) {
        // 绘制四个垃圾桶
        for (TrashBucket item : trashBuckets) {
            item.draw(canvas);
        }

        score.draw(canvas, scorePaint, trashScore);
        countDown.draw(canvas, countDownPaint);

        for (int l = 0; l < currentStar; l++) {
            stars.get(l).draw(canvas);
        }

        blutProgress.draw(canvas);

        returnButton.draw(canvas);

        if (getPlayType() == GamePlayType.CHOICE_TRASH) {
            drawChoiceTrash(canvas);
        } else if (getPlayType() == GamePlayType.CLICK_TRASH) {
            drawClickTrash(canvas);
        } else if (getPlayType() == GamePlayType.DRAG_TRASH) {
            drawDragTrash(canvas);
        }

        if (isDialog) {
            dialog.draw(canvas, dialogText);
        }
    }

    private void drawChoiceTrash(Canvas canvas) {

        for (Trash item : trashes) {
            if (!playTypeIsChoiceTrash_StopDraw) {
                item.setRate(Trash.rate);
            } else {
                if (currentTrashData != null)
                    tipText.draw(canvas, currentTrashData);
            }
            item.draw(canvas);

        }


        if (currentTrashId < 0) {
            Log.e(TAG, "currentTrashId less than zero.");
            currentTrashId = 0;
        }

        if (trashes.size() == 0) {
            Log.e(TAG, "trashes size is zero.");
            gameOverCallback.gameOver(GameOverType.NO_TRASH);
        }

    }

    private void drawClickTrash(Canvas canvas) {
        for (Trash item : trashes) {
            item.setRate(Trash.rate);
            item.draw(canvas);
        }
    }

    private void drawDragTrash(Canvas canvas) {
        for (Trash item : trashes) {
            if (item == currentTrash) {
                currentTrashPoint.offset(Trash.rate, 0);
            } else {
                item.setRate(Trash.rate);
            }
            item.draw(canvas);
        }
    }

    /////////////////////////////////逻辑/////////////////////////////////////////

    public void logic_playTypeIsChoiceTrash() {
        // 当垃圾位于箭头下方时, 垃圾停止绘制, 显示一句话, "这个是xx垃圾, 请问是什么类型"
        // 如果选择错误, 则弹窗, 如果正确, 则继续绘制
        Point position = new Point(
                (int) (ImageProcess.getScreenWidthPixels() * 0.5), 0
        );

        if (currentTrashId >= generatorTrashCount) return;

        // 防止越界
        if (currentTrashId >= trashes.size()) currentTrashId--;
        if (currentTrashId <= 0) currentTrashId = 0;

        if (trashes.get(currentTrashId).right() > position.x) {
            currentTrash = trashes.get(currentTrashId);
            currentTrashData = trashes.get(currentTrashId).getTrashData();
            playTypeIsChoiceTrash_StopDraw = true;
        }
    }

    public void logic_clearTrash() {

        if (clearTrashGroup.size() != 0) {
            trashes.removeAll(clearTrashGroup);
            clearTrashGroup.clear();
        }
    }

}
