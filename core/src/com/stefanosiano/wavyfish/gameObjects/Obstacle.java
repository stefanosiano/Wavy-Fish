package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public interface Obstacle {
	public void update(float delta);
    public void speedUp();
	public boolean passed(int fishX);
	public boolean finishPassed(int fishX);
	public void draw(SpriteBatch batcher, float runtime);
	public void stop();
	public void speedDownTo(float speedX, float speedY);
	public boolean overlaps(Circle fishCircle);
	public void drawCollision(ShapeRenderer shapeRenderer);
	public float getX();
	public void stopComing();
	public void postponeFurther();
}
