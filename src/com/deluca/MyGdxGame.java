package com.deluca;

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


public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	  	private SpriteBatch batch;
	    private TextureAtlas textureAtlas;
	    private Sprite orbSprite;
	    private int currentFrame = 1;
	    private int numOrbFrames = 17;
	    private String currentAtlasKey = new String("0001");
	    private levelState state = levelState.startScreen;
	    private boolean orbOrderDown = false;
	    
	    public enum levelState {
	    	startScreen, level0
	    }
	    
	    
	    int x= 0;
	    int y= 0;
	    
	    @Override
	    public void create() {        
	        
	    	batch = new SpriteBatch();
	        
	        textureAtlas = new TextureAtlas(Gdx.files.internal("orbPacked.atlas"));
	        AtlasRegion region = textureAtlas.findRegion("0001");
	        orbSprite = new Sprite(region);

	        Timer.schedule(new Task(){
	                @Override
	                public void run() {
	                	if(!orbOrderDown)	                	
	                		currentFrame++;
	                	else
	                		currentFrame--;
	                    if(currentFrame > numOrbFrames*3 || currentFrame==1)
	                    	orbOrderDown=!orbOrderDown;
	                    currentAtlasKey = String.format("%04d", currentFrame/3+1);
	                    orbSprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
	                }
	            }
	            ,0,1/30.0f);
	    }

	    @Override
	    public void dispose() {
	        batch.dispose();
	        textureAtlas.dispose();
	    }

	    @Override
	    public void render() {        
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	        batch.begin();
	        drawOrb();
	        batch.end();
	    }

	    private void drawOrb()
	    {
	        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
	            x=Gdx.input.getX();

	        }
	        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
	        	y=Gdx.graphics.getHeight()-Gdx.input.getY();

	        }
	    	orbSprite.setCenter(x, y);
	    	orbSprite.setScale((float) 0.4);
	        orbSprite.draw(batch);
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
