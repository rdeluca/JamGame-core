package com.deluca.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level {
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	MapLayer mapBaseLayer;
	MapLayer mapOverlayLayer;
	MapLayer mapCollisionLayer;

	SpriteBatch sb;
	Texture texture;
	public Sprite player;

	final int SCREENHEIGHT;
	
	int playerXMapCoord = 0;
	int playerYMapCoord = 0;
	int maxXCoord= -1;
	int maxYCoord = -1;
	int levelHeight = -1;
	int levelWidth = -1;
	float xCameraZoom = 1;
	float yCameraZoom = 2;
	int pixelWidth=-1;
	int pixelHeight=-1;
	
	public Level(String levelName) {

		tiledMap = new TmxMapLoader().load("level/" + levelName);
		MapProperties props = tiledMap.getProperties();
		levelWidth = (Integer) props.get("width")
				* (Integer) props.get("tilewidth");
		levelHeight = (Integer) props.get("height")
				* (Integer) props.get("tileheight");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, levelWidth / xCameraZoom, levelHeight / yCameraZoom);
		camera.update();

		pixelWidth=(int) (Gdx.graphics.getWidth()/xCameraZoom);
		pixelHeight=(int) (Gdx.graphics.getHeight()/yCameraZoom);
		
		// System.out.println(props.get());

		sb = new SpriteBatch();

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, sb);

		// TODO: UN-HARDCODE
		texture = new Texture(Gdx.files.internal("RookieBobby.png"));
		player = new Sprite(texture);
		player.setX(0);
		player.setY(0);
		playerXMapCoord = (int) player.getX();
		playerYMapCoord = (int) player.getY();
		maxXCoord = levelWidth;
		maxYCoord= (int) (levelHeight/yCameraZoom);
		//
		mapBaseLayer = tiledMap.getLayers().get(0);
		mapOverlayLayer = tiledMap.getLayers().get(1);
		mapCollisionLayer = tiledMap.getLayers().get(2);
		mapCollisionLayer.getObjects();
		SCREENHEIGHT=(int) (levelHeight/yCameraZoom);
	}

	public void renderbot() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		sb.begin();

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.renderTileLayer((TiledMapTileLayer) mapBaseLayer);
		player.draw(sb);
		sb.end();
	}

	public void renderOverlay() {
		sb.begin();

		tiledMapRenderer.renderTileLayer((TiledMapTileLayer) mapOverlayLayer);
		sb.end();
	}

	public boolean keyUp(int keycode) {
		MapProperties props = tiledMap.getProperties();

		// if()
		{
			// CELLS ARE: 20x20
			// SCREEN IS: 1280x720
			// "cell size": 64x75

			// 75 height of cell

			// Width is 20 tiles.
			// Screen width is 1280
			// viewportWidth=640.0
			// RATIO OF WIDTH TO VIEWPORT IS: 2:1
			// 64 pixel wide tiles
			// 64 px/tile = 1280px/20 tiles
			// pxPerTileWide= pixelWidth/numTiles
			// 1.6px= x/y
			//

			int numTilesWide = (int) ((Gdx.graphics.getWidth() / camera.viewportWidth));
			int screenTileWidth = Gdx.graphics.getWidth() / numTilesWide;
			int numTilesHigh = (int) ((Integer) (tiledMap.getProperties()
					.get("height")) / (Gdx.graphics.getHeight() / camera.viewportHeight));
			int screenTileHigh = Gdx.graphics.getHeight() / numTilesHigh;

			float msx = Gdx.input.getX();
			float msy = (Gdx.graphics.getHeight() - Gdx.input.getY());

			int cellX = (int) (msx / screenTileWidth);
			int cellY = (int) (msy / screenTileHigh);
			/*
			 * System.out.println(props.get("tilewidth"));
			 * System.out.println(props.get("tileheight"));
			 * System.out.println("msx:"+msy+" msy:"+msy);
			 * System.out.println("cellX:"+cellX+" cellY:"+cellY);
			 */

			Cell cell = ((TiledMapTileLayer) mapOverlayLayer).getCell(cellX,
					cellY);
			if (cell != null) {
				TiledMapTile i = cell.getTile();
				i.getId();
			}
			//System.out.println(cell != null);
		}

		// sprite.getY()+((3*sprite.getHeight())*sprite.getScaleY())<Gdx.graphics.getHeight()

		float characterEdgeBuffer = ((3 * player.getHeight()) * player
				.getScaleY());

		switch (keycode) 
		{

		case Input.Keys.W:
			if (playerYMapCoord + characterEdgeBuffer < pixelHeight) 
			{
				playerYMapCoord += 32;
				player.setY(player.getY() + 32);
				System.out.println("UP");
			} 
			else 
			{
				if (playerYMapCoord < camera.viewportHeight && maxYCoord<levelHeight) 
				{
					camera.translate(0, 32);
					maxYCoord+=32;
					playerYMapCoord-=32;
					System.out.println("UPnMAPUP");
				}
				else
				{
					if(player.getY()<levelHeight)
					{						
						player.setY(player.getY()+32);
						playerYMapCoord+=32;
						System.out.println("TopOfLevel");
					}
				}
			}
			break;
		case Input.Keys.S:
			if (playerYMapCoord - characterEdgeBuffer > 0) 
			{
				playerYMapCoord -= 32;
				player.setY(player.getY() - 32);
				System.out.println("DOWN");
			} 
			else 
			{
				boolean mapCoord=playerYMapCoord>0;
				boolean heightbool=maxYCoord-SCREENHEIGHT>0;
				//playerYMapCoord < camera.viewportHeight && maxYCoord<levelHeight
				if (mapCoord&&heightbool) 
				{
					camera.translate(0, -32);
					playerYMapCoord+=32;
					maxYCoord-=32;
					System.out.println("DOWNandMapDown");
				}
				else
				{
					if(player.getY()>0)
					{
						player.setY(player.getY()-32);
						System.out.println("bottomOfMap");
						playerYMapCoord-=32;
					}
				}
			}
			break;
			
		/*	if (playerYMapCoord > 0) {
				player.setY(player.getY() - 32);
				playerYMapCoord -= 32;
			} else {
				if (playerYMapCoord == 0) {
				} else if (playerYMapCoord > 0) {
					player.setY(player.getY() - 32);
				} else {
					camera.translate(0, -32);
				}
			}

			break;*/
		case Input.Keys.A:
			player.setX(player.getX() - 32);
			break;
		case Input.Keys.D:
			player.setX(player.getX() + 32);
			break;
		case Input.Keys.NUM_1:
			tiledMap.getLayers().get(0)
					.setVisible(!tiledMap.getLayers().get(0).isVisible());
			break;
		case Input.Keys.NUM_2:
			tiledMap.getLayers().get(1)
					.setVisible(!tiledMap.getLayers().get(1).isVisible());
			break;
		case Input.Keys.H:
			System.out.println("X:" + player.getX() + " Y:" + player.getY());
			break;
		case Input.Keys.J:
			System.out.println(Gdx.input.getX() + " "
					+ (Gdx.graphics.getHeight() - Gdx.input.getY()));
		case Input.Keys.Z:
			System.out.println("playerXMapCoord:" + playerXMapCoord + " playerYMapCoord:" + playerYMapCoord);
		}

		return false;
	}

}
