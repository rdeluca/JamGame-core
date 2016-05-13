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

	// here
	int mapx = 0;
	int mapy = 0;

	int levelHeight = -1;
	int levelWidth = -1;

	

	public Level(String levelName) {

		tiledMap = new TmxMapLoader().load("level/"+levelName);
		MapProperties props = tiledMap.getProperties();
		levelWidth = (Integer) props.get("width")
				* (Integer) props.get("tilewidth");
		levelHeight = (Integer) props.get("height")
				* (Integer) props.get("tileheight");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, levelWidth, levelHeight / 2);
		camera.update();

		// System.out.println(props.get());

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		
		//TODO: UN-HARDCODE
		sb = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("RookieBobby.png"));
		player = new Sprite(texture);
		player.setX(96);
		player.setY(224);
		
		//
		mapBaseLayer = tiledMap.getLayers().get(0);
		mapOverlayLayer = tiledMap.getLayers().get(1);
		mapCollisionLayer=tiledMap.getLayers().get(2);


	}

	public void renderbot() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[1]);
		sb.begin();
		player.draw(sb);
		sb.end();
	}

	public void renderOverlay()
	{
		tiledMap.
		tiledMapRenderer.render(new int[2]);		
	}

	public boolean keyUp(int keycode) {

		//sprite.getY()+((3*sprite.getHeight())*sprite.getScaleY())<Gdx.graphics.getHeight()

		float characterEdgeBuffer=((3*player.getHeight())*player.getScaleY());
		
		switch (keycode)
		{
		case Input.Keys.W:
			if(player.getY()+characterEdgeBuffer<Gdx.graphics.getHeight())
			{
				player.setY(player.getY() + 32);
			}
			else
			{
				if (mapy < camera.viewportHeight) {
					camera.translate(0, 32);
					mapy += 32;
				}
				else
				{
					player.setY(player.getY() + 32);
				}
			}
			
			break;
		case Input.Keys.S:
			if(player.getY()-characterEdgeBuffer>0)
			{
				player.setY(player.getY() - 32);
			}
			else
			{
				if (mapy >0) {
					camera.translate(0, -32);
					mapy -= 32;
				}
				else
				{
					player.setY(player.getY() - 32);
				}
			}
			
			break;
		case Input.Keys.A:
			player.setX(player.getX() - 32);
			break;
		case Input.Keys.D:
			player.setX(player.getX() + 32);
			break;

		case Input.Keys.LEFT:
				if(mapx>0)					
			{
				camera.translate(-32, 0);
				mapx -= 32;
			}
			break;
		case Input.Keys.RIGHT:
			if (mapx + camera.viewportWidth < levelWidth) 
			{
				camera.translate(32, 0);
				mapx += 32;
			}
			break;
		case Input.Keys.UP:
			if (mapy < camera.viewportHeight) {
				camera.translate(0, 32);
				mapy += 32;
			}

			break;
		case Input.Keys.DOWN:
			if (mapy > 0) {

				camera.translate(0, -32);
				mapy -= 32;
			}
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
			System.out.println("Mapx:" + mapx + " mapy:" + mapy);
		}

		return false;
	}

}
