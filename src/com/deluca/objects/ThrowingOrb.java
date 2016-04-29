package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ThrowingOrb extends AnimatedObject {
	final static float THROWSPEED = (float) .5;
	final static float SCALE =  0.4f;
	float debugTimer;
	final float maxSpeed=20;
	final static String filename="orbPacked.atlas";
	float width ;
	float radius;
	float deltaX=0f, deltaY=0f;
    public static Sound ballSound = Gdx.audio.newSound(Gdx.files.internal("boing.ogg"));

	
	
	public ThrowingOrb(float x, float y) {
		super(filename, 16, 3, x, y);
		textureAtlas = new TextureAtlas(Gdx.files.internal(filename));
		width = getWidth()*SCALE;
		radius=width/2;
		setBounds(0,0,width,width);
		loopback = true;
		debugTimer = 0;
		
		Circle c = new Circle(x+(width/2),y+(width/2),width/2f);
		setShape(c);
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
		super.draw(batch, 1);
	}

	@Override
	public void act(float delta) {

		
		//if the ball is moving AND (if the new position is greater than the width of the screen)  OR (the position is less than or equal to zero)
		if(deltaX!=0&&(getNewXLoc()+width>=Gdx.graphics.getWidth()||getNewXLoc()<=0))
		{
			deltaX=-deltaX;
			playsound();
		}

		if(deltaY!=0&&(getNewYLoc()+width>=Gdx.graphics.getHeight()||getNewYLoc()<=0))
		{
			deltaY=-deltaY;
			playsound();
		}

		setX(getNewXLoc());
		setY(getNewYLoc());
		
		setCenter();
				
		super.act(delta);
	}

	private void setCenter()
	{
		Circle circle = ((Circle)getShape());
		circle.x=getX()+(width/2);
		circle.y=getY()+(width/2);
		setShape(circle);
	}
	
	public float getNewXLoc()
	{
		return getX()+deltaX;
	}

	public float getNewYLoc()
	{
		return getY()+deltaY;
	}

	
	private void playsound() {
	//	ballSound.play();				
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
	public void collide(Actor actor) 
	{
		
	}

	public void bounce() {
		deltaX=-deltaX;
		deltaY=-deltaY;
	}

	public void bounceX() {
		deltaX=-deltaX;		
	}

	public void bounceY() {
		deltaY=-deltaY;
	}

}