package com.deluca;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deluca.objects.AnimatedObject;
import com.deluca.objects.ThrowingOrb;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor{

		//LibGDX lib items
	
		
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

        Stage stage;
        Actor actor;
        AnimatedObject orb;

	    @Override
	    public void create() {        
	    	
	    	//Create camera (this is what you're looking at within the viewport)
	    	camera = new OrthographicCamera(400, 400);
	    	
	    	batch = new SpriteBatch();
	    		texture = new Texture(Gdx.files.internal("stock-photo-33531251.jpg"));
	    		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    	sprite = new Sprite(texture);
	    		sprite.setOrigin(0,0);
	    		sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);
    	  
    		Gdx.input.setInputProcessor((this));
	    	
	    	
	    	FileHandle i = Gdx.files.internal("background.png");
	    	

	    	actor = new Actor();
	    	
	    	
	        int w = Gdx.graphics.getWidth();
	    	int h = Gdx.graphics.getHeight();
	    	System.out.println("w="+w+", h="+h);
	    	
	    	ScreenViewport viewport = new ScreenViewport();
	    	stage = new Stage(viewport);
	    	orb= new ThrowingOrb();
	    	
	    	stage.addActor(orb);

	  //  	Gdx.input.setInputProcessor(stage);
	    	stage.setKeyboardFocus(stage.getActors().first());
	    
	    }

	    @Override
	    public void dispose() {
	       batch.dispose();
	       texture.dispose();
	    }


	    @Override
	    public void render() { 
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	        

	        batch.setProjectionMatrix(camera.combined);
	        batch.begin();
	        sprite.draw(batch);
	        batch.end();
	        
	    	stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	    }

	    
		
		public void changeDisplay(int width, int height, boolean fullscreen)
		{
			Gdx.graphics.setDisplayMode(width,height,fullscreen);
		}
		
		public void changeCamera(int width, int height)
		{
			OrthographicCamera camera = new OrthographicCamera(width, height);
		}



		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {

			
			orb.act(Gdx.graphics.getDeltaTime());
			orb.setX(Gdx.input.getX());
			orb.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
			
			sprite.setCenter(orb.getX(), orb.getY());
			System.out.println("READ!");

			System.out.println(orb.getX() + " " + orb.getY());
		
			
			return false;
		}



		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

}
