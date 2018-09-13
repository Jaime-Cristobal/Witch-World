package com.mygdx.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 9/28/2017.
 *
 * Make sure that there is a constructor that autmatically uses
 * the asset's dimensions
 *
 * There's only 3 of us so I won't make the CreateBody a constant.
 *
 * You can use the CreateBody to change the position, rotation, angle,
 * and all that stuff.
 */

public class CreateActor
{
    final private Project project;

    public CreateBody box2dBody;
    private Render renderer;
    private Sprite sprite;
    public com.mygdx.project.Animate animate;

    private String file;
    private float x;
    private float y;
    private float width;
    private float height;

    public boolean checker;    //this is exclusively for checking if the individual
                                //actor has reached the limit before spawning defined
                                //in GameObject

    private CreateActor(String fileName, Project projectParam)
    {
        this.project = projectParam;
        file = fileName;

        x = 0;
        y = 0;

        checker = false;
    }

    /**for empty/no texture box2d bodies*/
    public CreateActor(BodyDef.BodyType type)
    {
        this(null, null);

        box2dBody = new CreateBody(type);
        renderer = new Render();

        file = null;
        sprite = null;
        animate = null;
    }

    /**for texture sprites*/
    public CreateActor(String fileName, Project projectParam, BodyDef.BodyType type)
    {
        this(fileName, projectParam);

        box2dBody = new CreateBody(type);
        renderer = new Render();
        animate = null;

        sprite = new Sprite((Texture) project.assetmanager.manager.get(file));
    }

    /**for animated atlas sprites*/
    public CreateActor(String fileName, Project projectParam, ArrayMap<String, Float> region, BodyDef.BodyType type)
    {
        this(fileName, projectParam);

        box2dBody = new CreateBody(type);
        renderer = null;
        sprite = null;

        animate = new com.mygdx.project.Animate(file, region, project.assetmanager.manager);
    }

    /**Use this with a contact filtering class*/
    public void setFilter(short category, short mask)
    {
        box2dBody.setFilter(category, mask);
    }

    public void create(World world, float xPos, float yPos, float w, float h, boolean isSensor)
    {
        if(animate == null)
        {
            width = w / Scaler.scaleX;
            height = h / Scaler.scaleY;
        }
        else
        {
            animate.setScale(w, h);
            width = animate.getWidth() / Scaler.scaleX;
            height = animate.getHeight() / Scaler.scaleY;
        }

        x = xPos + (width / 2);
        y = yPos + (height / 2);

        box2dBody.create(file, world, x, y, width, height, isSensor);
        box2dBody.body.setActive(true);
    }

    /**Only render if the file isn't null, empty box2D bodies do not need to rendered*/
    public void displayTexture()
    {
        renderer.render(project, sprite, box2dBody.body, width, height);
    }

    public void displayAnimation()
    {
        animate.render(project.batch, box2dBody.body.getPosition().x, box2dBody.body.getPosition().y);
    }

    public void setAnimate(String key)
    {
        animate.setRegion(key);
    }

    public void flipAnimation()
    {
        animate.flip(0);
    }
}