package com.mygdx.project;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by seacow on 2/28/2018.
 *
 * This has better collision accuracy than the filtercontact
 */

public class HitEvent implements ContactListener
{
    static private boolean lossScore;
    static private boolean gainScore;


    public HitEvent()
    {
        lossScore = false;
        gainScore = false;
    }

    /**for registering the listener to a Box2D world
     * Needed for collisions to work
     */
    public ContactListener getListerner()
    {
        return this;
    }

    public boolean getlossScore()
    {
        return lossScore;
    }

    public boolean getgainScore()
    {
        return gainScore;
    }

    /** Called when two fixtures begin to touch. */
    public void beginContact (Contact contact)
    {
        short filterA = contact.getFixtureA().getFilterData().categoryBits;
        short filterB = contact.getFixtureB().getFilterData().categoryBits;

        if((filterA == CollisionID.playerID || filterB == CollisionID.enemyID) &&
                (filterA == CollisionID.enemyID || filterB == CollisionID.playerID))
        {
            System.out.println();
            lossScore = true;
        }

        if((filterA == CollisionID.playerID || filterB == CollisionID.scoreID) &&
                (filterA == CollisionID.scoreID || filterB == CollisionID.scoreID))
        {
            gainScore = true;
        }
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
        lossScore = false;
        gainScore = false;
    }

    /*
     * This is called after a contact is updated. This allows you to inspect a contact before it goes to the solver. If you are
     * careful, you can modify the contact manifold (e.g. disable contact). A copy of the old manifold is provided so that you can
     * detect changes. Note: this is called only for awake bodies. Note: this is called even when the number of contact points is
     * zero. Note: this is not called for sensors. Note: if you set the number of contact points to zero, you will not get an
     * EndContact callback. However, you may get a BeginContact callback the next step.
     */
    public void preSolve (Contact contact, Manifold oldManifold)
    {

    }

    /*
     * This lets you inspect a contact after the solver is finished. This is useful for inspecting impulses. Note: the contact
     * manifold does not include time of impact impulses, which can be arbitrarily large if the sub-step is small. Hence the
     * impulse is provided explicitly in a separate data structure. Note: this is only called for contacts that are touching,
     * solid, and awake.
     */
    public void postSolve (Contact contact, ContactImpulse impulse)
    {

    }
}
