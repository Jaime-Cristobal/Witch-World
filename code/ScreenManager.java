package com.mygdx.project;

/**
 * Created by seacow on 10/25/2017.
 *
 * Things that will show up on the menu but you want to stay the same
 * in the game map will be placed here.
 */

public class ScreenManager
{
    //super sexy backgrounds
    final private BGManager cloud1;
    final private BGManager cloud2;
    final private BGManager tree1;
    final private BGManager star;
    final private BGManager doorleft;
    final private BGManager doorright;
    final private BGManager backleft;
    final private BGManager backright;
    final private Sunrise sun;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean finishLeft;
    private boolean finishRight;

    private float leftSpeed;
    private float rightSpeed;

    public ScreenManager(Project project)
    {
        cloud1 = new BGManager("cloud1.png", project, 2, true);
        cloud2 = new BGManager("cloud2.png", project, 1, true);
        tree1 = new BGManager("tree.png", project, 10, true);
        star = new BGManager("star.png", project, 10, true);

        doorleft = new BGManager("door.png", project, 1, true);
        doorright = new BGManager("door.png", project, 1, true);
        backleft = new BGManager("doorback_left.png", project, 1, true);
        backright = new BGManager("doorback_right.png", project, 1, true);

        sun = new Sunrise(project);

        moveLeft = false;
        moveRight = false;
        finishLeft = false;
        finishRight = false;
        leftSpeed = 0;
        rightSpeed = 0;
    }

    public void setContinuation()
    {
        cloud1.setSpawnArea(700, 1200, 300, 440);
        cloud1.setResolution(228, 47);
        cloud1.setData(-250, 90);

        cloud2.setSpawnArea(700, 1100, 300, 440);
        cloud2.setResolution(208, 42);
        cloud2.setData(-250, 90);

        tree1.setSpawnArea(700, 2000, 65, 75);
        tree1.setResolution(74, 99);
        tree1.setData(-80, 120);

        star.setSpawnArea(0, 600, 380, 450);
        star.setResolution(8,8);

        doorleft.setSpawnArea(0, 0, 0, 0);
        doorleft.setResolution(300, 460);
        doorleft.setData(-1000, 0);

        doorright.setSpawnArea(300, 300, 0, 0);
        doorright.setResolution(300, 460);
        doorright.setData(1000, 0);

        backleft.setSpawnArea(0, 0, 0, 0);
        backleft.setResolution(300, 460);
        backleft.setData(-1000, 0);

        backright.setSpawnArea(300, 300, 0, 0);
        backright.setResolution(300, 460);
        backright.setData(1000, 0);
    }

    public void displayAll()
    {
        star.display();
        tree1.displayMove();
        sun.render();
        cloud1.displayMove();
        cloud2.displayMove();
    }

    public void displayDoor()
    {
        backleft.displayMove();
        backright.displayMove();
        doorleft.displayMove();
        doorright.displayMove();

        doorleft.setData(-1000, leftSpeed);
        doorright.setData(1000, -rightSpeed);
        backleft.setData(-1000, leftSpeed);
        backright.setData(1000, -rightSpeed);
    }

    public void setMoveForDoor(boolean value)
    {
        moveRight = moveLeft = value;

        if(!value)
        {
            finishRight = false;
            finishLeft = false;
        }
    }

    public boolean toGameMap()
    {
        return doorMovement(300, -335, 635, false);
    }

    public boolean toMenu()
    {
        return doorMovement(-300, 0, 300, true);
    }

    /**moveLeft/moveRight = true means that the objects is still moving and has not reach
     * the limit of either xLeft or xRight
     *
     * @param xLeft -> distance limit that stops the door from moving in the left
     * @param xRight -> distance limit that stops the door from moving in the right
     * @param backwards -> if true, the door is opening; going from center to the side
     *                     if false, the door is closing; going from side to center*/
    private boolean doorMovement(float speed, int xLeft, int xRight, boolean backwards)
    {
        //This is from menu to map
        if(!backwards)
        {
            if ((int)doorleft.getX() <= xLeft) {
                leftSpeed = 0;
                doorleft.setX(-330);
                backleft.setX(-330);

                moveLeft = false;
                finishLeft = true;
            }

            if ((int)doorright.getX() >= xRight) {
                rightSpeed = 0;
                doorright.setX(630);
                backright.setX(630);

                moveRight = false;
                finishRight = true;
            }
        }
        //This is from map to menu
        else {
            if ((int)doorleft.getX() >= xLeft) {
                leftSpeed = 0;
                doorleft.setX(-5);
                backleft.setX(-5);

                moveLeft = false;
                finishLeft = true;
            }

            if ((int)doorright.getX() <= xRight) {
                rightSpeed = 0;
                doorright.setX(305);
                backright.setX(305);

                moveRight = false;
                finishRight = true;
            }
        }

        if(moveRight && moveLeft)
        {
            finishLeft = false;
            finishRight = false;
        }

        if(moveLeft)
            leftSpeed = speed;

        if(moveRight)
            rightSpeed = speed;

        if(finishLeft && finishRight)
        {
            return true;
        }

        return false;
    }

    public boolean getMoveVal()
    {
        return moveLeft && moveRight;
    }

    public void resetDoor()
    {
        doorleft.resetPos();
        doorright.resetPos();
        backleft.resetPos();
        backright.resetPos();
    }

    public void sunReset()
    {
        sun.reset();
    }

    public boolean playerLost()
    {
        return sun.lost();
    }

    public boolean showTint()
    {
        return sun.showRedTint();
    }

    public boolean getDoorBool()
    {
        if(moveRight && moveLeft)
            return true;
        else if(finishLeft && finishRight)
            return true;

        return false;
    }
}
