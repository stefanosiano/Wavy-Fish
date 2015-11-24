package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleGameObject;

public class Wall extends SimpleGameObject{
	
	public Wall(TextureRegion texture, float x, float y, int width, int height, float speedX, float speedY){
		super(texture, x, y, width, height, speedX, speedY);
	}
	
	@Override
	public void draw(SpriteBatch batcher){
		super.draw(batcher);
	}

}
