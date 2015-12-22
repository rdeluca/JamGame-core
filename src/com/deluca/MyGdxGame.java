package com.deluca;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.deluca.objects.AnimatedObject;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	  	private SpriteBatch batch;
	    private levelState state = levelState.startScreen;
	    private ArrayList<AnimatedObject> objectList =  new ArrayList<AnimatedObject>();
	    public enum levelState {
	    	startScreen, level0
	    }
	    
	    
	    int x= 0;
	    int y= 0;
	    
	    @Override
	    public void create() {        
	        
	    	batch = new SpriteBatch();
	        
	    	AnimatedObject orb = new AnimatedObject("orbPacked.atlas", 16 );
	    	
	    	
	    	objectList.add(orb);
	    	
	        Timer.schedule(new Task(){
	                @Override
	                public void run() {
	                	
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
	        for(AnimatedObject animatedObject:objectList)
	        {
	        	animatedObject.step();
	        	animatedObject.draw();
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
