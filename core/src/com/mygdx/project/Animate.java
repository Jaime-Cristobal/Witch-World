package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 9/28/2017.
 *
 * I should have this set to it's original resolution and then manually upscale
 * or downscale it when you want to but I am too lazy to do it right now.
 *
 * In ArrayMap region, String is the region name inside the TextureAtlas and Float is
 * the animation speed.
 */

public class Animate
{
    private Array<Animation> animation;
    private ArrayMap<String, Float> region;
    private float timeElapsed;
    private int regionKey;

    private float speed;
    private String file;

    private float width;
    private float height;
    private int box2dScale;

    private Animate(String fileName, float speedParam)
    {
        file = fileName;
        regionKey = 0;
        speed = speedParam;
        timeElapsed = 0f;

        animation = new Array<Animation>();
        region = new ArrayMap<String, Float>();

        box2dScale = 0;
    }

    /**This is for Atlases with no multiple regions*/
    public Animate(String fileName, AssetManager assetManager, float speedParam)
    {
        this(fileName, speedParam);

        animation.add(new Animation(speed * Gdx.graphics.getDeltaTime(), assetManager.get(file, TextureAtlas.class).getRegions()));

        width = assetManager.get(file, TextureAtlas.class).getRegions().first().packedWidth * Scaler.scaleX;
        height = assetManager.get(file, TextureAtlas.class).getRegions().first().packedHeight * Scaler.scaleY;
    }

    /**Use this for Atlases with multiple regions*/
    public Animate(String fileName, ArrayMap<String, Float> reg, AssetManager assetManager)
    {
        this(fileName, 0);

        region = reg;

        animation = new Array<Animation>();
        for(int n = 0; n < region.size; n++)
        {
            animation.add(new Animation(region.getValueAt(n) * Gdx.graphics.getDeltaTime(),
                    assetManager.get(file, TextureAtlas.class).findRegions(region.getKeyAt(n))));
        }

        width = assetManager.get(file, TextureAtlas.class).getRegions().first().packedWidth * Scaler.scaleX;
        height = assetManager.get(file, TextureAtlas.class).getRegions().first().packedHeight * Scaler.scaleY;
    }

    public void setRegion(String key)
    {
        for(int n = 0; n < region.size; n++)
        {
            if(region.getKeyAt(n) == key)
                regionKey = n;
        }
    }

    /**playback is the type or set of animations you want to render from the atlas.
     * The ordering is heavily dependant on the order you passed the regions*/
    public void render(Batch batch, float x, float y)
    {
        timeElapsed += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) animation.get(regionKey).getKeyFrame(timeElapsed, true),
                ((x * Scaler.PIXELS_TO_METERS) - width/2) + box2dScale,
                (y * Scaler.PIXELS_TO_METERS) - height/2,
                width, height);
    }

    /**For manually upscaling and downscaling resolutions*/
    public void setScale(float widthVal, float heightVal)
    {
        width = widthVal;
        height = heightVal;
    }

    /**Mirrors the region into the opposite direction it was facing. Only works left and right
     * for now; best to put between 50-70 for the val if the object is facing TO THE LEFT >:D ---->>>*/
    public void flip(int val)
    {
        width *= -1;

        box2dScale = val;
    }

    /**For scaling the regions with the box2D bodies if for some reason it drifts away.
     * Values between 10-70 are generally good scales*/
    public void setPosScale(int val)
    {
        box2dScale = val;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
}