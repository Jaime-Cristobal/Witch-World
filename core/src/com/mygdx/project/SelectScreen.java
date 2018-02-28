package com.mygdx.project;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by seacow on 9/28/2017.
 *
 * Everything here will be disposed after changing screens
 */

public class SelectScreen implements Screen
{
    final private Project project;

    private Stage stage;
    private Table table;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;

    //For settings field
    private Dialog settings;
    private TextButton back;

    private OrthographicCamera camera;
    private StretchViewport viewport;

    private BGManager title;
    private boolean dropTitle;

    public SelectScreen(final Project batch_param)
    {
        this.project = batch_param;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(600, 460, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        //Main menu
        table = new Table();
        table.setPosition(0, -190);
        button1 = new TextButton("Play", project.skin);
        button2 = new TextButton("Settings", project.skin);
        button3 = new TextButton("Exit", project.skin);

        //Setting screen
        settings = new Dialog("Settings", project.skin);
        settings.setPosition(2000, 2000);
        back = new TextButton("Back", project.skin);
        back.setPosition(settings.getX() + 30, settings.getY() + 30);

        title = new BGManager("title.png", project, 1, false);
        title.setResolution(208,176);
        title.setSpawnArea(30, 30, 460, 460);

        dropTitle = false;
    }

    /** Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void show ()
    {
        title.setY(460);

        project.backgrounds.sunReset();

        if(ScorePOD.score <= 0)
            ScorePOD.score = 1;

        button1.setTransform(true);
        button2.setTransform(true);
        button3.setTransform(true);

        project.assetmanager.manager.update();

        //Creates the buttons
        table.setFillParent(true);
        //table.setDebug(true);      //FOR DEBUGGING PURPOSES - DO NOT REMOVE
        stage.addActor(table);

        //stage.addActor(settings);
        //stage.addActor(back);

        table.add(button1).fillX().uniformX().center().right().pad(0, 20, 0, 20);
        table.add(button2).fillX().uniformX().center().pad(0, 20, 0, 20);
        table.add(button3).fillX().uniformX().center().left().pad(0, 20, 0, 20);

        //Button functionality
        button1.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                project.assetmanager.manager.get("main_menu.ogg", Music.class).stop();
                project.assetmanager.manager.get("door_open.wav", Sound.class).play(3);
                project.backgrounds.setMoveForDoor(true);
                dropTitle = true;
            }
        });

        /**
        button2.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                stage.addActor(settings);
                stage.addActor(back);
            }
        });

        back.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                //settings.setPosition(2000, 2000);
                //back.setPosition(settings.getX() + 30, settings.getY() + 30);
            }
        }); */

        button3.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
            }
        });

        project.assetmanager.manager.get("main_menu.ogg", Music.class).play();
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     *
     * You can comment out anything with the word debug if you want the boxes
     * to disappear*/
    @Override
    public void render (float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        project.batch.setProjectionMatrix(stage.getCamera().combined);

        project.batch.begin();

        if(project.backgrounds.getMoveVal() || dropTitle)
        {
            project.batch.draw((Texture) project.assetmanager.manager.get("background.png"), 0, 0,
                    stage.getWidth(), stage.getHeight());
            project.batch.draw((Texture) project.assetmanager.manager.get("mount.png"), 0, 0,
                    stage.getWidth(), stage.getHeight() + 140);
            project.backgrounds.displayAll();
            project.batch.draw((Texture) project.assetmanager.manager.get("grass.png"), 0, 0,
                    stage.getWidth(), stage.getHeight() + 140);
        }

        project.backgrounds.displayDoor();
        title.displayMove();

        if(!dropTitle && title.getY() >= 335)
            title.setData(-1000, -500);
        else if(dropTitle)
            title.setData(-1000, 500);
        else
            title.setData(-1000, 0);

        project.batch.end();

        if(project.backgrounds.toGameMap())
        {
            dropTitle = false;
            project.backgrounds.setMoveForDoor(false);
            project.setScreen(new NewMap(project));
            dispose();
        }

        if(!dropTitle)
        {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        //if(!project.backgrounds.getDoorBool())
        //{

        //}
    }

    /** @see ApplicationListener#resize(int, int) */
    @Override
    public void resize (int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    /** @see ApplicationListener#pause() */
    @Override
    public void pause ()
    {

    }

    /** @see ApplicationListener#resume() */
    @Override
    public void resume ()
    {

    }

    /** Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void hide ()
    {

    }

    /** Called when this screen should release all resources. */
    @Override
    public void dispose ()
    {
        stage.dispose();

        System.out.println("SelectScreen is disposed");
    }
}