package com.stefanosiano.wavyfish.screens;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.SplashScreenRenderer;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class SplashScreen extends SimpleScreen{
	private Runnable fadeInR, fadeOutR;
	
	public SplashScreen(WavyFishGame game){
		super(game, new SplashScreenRenderer());
		setRendererScreen(this);

		fadeInR = new Runnable(){
			@Override
			public void run() {
				delayedFadeOut(1f);
			}
		};
		fadeOutR = new Runnable(){
			@Override
			public void run() {
				getGame().changeScreen(Screens.menu);
			}
		};
		
		fadeIn(fadeInR, 0.1f);
	}
	
	private void delayedFadeOut(float delay){
		Timer.schedule(new Task(){
			@Override
			public void run() {
				fadeOut(fadeOutR, 0.1f);
			}
		}, delay);
	}

	@Override
	public void render(float delta) {
		renderer.renderAll(delta);
	}
}
