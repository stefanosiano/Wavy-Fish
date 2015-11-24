package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleBar;

public class ExperienceBar extends SimpleBar{
	private TextureRegion leftTexture, rightTexture, topTexture, bottomTexture;
	private float textureWidth, textureHeight;

	public ExperienceBar(TextureRegion backgroundTexture, TextureRegion frontTexture, TextureRegion leftTexture, TextureRegion rightTexture, TextureRegion topTexture, 
			TextureRegion bottomTexture, float textureWidth, float textureHeight, float x, float y, float width, float height) {
		super(backgroundTexture, frontTexture, x, y, width, height, 200);
		super.setRenderingValue(0);
		super.setOffset(0);
		super.setValueWidth(0);
		
		this.leftTexture = leftTexture;
		this.rightTexture= rightTexture;
		this.topTexture = topTexture;
		this.bottomTexture = bottomTexture;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batcher){
		batcher.disableBlending();
		batcher.draw(leftTexture, position.x - textureWidth, position.y, textureWidth, height);
		batcher.draw(topTexture, position.x - textureWidth, position.y - textureHeight, width + (2 * textureWidth), textureHeight);

		batcher.draw(texture, position.x, position.y, width, height);
		batcher.draw(frontTexture, position.x, position.y, valueWidth, height);
		
		batcher.draw(rightTexture, position.x + width, position.y, textureWidth, height);
		batcher.draw(bottomTexture, position.x - textureWidth, position.y + height, width + (2 * textureWidth), textureHeight);
		batcher.enableBlending();
	}
}
