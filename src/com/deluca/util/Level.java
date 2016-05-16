package com.deluca.util;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deluca.objects.Player;

public class Level {

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	MapLayer mapBaseLayer;
	MapLayer mapOverlayLayer;
	MapLayer mapCollisionLayer;
	public final float CHARACTER_EDGE_BUFFER;

	SpriteBatch sb;
	Texture texture;

	public final float SCREENHEIGHT;
	public final int MOVEMENT_AMT = 1;

	public int maxXCoord = -1;
	public int maxYCoord = -1;
	public int levelHeight = -1;
	public int levelWidth = -1;
	public float xCameraZoom = 1;
	public float yCameraZoom = 2;
	public int pixelWidth = -1;
	public int pixelHeight = -1;

	ArrayList<Actor> actorList = new ArrayList<Actor>();

	public Level(String levelName, OrthographicCamera cam) {

		// Load map
		tiledMap = new TmxMapLoader().load("level/" + levelName);

		MapProperties props = tiledMap.getProperties();
		levelWidth = (Integer) props.get("width")
				* (Integer) props.get("tilewidth");
		levelHeight = (Integer) props.get("height")
				* (Integer) props.get("tileheight");

		camera =  cam;
		camera.setToOrtho(false, levelWidth / xCameraZoom, levelHeight
				/ yCameraZoom);
		camera.update();

		pixelWidth = (int) (Utilities.getWindowWidth() / xCameraZoom);
		pixelHeight = (int) (Utilities.getWindowHeight() / yCameraZoom);

		sb = new SpriteBatch();

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, sb);

		maxXCoord = levelWidth;
		maxYCoord = (int) (levelHeight / yCameraZoom);
		//
		mapBaseLayer = tiledMap.getLayers().get(0);
		mapOverlayLayer = tiledMap.getLayers().get(1);
		mapCollisionLayer = tiledMap.getLayers().get(2);
		mapCollisionLayer.getObjects();
		SCREENHEIGHT = (levelHeight / yCameraZoom);

		setupActors();
		CHARACTER_EDGE_BUFFER = ((2 * actorList.get(0).getHeight()));

	}

	private void setupActors() {
		Player player = new Player();
		actorList.add(player);
	}

	public void renderbot() {
		update();

		camera.update();
		sb.begin();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.renderTileLayer((TiledMapTileLayer) mapBaseLayer);
		sb.end();
	}

	private void update() {
		checkKeyPress();
	}

	public void renderOverlay() {
		sb.begin();

		tiledMapRenderer.renderTileLayer((TiledMapTileLayer) mapOverlayLayer);
		sb.end();
	}

	public boolean checkKeyPress() {
		float msx = Utilities.getMouseX();
		float msy = Utilities.getMouseY();
		Vector2 cellVector = Utilities.getCell(msx, msy, this);

		return true;
	}

	/*
	  public boolean keyup(int keycode) { float msx = QuickRefs.getMouseX();
	 * float msy = QuickRefs.getMouseY(); Vector2 cellVector = getCell(msx,
	 * msy);
	 * 
	 * switch (keycode) {
	 * 
	 * case Input.Keys.W: if (playerYMapCoord + CHARACTER_EDGE_BUFFER <
	 * pixelHeight) { playerYMapCoord += 32; player.setY(player.getY() + 32); }
	 * else { boolean arg2=maxYCoord<levelHeight; if ( arg2) {
	 * 
	 * camera.translate(0, 32); 
	 * // playerYMapCoord += 32;
	 * player.setY(player.getY() + 32); maxYCoord+=32; } else {
	 * if(player.getY()<levelHeight) { player.setY(player.getY()+32);
	 * playerYMapCoord+=32; } } } break; case Input.Keys.S: if (playerYMapCoord
	 * - CHARACTER_EDGE_BUFFER > 0) { playerYMapCoord -= 32;
	 * player.setY(player.getY() - 32); } else { boolean arg1=playerYMapCoord>0;
	 * boolean arg2=maxYCoord-SCREENHEIGHT>0; //playerYMapCoord <
	 * camera.viewportHeight && maxYCoord<levelHeight if (arg1&&arg2) {
	 * camera.translate(0, -32); // playerYMapCoord=32;
	 * player.setY(player.getY() - 32); maxYCoord-=32; } else {
	 * if(player.getY()>0) { player.setY(player.getY()-32); playerYMapCoord-=32;
	 * } } } break;
	 * 
	 * case Input.Keys.A: player.setX(player.getX() - 32); break; case
	 * Input.Keys.D: player.setX(player.getX() + 32); break; }
	 * 
	 * return true; }
	 */

	public ArrayList<Actor> getActors() {
		return actorList;
	}

}
