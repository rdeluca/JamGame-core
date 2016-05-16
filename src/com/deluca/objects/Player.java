package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends AnimatedObject
{
	int SCALE=2;
	private float yVelocity=0;
	private float xVelocity=0;

	public Player()
	{
		// TODO: UN-HARDCODE?
		super("BobbySheet.atlas", 0, 0);
		

		int x=0;
		int y=0;		
		float width =getWidth()*SCALE;
		float height=getHeight()*SCALE;				
		setShape(new Rectangle(x,y,width,height));
		setWidth(width);
		setHeight(height);
		layout();
	    animationSpeed=1/15f;
		textureAtlas = new TextureAtlas(Gdx.files.internal("BobbySheet.atlas"));
	    animation = new Animation(animationSpeed, textureAtlas.getRegions());
		frames = textureAtlas.getRegions().size;
	}
	
	public Player(String file, float startX, float startY) {
		super(file, startX, startY);
	
		int x=0;
		int y=0;		
		float width = getWidth()*SCALE;
		float height=getHeight()*SCALE;				
		setShape(new Rectangle(x,y,width,height));
		setWidth(width);
		setHeight(height);
		layout();
	    animationSpeed=1/15f;
		textureAtlas = new TextureAtlas(Gdx.files.internal(file));
	    animation = new Animation(animationSpeed, textureAtlas.getRegions());
		frames = textureAtlas.getRegions().size;
	}

	@Override
	public void draw(Batch batch, float alpha) {

		Rectangle r = (Rectangle) getShape();
		
		elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), getX(),getY(), 
        		r.x+getWidth()/2, r.y+getHeight()/2, getWidth(), getHeight(),1,1,0);
	}
	
	@Override
	public void collide(Actor actor) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void act(float delta) {
		

		//TODO
		//move Xvelocty and Yvelocity	
		setX(getX()+xVelocity);
		xVelocity=0;
		setY(getY()+yVelocity);
		yVelocity=0;
		
		
		//send coords to world to check for collisions
		
	}
	

	public void setYVelocity(float newVelocity) {
		yVelocity=newVelocity;
	}

	public void setXVelocity(float newVelocity) {
		xVelocity=newVelocity;
	}
}