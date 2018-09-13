package com.mygdx.project;

/**
 * Created by seacow on 10/13/2017.
 *
 * For assigning an object with a ID for collision purposes.
 *
 * Used for collision filtering.
 */

public class CollisionID
{
    final static public short playerID = 0x0001;
    final static public short enemyID = 0x0002;
    final static public short ceilingID = 0x0004;
    final static public short floorID = 0x0008;
    final static public short scoreID = 0x0010;
}