package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.stefanosiano.common.SimpleGameObject;
import com.stefanosiano.wavyfish.utilities.Enums.FishState;

public class Fish extends SimpleGameObject {
    protected float rotation;
    private int minY, maxY;
    private float maxSpeed, originalMaxSpeed, currentAcceleration;
    private float speedUpFactor, maxSpeedSpeedUpFactor;
    private FishState fishState;
    private Circle circle;
    private float circleXGap, circleYGap, circleRadius;
    private float newFrameDuration;
    private float flapVelocity, originalFlapVelocity, originalFlapAcceleration, flapAccStep, flapVelStep;
    private Vector2 flapAcceleration;
    
	public Fish(TextureRegion[] textures, Animation.PlayMode playMode, float frameDuration, float x, float y, int width, int height, 
			int minY, int maxY, float maxSpeed, float currentAcceleration, float speedUpFactor, float speedX, float speedY,
			float circleXGap, float circleYGap, float circleRadius, float maxSpeedSpeedUpFactor){
		super(textures, playMode, frameDuration, x, y, width, height, speedX, speedY);
	    this.minY = minY;
	    this.maxY = maxY;
	    this.maxSpeed = maxSpeed;
	    this.originalMaxSpeed = maxSpeed;
	    this.currentAcceleration = currentAcceleration;
	    this.speedUpFactor = speedUpFactor;
	    this.fishState = FishState.ready;
	    this.circle = new Circle(circleXGap + x, circleYGap + y, circleRadius);
	    this.circleXGap = circleXGap;
	    this.circleYGap = circleYGap;
	    this.circleRadius = circleRadius;
	    this.newFrameDuration = frameDuration;
	    this.flapAcceleration = new Vector2();
		this.originalFlapVelocity = 0;
		this.originalFlapVelocity = 0;
		this.flapAccStep = 0;
		this.flapVelStep = 0;
		this.maxSpeedSpeedUpFactor = maxSpeedSpeedUpFactor;
	}
	
	public void goUp(float delta){
		acceleration.y = -currentAcceleration;
		//update is used to prevent the double update of velocity and position because of multiple touch
		velocity.add(acceleration.x * delta, acceleration.y * delta);
		update(delta);
		
        controlBounds();
	}
	
	public void goDown(float delta){
		acceleration.y = currentAcceleration;

		velocity.add(acceleration.x * delta, acceleration.y * delta);
		update(delta);
		
        controlBounds();
	}
	
	@Override
	public void update(float delta){
		position.add(velocity.x * delta, velocity.y * delta);
        controlBounds();
	}
	
	private void controlBounds(){
        if (velocity.y < -maxSpeed) {
        	velocity.y = -maxSpeed;
        	acceleration.y = 0;
        }
        else 
        	if (velocity.y > maxSpeed) {
        		velocity.y = maxSpeed;
            	acceleration.y = 0;
        	}
        if (position.y < minY) {
        	position.y = minY;
        }
        else 
        	if (position.y > maxY) {
		    	position.y = maxY;
		    }
        //circle.set(circleXGap + position.x, circleYGap + position.y, circleRadius);
        circle.set(circleXGap + position.x, circleYGap + position.y + (velocity.y * 20 / maxSpeed), circleRadius);
		rotation = velocity.y * 30 / maxSpeed;
	}
	
	@Override
	protected void draw(SpriteBatch batcher, float runTime) {
        batcher.draw(animation.getKeyFrame(runTime), position.x, position.y, width/2, height/2, width, height, 1, 1, rotation);
	}

	public void updateFlappy(float delta){
		velocity.add(flapAcceleration.x * delta, flapAcceleration.y * delta);
		position.add(velocity.x * delta, velocity.y * delta);
        controlFlappyBounds();
	}
	
	public void updateWavy(float delta, float touchY){
		float difference = touchY - (position.y + height/2);

		//tocco sotto il pesce
		if(difference > 20){
			goDown(delta);
	        controlWavyBounds(difference);
			return;
		}
		if(difference >= -20 && difference <= 20){
			update(delta);
			return;
		}
		//tocco sopra il pesce
		if(difference < -20){
			goUp(delta);
	        controlWavyBounds(difference);
			return;
		}
	}
	
	private void controlFlappyBounds(){
    	if (velocity.y > maxSpeed) {
    		velocity.y = maxSpeed;
    	}
        if (position.y < minY) {
        	position.y = minY;
        }
        
        if (position.y > maxY) {
		   	position.y = maxY;
		}
        //circle.set(circleXGap + position.x, circleYGap + position.y, circleRadius);
        circle.set(circleXGap + position.x, circleYGap + position.y + (velocity.y * 20 / maxSpeed), circleRadius);
		rotation = velocity.y * 30 / maxSpeed;
	}
	
	private void controlWavyBounds(float difference){
		float max = Math.max(Math.abs(difference), 70)/300 * maxSpeed;
		if(max > maxSpeed)
			max = maxSpeed;
    	if (velocity.y > max) {
    		velocity.y = max;
        	acceleration.y = 0;
    	}
    	if (velocity.y < -max) {
    		velocity.y = -max;
        	acceleration.y = 0;
    	}
        if (position.y < minY) {
        	position.y = minY;
        }
        
        if (position.y > maxY) {
		   	position.y = maxY;
		}
        //circle.set(circleXGap + position.x, circleYGap + position.y, circleRadius);
        circle.set(circleXGap + position.x, circleYGap + position.y + (velocity.y * 20 / maxSpeed), circleRadius);
		rotation = velocity.y * 30 / maxSpeed;
	}
	
	public void flap(){
        velocity.y = flapVelocity;
	}
	
	public void setFlapValues(float acc, float vel){
		this.flapAcceleration.y = acc;
	    this.flapVelocity = vel;
		this.originalFlapAcceleration = acc;
		this.originalFlapVelocity = vel;
		this.flapAccStep = acc / 20;
		this.flapVelStep = vel / 25;
	}
	

	public void startBounce(){
		this.velocity.y = maxSpeed;
	}
	
	public void changeBounceDirection(){
		this.velocity.y = -velocity.y;
	}

	public void updateBounce(float delta){
		position.add(velocity.x * delta, velocity.y * delta);
        controlBounceBounds();
	}
	
	private void controlBounceBounds(){
        if (position.y < minY) {
        	position.y = minY;
        	velocity.y = -velocity.y;
        }
        else 
        	if (position.y > maxY) {
            	position.y = maxY;
            	velocity.y = -velocity.y;
		    }
        circle.set(circleXGap + position.x, circleYGap + position.y + (velocity.y * 20 / maxSpeed), circleRadius);
		rotation = velocity.y * 30 / maxSpeed;
	}
	
	public void setVelocityY(float velocityY){
		this.velocity.y = velocityY;
	}
	
	public float getMaxSpeed(){
		return maxSpeed;
	}	

    public float getRotation() {
        return rotation;
    }
    
    public void speedUp(){
    	this.flapAcceleration.y += flapAccStep;
	    this.flapVelocity += flapVelStep;
    	this.currentAcceleration += speedUpFactor;
    	this.maxSpeed += maxSpeedSpeedUpFactor;
    	newFrameDuration = animation.getFrameDuration() / 1.03f;
    	newFrameDuration = Math.max(newFrameDuration, 0.1f);
    	animation.setFrameDuration(newFrameDuration);
    }
    
    public void fishStart(){
    	this.fishState = FishState.alive;
    }
    
    public void die(){
    	this.fishState = FishState.dead;
    }
    
    public FishState getState(){
    	return this.fishState;
    }
    
    public Circle getCircle(){
    	return this.circle;
    }
    
    public void setCircleXGap(float gap){
    	this.circleXGap = gap;
    }
    
    public void setWidth(int width){
    	this.width = width;
    }
    
    public void stop(){
    	this.acceleration = Vector2.Zero;
    }
    
    public void speedDownTo(float acceleration, float frameDuration){
    	this.currentAcceleration = acceleration;
    	animation.setFrameDuration(frameDuration);
		this.flapAcceleration.y = originalFlapAcceleration;
		this.flapVelocity = originalFlapVelocity;
		this.maxSpeed = originalMaxSpeed;
    }
}
