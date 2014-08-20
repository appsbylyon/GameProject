package com.appsbylyon.gameproject.objects;

import android.graphics.Bitmap;

/**
 * Created by infinite on 8/18/2014.
 */
public class Button
{


    private Bitmap unpressed;
    private Bitmap pressed;

    private int xPos;
    private int yPos;

    private boolean isPressed = false;


    public Button(int xPos, int yPos, Bitmap pressed, Bitmap unpressed)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.pressed = pressed;
        this.unpressed = unpressed;
    }

    public void setUnpressed(Bitmap bitmap)
    {
        unpressed = bitmap;
    }

    public void setPressed(Bitmap bitmap)
    {
        pressed = bitmap;
    }

    public Bitmap getImage()
    {
        if (isPressed())
        {
            return pressed;
        }
        else
        {
            return unpressed;
        }
    }

    public int getxPos()
    {
        return xPos;
    }

    public void setxPos(int xPos)
    {
        this.xPos = xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }

    public Bitmap getUnpressed()
    {
        return unpressed;
    }

    public Bitmap getPressed()
    {
        return pressed;
    }

    public boolean isPressed()
    {
        return isPressed;
    }

    public void setPressed(boolean isPressed)
    {
        this.isPressed = isPressed;
    }
}
