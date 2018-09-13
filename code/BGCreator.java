package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by seacow on 10/24/2017.
 */

public class BGCreator
{
    private Project project;

    private String file;
    private float speed;
    private float width;
    private float height;

    private float x;
    private float y;
    private float xMin;
    private float yMin;
    private float xMax;
    private float yMax;

    private boolean horizontal;
    private boolean isPositive;
    public boolean reseted;
    private int limit;


    public BGCreator(String file_name, Project projectParam, boolean horiz)
    {
        this.project = projectParam;
        file = file_name;

        speed = 0;
        width = 0;
        height = 0;

        xMin = Gdx.graphics.getWidth();
        xMax = 1100;
        yMin = 0;
        yMax = Gdx.graphics.getHeight();

        horizontal = horiz;
        reseted = false;
        isPositive = false;

        limit = 0;
    }


    /**Call this before generating the sprites or nothing will take effect*/
    public void setSpawnArea(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;

        x = MathUtils.random(xmin, xmax) + Scaler.scaleX;
        y = MathUtils.random(ymin, ymax) + Scaler.scaleY;
    }

    public void setResolution(float w, float h)
    {
        width = w;
        height = h;
    }

    public void setData(int limitParam, float s)
    {
        limit = limitParam;

        if(limit < 0)
            isPositive = false;
        else
            isPositive = true;

        speed = s;
    }

    /**display() must be called between the begin() and end() in the class where
     * your current screen is on*/

    /**Call this for non-moving/static sprites*/
    public void display()
    {
        reseted = false;
        project.batch.draw((Texture) project.assetmanager.manager.get(file), x, y, width + Scaler.scaleX, height);
    }

    /**Call this for moving/dynamic sprites
     * horizontal just means you want it to move across.
     * set false if you it to move vertically*/
    public void displayMove()
    {
        //The background objects will respawn as x or y reaches the limit
        if((x <= limit && !isPositive) || (x >= limit && isPositive) && horizontal)
        {
            resetPos();
        }
        else if((y >= limit && isPositive) || (y <= limit && !isPositive) && !horizontal)
        {
            resetPos();
        }
        else
            reseted = false;

        project.batch.draw((Texture) project.assetmanager.manager.get(file), x, y, width, height);

        if(horizontal)
            x -= Gdx.graphics.getDeltaTime() * speed;   //subtraction dictates its moving to the left
        else
            y += Gdx.graphics.getDeltaTime() * speed;   //subtraction dictates its moving to the down
    }

    public void resetPos()
    {
        reseted = true;
        x = MathUtils.random(xMin, xMax) + Scaler.scaleX;
        y = MathUtils.random(yMin, yMax) + Scaler.scaleY;
    }

    public void setPosX(float xVal)
    {
        x = xVal;
    }

    public void setPosY(float yVal)
    {
        y = yVal;
    }

    public float getPosY()
    {
        return y;
    }

    public float getPosX()
    {
        return x;
    }

    public float getLimit()
    {
        return limit;
    }
}