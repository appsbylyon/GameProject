package com.appsbylyon.gameproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.appsbylyon.gameproject.view.GameBoard;

/**
 * Created by infinite on 8/18/2014.
 */
public class GameActivity extends Activity
{
    private static final String TAG = "Game Project Activity";

    private static final int NAVIGATION_BAR_TOUCH_TIMEOUT = 500;
    private static final int FRAME_RATE = 20;

    private GameBoard gameBoard;

    private GestureDetector gestureDetector;

    private View decorView;

    private boolean nativationBarVisible = false;

    private long navigationBarShownTime;

    private Handler frame = new Handler();

    private boolean isInitialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameBoard = new GameBoard(this);
        setContentView(gameBoard);

        ViewTreeObserver vto = gameBoard.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                if (!isInitialized)
                {
                    isInitialized = true;
                    initialize();
                }
            }
        });

       //gestureDetector = new GestureDetector(getApplicationContext(), gestureListener);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        decorView = getWindow().getDecorView();
        hideNavigationBar();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
        {
            @Override
            public void onSystemUiVisibilityChange(int visibility)
            {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                {
                    nativationBarVisible = true;
                    navigationBarShownTime = System.currentTimeMillis();
                    gameBoard.pauseGame();
                } else
                {
                    nativationBarVisible = false;
                }

            }
        });
    }

    private void initialize()
    {
        gameBoard.setupBoard();
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        if (nativationBarVisible)
        {
            if ((System.currentTimeMillis() - navigationBarShownTime) >= NAVIGATION_BAR_TOUCH_TIMEOUT)
            {
                hideNavigationBar();
                gameBoard.resumeGame();
            }
        }
        else
        {
            gameBoard.screenTouched(motionEvent);
        }
        return true;
    }

    private void hideNavigationBar()
    {
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private Runnable frameUpdate = new Runnable()
    {
        synchronized public void run()
        {
            gameBoard.invalidate();
            //Log.i(TAG, "Updating Gameboard");
            frame.postDelayed(frameUpdate, FRAME_RATE);
        }
    };// End of frameUpdate Class


}
