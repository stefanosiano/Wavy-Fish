package com.stefanosiano.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SimpleBar extends SimpleGameObject {
	protected TextureRegion frontTexture;
	protected float renderingValue;
	protected float maxValue;
	protected float value, offset;
	protected float valueWidth;
	private float valueModifyingStep;

	public SimpleBar(TextureRegion backgroundTexture, TextureRegion frontTexture, float x, float y, float width, float height, float maxValue) {
		super(backgroundTexture, x, y, width, height, 0, 0);
		this.frontTexture = frontTexture;
		this.valueWidth = width;
		this.maxValue = maxValue;
		this.value = maxValue;
		this.renderingValue = value;
		this.offset = 0;
	}

	@Override
	public void update(float delta){
		if(renderingValue == value - offset)
			return;
		renderingValue = renderingValue - (valueModifyingStep * delta);
		valueWidth = renderingValue / maxValue * width;
		
		//if(i take damage, i reduce my current life, || i get health, i regenerate my current life) and if i do it too much, i adjust it
		if((valueModifyingStep > 0 && (renderingValue + offset) <= value) || (valueModifyingStep < 0 && (renderingValue + offset) >= value)){
			renderingValue = value - offset;
			valueWidth = renderingValue / maxValue * width;
		}
	}
	
	/**
	 * method used to reduce the value of the bar!
	 * you need to call update(delta) after this!
	 */
	public void modifyLife(float minusValue, float time){
		this.value = value - minusValue;
		valueModifyingStep = minusValue/time;
	}
	
	@Override
	protected void draw(SpriteBatch batcher){
		batcher.draw(texture, position.x, position.y, width, height);
		batcher.draw(frontTexture, position.x, position.y, valueWidth, height);
	}

	public void setValueWidth(float valueWidth) {
		this.valueWidth = valueWidth;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setRenderingValue(float currentValue) {
		this.renderingValue = currentValue;
	}

	public float getRenderingValue() {
		return renderingValue;
	}
	
	public void setOffset(float offset){
		this.offset = offset;
	}
	
	public float getOffset(){
		return offset;
	}
	
	public float getRealCurrentValue() {
		return renderingValue + offset;
	}

	public float getMaxValue() {
		return maxValue;
	}
}
