package com.stefanosiano.common;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stefanosiano.wavyfish.tweenAccessors.FloatAccessor;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;

public abstract class SimpleItem {
    protected boolean enabled;
    
    //drawing stuff
    protected float offsetX, offsetY, scaleX, scaleY, rotation;
    
	//Tween stuff
    private Color c;
    protected FloatValue blinkingAlpha;
    protected TweenManager blinkingTweenManager, rotatingTweenManager;
    protected boolean blinking;
	
    protected TweenManager managerIn, managerOut;
    protected FloatValue alpha, rotatingValue;
    protected boolean fadeIn, fadeOut, fading, rotating;


    public SimpleItem(){
        fadeIn = false;
        fadeOut = false;
        fading = false;
        rotating = false;
        blinking = false;
    }
    
	/**
	 * Calls the draw method and the fade-in/fade-out
	 * transitions. This is the method you need to call!
	 */
	public void render(SpriteBatch batcher, float delta){
		if(rotating){
			rotatingTweenManager.update(delta);
			rotation = rotatingValue.getValue();
		}
		if(blinking)
			blinkingTweenManager.update(delta);
			
		if(!fading && !blinking)
	    	draw(batcher);
		
		else{
	    	c = batcher.getColor();
	    	if(blinking)
		    	batcher.setColor(c.r, c.g, c.b, blinkingAlpha.getValue());
	    	else
	    		batcher.setColor(c.r, c.g, c.b, alpha.getValue());
	    	draw(batcher);
        	batcher.setColor(c);
        	
            if(fadeIn)
            	drawFadeIn(delta);
            
            if(fadeOut)
	        	drawFadeOut(delta);
		}
	}

	/**
	 * Calls the draw method and the fade-in/fade-out
	 * transitions. This is the method you need to call!
	 */
	public void render(SpriteBatch batcher, float runTime, float delta){
		if(!fading)
    		draw(batcher, runTime);
		else{
	    	c = batcher.getColor();
	    	batcher.setColor(c.r, c.g, c.b, alpha.getValue());
    		draw(batcher, runTime);
        	batcher.setColor(c);
        	
            if(fadeIn)
            	drawFadeIn(delta);
            
            if(fadeOut)
	        	drawFadeOut(delta);
		}
	}

	abstract protected void draw(SpriteBatch batcher);
	abstract protected void draw(SpriteBatch batcher, float runTime);

	/**
	 * Prepares the fade in transition (makes the object visible)
	 * and set the fade-in flag to true. At the end of the transition the run()
	 * method of the Runnable will be called. 
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeInTweens(final Runnable r, float duration) {
		alpha = new FloatValue(0);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerIn = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				if(r != null)
					r.run();
				fadeIn = false;
				enabled = true;
			}
        };
        Tween.to(alpha, -1, duration).target(1)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerIn);
        this.fadeIn = true;
        this.enabled = false;
        this.fading = true;
    }
	

	/**
	 * Prepares the fade in transition (makes the object visible)
	 * and set the fade-in flag to true. 
	 *
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeInTweens(float duration) {
		setupFadeInTweens(null, duration);
    }


	/**
	 * Makes the object visible
	 *
	 * @param  delta  the time of the transition
	 */
	protected void drawFadeIn(float delta) {
        if (alpha.getValue() < 1) {
            managerIn.update(delta);
        }
    }
	
	public void setAlpha(float value){
		this.alpha.setValue(value);
	}


	/**
	 * Prepares the fade out transition (makes the object invisible)
	 * and set the fade-out flag to true. At the end of the transition the run()
	 * method of the Runnable will be called.
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeOutTweens(final Runnable r, float duration) {
		alpha = new FloatValue(1);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerOut = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				if(r != null)
				r.run();
				fadeOut = false;
			}
        };
        Tween.to(alpha, -1, duration).target(0)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerOut);
        this.fadeOut = true;
        this.enabled = false;
        this.fading = true;
    }
	
	
	/**
	 * Prepares the fade out transition (makes the object invisible)
	 * and set the fade-out flag to true.
	 *
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeOutTweens(float duration) {
		setupFadeOutTweens(null, duration);
    }

	/**
	 * Makes the object invisible
	 *
	 * @param  delta  the time of the transition
	 */
	protected void drawFadeOut(float delta) {
        if (alpha.getValue() > 0) {
            managerOut.update(delta);
        }
    }

	/**
	 * Start the blink of the item.
	 * You can stop it with stopBlink()
	 *
	 * 
	 * @param  duration 	duration of the blinking (from opaque to transparent and vice versa)
	 */
	public void startBlink(float duration) {
		blinkingAlpha = new FloatValue(1);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        blinkingTweenManager = new TweenManager();

        Tween.to(blinkingAlpha, -1, duration).target(0f)
        .ease(TweenEquations.easeNone).repeatYoyo(Tween.INFINITY, 0)
        .start(blinkingTweenManager);
        this.blinking = true;
    }

	/**
	 * Start the rotating of the item.
	 * You can stop it with stopRotate()
	 * 
	 * @param  duration 	duration of the blinking (from opaque to transparent and vice versa)
	 */
	public void startRotate(float duration) {
		if(rotatingValue == null)
			rotatingValue = new FloatValue(0);
		else
			rotatingValue.setValue(rotatingValue.getValue() % 360);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        rotatingTweenManager = new TweenManager();

        Tween.to(rotatingValue, -1, duration).target(rotatingValue.getValue() + 360)
        .ease(TweenEquations.easeNone).repeat(Tween.INFINITY, 0)
        .start(rotatingTweenManager);
        this.rotating = true;
    }

	/**
	 * Stop rotating the item.
	 * 
	 * @param  duration 	duration of the blinking (from opaque to transparent and vice versa)
	 */
	public void stopRotate(boolean resetState) {
		this.rotating = false;
		if(resetState){
			this.rotation = 0;
			this.rotatingValue.setValue(0);
		}
    }


	/**
	 * Gets if the item is currently rotating or not
	 * 
	 * @param  duration 	duration of the blinking (from opaque to transparent and vice versa)
	 */
	public boolean getRotating(){
		return this.rotating;
	}

	/**
	 * Stop the blinking of the text.
	 */
	public void stopBlink() {
        this.blinking = false;
    }
}
