package com.mygdx.project;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by seacow on 11/17/2017.
 */

public class Death implements Screen
{
    final private Project project;

    private Stage stage;
    private Table table;
    private TextButton button1;
    private TextButton button3;

    private BitmapFont title;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    public Death(Project projectParam)
    {
        this.project = projectParam;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(600, 460, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        //Main menu
        table = new Table();
        table.setPosition(0, -190);
        button1 = new TextButton("Play Again", project.skin);
        button3 = new TextButton("Menu", project.skin);

        title = project.skin.getFont("title");
    }

    /** Called when this screen becomes the current screen for a {@link Game}. */
    public void show ()
    {
        project.backgrounds.sunReset();
        if(ScorePOD.score <= 0)
            ScorePOD.score = 1;

        button1.setTransform(true);
        button3.setTransform(true);

        project.assetmanager.manager.update();

        //Creates the buttons
        table.setFillParent(true);
        //table.setDebug(true);      //FOR DEBUGGING PURPOSES - DO NOT REMOVE
        stage.addActor(table);

        //stage.addActor(settings);
        //stage.addActor(back);

        table.add(button1).fillX().uniformX().center().right().pad(0, 20, 0, 20);
        table.add(button3).fillX().uniformX().center().left().pad(0, 20, 0, 20);

        //Button functionality
        button1.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                project.assetmanager.manager.get("320116__abett__folky-acoustic-guitar-version-2-85-bpm-in-g.wav", Music.class).stop();
                project.setScreen(new NewMap(project));
                dispose();
            }
        });

        button3.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                project.setScreen(new SelectScreen(project));
                dispose();
            }
        });
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render (float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        project.batch.setProjectionMatrix(stage.getCamera().combined);

        project.batch.begin();
        project.batch.draw((Texture) project.assetmanager.manager.get("background.png"), 0, 0,
                stage.getWidth(), stage.getHeight());
        project.batch.draw((Texture) project.assetmanager.manager.get("mount.png"), 0, 0,
                stage.getWidth(), stage.getHeight() + 140);
        title.draw(project.batch, "You suck", 90, 390);
        project.backgrounds.displayAll();
        project.batch.draw((Texture) project.assetmanager.manager.get("grass.png"), 0, 0,
                stage.getWidth(), stage.getHeight() + 140);
        project.backgrounds.displayDoor();
        project.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /** @see ApplicationListener#resize(int, int) */
    public void resize (int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    /** @see ApplicationListener#pause() */
    public void pause ()
    {

    }

    /** @see ApplicationListener#resume() */
    public void resume ()
    {

    }

    /** Called when this screen is no longer the current screen for a {@link Game}. */
    public void hide ()
    {

    }

    /** Called when this screen should release all resources. */
    public void dispose ()
    {
        stage.dispose();
        title.dispose();

        System.out.println("Death is disposed");
    }
}
