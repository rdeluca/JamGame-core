package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimatedObject
{
	int currentFrame=0;
	int animationFrames;
	int numFrames;
	float x;
	float y;
	boolean reverse=false;
	String currentAtlasKey="";
	Sprite sprite;
    public TextureAtlas textureAtlas;
	
	public AnimatedObject(String file, int totalFrames, int animaSpeed)
	{
		numFrames=totalFrames;
		animationFrames = animaSpeed*numFrames;
		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
        AtlasRegion region = textureAtlas.findRegion("0001");
        sprite = new Sprite(region);
	}
	
	
	
	public void step() {
    	if(!reverse)	                	
    		currentFrame++;
    	else
    		currentFrame--;
        if(currentFrame >  animationFrames + 1)
        {	reverse=true;
        	System.out.println("reverse");
        }
        	else if(currentFrame==0)
        {
        		System.out.println("forward");
        	currentFrame=1;
        	reverse=false;
        }

        currentAtlasKey = String.format("%04d", currentFrame/3+1);
        
        
        sprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
	}



	public void draw(Batch batch) {
//TODO Take this out and put it in a orb file

		{
	        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
	            x=Gdx.input.getX();

	        }
	        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
	        	y=Gdx.graphics.getHeight()-Gdx.input.getY();

	        }
	    	sprite.setCenter(x, y);
	    	sprite.setScale((float) 0.4);
	        sprite.draw(batch);
	    }
	}
}