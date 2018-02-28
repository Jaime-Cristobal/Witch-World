package com.mygdx.project;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by seacow on 9/28/2017.
 *
 * -Filtering values needs to be in hex values and
 *  powers of 2 (0x0001, 0x0002, 0x0004, etc..)
 *
 * -Body is public because it will most likely
 *  be accessed on a tight loop (mostly on rendering)
 *  where performance is faster when directly accessed
 *  than through a getter function
 *
 * -
 */

public class CreateBody
{
    private short category;
    private short mask;

    public Body body;
    private BodyDef.BodyType type;
    private BodyDef bodydef;
    private PolygonShape shape;
    private FixtureDef fixDef;

    public CreateBody(BodyDef.BodyType btype)
    {
        type = btype;
        bodydef = new BodyDef();
        fixDef = new FixtureDef();

        category = 0;
        mask = 0;
    }

    /**Set filters for collision*/
    public void setFilter(short catHex, short maskHex)
    {
        category = catHex;
        mask = maskHex;
    }

    /**Bodydef.BodyType sets whether the body tpye is static, kinematic, or dynamic
     *
     * You still have to fix the resolution scaling for data*/
    public void create(String file, World world, float x, float y, float w, float h, boolean isSensor)
    {
        bodydef.type = type;
        bodydef.position.set(x / Scaler.PIXELS_TO_METERS,
                y / Scaler.PIXELS_TO_METERS);                  //box collision at the same dimension as the sprite
        bodydef.fixedRotation = true;

        body = world.createBody(bodydef);

        shape = new PolygonShape();
        shape.setAsBox(w / 2 / Scaler.PIXELS_TO_METERS * Scaler.scaleX,
                h / 2 / Scaler.PIXELS_TO_METERS * Scaler.scaleY);      //box collision at the same dimension as the sprite

        fixDef.shape = shape;
        fixDef.restitution = 0.1f;
        fixDef.density = 0f;
        fixDef.isSensor = isSensor;
        fixDef.filter.categoryBits = category;       //short something = CATEGORY
        fixDef.filter.maskBits = mask;

        if(file != null)
            body.createFixture(fixDef).setUserData(file);
        else    //if its a null file name
            body.createFixture(fixDef);

        body.setActive(false);      //will not move if the body is not active.

        shape.dispose();
    }

    /**For collision purposes; collision filtering*/
    public Filter getFilter() {
        return fixDef.filter;
    }
}