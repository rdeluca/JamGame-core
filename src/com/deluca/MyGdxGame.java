package com.deluca;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deluca.objects.AnimatedObject;
import com.deluca.objects.ThrowingOrb;
import com.deluca.util.FixedSizeQueue;

public class MyGdxGame extends ApplicationAdapter {

	//
	private Director director;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	@Override
	public void create() {

		batch = new SpriteBatch();
		// Create camera (this is what you're looking at within the viewport)
		camera = new OrthographicCamera(400, 400);

		director = new Director(batch, camera, this);
		// director=new Director( level);
		// director.addEventListener(this);
		// TODO:^^^^^^^^^^^^^^

	}

	@Override
	public void dispose() {
		batch.dispose();
		director.dispose();
	}

	@Override
	public void render() {
		director.render();
	}

	
	@Override
	public void resize (int width, int height) {
	}


}
