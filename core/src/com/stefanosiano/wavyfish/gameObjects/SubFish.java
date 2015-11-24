package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleGameObject;

public class SubFish extends SimpleGameObject{

	public SubFish(TextureRegion[] textures, PlayMode playMode, float frameDuration, float x, float y, int width, int height, float speedX, float speedY) {
		super(textures, playMode, frameDuration, x, y, width, height, speedX, speedY);
	}

}
