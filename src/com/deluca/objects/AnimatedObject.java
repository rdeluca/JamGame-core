package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Shape;
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
	private static int animationSpeed;
	private Shape2D shape;
    
	public AnimatedObject(String file, int totalFrames, int animaSpeed, float startX, float startY)
	{
        super( new TextureAtlas(Gdx.files.internal(file)).findRegion("0001"));
        setX(startX);
        setY(startY);  
		numFrames=totalFrames;
        animationSpeed=animaSpeed;

        
        //		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
        new Actor();
		
//        AtlasRegion region = textureAtlas.findRegion("0001");
//        image.setScale((float) scale);		
	}
	
	public Shape2D getShape()
	{
		return shape;
	}
	
	protected void setShape(Shape2D theShape)
	{
		shape=theShape;
	}
	

	protected abstract void collide(Actor actor);

}