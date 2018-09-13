package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by seacow on 9/28/2017.
 *
 * Use the values here and have it changed through the Box2D Body variable by
 * passing it here (or by inheritance but I won't recommend that).
 *
 *             placement.set((xPos / 9f) / DataScale.scaleX, 0);
 *              placement.sub(body.getPosition());
 *              placement.nor();

 */

public class TouchInput implements GestureListener, InputProcessor
{
    private float xPos;
    private float yPos;
    private float xLastPos;
    private float yLastPos;
    private boolean move;

    private Vector2 placement;
    private Vector3 inputPos;
    private OrthographicCamera camera;

    final private GestureDetector detector;

    public TouchInput()
    {
        detector = new GestureDetector(this);

        xPos = 0;
        yPos = 0;
        xLastPos = 0;
        yLastPos = 0;

        placement = new Vector2();
        inputPos = new Vector3();
        camera = null;
    }

    public void setCamera(OrthographicCamera cameraParam)
    {
        camera = cameraParam;
    }

    public void movement(CreateActor player)
    {
        if(move)
        {
            //movement
            player.box2dBody.body.setLinearVelocity(0, (placement.y * Gdx.graphics.getDeltaTime()) * 2000);

            //Normalization
            placement.set(0, yPos / 9f);
            placement.sub(player.box2dBody.body.getPosition());
            placement.nor();
        }
    }

    public GestureDetector getGesture()
    {
        return detector;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        move = true;

        yLastPos = yPos;

        //Normalized with a vector3
        inputPos.set(0, y, 0);
        camera.unproject(inputPos);
        yPos = inputPos.y - 32f;

        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer)
    {
        return false;
    }
    @Override
    public void pinchStop () {
    }

    /** Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed */
    public boolean keyDown (int keycode)
    {
        return false;
    }

    /** Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed */
    public boolean keyUp (int keycode)
    {
        return false;
    }

    /** Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed */
    public boolean keyTyped (char character)
    {
        return false;
    }

    /** Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button the button
     * @return whether the input was processed */
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /** Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     * @param pointer the pointer for the event.
     * @param button the button
     * @return whether the input was processed */
    public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /** Called when a finger or the mouse was dragged.
     * @param pointer the pointer for the event.
     * @return whether the input was processed */
    public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        move = true;

        yLastPos = yPos;

        //Normalized with a vector3
        inputPos.set(0, screenY, 0);
        camera.unproject(inputPos);
        yPos = inputPos.y - 32f;

        return true;
    }

    /** Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     * @return whether the input was processed */
    public boolean mouseMoved (int screenX, int screenY){
        return false;
    }

    /** Called when the mouse wheel was scrolled. Will not be called on iOS.
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed. */
    public boolean scrolled (int amount)
    {
        return false;
    }
}