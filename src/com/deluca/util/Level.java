package com.deluca.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Level {

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	MapLayer mapBaseLayer;
	MapLayer mapOverlayLayer;
	MapLayer mapCollisionLayer;
	final float CHARACTER_EDGE_BUFFER;

	
	SpriteBatch sb;
	Texture texture;
	public Sprite player;

	final float SCREENHEIGHT;
	final int MOVEMENT_AMT=4;
	
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

		pixelWidth=(int) (Utilities.getWindowWidth()/xCameraZoom);
		pixelHeight=(int) (Utilities.getWindowHeight()/yCameraZoom);
		
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
		SCREENHEIGHT= (levelHeight/yCameraZoom);
		CHARACTER_EDGE_BUFFER= ((3 * player.getHeight()) * player.getScaleY());
	}

	public void renderbot() {
		update();
		
		
		camera.update();
		sb.begin();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.renderTileLayer((TiledMapTileLayer) mapBaseLayer);
		player.draw(sb);
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

	public boolean checkKeyPress() 
	{
		float msx = Utilities.getMouseX();
		float msy = Utilities.getMouseY();
		Vector2 cellVector  = Utilities.getCell(msx, msy, this);
	
//		if(Gdx.input.isKeyPressed(Input.Keys.W))
		//
		{
			if(Gdx.input.isKeyPressed(Input.Keys.W))
			{
				if (playerYMapCoord + CHARACTER_EDGE_BUFFER < pixelHeight) 
	
				{
					playerYMapCoord += MOVEMENT_AMT;
					player.setY(player.getY() + MOVEMENT_AMT);
				} 
				else 
				{
					boolean arg2=maxYCoord<levelHeight;
					if ( arg2) 
					{
						
						camera.translate(0, MOVEMENT_AMT);
						player.setY(player.getY() + MOVEMENT_AMT);
						maxYCoord+=MOVEMENT_AMT;
					}
					else
					{
						if(player.getY()<levelHeight)
						{						
							player.setY(player.getY()+MOVEMENT_AMT);
							playerYMapCoord+=MOVEMENT_AMT;
						}
					}
				}
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.S))
			{
				if (playerYMapCoord - CHARACTER_EDGE_BUFFER > 0) 
				{
					playerYMapCoord -= MOVEMENT_AMT;
					player.setY(player.getY() - MOVEMENT_AMT);
				} 
				else 
				{
					boolean arg1=playerYMapCoord>0;
					boolean arg2=maxYCoord-SCREENHEIGHT>0;
					if (arg1&&arg2) 
					{
						camera.translate(0, -MOVEMENT_AMT);
						player.setY(player.getY() - MOVEMENT_AMT);
						maxYCoord-=MOVEMENT_AMT;
					}
					else
					{
	 					if(player.getY()>0)
						{
							player.setY(player.getY()-MOVEMENT_AMT);
							playerYMapCoord-=MOVEMENT_AMT;
						}
					}
				}
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.A))
			{
				player.setX(player.getX() - MOVEMENT_AMT);
			}		
			else if(Gdx.input.isKeyPressed(Input.Keys.D))
			{			
				player.setX(player.getX() + MOVEMENT_AMT);
			}
		}	
		return true;
	}
/*	
	public boolean keyup(int keycode) {
		float msx = QuickRefs.getMouseX();
		float msy = QuickRefs.getMouseY();
		Vector2 cellVector  = getCell(msx, msy);
		
		switch (keycode) 
		{

		case Input.Keys.W:
			if (playerYMapCoord + CHARACTER_EDGE_BUFFER < pixelHeight) 
			{
				playerYMapCoord += 32;
				player.setY(player.getY() + 32);
			} 
			else 
			{
				boolean arg2=maxYCoord<levelHeight;
				if ( arg2) 
				{
					
					camera.translate(0, 32);
				//	playerYMapCoord += 32;
					player.setY(player.getY() + 32);
					maxYCoord+=32;
				}
				else
				{
					if(player.getY()<levelHeight)
					{						
						player.setY(player.getY()+32);
						playerYMapCoord+=32;
					}
				}
			}
			break;
		case Input.Keys.S:
			if (playerYMapCoord - CHARACTER_EDGE_BUFFER > 0) 
			{
				playerYMapCoord -= 32;
				player.setY(player.getY() - 32);
			} 
			else 
			{
				boolean arg1=playerYMapCoord>0;
				boolean arg2=maxYCoord-SCREENHEIGHT>0;
				//playerYMapCoord < camera.viewportHeight && maxYCoord<levelHeight
				if (arg1&&arg2) 
				{
					camera.translate(0, -32);
//					playerYMapCoord=32;
					player.setY(player.getY() - 32);
					maxYCoord-=32;
				}
				else
				{
 					if(player.getY()>0)
					{
						player.setY(player.getY()-32);
						playerYMapCoord-=32;
					}
				}
			}
			break;
	
		case Input.Keys.A:
			player.setX(player.getX() - 32);
			break;
		case Input.Keys.D:
			player.setX(player.getX() + 32);
			break;
		}

		return true;
	}
*/
	

}
