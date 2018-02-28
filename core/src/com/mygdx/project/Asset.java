package com.mygdx.project;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by seacow on 9/28/2017.
 */

public class Asset
{
    public AssetManager manager;

    public Asset()
    {
        manager = new AssetManager();
    }

    public void loadFiles()
    {
        //textures
        manager.load("cloud1.png", Texture.class);
        manager.load("cloud2.png", Texture.class);
        manager.load("background.png", Texture.class);
        manager.load("sun.png", Texture.class);
        manager.load("tree.png", Texture.class);
        manager.load("grass.png", Texture.class);
        manager.load("mount.png", Texture.class);
        manager.load("star.png", Texture.class);
        manager.load("red.png", Texture.class);
        manager.load("door.png", Texture.class);
        manager.load("doorback_left.png", Texture.class);
        manager.load("doorback_right.png", Texture.class);
        manager.load("arrow.png", Texture.class);
        manager.load("title.png", Texture.class);

        //atlas
        manager.load("player.atlas", TextureAtlas.class);
        manager.load("hippo.atlas", TextureAtlas.class);
        manager.load("snail.atlas", TextureAtlas.class);
        manager.load("watchHUD.atlas", TextureAtlas.class);
        manager.load("watchHUD1.atlas", TextureAtlas.class);
        manager.load("watch.atlas", TextureAtlas.class);
        manager.load("flash.atlas", TextureAtlas.class);

        //skins
        manager.load("skin/flat-earth-ui.json", Skin.class);

        //music
        manager.load("main_menu.ogg", Music.class);
        manager.load("newmap.ogg", Music.class);
        manager.load("grunt1.mp3", Sound.class);
        manager.load("grunt2.mp3", Sound.class);
        manager.load("tick.wav", Sound.class);
        manager.load("door_open.wav", Sound.class);
        manager.finishLoading();

        /**Setting changes under here*/

        //Music
        manager.get("main_menu.ogg", Music.class).setLooping(true);
        manager.get("main_menu.ogg", Music.class).setVolume(2);

        manager.get("newmap.ogg", Music.class).setLooping(true);
        manager.get("newmap.ogg", Music.class).setVolume(2);
    }

    public void dispose()
    {
        manager.dispose();
    }
}