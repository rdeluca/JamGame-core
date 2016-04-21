package com.deluca.etc;

import jdk.internal.util.xml.impl.Input;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


public class helloWorldold implements ApplicationListener, InputProcessor{
	
	private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Sprite sprite;
    private int spriteFrame = 1;
    private String currentAtlasKey = new String("0001");
    private Animation rotateUpAnimation;
    private Animation rotateDownAnimation;
    private Animation animation;
    private float elapsedTime = 0;

    int posX,posY;
	float moveAmount = 1.0f;

    int inputDirection;
    
    @Override
    public void create() 
    {     
    	inputDirection=-1;
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("orbpacked.atlas"));
        TextureRegion[] rotateUpFrames = {textureAtlas.findRegion("0001"), textureAtlas.findRegion("0001"),textureAtlas.findRegion("0002"),textureAtlas.findRegion("0003"),textureAtlas.findRegion("0004"),textureAtlas.findRegion("0005"),textureAtlas.findRegion("0006"),textureAtlas.findRegion("0007")};
        TextureRegion[] rotateDownFrames = {textureAtlas.findRegion("0008"),textureAtlas.findRegion("0009"),textureAtlas.findRegion("0010"),textureAtlas.findRegion("0011"),textureAtlas.findRegion("0012"),textureAtlas.findRegion("0013"),textureAtlas.findRegion("0014"),textureAtlas.findRegion("0015")};
        
	    rotateUpAnimation = new Animation(0.1f,rotateUpFrames);
        rotateDownAnimation = new Animation(0.1f,rotateDownFrames);
        animation=rotateUpAnimation;
        posX=0;
        posY=0;
        
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    @Override
    public void render() {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
        
        checkMovement();
        
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion i = animation.getKeyFrame(0);
        batch.draw(animation.getKeyFrame(elapsedTime, true), posX, posY);


        batch.end();
    }

    public void checkMovement()
    {

        switch(inputDirection)
        {
	        case Keys.LEFT:
			    posX-=moveAmount;
			    break;
	        case Keys.RIGHT:
				posX+=moveAmount;
				break;
	        case Keys.UP:
				posY+=moveAmount;
				break;
	        case Keys.DOWN:		
				posY-=moveAmount;
			    break;
	        default:
	        	break;
        }

    }

    @Override
    public void resize(int width, int height) 
    {
    }

    @Override
    public void pause() 
    {
    }

    @Override
    public void resume()
    {
    }

	
	@Override
	public boolean keyDown(int keycode) 
	{		     
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
		{
			moveAmount = 10.0f;
		}
		
		inputDirection=keycode;
		
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) 
	{     
		if(keycode==Keys.CONTROL_LEFT)
		{
			moveAmount = 1.0f;
		}
		
		
		

		return true;
	}
	

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
}