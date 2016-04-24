package com.deluca;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deluca.objects.ThrowingOrb;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor{

		//LibGDX lib items
	
		
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

        Stage stage;
        Actor actor;
        ThrowingOrb orb;
        private final int maxNumMouseDragSamples = 5;
        FixedSizeQueue<Integer> xLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
        FixedSizeQueue<Integer> yLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
        float clickX=0;
        float clickY=0;
        int numLocs=0;
        
        
	    @Override
	    public void create() {        
	    	
	    	//Create camera (this is what you're looking at within the viewport)
	    	camera = new OrthographicCamera(400, 400);
	    	
	    	batch = new SpriteBatch();
	    		texture = new Texture(Gdx.files.internal("stock-photo-33531251.jpg"));
	    		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    	sprite = new Sprite(texture);
	    		sprite.setOrigin(0,0);
	    		sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);
    	  
    		Gdx.input.setInputProcessor((this));
	    	
	    	
	    	FileHandle i = Gdx.files.internal("background.png");
	    	

	    	actor = new Actor();
	    	
	    	
	        int w = Gdx.graphics.getWidth();
	    	int h = Gdx.graphics.getHeight();
	    	System.out.println("w="+w+", h="+h);
	    	
	    	ScreenViewport viewport = new ScreenViewport();
	    	stage = new Stage(viewport);
	    	orb= new ThrowingOrb();
	    	
	    	stage.addActor(orb);

	  //  	Gdx.input.setInputProcessor(stage);
	    	stage.setKeyboardFocus(stage.getActors().first());
	    
	    }

	    @Override
	    public void dispose() {
	       batch.dispose();
	       texture.dispose();
	    }


	    @Override
	    public void render() { 
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	        

	        batch.setProjectionMatrix(camera.combined);
	        batch.begin();
	        sprite.draw(batch);
	        batch.end();
	        
	    	stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	    }

	    
		
		public void changeDisplay(int width, int height, boolean fullscreen)
		{
			Gdx.graphics.setDisplayMode(width,height,fullscreen);
		}
		
		public void changeCamera(int width, int height)
		{
			//OrthographicCamera camera = new OrthographicCamera(width, height);
		}



		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) 
		{
			orb.act(Gdx.graphics.getDeltaTime());
			int mx=Gdx.input.getX();
			int my=Gdx.input.getY();

			orb.getOriginX();

			centerOrbOnCursor();	
			
			return false;
		}



		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {

			orb.setDeltaX(getAverage(xLocationSamples));
			//TODO: NOTE - NEGATIVE Y, y is opposite direction than x. Change getAvg?
			orb.setDeltaY(-getAverage(yLocationSamples));
			
			return true;
		}

		


		private float getAverage(FixedSizeQueue<Integer> queue) {
			int numVals=queue.size();
			float averageDistance=0;
			float first=queue.poll();
			for(int i=0; i<queue.size(); i++)
			{
				float second = queue.poll();
				averageDistance+=second-first;
				first=second;
			}
			averageDistance=averageDistance/numVals;
			return averageDistance;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			
			//GetX points to bottom-left of a sprite
			orb.setX(Gdx.input.getX());
			
			//getY is the opposite for GDX.input as it is for sprites for some reason
			//0 is bottom for sprites and top for mouse.
			orb.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
			
			centerOrbOnCursor();
			
			return true;
		}

		/**
		 * Center orb on mouse 
		 * Add location to sample list
		 */
		private void centerOrbOnCursor()
		{
			float msX=Gdx.input.getX();
			float msY=Gdx.input.getY();
	float X =Gdx.input.getX();
	float Y = Gdx.graphics.getHeight() - Gdx.input.getY();
	System.out.println("MOUSE:"+msX + " BALLX:" +X);
	System.out.println("MOUSEY:"+msY + " BALLy:" +Y);

	orb.setX(X);
	orb.setY(Y);
	orb.setDeltaX(0);
	orb.setDeltaY(0);

			//Give queue the new touched location
			xLocationSamples.offer(Gdx.input.getX());		
			yLocationSamples.offer(Gdx.input.getY());
		}

		
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			
		//	System.out.println("~"+Gdx.input.getX());
			// TODO Auto-generated method stub
			return false;
		}



		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

}

/**
 * Fixed size Queue, that dumps oldest when it 
 *  gets a new and size limit is reached 
 *   
 *   
 *   
 * @author Rich
 *
 * @param <E> - element
 */
class FixedSizeQueue<E> extends LinkedList<E>{
	
	  private final int maxSize;
	  private final LinkedList<E> list = new LinkedList<E>();

	  public FixedSizeQueue(int maxSize) {
		  //Compacted if statement
		  //if maxSize<0 (? represents then) trueresult (: represents else) falseresult
	    this.maxSize = maxSize < 0 ? 0 : maxSize;
	  }

	  @Override
	public boolean offer(E e) {
		  if(super.size()==maxSize)
			  super.poll();
		  return super.offer(e);
	}
	  

	  
}
