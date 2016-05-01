package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ThrowingOrb extends AnimatedObject {
	final static float THROWSPEED = (float) .5;
	final static float SCALE =  0.4f;
	float debugTimer;
	final float maxSpeed=15;
	final static String filename="orbPacked.atlas";
	float width;
	float height;
	float radius;
	float deltaX=0f, deltaY=0f;
    public static Sound ballSound = Gdx.audio.newSound(Gdx.files.internal("boing.ogg"));
    boolean grabbed=false;
	
	
	public ThrowingOrb(float x, float y) {
		super(filename, x, y);
		
		width = getWidth()*SCALE;
		height=width;		
		radius=width/2;
		loopback = true;
		debugTimer = 0;
        Circle c = new Circle(x+(width/2),y+(width/2),width/2f);
		setShape(c);
		setWidth(width);
		setHeight(height);
		layout();
		
        animationSpeed=1/15f;
		textureAtlas = new TextureAtlas(Gdx.files.internal(filename));
        animation = new Animation(animationSpeed, textureAtlas.getRegions());
		frames = textureAtlas.getRegions().size;

		
	}

	@Override
	public void draw(Batch batch, float alpha) {

		Circle c = (Circle) getShape();
		
		elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), getX(),getY(), 
        		c.x, c.y, width, height,1,1,0);
	}

	@Override
	public void act(float delta) {

		if(grabbed)
		{
			float msX = Gdx.input.getX();
			float msY = Gdx.graphics.getHeight() - Gdx.input.getY();
			Circle c= (Circle)getShape();
			System.out.println("MOUSEX:" + msX + " imageX:" + getX()+ " ballX:"+c.x);
			System.out.println("MOUSEY:" + (Gdx.graphics.getHeight()-msY) + " imageY:" + getY()+ " ballY:"+c.y);


			setX(msX-(width/2));
			setY(msY-(height/2));
			setDeltaX(0);
			setDeltaY(0);
		}
		
		
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
		else if(movementX<-maxSpeed)
			movementX=-maxSpeed;
		
		deltaX=movementX;
	}

	public void setDeltaY(float movementY)
	{
		if(movementY>maxSpeed )
			movementY=maxSpeed;
		else if (movementY<-maxSpeed)
			movementY=-maxSpeed;
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

	public void setGrabbed(boolean b) {
		grabbed=b;	
	}
	
	public boolean getGrabbed()
	{
		return grabbed;
	}
	
}