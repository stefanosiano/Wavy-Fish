package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.experience.ObjectDisabler;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.screens.MenuScreen2;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Initializer;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class MenuScreenUpdater2 {
	private List<Enums.ButtonNames> names;
	private MenuScreen2 menuScreen2;
	private ObjectDisabler objectDisabler;
	private Text textDifficulty, textControl, textMode;
	
	public MenuScreenUpdater2(MenuScreen2 menuScreen2){
		this.objectDisabler = new ObjectDisabler();
		this.names = new ArrayList<Enums.ButtonNames>();
		this.menuScreen2 = menuScreen2;
		this.objectDisabler.disableMenuButtons();
		
    	SimpleButton button = GameButtonContainer.findActiveButton(ButtonNames.buttonEasy);
    	float textY = button.getY() - 60;
    	float panelW = button.getWidth();
    	float panelX = button.getX() + panelW/2;
    	float panelX2 = panelX + panelW + 120;
    	float panelX3 = panelX2 + panelW + 120;

    	float scaleX = 0.75f;
    	float scaleY = -0.65f;
		this.textDifficulty = new Text(TextureLoader.fontWhite, scaleX, scaleY, false);
		this.textControl = new Text(TextureLoader.fontWhite, scaleX, scaleY, false);
		this.textMode = new Text(TextureLoader.fontWhite, scaleX, scaleY, false);
		this.textDifficulty.setCenteredHorizzontally("Difficulty", panelX, textY);
		this.textControl.setCenteredHorizzontally("Controls", panelX2, textY);
		this.textMode.setCenteredHorizzontally("Modes", panelX3, textY);
		GameObjectContainer.clearTexts();
		GameObjectContainer.addText(textDifficulty);
		GameObjectContainer.addText(textControl);
		GameObjectContainer.addText(textMode);

		if(Settings.TUT_MENU2)
	    	GameButtonContainer.setButtons(GameState.tutorial);
	}
	
	public void update(float delta){
		if(Settings.TUT_MENU2)
			updateTutorial(delta);
		else
			updateMenu(delta);
	}

	private void updateTutorial(float delta) {
		names = GameButtonContainer.getBtnPressed();
		for(Enums.ButtonNames name : names){
			switch(name){
				case hardwareBack:
				case buttonBackgroundTutorial:
					Settings.tutorialShown(SavedItems.showTutMenu2);
					GameButtonContainer.setButtons(GameState.menu2);
					menuScreen2.setSelectedDifficulty(ButtonNames.buttonEasy);
					menuScreen2.setSelectedControl(ButtonNames.buttonWavyControl);
					menuScreen2.setSelectedMode(ButtonNames.buttonNormalMode);
					break;
				default:
					break;
			}
		}
	}

	private void updateMenu(float delta) {
		names = GameButtonContainer.getBtnPressed();
		for(Enums.ButtonNames name : names){
			switch(name){
				case buttonContinue:
					play();
					break;
				case buttonEasy:
				case buttonMedium:
				case buttonHard:
				case buttonCrazy:
					menuScreen2.setSelectedDifficulty(name);
					break;
				case buttonClassicControl:
				case buttonFlappyControl:
				case buttonWavyControl:
				case buttonBouncingControl:
					menuScreen2.setSelectedControl(name);
					break;
				case buttonBlinkingkMode:
				case buttonNormalMode:
				case buttonPiranhaMode:
					menuScreen2.setSelectedMode(name);
					break;
				case buttonBack:
				case hardwareBack:
					back(menuScreen2, Screens.menu);
				default:
					break;
			}
		}
	}
	
	private void back(final SimpleScreen screen, final Screens screenTo){
		GameButtonContainer.disableAllButtons();
		screen.fadeOut(new Runnable() {
			@Override
			public void run() {
				screen.getGame().changeScreen(screenTo);
				screen.dispose();
			}
		}, 0.4f);
	}
	
	private void play(){
		GameButtonContainer.disableAllButtons();
		Initializer.updateTextures();
		Initializer.updateDifficulty();
		SoundLoader.stopMusics();
		menuScreen2.fadeOut(new Runnable() {
			@Override
			public void run() {
				GameButtonContainer.setButtons(GameState.ready);
				menuScreen2.getGame().changeScreen(Screens.game);
				menuScreen2.dispose();
			}
		}, 0.4f);
	}
}
