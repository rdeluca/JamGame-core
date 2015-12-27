package com.deluca;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.deluca.objects.AnimatedObject;
import com.deluca.objects.ThrowingOrb;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	  	private SpriteBatch batch;
	    private levelState state = levelState.startScreen;
	    private ArrayList<AnimatedObject> objectList =  new ArrayList<AnimatedObject>();
        Sprite background;
        	    
	    public enum levelState {
	    	startScreen, level01
	    }

	    @Override
	    public void create() {        

	    	FileHandle i = Gdx.files.internal("background.png");
	    	background = new Sprite(new Texture(i));
	    	batch = new SpriteBatch();
	        int w = Gdx.graphics.getWidth();
	    	int h = Gdx.graphics.getHeight();
	    	Stage stage = new Stage();
	    	
	    	AnimatedObject orb = new ThrowingOrb();
	    	
	    	
	    	objectList.add(orb);
	    	
	        Timer.schedule(new Task(){
	                @Override
	                public void run() {
	        	        for(AnimatedObject aObject: objectList){
	        	        	aObject.step();
	        	        }
	        	        render();

	                }
	            }
	            ,0,1/30.0f);
	    }

	    @Override
	    public void dispose() {
	        
	    	batch.dispose();
	        for(AnimatedObject aObject: objectList)
	        	aObject.textureAtlas.dispose();
	    }

	    @Override
	    public void render() { 
	    	
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	        batch.begin();
	        
	        background.draw(batch);
	        for(AnimatedObject animatedObject:objectList)
	        {
	        	animatedObject.draw(batch);
	        }
	        batch.end();
	    }

	    
	    @Override
	    public void resize(int width, int height) {
	    }

	    @Override
	    public void pause() {
	    }

	    @Override
	    public void resume() {
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
		public boolean touchDown(int screenX, int screenY, int pointer,int button) {
			// TODO Auto-generated method stub
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
		
		public void changeDisplay(int width, int height, boolean fullscreen)
		{
			Gdx.graphics.setDisplayMode(width,height,fullscreen);
		}
		
		public void changeCamera(int width, int height)
		{
			OrthographicCamera camera = new OrthographicCamera(width, height);
		}
}
