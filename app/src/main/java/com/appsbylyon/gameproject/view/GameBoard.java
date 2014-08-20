package com.appsbylyon.gameproject.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.appsbylyon.gameproject.objects.Controls;
import com.appsbylyon.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by infinite on 8/18/2014.
 */
public class GameBoard extends View implements Controls.OnControlsPressedListener
{
    private static final String TAG = "Game Project Gameboard";

    private static final double BUTTON_SCREEN_WIDTH_RATIO = .15;

    private static final int CONTROL_JUMP = 0;

    private Context context;

    private Controls controls = new Controls(this);

    private Paint paint;

    private boolean isPaused = false;

    private HashMap<Integer, Integer> controlMap = new HashMap<Integer, Integer>();

    private ArrayList<Integer> pressedButtons = new ArrayList<Integer>(); //TEMP DELETE THIS

    public GameBoard(Context context)
    {
        super(context);
        this.context = context;
        paint = new Paint();
        paint.setAlpha(255);
        paint.setStrokeWidth(1);
    }

    public void setupBoard()
    {
        int buttonDiameter = (int) ((double) getWidth() * BUTTON_SCREEN_WIDTH_RATIO);
        for (int i = 0; i < 5; i++)
        {
            controlMap.put(
                    controls.addButton((5*(i+1))+(i*buttonDiameter), getHeight() - 5 - buttonDiameter, buttonDiameter, buttonDiameter,
                            BitmapFactory.decodeResource(context.getResources(), R.drawable.a_pressed),
                            BitmapFactory.decodeResource(context.getResources(), R.drawable.a_unpressed)),
                    CONTROL_JUMP
            );
        }
    }

    public void screenTouched(MotionEvent event)
    {
        controls.backgroundGetButtonsTouched(event);

    }

    @Override
    public void onControlsPressed(ArrayList<Integer> buttonList)
    {
        pressedButtons = buttonList;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if (!isPaused)
        {
            controls.drawControls(canvas);
            paint.setTextSize(30);
            for (Integer i : pressedButtons)
            {
                canvas.drawText("Button "+i+" is presssed", 25, (20+(30*i)), paint);
            }
        }
    }

    public void pauseGame()
    {
        isPaused = true;
    }

    public void resumeGame()
    {
        isPaused = false;
    }
}
