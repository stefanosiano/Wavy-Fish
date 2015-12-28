package com.stefanosiano.wavyfish.screenHelpers;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.stefanosiano.wavyfish.tweenAccessors.FloatAccessor;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;
import com.stefanosiano.wavyfish.utilities.Settings;

public class GameScreenBlinkingRenderer extends GameScreenCommonRenderer {
    private TweenManager manager;
    private FloatValue alpha;
    private Color c1;
	private float duration, originalDuration;
	private int speedDownTimes;

    public GameScreenBlinkingRenderer(){
    	super();
    	switch(Settings.difficulty){
			case easy:
				duration = 1.1f;
				break;
			case medium:
				duration = 1f;
				break;
			case hard:
				duration = 0.9f;
				break;
			case crazy:
				duration = 0.8f;
				break;
			default:
				duration = 1f;
    	}
    	originalDuration = duration;
    	setupTweens(duration);
    	speedDownTimes = 0;
    }
    
	@Override
	public void render(float delta){
        manager.update(delta);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        c1 = batcher.getColor();
        super.render(delta);
	}
	
	@Override
	protected void drawWallAndFish(float runTime, float delta){
        batcher.setColor(c1.r, c1.g, c1.b, alpha.getValue());
        drawWalls(runTime);
        drawFish(runTime, delta);
        batcher.setColor(c1);
	}
	
	@Override
	public void speedUp(){
		duration /= 1.025;
		duration = Math.max(duration, 0.25f);
	}
	
	@Override
	public void speedDown(){
		speedDownTimes++;
		int x = speedDownTimes;
		//normally i get 20 speedUp (40/2)
    	switch(Settings.difficulty){
			case easy:
				x = 6 * x;
				break;
			case medium:
				x = 9 * x;
				break;
			case hard:
				x = 12 * x;
				break;
			case crazy:
				x = 15 * x;
				break;
    	}
    	duration = originalDuration;
		for(int i = 0; i < x; i++)
			speedUp();
	}

	public void changeDuration(float dur){
		duration = dur;
	}

	public float getDuration(){
		return duration;
	}
	
	@Override
    public void initialize(){
		super.initialize();
		duration = originalDuration;
		speedDownTimes = 0;
	}
	
	public void setupTweens(float dur) {
		this.duration = dur;
		this.originalDuration = dur;
		alpha = new FloatValue(0);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        manager = new TweenManager();
        
        TweenCallback callback = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
		        Tween.to(alpha, -1, duration).target(1)
		        .ease(TweenEquations.easeNone).repeatYoyo(1, 0f).setCallback(this).setCallbackTriggers(TweenCallback.COMPLETE)
		        .start(manager);
			}
		};
        Tween.to(alpha, -1, duration).target(1)
        .ease(TweenEquations.easeNone).repeatYoyo(1, 0f).setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(manager);
    }
}
