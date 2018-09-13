package com.mygdx.project;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 11/10/2017.
 */

public class Player
{
    final private Project project;
    final private World world;

    private ArrayMap<String, Float> region;
    private CreateActor actor;
    private TouchInput input;

    private boolean normal;

    public Player(Project projectParam, World worldParam)
    {
        this.project = projectParam;
        this.world = worldParam;

        input = new TouchInput();                   //actually moves the player

        //animation set for the player
        region = new ArrayMap<String, Float>();
        region.put("Armature_fly", 3.5f, 0);
        region.put("Armature_up", 3.5f, 1);

        actor = new CreateActor("player.atlas", project, region, BodyDef.BodyType.DynamicBody);

        normal = true;
    }

    public void create()
    {
        actor.setFilter(CollisionID.playerID, (short)(CollisionID.floorID | CollisionID.ceilingID | CollisionID.enemyID
                                                        | CollisionID.scoreID));
        actor.create(world, 50, 200, 64, 48, true);
    }

    public void render()
    {
        actor.displayAnimation();
        input.movement(actor);                     //because being able to control the player is pretty cool
        if(normal)
            actor.setAnimate("Armature_fly");
    }

    public void setNormal()
    {
        normal = true;
    }

    public void setAnimate(String key)
    {
        normal = false;
        actor.setAnimate(key);
    }

    /**Provides the input normalization with a camera to unproject with*/
    public void setInput(OrthographicCamera camera)
    {
        input.setCamera(camera);    //for input normalization using the camera to unproject the input vector
    }

    /**For providing the inputprocessor with player inputs*/
    public TouchInput getInput()
    {
        return input;
    }

    public float getX()
    {
        return actor.box2dBody.body.getPosition().x;
    }

    public float getY()
    {
        return actor.box2dBody.body.getPosition().y;
    }
}