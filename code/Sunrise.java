package com.mygdx.project;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by seacow on 11/19/2017.
 */

public class Sunrise
{
    final private Project project;
    final private BGManager sun;
    private boolean sunMech;

    public Sunrise(Project projectParam)
    {
        this.project = projectParam;

        sun = new BGManager("sun.png", project, 1, false);
        sun.setSpawnArea(150, 150, 170, 170);
        sun.setResolution(258, 240);

        sunMech = false;
    }

    public void render()
    {
        if(sun.getY() >= 435)
            sunMech = false;

        if(sunMech)
            sun.setData(500, 5);
        else
            sun.setData(500, 0);

        moveSun();
        sun.displayMove();
    }

    private void moveSun()
    {
        if(ScorePOD.score <= 0)
            sunMech = true;
        else
            sunMech = false;
    }

    public boolean showRedTint()
    {
        if(sun.getY() > 300)
            return true;

        return false;
    }

    public void reset()
    {
        sunMech = false;

        if(sun.getY() >= 435)
            sun.resetPos();
    }

    public boolean lost()
    {
        if(sun.getY() >= 435)
            return true;

        return false;
    }
}