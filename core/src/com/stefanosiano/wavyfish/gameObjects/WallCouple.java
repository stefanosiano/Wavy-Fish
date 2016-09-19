package com.stefanosiano.wavyfish.gameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class WallCouple implements Obstacle {
	private Wall wall1, wall2;
	private Rectangle rect1, rect2;
	private int xGap, yGap, wallNumber, screenHeight;
	private int minHeight, maxHeight, width;
	private Random r;
	private boolean passed, finishPassed, stop, postponeFurther;
	private float speedUpFactor, maxSpeed, newPositionX;
	static int increment = 0;
	private int incrementStep;
	
	public WallCouple(TextureRegion wallTexture, TextureRegion wallTexture2, float x, float y, int width, int speedX, 
			int yGap, int screenHeight, int xGap, int wallNumber, int minHeight, int maxHeight, float speedUpFactor, int incrementStep, float maxSpeed) {
		this.r = new Random();
		this.xGap = xGap;
		this.yGap = yGap;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.screenHeight = screenHeight;
		this.wallNumber = wallNumber;
		this.width = width;
		this.speedUpFactor = speedUpFactor;
		int newHeight = minHeight + r.nextInt(maxHeight);
		this.wall1 = new Wall(wallTexture, x, newHeight - (minHeight + maxHeight), width, (minHeight + maxHeight), speedX, 0);
		this.wall2 = new Wall(wallTexture2, x, newHeight + yGap, width, (minHeight + maxHeight), speedX, 0);
		this.rect1 = new Rectangle(x, y, width, newHeight);
		this.rect2 = new Rectangle(x, newHeight + yGap, width, screenHeight - newHeight - yGap);
		this.postponeFurther = false;
		this.stop = false;
		this.passed = false;
		this.finishPassed = false;
		this.incrementStep = incrementStep;
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void update(float delta){
		wall1.update(delta);
		wall2.update(delta);
		if(wall1.getPosition().x < -wall1.getWidth()){
        	if(stop)
        		return;
			WallCouple.increment += incrementStep;
			int newHeight = minHeight + r.nextInt(maxHeight);

            newPositionX = wallNumber * (xGap + WallCouple.increment);
			if(postponeFurther){
				newPositionX += xGap + WallCouple.increment;
				postponeFurther = false;
			}
			wall1.getPosition().x += newPositionX;
			wall1.getPosition().y = newHeight - (minHeight + maxHeight);
			
			wall2.getPosition().x += newPositionX;
			wall2.getPosition().y = newHeight + yGap;
			
			this.passed = false;
			this.finishPassed = false;
		}
		rect1.set(wall1.getPosition().x + 3, wall1.getPosition().y, width - 6, wall1.getHeight() - 5);
		rect2.set(wall2.getPosition().x + 3, wall2.getPosition().y, width - 6, wall2.getHeight() - 5);
	}

	@Override
    public void speedUp(){
    	float speed = wall1.getVelocity().x += speedUpFactor;
    	//speed = Math.max(speed, maxSpeed);
    	this.wall1.getVelocity().x = speed;
    	this.wall2.getVelocity().x = speed;
    }
	
	@Override
	public void stopComing(){
		this.stop = true;
	}
	
	@Override
	public boolean passed(int fishX){
		if(!passed)
			if(wall1.getPosition().x + wall1.getWidth() <= fishX){
				passed = true;
				return true;
			}
		return false;
	}
	
	@Override
	public boolean finishPassed(int fishX){
		if(!finishPassed)
			if(wall1.getPosition().x + wall1.getWidth() <= fishX){
				finishPassed = true;
				return true;
			}
		return false;
	}
	
	@Override
	public void draw(SpriteBatch batcher, float runtime){
		wall1.draw(batcher);
		wall2.draw(batcher);
	}

	@Override
	public void drawCollision(ShapeRenderer shapeRenderer){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(rect1.x, rect1.y, rect1.width, rect1.height);
		shapeRenderer.rect(rect2.x, rect2.y, rect2.width, rect2.height);
		shapeRenderer.end();
	}

	@Override
	public boolean overlaps(Circle fishCircle){
		if(Intersector.overlaps(fishCircle, rect1) || Intersector.overlaps(fishCircle, rect2))
			return true;
		else
			return false;
	}
	
	@Override
	public void stop(){
		wall1.getVelocity().x = 0;
		wall2.getVelocity().x = 0;
		WallCouple.increment = 0;
	}

	@Override
	public float getX(){
		return wall1.getPosition().x;
	}

	@Override
	public float getSpeedX(){
		return wall1.getVelocity().x;
	}
	
	@Override
	public void speedDownTo(float speedX, float speedY){
		wall1.getVelocity().x = speedX;
		wall2.getVelocity().x = speedX;
	}
	
	@Override
	public void postponeFurther(){
		this.postponeFurther = true;
	};
}
