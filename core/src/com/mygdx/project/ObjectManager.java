package com.mygdx.project;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 10/31/2017.
 *
 * The GameObject class has a constructor, setFunctions, and display
 */

public class ObjectManager
{
    final private Project project;
    final private World world;

    private int roll;

    //side border so you don't fall off and die
    private CreateActor floor;
    private CreateActor ceiling;

    //enemies
    private GameObject enemy1;
    private GameObject animate1;
    private GameObject arrow;

    private ArrayMap<String, Float> region;
    private ArrayMap<String, Float> region1;
    private boolean spawn1;
    private boolean spawn2;

    //random stuff
    private GameObject watch;

    public ObjectManager(Project projectParam, World worldParam)
    {
        this.project = projectParam;
        this.world = worldParam;

        region = new ArrayMap<String, Float>();
        region.put("Armature_fly", 3.5f, 0);
        region.put("Armature_up", 3.5f, 1);

        region1 = new ArrayMap<String, Float>();
        region1.put("Armature_fly", 2.5f, 0);

        roll = 0;

        spawn1 = false;
        spawn2 = false;
    }

    public void createObjects()
    {
        floor = new CreateActor(BodyDef.BodyType.StaticBody);
        floor.setFilter(CollisionID.floorID, CollisionID.playerID);
        floor.create(world, 0, 0, 600 * Scaler.scaleX, 10 * Scaler.scaleY, false);

        ceiling = new CreateActor(BodyDef.BodyType.StaticBody);
        ceiling.setFilter(CollisionID.ceilingID, CollisionID.playerID);
        ceiling.create(world, 0, 460, 600 * Scaler.scaleX, 10 * Scaler.scaleY, false);

        watch = new GameObject("watch.atlas", project);
        watch.setResolution(32, 32);
        watch.setSpawn(650, 1400, 0, 440);
        watch.setFilter(CollisionID.scoreID, CollisionID.playerID);
        watch.setLimit(-100);
        watch.setAnimated(world, region1, 3, false);
    }

    public void createEnemies()
    {
        enemy1 = new GameObject("snail.atlas", project);
        enemy1.setResolution(102, 64);
        enemy1.setSpawn(650, 1500, 30, 70);
        enemy1.setFilter(CollisionID.enemyID, CollisionID.playerID);
        enemy1.setLimit(-30);
        enemy1.setAnimated(world, region, 3, false);

        animate1 = new GameObject("hippo.atlas", project);
        animate1.setResolution(102, 64);
        animate1.setSpawn(650, 1800, 200, 410);
        animate1.setFilter(CollisionID.enemyID, CollisionID.playerID);
        animate1.setLimit(-30);
        animate1.setAnimated(world, region, 2, false);

        arrow = new GameObject("arrow.png", project);
        arrow.setResolution(48, 14);
        arrow.setSpawn(650, 1800, 100, 410);
        arrow.setFilter(CollisionID.enemyID, CollisionID.playerID);
        arrow.setLimit(-30);
        arrow.setTexture(world, 4);
    }

    public void spawnOption()
    {
        roll = MathUtils.random(0, 100);

        if(roll >= 0 && roll <= 10)
        {
            spawn1 = true;
            spawn2 = false;
        }
        if(roll >= 11 && roll <= 30)
        {
            spawn2 = true;
            spawn1 = false;
        }
        else
        {
            spawn1 = false;
            spawn2 = false;
        }

        /***
         * Have a conditional if statement that displays a certain enemy
         * depending on the roll. It should only roll once all the enemies reach
         * the limit.
         *
         * Inside the conditional statement are speeds for the enemies that you want
         * to display. If you don't want to display, set it to zero****/
    }

    public void render()
    {
        //enemy1.displayAllTexture(-400);      //negatives dictates right to left
        //enemy1.displayTexture(-400, 2);

        enemy1.displayAllAnimation(-1500);
        animate1.displayAllAnimation(-500);
        watch.displayAllAnimation(-1800);
        arrow.displayAllTexture(-3000);
    }
}
