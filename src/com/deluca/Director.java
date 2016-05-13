package com.deluca;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

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
import com.deluca.util.FixedSizeQueue;
import com.deluca.util.Level;

public class Director implements InputProcessor {

	public boolean DEBUG = true;

	float numx = 0;
	float numy = 0;

	// LibGDX lib items
	private Texture texture;
	private Stage stage;
	private Actor actor;
	private OrthographicCamera camera;
	private ThrowingOrb orb;
	private SpriteBatch batch;
	private final int maxNumMouseDragSamples = 5;
	private orbSide lastHit = null;
	private MyGdxGame game;

	FixedSizeQueue<Float> xLocationSamples = new FixedSizeQueue<Float>(
			maxNumMouseDragSamples);
	FixedSizeQueue<Float> yLocationSamples = new FixedSizeQueue<Float>(
			maxNumMouseDragSamples);

	FileWriter writer;

	private Level currentLevel;

	enum orbSide {
		left, right, top, bot
	}

	public Director(SpriteBatch spriteBatch, OrthographicCamera cam,
			MyGdxGame myGdxGame) throws IOException {

		writer = new FileWriter("C:/gameJam/JamGame-core/assets/Points.txt");

		game = myGdxGame;
		camera = cam;

		Level levelOne = new Level("testFactoryGrey.tmx");
		currentLevel = levelOne;
		batch = spriteBatch;
		texture = new Texture(Gdx.files.internal("map.png"));
		// texture = new
		// Texture(Gdx.files.internal("stock-photo-33531251.jpg"));

		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		/*
		 * background = new Sprite(texture); background.setOrigin(0,0);
		 * background
		 * .setPosition(-background.getWidth()/2,-background.getHeight()/2);
		 */
		Gdx.input.setInputProcessor((this));

		actor = new Actor();

		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if(DEBUG)
			System.out.println("w=" + w + ", h=" + h);

		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		orb = new ThrowingOrb(10, 10);

		stage.addActor(orb);
		stage.setKeyboardFocus(stage.getActors().first());

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return currentLevel.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		try {
			if (character == 'o')
				DEBUG = !DEBUG;
			if (character == 'p')
				game.pause();
			else if (character == 'm') {

				writer.append("Mx: " + Gdx.input.getX() + ' ');
				writer.append("My: " + Gdx.input.getY() + '\n');

			} else if (character == 'b') {
				writer.append("BadguyX: " + Gdx.input.getX() + ' ');
				writer.append("BadguyY: " + Gdx.input.getY() + '\n');
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Input.Buttons.LEFT == button) {

			// orb.getOriginX();
			// centerOrbOnCursor();
			if (!orb.getGrabbed())
				sendOrbTowardsCursor();
		} else {
			stage.addActor(new WalkCroc(screenX, Gdx.graphics.getHeight()
					- screenY));
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (Input.Buttons.LEFT == button) {
			if (orb.getGrabbed()) {
				if (xLocationSamples.size() > 0) {

					float x = getAverage(xLocationSamples);
					orb.setDeltaX(x);

					float y = -getAverage(yLocationSamples);
					// TODO: NOTE - NEGATIVE Y. y is opposite direction than x.
					// Change getAvg?
					orb.setDeltaY(y);
					if(DEBUG)
					{	
						System.out.println("dx" + x);
						System.out.println("dy" + y);
					}
					xLocationSamples = new FixedSizeQueue<Float>(
							maxNumMouseDragSamples);
					yLocationSamples = new FixedSizeQueue<Float>(
							maxNumMouseDragSamples);
				}
			}
			orb.setGrabbed(false);
		} else {
			AnimatedObject actor = (AnimatedObject) stage.getActors().get(
					stage.getActors().size - 1);

			// System.out.println("|a|"+ actor.getX() +" "+actor.getY());

		}
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

	private void sendOrbTowardsCursor() {
		float msX = Gdx.input.getX();
		float msY = Gdx.input.getY();
		Circle c = (Circle) orb.getShape();

		xLocationSamples = new FixedSizeQueue<Float>(maxNumMouseDragSamples);
		yLocationSamples = new FixedSizeQueue<Float>(maxNumMouseDragSamples);

		// Average where ball is and where cursor is to get speed to send
		// towards cursor
		xLocationSamples.add(c.x);
		xLocationSamples.add(msX);

		yLocationSamples.add((Gdx.graphics.getHeight() - c.y));
		yLocationSamples.add(msY);

		float deltX = getAverage(xLocationSamples);
		orb.setDeltaX(deltX);

		float deltY = getAverage(yLocationSamples);
		orb.setDeltaY(-deltY);
	}

	private void sendOrbTowardsPlayer() {

		float msX = currentLevel.player.getX();
		float msY = Gdx.graphics.getHeight() - currentLevel.player.getY();
		Circle c = (Circle) orb.getShape();

		xLocationSamples = new FixedSizeQueue<Float>(maxNumMouseDragSamples);
		yLocationSamples = new FixedSizeQueue<Float>(maxNumMouseDragSamples);

		// Average where ball is and where cursor is to get speed to send
		// towards cursor
		xLocationSamples.add(c.x);
		xLocationSamples.add(msX);

		yLocationSamples.add((Gdx.graphics.getHeight() - c.y));
		yLocationSamples.add(msY);

		float deltX = getAverage(xLocationSamples);
		orb.setDeltaX(deltX);

		float deltY = getAverage(yLocationSamples);
		orb.setDeltaY(-deltY);
	}

	private float getAverage(LinkedList<Float> queue) {
		int numVals = queue.size();
		float averageDistance = 0;
		float first = queue.poll();
		for (int i = 0; i < queue.size(); i++) {
			float second = queue.poll();
			averageDistance += second - first;
			first = second;
		}
		averageDistance = averageDistance / numVals;
		return averageDistance;
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
			lastHit = orbSide.top;
		} else if (rect.contains(bot)) {
			intersect = true;
			orb.bounceY();
			lastHit = orbSide.bot;
		} else if (rect.contains(left)) {
			intersect = true;
			orb.bounceX();
			lastHit = orbSide.left;
		} else if (rect.contains(right)) {
			intersect = true;
			orb.bounceX();
			lastHit = orbSide.right;
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

		return intersect;
	}

	public void render() {

		// Clear screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentLevel.renderbot();

		float msX = Gdx.input.getX();
		float msY = Gdx.input.getY();
		Circle c = (Circle) orb.getShape();

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			// if not grabbed
			if (!orb.getGrabbed()) {
				if (c.contains(msX, Gdx.graphics.getHeight() - msY)) {
					orb.setGrabbed(true);
				} else {
					sendOrbTowardsCursor();
				}
			} else {
				// if grabbed
				xLocationSamples.offer(msX);
				yLocationSamples.offer(msY);
			}
		}

		// update all "actors" on the stage before drawing.
		stage.act(Gdx.graphics.getDeltaTime());
		if (stage.getActors().size > 1) {
			// TODO: Make sure to hardcode orb as 0

			// For each actor check if the orb is colliding
			for (int i = 1; i < stage.getActors().size; i++) {
				// if it is
				if (checkCollisionOrb(orb, (AnimatedObject) stage.getActors()
						.get(i))) {
					((AnimatedObject) stage.getActors().get(i)).collide(orb);
				} else {

				}
			}
		}

		// Start the sprite batch processor
		batch.begin();

		// Set camera to draw
		batch.setProjectionMatrix(camera.combined);

		// Draw sprite
		// background.draw(batch);

		batch.end();
		stage.draw();
		currentLevel.renderOverlay();

	}

	private void bounce() {
		orb.bounce();
	}

	public void dispose() throws IOException {
		writer.close();
	}

}