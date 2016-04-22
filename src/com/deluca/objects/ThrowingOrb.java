package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class ThrowingOrb extends AnimatedObject {
	final static float THROWSPEED = (float) .5;
	final static float SCALE = (float) 0.4;
	float debugTimer;
	float width;

	float deltaX=0f, deltaY=0f;
	
	public ThrowingOrb() {
		super("orbPacked.atlas", 16, 3, 0, 0, SCALE);
		textureAtlas = new TextureAtlas(Gdx.files.internal("orbPacked.atlas"));

        setBounds(0,0,textureAtlas.getTextures().first().getWidth(),textureAtlas.getTextures().first().getHeight());

		loopback = true;
		sprite.setScale((float) SCALE);
		width = sprite.getWidth() * SCALE;
		debugTimer = 0;
		MoveToAction x = new MoveToAction();
		x.setPosition(10, -10);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		sprite.setCenter(getX(), getY());
		sprite.draw(batch);
	}

	@Override
	public void act(float delta) {

		float newX= getX()+deltaX;
		float newY=getY()+deltaY;
		
		setX(newX);
		setY(newY);
		super.act(delta);
	}

	public void setDeltaX(float movementX)
	{
		deltaX=movementX;
	}

	public void setDeltaY(float movementY)
	{
		deltaY=movementY;
	}
}