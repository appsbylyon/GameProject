package com.appsbylyon.gameproject.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by infinite on 8/18/2014.
 */
public class Controls
{
    public interface OnControlsPressedListener
    {
        public void onControlsPressed(ArrayList<Integer> buttonList);
    }
    private static final String TAG = "Controls Class";

    private OnControlsPressedListener host;

    private Paint paint;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    public Controls(View host)
    {
        paint = new Paint();
        paint.setAlpha(255);
        paint.setStrokeWidth(1);
        this.host = (OnControlsPressedListener) host;
    }

    public int addButton(int xPos, int yPos, int width, int height, Bitmap pressed, Bitmap unpressed)
    {
        int index = buttons.size();
        Bitmap scaledPressed = Bitmap.createScaledBitmap(pressed, width, height, false);
        Bitmap scaledUnpressed = Bitmap.createScaledBitmap(unpressed, width, height, false);
        buttons.add(new Button(xPos, yPos, scaledPressed, scaledUnpressed));
        return index;
    }

    public ArrayList<Integer> getButtonTouched(MotionEvent event)
    {

        ArrayList<Integer> touchedButtons = new ArrayList<Integer>();
        for (int i = 0; i < buttons.size(); i++)
        {
            Button button = buttons.get(i);
            for (int a = 0; a < event.getPointerCount(); a++)
            {
                int touchX = (int) event.getX(a);
                int touchY = (int) event.getY(a);
                if (touchX > button.getxPos() && touchX < (button.getxPos() + button.getImage().getWidth()) &&
                        (touchY > button.getyPos()) && (touchY < (button.getyPos() + button.getImage().getHeight())))
                {
                    int xOffSet = touchX - button.getxPos();
                    int yOffSet = touchY - button.getyPos();
                    if (button.getImage().getPixel(xOffSet, yOffSet) != Color.TRANSPARENT)
                    {
                        if (event.getAction() == MotionEvent.ACTION_UP)
                        {
                            button.setPressed(false);
                            break;
                        }
                        else
                        {
                            button.setPressed(true);
                            //buttons.set(i, button);
                            touchedButtons.add(i);
                            break;
                        }
                    }
                    else
                    {
                        button.setPressed(false);
                        buttons.set(i, button);
                    }
                }
                else
                {
                    button.setPressed(false);
                    buttons.set(i, button);
                }

            }

        }
        return touchedButtons;
    }

    public void backgroundGetButtonsTouched(final MotionEvent event)
    {

        Runnable getButtonsTouched = new Runnable()
        {
            @Override
            public void run()
            {
                ArrayList<Integer> touchedButtons = new ArrayList<Integer>();
                for (int i = 0; i < buttons.size(); i++)
                {
                    Button button = buttons.get(i);
                    for (int a = 0; a < event.getPointerCount(); a++)
                    {
                        int touchX = (int) event.getX(a);
                        int touchY = (int) event.getY(a);
                        if (touchX > button.getxPos() && touchX < (button.getxPos() + button.getImage().getWidth()) &&
                                (touchY > button.getyPos()) && (touchY < (button.getyPos() + button.getImage().getHeight())))
                        {
                            int xOffSet = touchX - button.getxPos();
                            int yOffSet = touchY - button.getyPos();
                            if (button.getImage().getPixel(xOffSet, yOffSet) != Color.TRANSPARENT)
                            {
                                if (event.getAction() == MotionEvent.ACTION_UP)
                                {
                                    button.setPressed(false);
                                    break;
                                }
                                else
                                {
                                    button.setPressed(true);
                                    //buttons.set(i, button);
                                    touchedButtons.add(i);
                                    break;
                                }
                            }
                            else
                            {
                                button.setPressed(false);
                                buttons.set(i, button);
                            }
                        }
                        else
                        {
                            button.setPressed(false);
                            buttons.set(i, button);
                        }

                    }

                }
                host.onControlsPressed(touchedButtons);
            }
        };
        getButtonsTouched.run();

    }

    public void drawControls(Canvas canvas)
    {
        for (Button button: buttons)
        {
            canvas.drawBitmap(button.getImage(), button.getxPos(), button.getyPos(), paint);
        }
    }


}
