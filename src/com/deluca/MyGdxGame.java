package com.deluca;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deluca.objects.AnimatedObject;
import com.deluca.objects.ThrowingOrb;
import com.deluca.objects.WalkCroc;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{

		//LibGDX lib items
	
		
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite background;
	
	private Polygon collisionShape;

        Stage stage;
        Actor actor;
        ThrowingOrb orb;
        WalkCroc croc;
        private final int maxNumMouseDragSamples = 3;
        FixedSizeQueue<Integer> xLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
        FixedSizeQueue<Integer> yLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
//        FixedSizeQueue<Vector2> locationSamples = new FiedSizeQueue<Vector2>(maxNumMouseDragSamples);
        
        float clickX=0;
        float clickY=0;
        int numLocs=0;
        
        boolean grabbed=false;
        boolean leftdown=false;
        
        
	    @Override
	    public void create() {        
	    	
	    	//Create camera (this is what you're looking at within the viewport)
	    	camera = new OrthographicCamera(400, 400);
	    	
	    	batch = new SpriteBatch();
    		texture = new Texture(Gdx.files.internal("background.png"));
//    		texture = new Texture(Gdx.files.internal("stock-photo-33531251.jpg"));

    		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    	background = new Sprite(texture);
	    		background.setOrigin(0,0);
	    		background.setPosition(-background.getWidth()/2,-background.getHeight()/2);
    	  
    		Gdx.input.setInputProcessor((this));
	    	
	    	
	    	//FileHandle i = Gdx.files.internal("background.png");
	    	

	    	actor = new Actor();
	    	
	    	
	        int w = Gdx.graphics.getWidth();
	    	int h = Gdx.graphics.getHeight();
	    	System.out.println("w="+w+", h="+h);
	    	
	    	ScreenViewport viewport = new ScreenViewport();
	    	stage = new Stage(viewport);
	    	orb= new ThrowingOrb(10,10);
	    	croc= new WalkCroc(40, 40);
	    	
	    	stage.addActor(orb);
	    //	stage.addActor(croc);
	  //  	Gdx.input.setInputProcessor(stage);
	    	stage.setKeyboardFocus(stage.getActors().first());
	    
	    }

	    @Override
	    public void dispose() {
	       batch.dispose();
	       orb.ballSound.dispose();
	       texture.dispose();
	    }


	    @Override
	    public void render() { 
	    	
	    	//Clear screen
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    	float msX=Gdx.input.getX();
			float msY=Gdx.input.getY();
			Circle c = (Circle) orb.getShape();
			
			System.out.println("MOUSE:" + msX + " BALLX:" + c.x);
			System.out.println("MOUSEY:" + (Gdx.graphics.getHeight()-msY) + " BALLy:" + c.y);
			System.out.println(c.contains(msX, Gdx.graphics.getHeight()-msY));
			
	    	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
	    	{
	    		
				
				if(c.contains(msX, Gdx.graphics.getHeight()-msY)||grabbed)
				{
					centerOrbOnCursor();
				}
				else
				{

					sendOrbTowardsCursor();
				}
	    	}
	    	
	    	
	    	//update all "actors" on the stage before drawing.
	    	stage.act(Gdx.graphics.getDeltaTime());
	    	if(stage.getActors().size>1)
	    	{
	    		//TODO: Make sure to hardcode orb as 0
	    		
	    		//For each actor check if the orb is colliding
	    		for(int i=1; i<stage.getActors().size; i++)
    			{
	    				//if it is
	    				if(checkCollisionOrb(orb, (AnimatedObject)stage.getActors().get(i) ))
	    				{
	    					((AnimatedObject)stage.getActors().get(i)).collide(orb);
	    				}
    			}
	    	}

	    	
	    	//Start the sprite batch processor
	        batch.begin();
	        
	        //Set camera to draw
	        batch.setProjectionMatrix(camera.combined);

	        //Draw sprite
	        background.draw(batch);
	        batch.end();
	    	
	    	/*
	    	 * Camera movement
	    	 */
	    	if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
				camera.translate(-5f,0);				
	    	if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    		camera.translate(5f,0);				
	    	if(Gdx.input.isKeyPressed(Input.Keys.UP))
	    		camera.translate(0,5f);				
	    	if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    		camera.translate(0,-5f);							
			camera.update();

	        stage.draw();
	    }

		
		private void bounce() {
			// TODO Auto-generated method stub
			orb.bounce();

		}

		private boolean checkCollision(AnimatedObject a, AnimatedObject b) {

			boolean intersect=false;
			 collisionShape = new Polygon();
			if(Intersector.intersectPolygons((Polygon)a.getShape(), (Polygon)b.getShape(),collisionShape))
				intersect=true;
			
			return intersect;
		}
	    
		private boolean checkCollisionOrb(ThrowingOrb orb, AnimatedObject obj) 
		{
			boolean intersect=false;
			Circle c = (Circle) orb.getShape();
			Rectangle rect = (Rectangle) obj.getShape();
			float r = c.radius;
			
			//If the sides of the circle are inside the rectangle or the circle contains the corners of the rectangle
			//then a collision is occurring
			
			//Sides of the circle
			Vector2 top, bot, left, right;			
			top= new Vector2(c.x,c.y+r);
			bot= new Vector2(c.x,c.y-r);
			left= new Vector2(c.x-r, c.y);
			right= new Vector2(c.x+r,c.y);
			
			//corners of the rectangle
			Vector2 botLeft, topLeft, botRight, topRight;
			botLeft= new Vector2(rect.x,rect.y);
			botRight= new Vector2(rect.x+rect.width,rect.y);
			topLeft= new Vector2(rect.x,rect.y+rect.height);
			topRight= new Vector2(rect.x+rect.width,rect.y+rect.height);
			
			//If the sides of the circle are inside the rectangle 
			if(rect.contains(top)||rect.contains(bot))
			{
				intersect=true;
				orb.bounceY();

			}if(rect.contains(left)||rect.contains(right))
			{
				intersect=true;
				orb.bounceX();

			}
			//and if it isn't already doing one of the above - or the circle contains the corners of the rectangle
			if(!intersect&&(c.contains(botLeft)||c.contains(botRight)||c.contains(topLeft)||c.contains(topRight)))
			{
				intersect=true;
				orb.bounce();
			}
			
			return intersect;
		}

		
		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) 
		{
			if(Input.Buttons.LEFT==button)
			{

			//	orb.getOriginX();
			//	centerOrbOnCursor();
				if(grabbed)
					centerOrbOnCursor();
				else
					sendOrbTowardsCursor();
			}
			else
			{
				stage.addActor(new WalkCroc(screenX,Gdx.graphics.getHeight()-screenY));
			}
			return true;
		}



		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {

			if(Input.Buttons.LEFT==button)
			{
				/*
				orb.setDeltaX(getAverage(xLocationSamples));

				//TODO: NOTE - NEGATIVE Y. y is opposite direction than x. Change getAvg?
				orb.setDeltaY(-getAverage(yLocationSamples));
								
				leftdown=false;
				xLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
				yLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
				 */
				grabbed=false;
			}
			else
			{				
				AnimatedObject actor = (AnimatedObject) stage.getActors().get(stage.getActors().size-1);
				
				//System.out.println("|a|"+ actor.getX() +" "+actor.getY());
				
			}
			return true;
		}

		private float getAverage(LinkedList<Integer> queue) {
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
			if(leftdown)
			{
	
			}
			else
			{
				
			}
			return true;
		}

		/**
		 * Center orb on mouse 
		 * Add location to sample list
		 */
		private void centerOrbOnCursor()
		{
			grabbed=true;
			float msX=Gdx.input.getX();
			float msY=Gdx.input.getY();
			float X = Gdx.input.getX();
			float Y = Gdx.graphics.getHeight() - Gdx.input.getY();
	
			orb.setX(X - (orb.getImageWidth() / 2));
			orb.setY(Y - (orb.getImageWidth() / 2));
			orb.setDeltaX(0);
			orb.setDeltaY(0);

			//Give queue the new touched location
			xLocationSamples.offer(Gdx.input.getX());		
			yLocationSamples.offer(Gdx.input.getY());
		}

		private void sendOrbTowardsCursor()
		{
			float msX=Gdx.input.getX();
			float msY=Gdx.input.getY();
			Circle c = (Circle) orb.getShape();
			
			
			//TODO: FIX Y SAMPLING?
			xLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);
			yLocationSamples = new FixedSizeQueue<Integer>(maxNumMouseDragSamples);

			xLocationSamples.add((int) c.x);
			xLocationSamples.add((int) msX);
			
			yLocationSamples.add((int) (Gdx.graphics.getHeight()-c.y));
			yLocationSamples.add((int)msY);			
			
			orb.setDeltaX(getAverage(xLocationSamples));

			int deltY=(int) getAverage(yLocationSamples);
		
			orb.setDeltaY(deltY);
			
			System.out.println("deltY="+deltY);
			orb.setDeltaY(-deltY);

		}
		
		
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			
		//	System.out.println("~"+Gdx.input.getX());
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
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
