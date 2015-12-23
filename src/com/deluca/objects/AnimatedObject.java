package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class AnimatedObject
{
	int currentFrame;
	int animationFrames;
	int numFrames;
	float x;
	float y;
	
	boolean loopback=false;
	boolean reverse=false;
	String currentAtlasKey;
	Sprite sprite;
    public TextureAtlas textureAtlas;
	private static int totalFrames;
	private static int animaSpeed;

    
	public AnimatedObject(String file, int totalFrames, int animaSpeed)
	{
		numFrames=totalFrames;
		animationFrames = animaSpeed*numFrames;
		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
        AtlasRegion region = textureAtlas.findRegion("0001");
        sprite = new Sprite(region);
	}
	
	
	
	public void step() 
	{
		if(!reverse)	                	
    		currentFrame++;
    	else
    		currentFrame--;
      
		if(currentFrame >  animationFrames + 1)
        {
        	reverse=true;
    		if(loopback)
        	{
    			currentFrame=0;
        	}
    	}
    	
        if(currentFrame==0)
    	{
        	currentFrame=1;
        	reverse=false;
        }

		currentAtlasKey = String.format("%04d", currentFrame/3+1);

        
        sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	public abstract void draw(Batch batch);
		
		
		
	
}