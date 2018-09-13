package com.mygdx.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Project extends Game
{
	public SpriteBatch batch;
	public Asset assetmanager;

	public static ScreenManager backgrounds;

	public Skin skin;

	public void create()
	{
		batch = new SpriteBatch();

		assetmanager = new Asset();
		assetmanager.loadFiles();

		backgrounds = new ScreenManager(this);
		backgrounds.setContinuation();

		skin = assetmanager.manager.get("skin/flat-earth-ui.json");

		this.setScreen(new com.mygdx.project.SelectScreen(this));
	}

	public void render()
	{
		super.render();
	}

	public void dispose()
	{
		batch.dispose();
		assetmanager.dispose();
		skin.dispose();

		if(this.getScreen() != null)
			this.getScreen().dispose();

		System.out.println("App is disposed");
	}
}
