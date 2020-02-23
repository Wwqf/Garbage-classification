package com.trashnew.trsahnew.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.trashnew.trsahnew.R;
import com.trashnew.trsahnew.entity.GameBackground;
import com.trashnew.trsahnew.manager.LoadResources;
import com.trashnew.trsahnew.model.GamePlayType;
import com.trashnew.trsahnew.model.GameState;
import com.trashnew.trsahnew.util.ImageProcess;


/**
 * 总的绘制view
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "GameViewTAG";
    /*每帧刷新时间 ms*/
    private static final int TIME_IN_FRAME = 30;
    /*管理调度*/
    public static GameView gm;
    /*屏幕宽度及高度*/
    public static int mScreenWidth, mScreenHeight;

    private SurfaceHolder mSurfaceHolder;
    private GameThread mGameThread;
    private boolean mGameIsRun;
    private Canvas mCanvas;
    private Paint mPaint;

    //TODO 变量区域
    public static GameState gameState = GameState.GAME_MENU;

    private LoadResources loadResources;
    private GameBackground gameBackground;
    private MenuView menuView;
    private PlayView playView;
    private MedalView medalView;
    private HandbookView handbookView;


    public GameView(Context context) {
        super(context);
        init();
    }

    private void init() {

        gm = this;

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    private void initObject() {
        loadResources = new LoadResources();
        gameBackground = new GameBackground(mScreenWidth, mScreenHeight);
        gameBackground.setMenuBackground(ImageProcess.getImage(R.drawable.timg3));
        gameBackground.setPlayBackground(ImageProcess.getImage(R.drawable.game_bg));

        menuView = new MenuView(this);
        medalView = new MedalView();
        handbookView = new HandbookView();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mGameThread == null) {
            mGameIsRun = true;
            mGameThread = new GameThread();
            mGameThread.start();
        } else {
            mGameIsRun = true;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mGameIsRun = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth = w;
        mScreenHeight = h;

        initObject();
    }

    /*TODO 添加代码区域*/

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameState == GameState.GAME_MENU) {
                    return menuView.onTouchEvent(event);
                } else if (gameState == GameState.GAME_PLAY) {
                    return playView.onTouchEvent(event);
                } else if (gameState == GameState.GAME_MEDAL) {
                    return medalView.onTouchEvent(event);
                } else if (gameState == GameState.GAME_HANDBOOK) {
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (gameState == GameState.GAME_PLAY && playView.getPlayType() == GamePlayType.DRAG_TRASH) {
                    return playView.dragTrashOnTouchEvent_Move(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (gameState == GameState.GAME_PLAY && playView.getPlayType() == GamePlayType.DRAG_TRASH) {
                    return playView.dragTrashOnTouchEvent_Up(event);
                } else if (gameState == GameState.GAME_PLAY && playView.getPlayType() == GamePlayType.CHOICE_TRASH) {
                    playView.upOnTouchEvent(event);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setPlayView(PlayView playView) {
        this.playView = playView;
    }

    public PlayView getPlayView() {
        return playView;
    }

    public void setMedalView(MedalView medalView) {
        this.medalView = medalView;
    }

    public MedalView getMedalView() {
        return medalView;
    }

    /*---------------------------------------------------------*/

    public class GameThread extends Thread {

        public void run() {
            while (mGameIsRun) {
                long startTime = System.currentTimeMillis();
                try {
                    synchronized (mSurfaceHolder) {
                        mCanvas = mSurfaceHolder.lockCanvas();
                        draw();
                        logic();
                    }
                } finally {
                    if (mCanvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
                long endTime = System.currentTimeMillis();
                int differentTime = (int) (endTime - startTime);
                while (differentTime <= TIME_IN_FRAME) {
                    differentTime = (int) (System.currentTimeMillis() - startTime);
                    Thread.yield();
                }
            }
        }

        private void draw() {
            if (mCanvas != null) {
                mCanvas.drawColor(Color.WHITE);

                gameBackground.draw(mCanvas, gameState);
                //TODO 绘制区域
                if (gameState == GameState.GAME_MENU) {
                    menuView.draw(mCanvas);
                } else if (gameState == GameState.GAME_PLAY) {
                    playView.draw(mCanvas);
                } else if (gameState == GameState.GAME_MEDAL) {
                    medalView.draw(mCanvas);
                } else if (gameState == GameState.GAME_HANDBOOK) {
                    handbookView.draw(mCanvas);
                }
            }
        }

        /*总逻辑*/
        private void logic() {
            if (gameState == GameState.GAME_MENU) {
            } else if (gameState == GameState.GAME_PLAY) {
                playView.logic_clearTrash();
                if (playView.getPlayType() == GamePlayType.CHOICE_TRASH) {
                    playView.logic_playTypeIsChoiceTrash();
                } else if (playView.getPlayType() == GamePlayType.CLICK_TRASH) {
                } else if (playView.getPlayType() == GamePlayType.DRAG_TRASH) {
                }
            } else if (gameState == GameState.GAME_MEDAL) {
            } else if (gameState == GameState.GAME_HANDBOOK) {
            }
        }
    }
}