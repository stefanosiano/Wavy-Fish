package com.stefanosiano.common;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;

public abstract class SimpleGameObject extends SimpleItem {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    
    protected float width;
    protected float height;
    
    protected TextureRegion texture;
    protected Animation<TextureRegion> animation;
    
    //Tween stuffs
    

    
	// !!!!THIS OBJECT IS THE SAME FOR ANY RESOLUTION, THANKS TO THE SCREENCONFIG THAT IS CREATED BY WAVYFISHGAME AND UPDATED IN THE SCREENS!!!!!
	public SimpleGameObject(TextureRegion texture, float x, float y, float width, float height, float speedX, float speedY){
		this.animation = null;
		this.texture = texture;
        this.width = width;
        this.height = height;
        this.offsetX = width/2;
        this.offsetY = height/2;
        this.scaleX = 1;
        this.scaleY = 1;
        this.rotation = 0;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(speedX, speedY);
        this.acceleration = new Vector2(0, 0);
        this.alpha = new FloatValue(1);
	}
	
	public SimpleGameObject(TextureRegion[] textures, Animation.PlayMode playMode, float frameDuration, float x, float y, float width, float height, float speedX, float speedY){
	    this.animation = new Animation<>(frameDuration, textures);
		this.animation.setPlayMode(playMode);
		this.texture = null;
        this.width = width;
        this.height = height;
        this.offsetX = width/2;
        this.offsetY = height/2;
        this.scaleX = 1;
        this.scaleY = 1;
        this.rotation = 0;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(speedX, speedY);
        this.acceleration = new Vector2(0, 0);
        this.alpha = new FloatValue(1);
	}
	
	
	public void update(float delta){
        position.add(velocity.x * delta, velocity.y * delta);
	}

	/**
	 * Overridable method to draw the object.
	 * Remember to call render() to display the object!
	 */
	@Override
	protected void draw(SpriteBatch batcher) {
        batcher.draw(texture, position.x, position.y, offsetX, offsetY, width, height, scaleX, scaleY, rotation);
    }
    
	/**
	 * Overridable method to draw the object.
	 * Remember to call render() to display the object!
	 */
	@Override
	protected void draw(SpriteBatch batcher, float runTime) {
        batcher.draw(animation.getKeyFrame(runTime), position.x, position.y, offsetX, offsetY, width, height, scaleX, scaleY, rotation);
    }
	

	

    public void setSpeed(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    
}
