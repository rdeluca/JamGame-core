package com.deluca.etc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class helloWorld implements ApplicationListener {
    
	    
	    private Stage stage;
	    
	    @Override
	    public void create() {        
	        stage = new Stage();
	        
	       // stage.addActor(myActor);
	    }

	    @Override
	    public void dispose() {
	        stage.dispose();
	    }

	    @Override
	    public void render() {    
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        stage.draw();
	    }

	    @Override
	    public void resize(int width, int height) {
	    }

	    @Override
	    public void pause() {
	    }

	    @Override
	    public void resume() {
	    }}