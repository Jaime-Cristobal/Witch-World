package com.mygdx.project;

import com.badlogic.gdx.utils.Array;

/**
 * Created by seacow on 9/29/2017.
 *
 * This is different from the CreateActor class since it shouldn't
 * need additional resource to render with no collision statement
 * blocks and CPU resources for its physics
 *
 *
 */

public class BGManager
{
    private Array<BGCreator> backgrounds;

    public BGManager(String file, Project project, int amount, boolean horizontal)
    {
        backgrounds = new Array<BGCreator>();

        for(int n = 0; n < amount; n++)
            backgrounds.add(new com.mygdx.project.BGCreator(file, project, horizontal));
    }

    public void setSpawnArea(float x_min, float x_max, float y_min, float y_max)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).setSpawnArea(x_min, x_max, y_min, y_max);
    }

    /**This is a necessity if you want the background to display. Default is are 0 x 0*/
    public void setResolution(float width, float height)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).setResolution(width, height);
    }

    /**This is for the limit and speed for a moving sprite*/
    public void setData(int limit, float speed)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).setData(limit, speed);
    }

    public void display()
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).display();
    }

    public void displayMove()
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).displayMove();
    }

    public void resetPos()
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).resetPos();
    }

    public boolean getReseted()
    {
        for(int n = 0; n < backgrounds.size; n++)
            return backgrounds.get(n).reseted;

        return false;
    }

    public int size()
    {
        return backgrounds.size;
    }

    /**This is mainly created for the sun*/
    public float getY()
    {
        return backgrounds.get(0).getPosY();
    }

    public float getX()
    {
        return backgrounds.get(0).getPosX();
    }

    public void setX(float x)
    {
        backgrounds.get(0).setPosX(x);
    }

    public void setY(float y)
    {
        backgrounds.get(0).setPosY(y);
    }
}
