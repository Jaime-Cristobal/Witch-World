package com.mygdx.project;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by seacow on 9/29/2017.
 * To use this class, you should create it and then register it
 * as a ContactFilter in World. It's like world.setContactFilter
 * or something like that.
 */

public class FilterContact implements ContactFilter
{

    private boolean collide;
    private Filter filter1;
    private Filter filter2;

    public FilterContact()
    {
        filter1 = new Filter();
        filter2 = new Filter();
    }

    /**Happens automatically, no need to explicitly call*/
    public boolean shouldCollide (Fixture fixtureA, Fixture fixtureB)
    {
        collide = false;

        final Filter filterA = fixtureA.getFilterData();
        final Filter filterB = fixtureB.getFilterData();

        filter1 = filterA;
        filter2 = filterB;

        if (filterA.categoryBits == filterB.categoryBits && filterA.categoryBits != 0)
        {
            //Checks if there are no collisions, imply there are no collision if
            //collide isn't returned.

            return filterA.categoryBits > 0;
        }

        collide = (filterA.maskBits & filterB.categoryBits) != 0 &&
                (filterA.categoryBits & filterB.maskBits) != 0;

        return collide;
    }

    /**Kind of useless but I'll leave it here in case it is needed*/
    public boolean isColliding()
    {
        return collide;
    }

    /**This is to check that the correct hex values are registering*/
    public boolean feedback(short bit1, short bit2)
    {
        if (collide)
        {
            if ((filter1.categoryBits == bit1 && filter2.categoryBits == bit2) ||
                    (filter1.categoryBits == bit2 && filter2.categoryBits == bit1))
            {
                System.out.println("colliding");
                return true;
            }
        }
        else
            release();

        return false;
    }

    public void release()
    {
        filter1.categoryBits = 0;
        filter2.categoryBits = 0;
    }
}
