package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;



public class ThrowingOrb extends AnimatedObject
{
	float deltaX;
	float deltaY;
	final static float THROWSPEED=(float) .5;
	final static float SCALE=(float) 0.4;
	float debugTimer;
	float width;
	
	public ThrowingOrb() 
	{
		super("orbPacked.atlas", 16, 3,0,0, SCALE);
		loopback=true;
    	sprite.setScale((float) SCALE);
    	
    	width = sprite.getWidth()*SCALE;
    	debugTimer=0;
	}

	@Override
	public void draw(Batch batch)
	{

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            setX(Gdx.input.getX());
            deltaX=(float) (Gdx.input.getDeltaX()*THROWSPEED);

            setY(Gdx.graphics.getHeight()-Gdx.input.getY());
            deltaY=(float) (-1*Gdx.input.getDeltaY()*THROWSPEED);

           // sprite.setCenter(x, y);
        }
        else
        {
        //	x=Gdx.input.
       // 	y=y+deltaY;	
        }
        
        sprite.setCenter(getX(), getY());
        sprite.draw(batch);
	}
}