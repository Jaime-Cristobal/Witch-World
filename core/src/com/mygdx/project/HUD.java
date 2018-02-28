package com.mygdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by seacow on 10/17/2017.
 *
 * This only contains the menu for now
 *
 * Also has the game timer
 */

public class HUD
{
    final private Stage stage;
    final private Project project;

    private Table table;
    private Label showTime;
    private int time;
    public Button toMenu;

    private Animate watchHUD;
    private Animate watchHUD1;
    private ArrayMap<String, Float> region;
    private ArrayMap<String, Float> region1;

    public HUD(Project projectParam, Stage stageParam)
    {
        this.project = projectParam;
        this.stage = stageParam;

        table = new Table();
        table.setPosition(20, 210);    //sets it at the right top corner

        toMenu = new Button(project.skin, "close");

        time = 1000;
        showTime = new Label("" + time, project.skin);

        region = new ArrayMap<String, Float>();
        region.put("Armature_spin", 2.5f);

        region1 = new ArrayMap<String, Float>();
        region1.put("Armature_spin", 1.5f);

        watchHUD = new Animate("watchHUD.atlas", region, project.assetmanager.manager);
        watchHUD1 = new Animate("watchHUD1.atlas", region1, project.assetmanager.manager);
    }

    public void create()
    {
        table.setTransform(true);
        table.setFillParent(true);
        stage.addActor(table);

        table.add(showTime).fillX().uniformX().center().right().pad(0, 240, 0, 240);
        table.add(toMenu).uniformX();       //button should be slotted into the table in the top right corner

        watchHUD.setScale(32, 32);
        watchHUD1.setScale(32, 32);
    }

    public void displayWatch()
    {
        if(time <= 200)
            watchHUD1.render(project.batch, 27, 44);
        else
            watchHUD.render(project.batch, 27, 44);
    }

    public void display()
    {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showTime.setText("" + time);

        timer();
        ScorePOD.score = time;      //A global value that will be used for the class Sunrise
    }

    public void timer()
    {
        time -= 1 * (Gdx.graphics.getDeltaTime() / 100);

        if(time < 0)    //The score will be set to 0 when it becomes negative
            time = 0;   //Doesn't add when 0
    }

    /**For when the player collides with an enemy*/
    public void subtractTime(int n)
    {
        if(time > 0)    //to avoid the annoying popping when a difference total is below 0
            time -= n;
    }

    public void addTime(int n)
    {
        time += n;
    }

    public int getTime()
    {
        return time;
    }
}
