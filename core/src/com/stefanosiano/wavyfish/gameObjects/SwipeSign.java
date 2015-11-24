package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleGameObject;

public class SwipeSign extends SimpleGameObject {
	private float maxWidth;
	boolean startFadeOut;
	private TextureRegion textureFlipped;
	private TextureRegion activeTexture;
	
	public SwipeSign(TextureRegion texture, TextureRegion textureFlipped, float x, float y, float width, float height, float speedX, float maxWidth) {
		super(texture, x, y, width, height, speedX, 0);
		this.maxWidth = maxWidth;
		startFadeOut = true;
		this.textureFlipped = textureFlipped;
		activeTexture = texture;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(startFadeOut && position.x > 10 && position.x < (maxWidth - width - 10)){
			this.setupFadeOutTweens(1f);
			startFadeOut = false;
		}
		if(this.position.x + width < -30){
			activeTexture = texture;
			position.x += 5;
			this.velocity.x = -velocity.x;
			startFadeOut = true;
			this.setAlpha(1);
		}
		if(this.position.x > maxWidth + 30){
			activeTexture = textureFlipped;
			position.x -= 5;
			this.velocity.x = -velocity.x;
			startFadeOut = true;
			this.setAlpha(1);
		}
	}

	@Override
	protected void draw(SpriteBatch batcher) {
        batcher.draw(activeTexture, position.x, position.y, width, height);
    }
}
