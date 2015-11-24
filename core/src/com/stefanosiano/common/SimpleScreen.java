package com.stefanosiano.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.common.inputs.InputHandler;
import com.stefanosiano.common.inputs.SimpleDirectionGestureDetector;
import com.stefanosiano.common.inputs.SimpleDirectionGestureDetector.DirectionListener;

public class SimpleScreen implements Screen, DirectionListener{
	protected SimpleRenderer renderer;
	private WavyFishGame game;

	
	/**
	 * Creates the renderer and takes the link to the Game class.
	 * Implements Screen and all of its methods, and sets the InputProcessor.
	 *
	 * @param  game  		game Class used to manage screen changes
	 * @param  renderer 	SubClass of the renderer
	 */
	public SimpleScreen(WavyFishGame game, SimpleRenderer renderer){
    	this.game = game;
    	this.renderer = renderer;
        Gdx.input.setCatchBackKey(true);
        
        InputMultiplexer multi = new InputMultiplexer();

        multi.addProcessor(new InputHandler());
        multi.addProcessor(new SimpleDirectionGestureDetector(this));

        Gdx.input.setInputProcessor(multi);
	}

	
	/**
	 * Sets the screen the renderer is part of.
	 *
	 * @param  screen 	SubClass of Screen
	 */
	public final void setRendererScreen(SimpleScreen screen){
		this.renderer.SetScreen(screen);
	}

	/**
	 * Method to override to update and render game objects.
	 *
	 * @param  delta 	Time between the frames (taken automatically)
	 */
	@Override
	public void render(float delta) {
	}

	@Override
	public final void show() {
	}

	/**
	 * Handle the resize of the screen.
	 */
	@Override
	public final void resize(int width, int height) {
		ScreenConfig.updateDimensions();
		renderer.updateScreenDimensions();
	}


	/**
	 * Prepares the fade out transition (makes the screen from visible to black)  
	 * and calls the run() method of the Runnable.
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public final void fadeOut(final Runnable r, float duration) {
		this.renderer.setupFadeOutTweens(r, duration);
	}

	/**
	 * Prepares the fade in transition (makes the screen from black to visible) 
	 * and calls the run() method of the Runnable.
	 *
	 * @param  r  			runnable to run when the transition is completed
	 * @param  duration 	duration of the transition
	 */
	public final void fadeIn(final Runnable r, float duration) {
		this.renderer.setupFadeInTweens(r, duration);
	}


	/**
	 * Prepares the fade out transition (makes the screen from visible to black) 
	 *
	 * @param  duration 	duration of the transition
	 */
	public final void fadeOut(float duration) {
		this.renderer.setupFadeOutTweens(duration);
	}

	/**
	 * Prepares the fade in transition (makes the screen from black to visible) 
	 *
	 * @param  duration 	duration of the transition
	 */
	public final void fadeIn(float duration) {
		this.renderer.setupFadeInTweens(duration);
	}

	/**
	 * Prepares the countdown drawing 
	 *
	 * @param  time 	time of the countdown
	 */
	public final void prepareCount(float time, BitmapFont font, float scaleX, float scaleY, float x, float y, String message) {
		this.renderer.prepareCount(time, font, scaleX, scaleY, x, y, message);
	}

	/**
	 * Returns the Game instance linked.
	 */
	public final WavyFishGame getGame(){
		return this.game;
	}

	@Override
	public void pause() {
	}

	@Override
	public final void resume() {
	}

	@Override
	public final void hide() {
	}

	@Override
	public final void dispose() {
		renderer.dispose();
	}

	
	@Override
	public void onSwipeLeft() {}


	@Override
	public void onSwipeRight() {}


	@Override
	public void onSwipeUp() {}


	@Override
	public void onSwipeDown() {}

}
