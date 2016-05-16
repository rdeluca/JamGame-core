package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class AnimatedObject extends Image
{
	int currentFrame;
	int animationFrames;
	int numFrames;

	
	boolean loopback=false;
	boolean reverse=false;
	String currentAtlasKey;
    public TextureAtlas textureAtlas;
	protected static float animationSpeed;
	int frames;
	private Shape2D shape;
	Animation animation;
	float elapsedTime = 0;

	public AnimatedObject(String file, float startX, float startY)
	{
        super( new TextureAtlas(Gdx.files.internal(file)).findRegion("0001"));
        setX(startX);
        setY(startY);  

	}
	
	public Shape2D getShape()
	{
		return shape;
	}
	
	protected void setShape(Shape2D theShape)
	{
		shape=theShape;
	}
	

	public abstract void collide(Actor actor);

}