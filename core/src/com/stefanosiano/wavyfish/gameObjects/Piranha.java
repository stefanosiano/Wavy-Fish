package com.stefanosiano.wavyfish.gameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.stefanosiano.common.SimpleGameObject;

public class Piranha extends SimpleGameObject implements Obstacle {
	private Circle circle;
	private int xGap, piranhaNumber, screenHeight;
	private boolean passed, finishPassed, stop, postponeFurther;
	private float speedUpFactorX, speedUpFactorY, speedUpFactorFrameDuration, maxSpeedX, maxSpeedY, newPositionX;
	static int increment = 0;
	private int incrementStep;
	private Random r;
	private float circleXGap, circleYGap, circleRadius;
    private float newFrameDuration;
    private Animation flippedAnimation, normalAnimation;
	
	public Piranha(TextureRegion[] textures, TextureRegion[] flippedTextures, Animation.PlayMode playMode, float frameDuration, float x, float y, float width, float height, float speedX, float speedY, 
			int screenHeight, int xGap, int piranhaNumber, float speedUpFactorX, float speedUpFactorY, float speedUpFactorFrameDuration,
			int incrementStep, float maxSpeedX, float maxSpeedY, float circleXGap, float circleYGap, float circleRadius) {
		super(textures, playMode, frameDuration, x, y, width, height, speedX, speedY);
		this.xGap = xGap;
		this.screenHeight = screenHeight;
		this.piranhaNumber = piranhaNumber;
		this.speedUpFactorX = speedUpFactorX;
		this.speedUpFactorY = speedUpFactorY;
		this.speedUpFactorFrameDuration = speedUpFactorFrameDuration;
	    this.newFrameDuration = frameDuration;
	    
		this.passed = false;
		this.finishPassed = false;
		this.postponeFurther = false;
		this.stop = false;
		this.incrementStep = incrementStep;
		this.maxSpeedX = maxSpeedX;
		this.maxSpeedY = maxSpeedY;
		
		this.circleXGap = circleXGap;
		this.circleYGap = circleYGap;
		this.circleRadius = circleRadius;
	    this.circle = new Circle(circleXGap + x, circleYGap + y, circleRadius);
		
		this.r = new Random();
		if(speedY > 0)
			speedY = Math.abs(speedY - 100 + r.nextInt(200));
		else
			speedY = -Math.abs(speedY + 100 - r.nextInt(200));
		
		this.normalAnimation = new Animation(frameDuration, textures);
		this.normalAnimation.setPlayMode(playMode);
		this.flippedAnimation = new Animation(frameDuration, flippedTextures);
		this.flippedAnimation.setPlayMode(playMode);

		if(velocity.y < 0){
			animation = normalAnimation;
		}
		else {
			animation = flippedAnimation;
	    	this.circleYGap = height - this.circleYGap;
		}
	}

	@Override
	public void update(float delta){
		super.update(delta);
	    
        if(position.x < - width){
        	if(stop)
        		return;

            Piranha.increment += incrementStep;
			newPositionX = position.x + (xGap + increment) * piranhaNumber;
			if(postponeFurther){
				newPositionX *= 2;
				postponeFurther = false;
			}
        	position.x = newPositionX;
        	position.y = r.nextInt(screenHeight);
			this.passed = false;
			this.finishPassed = false;
        }

        //the piranha is going up
        if(velocity.y < 0 && position.y < -height){
        	//we move the circle in order to be consistent with the fish shape
        	circleYGap = height - circleYGap;
        	velocity.y = Math.abs(velocity.y);
        	
        	position.y = -r.nextInt(450) -height;
        	animation = flippedAnimation;
        }

        //the piranha is going down
        if(velocity.y > 0 && position.y > screenHeight){
        	circleYGap = height - circleYGap;
        	velocity.y = -Math.abs(velocity.y);
        	position.y = r.nextInt(450) + screenHeight;
        	animation = normalAnimation;
        }

        circle.set(circleXGap + position.x, circleYGap + position.y, circleRadius);
	}

	@Override
    public void speedUp(){
    	float speed = velocity.x += speedUpFactorX;
    	float speed2 = velocity.y += speedUpFactorY;
    	
    	this.velocity.x = speed;
    	this.velocity.y = speed2;
    	
    	newFrameDuration = animation.getFrameDuration() / 1.03f;
    	newFrameDuration = Math.max(newFrameDuration, 0.1f);
    	animation.setFrameDuration(newFrameDuration);
    }
	
	@Override
	public void stopComing(){
		this.stop = true;
	}
	
	@Override
	public boolean passed(int fishX){
		if(!passed)
			if(position.x + width <= fishX){
				passed = true;
				return true;
			}
		return false;
	}
	
	@Override
	public boolean finishPassed(int fishX){
		if(!finishPassed)
			if(position.x + width <= fishX){
				finishPassed = true;
				return true;
			}
		return false;
	}
	
	@Override
	public void draw(SpriteBatch batcher, float runtime){
		super.draw(batcher, runtime);
	}

	@Override
	public boolean overlaps(Circle fishCircle){
		if(Intersector.overlaps(fishCircle, circle))
			return true;
		else
			return false;
	}

	@Override
	public void drawCollision(ShapeRenderer shapeRenderer){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(circle.x, circle.y, circle.radius);
		shapeRenderer.end();
	}
	
	@Override
	public void stop(){
		velocity.x = 0;
		velocity.y = 0;
		Piranha.increment = 0;
	}
	
	@Override
	public float getX(){
		return position.x;
	}

	@Override
	public float getSpeedX(){
		return velocity.x;
	}
	
	public Circle getCircle(){
		return circle;
	}
	
	@Override
	public void speedDownTo(float speedX, float speedY){
		velocity.x = speedX;
		if(velocity.y < 0)
			velocity.y = -speedY;
		else
			velocity.y = speedY;
	}
	
	@Override
	public void postponeFurther(){
		this.postponeFurther = true;
	};

}
