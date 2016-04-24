package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class ThrowingOrb extends AnimatedObject {
	final static float THROWSPEED = (float) .5;
	final static float SCALE =  0.4f;
	float debugTimer;
	final float maxSpeed=20;
	float width = 0;

	
	float deltaX=0f, deltaY=0f;
	
	public ThrowingOrb() {
		super("orbPacked.atlas", 16, 3, 0, 0, SCALE);
		textureAtlas = new TextureAtlas(Gdx.files.internal("orbPacked.atlas"));
		width = sprite.getBoundingRectangle().width;
		setBounds(0,0,width,width);
		loopback = true;
		debugTimer = 0;
		this.scaleBy(SCALE);
		sprite.scale(SCALE);
	}

	@Override
	public void draw(Batch batch, float alpha) {
	/*	System.out.println("mX= "+ Gdx.input.getX());
		System.out.println("mY= "+ Gdx.input.getY());


		float topright=sprite.getBoundingRectangle().y+width;
		float botright=sprite.getBoundingRectangle().x+width;
		System.out.println(width);
		System.out.println(sprite.getBoundingRectangle().y+"______"+topright);
		System.out.println(sprite.getBoundingRectangle().x +"______"+botright );		
		System.out.println("-----------------------");
		System.out.println(sprite.getY());
		System.out.println(sprite.getX());		
		System.out.println("================================");
*/
		
		sprite.draw(batch);
	}

	@Override
	public void act(float delta) {

		
		float width = sprite.getBoundingRectangle().width;

		if(deltaX!=0&&(getX()+deltaX+width>=Gdx.graphics.getWidth()||getX()+deltaX<=0))
		{
			deltaX=-deltaX;
	//		deltaX=deltaX*.5f;
			//System.out.println("deltaX="+deltaX);
		}

		if(deltaY!=0&&(getY()+deltaY+width>=Gdx.graphics.getHeight()||getY()+deltaY<=0))
		{
			deltaY=-deltaY;
//			deltaY=deltaY*.5f;
	//		System.out.println("deltaY="+deltaY);
		}

		setX(getX()+deltaX);
		setY(getY()+deltaY);
		sprite.setX(this.getX());
		sprite.setY(this.getY());


		super.act(delta);
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
}