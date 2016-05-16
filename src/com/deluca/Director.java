package com.deluca;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.deluca.objects.Player;
import com.deluca.objects.ThrowingOrb;
import com.deluca.objects.WalkCroc;
import com.deluca.util.Level;
import com.deluca.util.Utilities;

public class Director implements InputProcessor {

	public boolean DEBUG = true;

	float numx = 0;
	float numy = 0;

	// LibGDX lib items
	private Stage stage;
	private OrthographicCamera camera;
	
	private ThrowingOrb orb;
	private Player player;
	
	private SpriteBatch batch;
	private MyGdxGame game;


	private Level currentLevel;

	enum orbSide {
		left, right, top, bot
	}

	public Director(SpriteBatch spriteBatch, OrthographicCamera cam,
			MyGdxGame myGdxGame) throws IOException {

		game = myGdxGame;
		camera = cam;

		Level levelOne = new Level("testFactoryGrey.tmx", cam);
		currentLevel = levelOne;
		
		
		batch = spriteBatch;
		Gdx.input.setInputProcessor((this));

		if (DEBUG)
			System.out.println("w=" + Utilities.getWindowWidth() + ", h="
					+ Utilities.getWindowHeight());

		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		orb = new ThrowingOrb(10, 10);

		stage.addActor(orb);
		stage.setKeyboardFocus(stage.getActors().first());
		for(Actor actor: currentLevel.getActors()){
			if(actor instanceof Player)
				player=(Player) actor;
			stage.addActor(actor);
		}
		
	}

	public void render() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
System.out.println(		Utilities.getMouseX() +" " +Utilities.getMouseY());

		doUpdates();
		
		//RENDERING
		{

			// Clear screen
			currentLevel.renderbot();

			// Start the sprite batch processor
			batch.begin();
	
			// Set camera to draw
			batch.setProjectionMatrix(camera.combined);
			batch.end();
			stage.draw();
			batch.begin();
			currentLevel.renderOverlay();
			batch.end();
		}
	}

	private void doUpdates() {
		
			//TODO CHANGE PIXELHEIGHT and movement_amt
			float MOVEMENT_AMT = currentLevel.MOVEMENT_AMT;
			float TILESIZE=32;
			
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			float playY=player.getY();
			float buff =currentLevel.CHARACTER_EDGE_BUFFER ;
			if ( playY+ buff< currentLevel.pixelHeight) 
			{
				player.setYVelocity(MOVEMENT_AMT);
			} 
			else 
			{
				if ( currentLevel.maxYCoord < currentLevel.levelHeight) 
				{
					translate(0, TILESIZE);
					player.setYVelocity(MOVEMENT_AMT);
					currentLevel.maxYCoord +=TILESIZE;
					
				}
				else
				{
					if(player.getY()<currentLevel.levelHeight)
					{						
						player.setYVelocity(MOVEMENT_AMT);
					}
				}
			}
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			if (player.getY() - currentLevel.CHARACTER_EDGE_BUFFER > 0) 
			{
				player.setYVelocity(-MOVEMENT_AMT);
			} 
			else 
			{
				boolean arg1=player.getY()>0;
				boolean arg2=currentLevel.maxYCoord-currentLevel.SCREENHEIGHT>0;
				if (arg1&&arg2) 
				{
					translate(0, -TILESIZE);
					player.setYVelocity(-MOVEMENT_AMT);
					currentLevel.maxYCoord-=TILESIZE;
				}
				else
				{
 					if(player.getY()>0)
					{
						player.setYVelocity(-MOVEMENT_AMT);
					}
				}
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			player.setXVelocity( - MOVEMENT_AMT);
		}		
		else if(Gdx.input.isKeyPressed(Input.Keys.D))
		{			
			player.setXVelocity( MOVEMENT_AMT);
		}

		
		
		camera.update();
		
		
		
/////////////////		
		
		
		// update all "actors" on the stage before drawing.

		stage.act(Gdx.graphics.getDeltaTime());

		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Input.Buttons.RIGHT == button) {
			stage.addActor(new WalkCroc(Utilities.getMouseX(), Utilities.getMouseY()));
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		orb.touchUp(button);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}




	private boolean checkCollision(AnimatedObject a, AnimatedObject b) {

		boolean intersect = false;
		Polygon collisionShape = new Polygon();
		if (Intersector.intersectPolygons((Polygon) a.getShape(),
				(Polygon) b.getShape(), collisionShape))
			intersect = true;

		return intersect;
	}

	private boolean checkCollisionOrb(ThrowingOrb orb, AnimatedObject obj) {

		boolean intersect = false;
		Circle c = (Circle) orb.getShape();
		Rectangle rect = (Rectangle) obj.getShape();
		float r = c.radius;

		// If the sides of the circle are inside the rectangle or the circle
		// contains the corners of the rectangle
		// then a collision is occurring
		// This assumes that all the dimensions of the rectangle are larger than
		// the circle's

		// Sides of the circle
		Vector2 top, bot, left, right;
		top = new Vector2(c.x, c.y + r);
		bot = new Vector2(c.x, c.y - r);
		left = new Vector2(c.x - r, c.y);
		right = new Vector2(c.x + r, c.y);

		// corners of the rectangle
		Vector2 botLeft, topLeft, botRight, topRight;
		botLeft = new Vector2(rect.x, rect.y);
		botRight = new Vector2(rect.x + rect.width, rect.y);
		topLeft = new Vector2(rect.x, rect.y + rect.height);
		topRight = new Vector2(rect.x + rect.width, rect.y + rect.height);

		// If the sides of the circle are inside the rectangle
		if (rect.contains(top)) {
			intersect = true;
			orb.bounceY();
		} else if (rect.contains(bot)) {
			intersect = true;
			orb.bounceY();
		} else if (rect.contains(left)) {
			intersect = true;
			orb.bounceX();
		} else if (rect.contains(right)) {
			intersect = true;
			orb.bounceX();
		} else if (rect.contains(new Vector2(c.x, c.y))) {
			intersect = true;
			orb.bounce();
		}

		// and if it isn't already doing one of the above - or the circle
		// contains the corners of the rectangle
		if (!intersect
				&& (c.contains(botLeft) || c.contains(botRight)
						|| c.contains(topLeft) || c.contains(topRight))) {
			intersect = true;
			orb.bounce();
		}

		if (intersect)
			System.out.println(intersect);

		return intersect;
	}

	private void bounce() {
		orb.bounce();
	}

	public void dispose() {
		//
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
	
	public void translate(float x, float y)
	{
		int i=0;
		camera.translate(x, y);
		for(Actor actors : stage.getActors())
		{
			if(actors instanceof Player)
			{
				System.out.println("B: "+actors.getX()+" "+actors.getY() );
				i++;
			}
			if(actors instanceof AnimatedObject)
			{
				actors.setPosition(actors.getX()-x, actors.getY()-y);
			}
			if(actors instanceof Player)
				System.out.println("af: "+actors.getX()+" "+actors.getY() );

		}
		
		System.out.println(i);
	}

}