package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 10/28/2017.
 *
 * For various objects like enemies and power ups.
 */

public class GameObject
{
    final private Project main;
    private Array<CreateActor> actors;

    private String file;
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float width;
    private float height;
    private float angle;
    private float limit;

    private short objectHex;
    private short playerHex;

    private boolean reachLimit;     //true only if every individual object has respawned

    public GameObject(String fileName, Project projectParam)
    {
        this.main = projectParam;
        file = fileName;
        actors = new Array<CreateActor>();
        limit = -50;

        objectHex = 0x0000;
        playerHex = 0x0000;

        reachLimit = false;
    }

    /**Please use these to randomize by using the LibGdx MathUtensil.random*/
    public void setSpawn(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;
    }

    /**Because having it automatically take the images' original width and height
     * is too much hassle*/
    public void setResolution(float w, float h)
    {
        width = w;
        height = h;
    }

    public void setFilter(short hex1, short hex2)
    {
        objectHex = hex1;
        playerHex = hex2;
    }

    /**Because we like controlling when our objects respawn*/
    public void setLimit(float value)
    {
        limit = value;
    }

    /***
     * DO NOT CALL setTexture or setAnimated without calling setSpawn, setFilter, AND setResolution
     * beforehand. If you do, you are a bad mannnnnnnnn.*/
    public void setTexture(World world, int amount)
    {
        for(int n = 0; n < amount; n++)
        {
            actors.add(new CreateActor(file, main, BodyDef.BodyType.DynamicBody));  //This creates ensures there's an actor
            actors.get(n).box2dBody.setFilter(objectHex, playerHex);
            actors.get(n).create(world, MathUtils.random(xMin, xMax),
                    MathUtils.random(yMin, yMax), width, height, true);
        }

        angle = actors.get(0).box2dBody.body.getAngle();    //use this when were respawning
    }

    public void setAnimated(World world, ArrayMap<String, Float> region, int amount, boolean flip)
    {
        for(int n = 0; n < amount; n++)
        {
            actors.add(new CreateActor(file, main, region, BodyDef.BodyType.DynamicBody));
            actors.get(n).box2dBody.setFilter(objectHex, playerHex);
            actors.get(n).create(world, MathUtils.random(xMin, xMax),
                    MathUtils.random(yMin, yMax), width, height, true);

            if(flip)
                actors.get(n).flipAnimation();
        }

        angle = actors.get(0).box2dBody.body.getAngle();    //use this when were respawning
    }

    public void displayAllTexture(int speed)
    {
        for(int n = 0; n < actors.size; n++)
        {
            actors.get(n).displayTexture();
            movement(n, speed);
        }
    }

    public void displayTexture(int speed, int amount)
    {
        for(int n = 0; n < amount; n++)
        {
            actors.get(n).displayTexture();
            movement(n, speed);
        }
    }

    public void displayAllAnimation(int speed)
    {
        for(int n = 0; n < actors.size; n++)
        {
            actors.get(n).displayAnimation();
            movement(n, speed);
        }
    }

    public void displayAnimation(int speed, int amount)
    {
        for(int n = 0; n < amount; n++)
        {
            actors.get(n).displayAnimation();
            movement(n, speed);
        }
    }

    private void movement(int index, int speed)
    {
        //These 2 lines just moves the actor across the map
        actors.get(index).box2dBody.body.setActive(true);   //body might not be active for moving
        actors.get(index).checker = false;
        actors.get(index).box2dBody.body.setLinearVelocity(speed * Gdx.graphics.getDeltaTime(), 0);

        //If it reaches at -70 METERS (NOT PIXELS), it will respawn
        if(actors.get(index).box2dBody.body.getPosition().x < limit)
            actors.get(index).box2dBody.body.setTransform(MathUtils.random(xMin / Scaler.PIXELS_TO_METERS,
                    xMax / Scaler.PIXELS_TO_METERS), MathUtils.random(yMin / Scaler.PIXELS_TO_METERS,
                    yMax / Scaler.PIXELS_TO_METERS), angle);
    }

    /**Stops the individual object once it respawns*/
    private void stopAtSpawn()
    {
        for(int n = 0; n < actors.size; n++)
        {
            if (actors.get(n).box2dBody.body.getPosition().x < limit)
            {
                actors.get(n).box2dBody.body.setActive(false);
                actors.get(n).checker = true;
            }
        }
    }

    private boolean allRespawned()
    {
        for(int n = 0; n < actors.size; n++)
            if(actors.get(n).checker)
                reachLimit = true;
            else
                reachLimit = false;

        return reachLimit;
    }
}