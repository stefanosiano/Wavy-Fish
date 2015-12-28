package com.stefanosiano.wavyfish.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.SplashScreenRenderer;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;
import com.stefanosiano.wavyfish.utilities.Settings;

public class SplashScreen extends SimpleScreen{
    private TextureRegion splash;
	private Runnable fadeInR, fadeOutR;
	
	public SplashScreen(final WavyFishGame game){
		super(game, new SplashScreenRenderer());
        splash = TextureLoader.loadSplahRegion();
        ((SplashScreenRenderer)renderer).setSplash(splash);
		setRendererScreen(this);

        fadeInR = new Runnable(){
            @Override
            public void run() {
                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        //if(Settings.REWARDEDVIDEOSWATCHED < Settings.rewardedVideosToWatch)
                            //game.getCommonApiController().loadRewardedVideoAd();
                        game.initialize();
                        delayedFadeOut(1f);
                    }
                }, 0.1f);
            }
        };
        fadeOutR = new Runnable(){
            @Override
            public void run() {
                getGame().changeScreen(Screens.menu);
                splash.getTexture().dispose();
            }
        };
		
		fadeIn(fadeInR, 0.5f);
	}
	
	private void delayedFadeOut(float delay) {
        Timer.schedule(new Task() {
            @Override
            public void run() {
                fadeOut(fadeOutR, 0.5f);
			}
		}, delay);
	}

	@Override
	public void render(float delta) {
		renderer.renderAll(delta);
	}
}
