package com.stefanosiano.common;

import java.text.DecimalFormat;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.wavyfish.tweenAccessors.FloatAccessor;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;

public class SimpleRenderer {

    protected final OrthographicCamera cam;
    protected final ShapeRenderer shapeRenderer;
    protected float width;
    protected float height;
    protected int virtualWidth, virtualHeight;
    private Rectangle viewport;
    protected SpriteBatch batcher;
    protected SimpleScreen screen;
    private float countTime, countReverseTime;
    private Text simpleText;
	private DecimalFormat df;
    
    //Tween stuffs
    private TweenManager managerIn, managerOut;
    private FloatValue alphaIn, alphaOut;
    private boolean fadeIn, fadeOut;


	/**
	 * Constructor of the Simple Renderer. It creates shapeRenderer and batcher.
	 * Also, it calls updateScreenDimensions(), which makes it render in the right
	 * width and height.
	 */
    public SimpleRenderer(){
    	this.width = ScreenConfig.VIRTUALWIDTH;
    	this.height = ScreenConfig.VIRTUALHEIGHT;
    	this.virtualWidth = ScreenConfig.VIRTUALWIDTH;
    	this.virtualHeight = ScreenConfig.VIRTUALHEIGHT;
    	this.fadeIn = false;
    	this.fadeOut = false;
    	this.countTime = 0;
    	this.simpleText = null;
    	this.df = new DecimalFormat("0.00");
    	
    	cam = new OrthographicCamera();
        cam.setToOrtho(true, width, height);
        cam.update();
        updateScreenDimensions();
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
    }

	/**
	 * Sets the screen witch this renderer is part of.
	 * Can't put in constructor because of "this".
	 */
    public void SetScreen(SimpleScreen screen){
    	this.screen = screen;
    }

	/**
	 * Sets the right dimensions for the renderer.
	 * Called by the constructor and by "onResize()" of the screen.
	 */
    public void updateScreenDimensions(){
    	this.viewport = ScreenConfig.VIEWPORT;
    	this.width = ScreenConfig.RENDERINGWIDTH;
    	this.height = ScreenConfig.RENDERINGHEIGHT;
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
    }

	/**
	 * Overridable method to draw game objects. Remember that SpriteBatch and ShapeRenderer can't be started toghether!
	 */
	protected void render(float delta){
	}

	/**
	 * Calls the render(delta) method and the fade-in/fade-out
	 * transitions. This is the method you need to call!
	 * It takes the right dimensions of rendering and clear 
	 * previous frames using GL function
	 */
	public final void renderAll(float delta){
        // clear previous frame
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render(delta);

        if(fadeIn)
        	drawFadeIn(delta);

        if(fadeOut)
        	drawFadeOut(delta);
	}

	/**
	 * Prepares the fade in transition (makes the screen from black to visible)
	 * and set the fade-in flag to true. At the end of the transition the run()
	 * method of the Runnable will be called. 
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeInTweens(final Runnable r, float duration) {
		alphaIn = new FloatValue(1);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerIn = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				r.run();
				fadeIn = false;
			}
        };
        Tween.to(alphaIn, -1, duration).target(0)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerIn);
        this.fadeIn = true;
    }
	

	/**
	 * Prepares the fade in transition (makes the screen from black to visible)
	 * and set the fade-in flag to true. 
	 *
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeInTweens(float duration) {
		alphaIn = new FloatValue(1);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerIn = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				fadeIn = false;
			}
        };
        Tween.to(alphaIn, -1, duration).target(0)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerIn);
        this.fadeIn = true;
    }


	/**
	 * Makes the screen from black to visible
	 *
	 * @param  delta  the time of the transition
	 */
	private void drawFadeIn(float delta) {
        if (alphaIn.getValue() > 0) {
            managerIn.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alphaIn.getValue());
            shapeRenderer.rect(0, 0, virtualWidth, virtualHeight);
            shapeRenderer.end();
        }
    }


	/**
	 * Prepares the fade out transition (makes the screen from visible to black)
	 * and set the fade-out flag to true. At the end of the transition the run()
	 * method of the Runnable will be called.
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeOutTweens(final Runnable r, float duration) {
		alphaOut = new FloatValue(0);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerOut = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				r.run();
				fadeOut = false;
			}
        };
        Tween.to(alphaOut, -1, duration).target(1)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerOut);
        this.fadeOut = true;
    }
	
	
	/**
	 * Prepares the fade out transition (makes the screen from visible to black)
	 * and set the fade-out flag to true.
	 *
	 * @param  duration 	duration of the transition
	 */
	public void setupFadeOutTweens(float duration) {
		alphaOut = new FloatValue(0);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        managerOut = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				fadeOut = false;
			}
        };
        Tween.to(alphaOut, -1, duration).target(1)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(managerOut);
        this.fadeOut = true;
    }

	/**
	 * Makes the screen from visible to black
	 *
	 * @param  delta  the time of the transition
	 */
	private void drawFadeOut(float delta) {
        if (alphaOut.getValue() < 1) {
            managerOut.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alphaOut.getValue());
            shapeRenderer.rect(0, 0, virtualWidth, virtualHeight);
            shapeRenderer.end();
        }
    }


	/**
	 * Makes the time counter equal to time. Must be used before drawReverseCount or drawCount!
	 * 
	 * @param  time  the time of the countdown
	 */
	public final void prepareCount(float time, BitmapFont font, float scaleX, float scaleY, float x, float y, String message){
		simpleText = new Text(font, scaleX, scaleY, false);
		simpleText.setCenteredHorizzontally(message, x, y);
		this.countReverseTime = time;
		this.countTime = 0;
	}

	/**
	 * Draws the countDown from time to 0. Remember to call prepareCount before this!
	 *
	 * @param  delta time between frames
	 */
	public void drawReverseCount(float delta, String message){
		countReverseTime -= delta;
		if(countReverseTime < 0)
			countReverseTime = 0;
		simpleText.updateText(message + df.format(countReverseTime));
		batcher.enableBlending();
		simpleText.draw(batcher, delta);
	}

	/**
	 * Draws the countDown from 0 to time. Remember to call prepareCount before this!
	 *
	 * @param  delta time between frames
	 */
	public void drawCount(float delta){
		countTime += delta;
		if(countTime > countReverseTime)
			countTime = countReverseTime;
		simpleText.updateTextCentered(df.format(countReverseTime));
		batcher.enableBlending();
		simpleText.draw(batcher, delta);
	}
	
	/**
	 * Dispose of the items used in the renderer (SpriteBatch/ ShaperRenderer...)
	 * 
	 */
	public void dispose(){
		batcher.dispose();
	}
}
