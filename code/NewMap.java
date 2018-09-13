package com.mygdx.project;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by seacow on 9/29/2017.
 *
 * Debugmatrixes let you see the box2D boxes. If you don't want to see
 * them, just comment them out.
 */

public class NewMap implements Screen
{
    //Setting and physics stuff
    private final Project project;
    private World world;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    //Player actor
    private Player player;
    //objects other than the player which don't exist yet (but exist now)
    private ObjectManager objects;
    //collision
    private FilterContact collision;
    private HitEvent enemyEvent;
    //controls
    private InputMultiplexer multiProcessor;
    //debugging for box2D
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;
    private HUD hud;
    private Animate flash;
    private ArrayMap<String, Float> region;
    private boolean lostEffect;     //door sounds should only happen once with this after lost
    private boolean hitOnce;

    public NewMap(Project project)
    {
        this.project = project;
        world = new World(new Vector2(0, 0), true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(600, 460, camera);
        stage = new Stage(viewport);
        collision = new FilterContact();

        player = new Player(project, world);
        player.create();

        objects = new ObjectManager(project, world);
        objects.createObjects();
        objects.createEnemies();

        debugRenderer = new Box2DDebugRenderer();   //debugging is cool
        multiProcessor = new InputMultiplexer();    //for player inputs
        hud = new HUD(project, stage);

        region = new ArrayMap<String, Float>();
        region.put("Armature_hit", 2.5f);

        flash = new Animate("flash.atlas", region, project.assetmanager.manager);
        lostEffect = false;
        hitOnce = false;
        enemyEvent = new HitEvent();

        world.setContactFilter(collision);  //This sets the collision in the game
        //world.setContactListener(enemyEvent.getListener());
    }

    /** Called when this screen becomes the current screen for a {@link Game}. */
    @Override
    public void show ()
    {
        player.setInput(camera);

        flash.setScale(124, 117);

        //adds the gestures and touch screen input into the game's input processor
        multiProcessor.addProcessor(stage);
        multiProcessor.addProcessor(player.getInput().getGesture());
        multiProcessor.addProcessor(player.getInput());
        Gdx.input.setInputProcessor(multiProcessor);

        hud.create();

        //button functions
        //this is for the "x" button on the top right
        hud.toMenu.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                project.assetmanager.manager.get("door_open.wav", Sound.class).play(3);
                project.backgrounds.setMoveForDoor(true);
            }
        });

        project.assetmanager.manager.get("newmap.ogg", Music.class).play();
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
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //debugMatrix = project.batch.getProjectionMatrix().cpy().scale(com.mygdx.project.Scaler.PIXELS_TO_METERS, com.mygdx.project.Scaler.PIXELS_TO_METERS, 0);

        /**BATCH BEGIN
         * NOTE: ScreenManager is .backgrounds
         * - first texture called is rendered on the back
         * - last texture is on the front*/
        project.batch.begin();

        project.batch.draw((Texture) project.assetmanager.manager.get("background.png"), 0, 0, stage.getWidth(), stage.getHeight());
        project.batch.draw((Texture) project.assetmanager.manager.get("mount.png"), 0, 0,
                stage.getWidth(), stage.getHeight() + 140);
        project.backgrounds.displayAll();           //This displays the trees and sun
        project.batch.draw((Texture) project.assetmanager.manager.get("grass.png"), 0, 0,
                stage.getWidth(), stage.getHeight() + 140);
        objects.render();
        player.render();
        hud.displayWatch();

        if(collision.feedback(CollisionID.playerID, CollisionID.scoreID))
        {
            project.assetmanager.manager.get("tick.wav", Sound.class).play(3);
            hud.addTime(200);
        }
        if(collision.feedback(CollisionID.playerID, CollisionID.enemyID))
        {
            if(!hitOnce)
            {
                hitOnce = true;
                project.assetmanager.manager.get("grunt1.mp3", Sound.class).play(3);
            }

            flash.render(project.batch, player.getX(), player.getY());
            hud.subtractTime(200);
            player.setAnimate("Armature_up");
        }
        else
        {
            hitOnce = false;
            collision.release();
            player.setNormal();
        }

        project.backgrounds.displayDoor();
        project.batch.end();
        /**END OF BATCH*/

        hud.display();

        //debugRenderer.render(world, debugMatrix);

        if(project.backgrounds.playerLost())    //triggers when the sun is above the sky
        {
            if(!lostEffect) //Sound effect only happens once
            {
                lostEffect = true;
                project.assetmanager.manager.get("door_open.wav", Sound.class).play(3);
            }
            project.backgrounds.setMoveForDoor(true);
        }
        if(project.backgrounds.toMenu())        //is true when the doors are on the center and closes the screen
        {
            lostEffect = false;
            project.assetmanager.manager.get("newmap.ogg", Music.class).stop();
            project.backgrounds.setMoveForDoor(false);      //door is stopped from moving
            project.setScreen(new SelectScreen(project));
            dispose();
        }
    }

    /** @see ApplicationListener#resize(int, int) */
    @Override
    public void resize (int width, int height)
    {
        viewport.setScreenSize(width, height);      //This is for scaling purposes on mobile devices.
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

    /** Called when this screen is no longer the current screen for a {@link Game}. */
    @Override
    public void hide ()
    {

    }

    /** Called when this screen should release all resources. */
    @Override
    public void dispose ()
    {
        stage.dispose();
        world.dispose();

        System.out.println("NewMap disposed");
    }
}