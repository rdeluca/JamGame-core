package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class WalkCroc extends AnimatedObject {


	final static float SCALE =  2.5f;
	final float maxSpeed=20;
	final int frames=9;
	final static String filename="CrocSheet.atlas";
	float width;
	float height;
	float deltaX=0f, deltaY=0f;

    
    
	public WalkCroc(float startX, float startY) {
		super(filename, 16, 3, startX, startY);
		textureAtlas = new TextureAtlas(Gdx.files.internal(filename));
		width = getWidth()*SCALE;
		height = getHeight()*SCALE;
		setBounds(startX,startY,width,height);
		loopback = true;
		
		setShape(new Rectangle(startX,startY,width,height));
	}

	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, 1);
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
	protected void collide() {
		
	}
	
	
 
}