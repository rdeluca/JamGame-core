package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimatedObject
{
	int currentFrame=0;
	int numFrames;
	boolean reverse=false;
	String currentAtlasKey="";
	Sprite orbSprite;
    TextureAtlas textureAtlas;
	
	public AnimatedObject(String file, int totalFrames)
	{
		numFrames=totalFrames;
        textureAtlas = new TextureAtlas(Gdx.files.internal(file));
        AtlasRegion region = textureAtlas.findRegion("0001");
        orbSprite = new Sprite(region);
	}
	
	
	
	public void step() {
    	if(!reverse)	                	
    		currentFrame++;
    	else
    		currentFrame--;
        if(currentFrame > numFrames*3 || currentFrame==1)
        	reverse=!reverse;
        currentAtlasKey = String.format("%04d", currentFrame/3+1);
        orbSprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
	}



	public void draw() {
	
	    		
	}
}