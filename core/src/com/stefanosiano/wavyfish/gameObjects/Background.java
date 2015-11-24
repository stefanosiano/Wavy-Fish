package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleGameObject;

public class Background extends SimpleGameObject {
	private float speedUpFactor, maxSpeed;

	public Background(TextureRegion texture, float x, float y, int width, int height, int speedX, float speedY, float speedUpFactor, float maxSpeed){
		super(texture, x, y, width, height, speedX, speedY);
		this.speedUpFactor = speedUpFactor;
		this.maxSpeed = maxSpeed;
	}


	public void update(float delta){
		super.update(delta);
        if(position.x < - width)
        	position.x += width * 2;
	}

    
    public void speedUp(){
    	this.velocity.x += speedUpFactor;

    	//this.velocity.x = Math.max(this.velocity.x, maxSpeed);
    }
}
