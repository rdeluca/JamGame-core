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
		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
		numFrames=totalFrames;
        animationSpeed=animaSpeed;
		
        AtlasRegion region = textureAtlas.findRegion("0001");
        sprite = new Sprite(region);
        setX(startX);
        setY(startY);    	
        sprite.setScale((float) scale);		
        sprite.setCenter(getX(), getY());
	}
	
	
	@Override
	public float getX() {
		return sprite.getX();
	}

	@Override
	public float getY() {
		return sprite.getY();
	}

	@Override
	public void setX(float x) {
		sprite.setX(x);
	}

	@Override
	public void setY(float y) {
		sprite.setY(y);
	}	
}