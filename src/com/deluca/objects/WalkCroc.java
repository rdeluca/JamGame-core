package com.deluca.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WalkCroc extends AnimatedObject {


	final static float SCALE =  2.5f;
	final float maxSpeed=20;
	final static String filename="CrocSheet.atlas";
	float width;
	float height;
	float deltaX=0f, deltaY=0f;
	Animation activeAnimation, animationTwo, animationEnd;
	ArrayList<Animation> animationList=	new ArrayList<Animation>();
	int currAnimation=0;	
	
	public WalkCroc(float startX, float startY) 
	{
		super(filename, startX, startY);
		width = getWidth()*SCALE;
		height = getHeight()*SCALE;
		setBounds(startX,startY,width,height);
		loopback = true;
		setShape(new Rectangle(startX,startY,width,height));
		
		
        animationSpeed=1/15f;
		textureAtlas = new TextureAtlas(Gdx.files.internal(filename));
        animation = new Animation(animationSpeed,textureAtlas.findRegion("0001"));
		frames = textureAtlas.getRegions().size;
        animationTwo = new Animation(animationSpeed,textureAtlas.findRegion("0001"));
		    	
		activeAnimation = new Animation(0.5f,
		        (textureAtlas.findRegion("0002")),
		        (textureAtlas.findRegion("0003")));
		animationEnd = new Animation(animationSpeed, textureAtlas.findRegions("0004"));
		animationList.add(animation);
	//	animationList.add(animationTwo);
		animationList.add(activeAnimation);
		animationList.add(animationEnd);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Rectangle rect = (Rectangle) getShape();
		
		elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animationList.get(currAnimation).getKeyFrame(elapsedTime, true), getX(),getY(), 
        		rect.x, rect.y, width, height,1,1,0);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	private void playsound() {
	}

	public void setDeltaX(float movementX)
	{
		if(movementX>maxSpeed)
			movementX=maxSpeed;
		deltaX=movementX;
	}

	public void setDeltaY(float movementY)
	{
		if(movementY>maxSpeed)
			movementY=maxSpeed;
		deltaY=movementY;
	}

	@Override
	public void collide(Actor actor) {
		if(currAnimation<animationList.size()-1)
			currAnimation++;
		else
			currAnimation=0;
	}
	
	
 
}