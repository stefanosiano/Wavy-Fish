package com.stefanosiano.wavyfish.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.SimpleGameObject;

public class LifeBar2 extends SimpleGameObject{
	private TextureRegion lifeTexture, leftBorder, rightBorder;
	private float currentLifeValue;
	private float maxLife;
	private float lifeValue;
	private float lifeWidth;
	private float lifeModifyingStep;
	private float borderWidth;
	private float currentBorderWidth;
	private float borderModifyingStep;
	private float value;
	private int caseColor;
	
	private Color c;
	
	//change color
	private Color[] colors;
	private int numColors;

	public LifeBar2(TextureRegion leftBorder, TextureRegion lifeTexture, TextureRegion rightBorder, float x, float y, float width, float height, float maxLife, float borderWidth) {
		super(lifeTexture, x, y, width, height, 0, 0);
		this.lifeTexture = lifeTexture;
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
		this.lifeWidth = width;
		this.maxLife = maxLife;
		this.lifeValue = maxLife;
		this.currentLifeValue = lifeValue;
		this.borderWidth = borderWidth;
		this.currentBorderWidth = borderWidth;
		

		this.numColors = 2;
	}


	@Override
	public void update(float delta){
		//if i reach 0, the borders of the bar disappear
		if(lifeValue == 0 && currentBorderWidth > 0){
			currentBorderWidth = currentBorderWidth - (borderModifyingStep * delta);
		}
		
		if(currentBorderWidth < 0)
			currentBorderWidth = 0;
		
		if(currentLifeValue == lifeValue)
			return;
		currentLifeValue = currentLifeValue - (lifeModifyingStep * delta);
		lifeWidth = currentLifeValue / maxLife * width;
		
		//if i take damage, i reduce my current life, and if i reduce too much, i adjust it
		if(lifeModifyingStep > 0 && currentLifeValue <= lifeValue){
			currentLifeValue = lifeValue;
			lifeWidth = currentLifeValue / maxLife * width;
		}
		//if i get health, i regenerate my current life, and if i add too much, i adjust it
		if(lifeModifyingStep < 0 && currentLifeValue >= lifeValue){
			currentLifeValue = lifeValue;
			lifeWidth = currentLifeValue / maxLife * width;
		}
	}
	
	@Override
	protected void draw(SpriteBatch batcher){
		c = batcher.getColor();
		changeBatcherColor(batcher);
		batcher.draw(lifeTexture, position.x + currentBorderWidth - 2, position.y, lifeWidth, height);
		batcher.draw(leftBorder, position.x, position.y, currentBorderWidth, height);
		batcher.draw(rightBorder, position.x + lifeWidth + currentBorderWidth - 4, position.y, currentBorderWidth, height);
		batcher.setColor(c);
	}
	
	private void changeBatcherColor(SpriteBatch batcher){
		value = ((currentLifeValue - maxLife)* numColors/maxLife) * 0.75f - 0.25f;
		switch((int)value){
			case 0:
				if(value < -0.75f)
					value = -0.75f;
				batcher.setColor(-value, 1, 0, c.a);
				break;
			case -1:
				batcher.setColor(0.75f, 2 + value, 0, c.a);
				break;
			case -2:
				batcher.setColor(0.75f, 0.25f, 0, c.a);
				break;
			default:
				batcher.setColor(0.75f, 0.75f, 0, c.a);
				break;
		}
	}
	
	/**
	 * method used to reduce the value of the bar!
	 * you need to call update(delta) after this!
	 */
	public void modifyLife(float minusValue, float time){
		this.lifeValue = lifeValue - minusValue;
		if(lifeValue <= 0.0001f)
			lifeValue = 0;
		lifeModifyingStep = minusValue/time;
		borderModifyingStep = borderWidth/time;
	}

	public float getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(float maxLife) {
		this.maxLife = maxLife;
	}

}
