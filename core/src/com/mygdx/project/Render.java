package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by seacow on 9/28/2017.
 *
 * Renders textures; rendering animated sprites are in Animate
 */

public class Render
{
    public OrthographicCamera camera;

    public Render()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    public void render(Project project, Sprite sprite, Body body, float width, float height)
    {
        sprite.setPosition((body.getPosition().x  / Scaler.scaleX * Scaler.PIXELS_TO_METERS) - width/2,
                (body.getPosition().y / Scaler.scaleY * Scaler.PIXELS_TO_METERS) -height/2);

        project.batch.draw(sprite, sprite.getX() * Scaler.scaleX, sprite.getY() * Scaler.scaleY,
                sprite.getOriginX(), sprite.getOriginY(),
                width * Scaler.scaleX, height * Scaler.scaleY,
                sprite.getScaleX(), sprite.getScaleY(),
                sprite.getRotation());
    }
}
