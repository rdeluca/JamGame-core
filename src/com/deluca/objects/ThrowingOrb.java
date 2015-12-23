package com.deluca.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;



public class ThrowingOrb extends AnimatedObject
{
	
	public ThrowingOrb() {
		super("orbPacked.atlas", 16, 3);
		loopback=true;
	}

	@Override
	public void draw(Batch batch) //TODO Take this out and put it in a orb file
	{
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            x=Gdx.input.getX();

        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
        	y=Gdx.graphics.getHeight()-Gdx.input.getY();
        }
        
    	sprite.setCenter(x, y);
    	sprite.setScale((float) 0.4);
        sprite.draw(batch);
	}
}