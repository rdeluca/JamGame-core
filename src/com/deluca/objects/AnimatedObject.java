package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AnimatedObject extends Actor
{
	int currentFrame;
	int animationFrames;
	int numFrames;	
	boolean loopback=false;
	boolean reverse=false;
	String currentAtlasKey;
	Sprite sprite;
    public TextureAtlas textureAtlas;
	private static int animationSpeed;

    
	public AnimatedObject(String file, int totalFrames, int animaSpeed, int startX, int startY, float scale)
	{
		//Setup image/animation
		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
		numFrames=totalFrames;
		animationFrames = animaSpeed*numFrames;
        animationSpeed=animaSpeed;
		AtlasRegion region = textureAtlas.findRegion("0001");
		sprite = new Sprite(region);

		//Setup sprite
		setBounds(startX,startY, getWidth(), sprite.getHeight());
        sprite.setCenter(getX(), getY());
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
    		if(!loopback)
        	{
    			currentFrame=0;
        	}
    	}
		
        if(currentFrame==0)
    	{
        	currentFrame=1;
        	reverse=false;
        }
        
		currentAtlasKey = String.format("%04d", currentFrame/animationSpeed+1);

        sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	public abstract void draw(Batch batch);
		
		
		
	
}