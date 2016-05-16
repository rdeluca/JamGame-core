package com.deluca;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	//
	private Director director;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	

	@Override
	public void create() {

		batch = new SpriteBatch();
		Texture texture = new Texture(Gdx.files.internal("map.png"));

		// Create camera (this is what you're looking at within the viewport)

		camera = new OrthographicCamera(texture.getWidth(), texture.getHeight());		
		try {
			director = new Director(batch, camera, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
