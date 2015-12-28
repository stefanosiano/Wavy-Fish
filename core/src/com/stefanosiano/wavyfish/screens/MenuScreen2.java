package com.stefanosiano.wavyfish.screens;

import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.MenuScreenRenderer;
import com.stefanosiano.wavyfish.screenHelpers.MenuScreenRenderer2;
import com.stefanosiano.wavyfish.screenHelpers.MenuScreenUpdater2;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.Difficulty;
import com.stefanosiano.wavyfish.utilities.Enums.GameControl;
import com.stefanosiano.wavyfish.utilities.Enums.GameMode;
import com.stefanosiano.wavyfish.utilities.Enums.GameObstacle;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;

public class MenuScreen2 extends SimpleScreen{
	private MenuScreenUpdater2 updater;

	public MenuScreen2(WavyFishGame game){
		super(game, new MenuScreenRenderer2());
		setRendererScreen(this);
		GameButtonContainer.setButtons(GameState.menu2);
		this.updater = new MenuScreenUpdater2(this);
		if(!Settings.TUT_MENU2){
			setSelectedDifficulty(ButtonNames.buttonEasy);
			setSelectedControl(ButtonNames.buttonWavyControl);
			setSelectedMode(ButtonNames.buttonNormalMode);
		}
        else
            ((MenuScreenRenderer2) this.renderer).createTutorialTexts();
		fadeIn(0.4f);
	}
	
	@Override
	public void render(float delta) {
		updater.update(delta);
		renderer.renderAll(delta);
	}
	
	
	public void setSelectedDifficulty(ButtonNames name){
		((MenuScreenRenderer2) this.renderer).setSelectedDifficulty(name);
		switch(name){
			case buttonEasy:
				Settings.difficulty = Difficulty.easy;
				break;
			case buttonMedium:
				Settings.difficulty = Difficulty.medium;
				break;
			case buttonHard:
				Settings.difficulty = Difficulty.hard;
				break;
			case buttonCrazy:
				Settings.difficulty = Difficulty.crazy;
			default:
				break;
		}
	}
	
	public void setSelectedControl(ButtonNames name){
		((MenuScreenRenderer2) this.renderer).setSelectedControl(name);
		switch(name){
			case buttonClassicControl:
				Settings.gameControl = GameControl.classic;
				break;
			case buttonFlappyControl:
				Settings.gameControl = GameControl.flappy;
				break;
			case buttonWavyControl:
				Settings.gameControl = GameControl.wavy;
				break;
			case buttonBouncingControl:
				Settings.gameControl = GameControl.bouncing;
				break;
			default:
				break;
		}
	}
	
	public void setSelectedMode(ButtonNames name){
		((MenuScreenRenderer2) this.renderer).setSelectedMode(name);
		switch(name){
			case buttonNormalMode:
				Settings.gameMode = GameMode.normal;
				Settings.gameObstacle = GameObstacle.wall;
				break;
			case buttonBlinkingkMode:
				Settings.gameMode = GameMode.blinking;
				Settings.gameObstacle = GameObstacle.wall;
				break;
			case buttonPiranhaMode:
				Settings.gameMode = GameMode.piranha;
				Settings.gameObstacle = GameObstacle.piranha;
				break;
			default:
				Settings.gameMode = GameMode.normal;
				Settings.gameObstacle = GameObstacle.wall;
				break;
		}
	}
	
	public void restart(){
		((MenuScreenRenderer) renderer).restart();
	}
}
