package com.deluca.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Utilities {

	
	public static int getWindowWidth()
	{
		return Gdx.graphics.getWidth();
	}
	
	public static int getWindowHeight()
	{
		return Gdx.graphics.getHeight();
	}
	
	/**
	 * Returns X mouse position
	 * @return
	 */
	public static int getMouseX() {
		return Gdx.input.getX();
	}

	/**
	 * Returns Y mouse position adjusted to match graphics coordinates
	 * @return
	 */
	public static int getMouseY() {
		return (Gdx.graphics.getHeight() - Gdx.input.getY());
	}
	
	/**
	 * Checks on the collision layer of the tiledMap for a tile at x, y
	 * @param x coord
	 * @param y coord
	 * @param collisionMapLayer layer to check within
	 * @return isCollision
	 */
	public static boolean collision(int x, int y, TiledMapTileLayer collisionMapLayer)
	{
		return ((TiledMapTileLayer)collisionMapLayer).getCell(x, y)!=null;
	}
	
	
	/**
	 * Translates from x,y pixel location to
	 * tiledMap cell. 
	 * 
	 * @param msx x pixel coordinate 
	 * @param msy y pixel coordinate 
	 * @return Vector2 coordinates of cell in collisionLayer
	 */
	public static Vector2 getCell(float msx, float msy, Level level)
	{
		
		int numTilesHigh=(Integer)level.tiledMap.getProperties().get("height");
		int numTilesWide=(Integer)level.tiledMap.getProperties().get("width");
		int screenNumTileWide = (int) (numTilesWide* ((level.maxXCoord/ level.camera.viewportWidth)));			
		int screenNumTileHigh =  (int) (numTilesHigh*(level.SCREENHEIGHT/level.levelHeight));
		int cellWidth = (int) (level.pixelWidth*level.xCameraZoom / screenNumTileWide);
		int cellHeight = (int) (level.pixelHeight*level.yCameraZoom/ screenNumTileHigh);
		int cellX=(int) msx/cellWidth;
		int cellY=(int) msy/cellHeight;
		
		return new Vector2(cellX,cellY);
	}

}