package com.stefanosiano.wavyfish.game;

import com.badlogic.gdx.Game;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.common.tochange.DataSaver;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.screens.CreditsScreen;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.screens.MenuScreen;
import com.stefanosiano.wavyfish.screens.MenuScreen2;
import com.stefanosiano.wavyfish.screens.SplashScreen;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Initializer;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class WavyFishGame extends Game {
	private CommonApiController commonApiController;
	
	public WavyFishGame(CommonApiController commonApiController){
		this.commonApiController = commonApiController;
	}
	
	@Override
	public void create () {
        DataSaver.load();
		if(Settings.totalPlayTime + Settings.timeForAds > 0){
			AnalyticsSender.sendTotalPlayTime(this, (long)(Settings.totalPlayTime + Settings.timeForAds));
			Settings.totalPlayTime = 0;
			Settings.timeForAds = 0;
		}
		ScreenConfig.initialize();
        Settings.load();
        TextureLoader.load();
        SoundLoader.load();
        GameObjectContainer.initialize();
        Initializer.initValues();
        Initializer.initializeButtons();
        Initializer.createGameObjects();
        setScreen(new SplashScreen(this));
        Experience.load();
	}
	
	public CommonApiController getCommonApiController(){
		return commonApiController;
	}
	
	public void changeScreen(Screens screen){
		switch(screen){
			case splash:
				setScreen(new SplashScreen(this));
				break;
			case menu:
				setScreen(new MenuScreen(this));
				break;
			case menu2:
				setScreen(new MenuScreen2(this));
				break;
			case game:
				setScreen(new GameScreen(this));
				break;
			case credits:
				setScreen(new CreditsScreen(this));
				break;
			default:
				break;
		}
	}

    @Override
    public void dispose() {
        super.dispose();
		if(Settings.totalPlayTime + Settings.timeForAds > 0){
			AnalyticsSender.sendTotalPlayTime(this, (long)(Settings.totalPlayTime + Settings.timeForAds));
			Settings.totalPlayTime = 0;
			Settings.timeForAds = 0;
		}
        TextureLoader.dispose();
        SoundLoader.dispose();
        GameObjectContainer.dispose();
    }
}
