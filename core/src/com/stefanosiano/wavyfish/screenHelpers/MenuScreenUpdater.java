package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.wavyfish.experience.ObjectDisabler;
import com.stefanosiano.wavyfish.game.AnalyticsEnums.ShareType;
import com.stefanosiano.wavyfish.game.ShareCallback;
import com.stefanosiano.wavyfish.gameObjects.ExperienceBar;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.SwipeSign;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.screens.MenuScreen;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Initializer;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.ImageType;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class MenuScreenUpdater {
	private List<Enums.ButtonNames> names;
	private MenuScreen menuScreen;
	private SwipeSign swipeSign;
	private ObjectDisabler objectDisabler;
	//we need this flag so we can deny request done by gestures (like disabling buttons) when changing screens
	private boolean getGestures;
	private boolean optionsEnabled, updatable, restartBar;
	private Text textHigherLevel, currentExperienceText, currentLevelText; 
    private ExperienceBar menuExperienceBar;
	
	public MenuScreenUpdater(MenuScreen menuScreen){
		this.objectDisabler = new ObjectDisabler();
		this.names = new ArrayList<Enums.ButtonNames>();
		this.menuScreen = menuScreen;
		this.restartBar = true;
		this.swipeSign = GameObjectContainer.swipeSign;
		this.menuExperienceBar = GameObjectContainer.menuExperienceBar;
		this.textHigherLevel = new Text(TextureLoader.fontWhite, 1f, -1f, false);
		this.textHigherLevel.setCenteredHorizzontally("You need higher level", 800, 680);
		this.currentExperienceText = new Text(TextureLoader.fontWhite, 0.8f, -0.6f, false);
		this.currentExperienceText.set("Experience: " + Experience.getExperience() + "/" + Experience.getNextLevelExperience(Experience.getExperience()), 200, 790);
		this.currentLevelText = new Text(TextureLoader.fontWhite, 0.8f, -0.6f, false);
		this.currentLevelText.setToLeftOf("LVL: " + Experience.getLevel(), 1400, 790);
		GameObjectContainer.clearTexts();
		GameObjectContainer.addText(textHigherLevel);
		GameObjectContainer.addText(currentExperienceText);
		GameObjectContainer.addText(currentLevelText);
		changeText();
		SoundLoader.playMusic(SoundLoader.musicIntro);
		this.objectDisabler.disableMenuFish(Settings.imageType);
		Initializer.updateTextures();
		getGestures = true;
		if(!Settings.TUT_MENU1){
			disableOptionsAtStart();
			if(!Settings.FIRST_SHARE){
				SimpleButton b = GameButtonContainer.findActiveButton(ButtonNames.moreLivesShine);
				b.startBlink(1f);
				b.startRotate(2);
			}
		}
	}
	
	public void restart(){
		this.swipeSign = GameObjectContainer.swipeSign;
		this.menuExperienceBar = GameObjectContainer.menuExperienceBar;

		//restart bar is used to start updating the bar after the fade in effect or after the swipe! 
		if(restartBar){
			menuExperienceBar.setValue(Experience.getPreviousLevelExperience(Experience.getExperience()));
			menuExperienceBar.setRenderingValue(0);
			
			menuExperienceBar.modifyLife(-(Experience.getExperience() - Experience.getPreviousLevelExperience(Experience.getExperience())), 1.2f);
		}
		else{
			menuExperienceBar.setValue(Experience.getExperience());
			menuExperienceBar.setRenderingValue(Experience.getExperience() - Experience.getPreviousLevelExperience(Experience.getExperience()));
		}
		menuExperienceBar.setOffset(Experience.getPreviousLevelExperience(Experience.getExperience()));
		menuExperienceBar.setMaxValue(Experience.getNextLevelExperience(Experience.getExperience()) - menuExperienceBar.getOffset());
		menuExperienceBar.setValueWidth(menuExperienceBar.getRenderingValue()/(float)menuExperienceBar.getMaxValue() * menuExperienceBar.getWidth());
		
	}
	
	public void startUpdate(){
		updatable = true;
		restartBar = false;
	}
	
	public void update(float delta){
		if(updatable){
			if(Settings.TUT_MENU1){
				updateTutorial(delta);
			}
			else
				updateMenu(delta);
		}
		else
			names = GameButtonContainer.getBtnPressed();
	}
	
	private void updateTutorial(float delta) {
		names = GameButtonContainer.getBtnPressed();
		for(Enums.ButtonNames name : names){
			switch(name){
				case hardwareBack:
				case buttonBackgroundTutorial:
					Settings.tutorialShown(SavedItems.showTutMenu1);
					GameButtonContainer.setButtons(GameState.menu);
					disableOptionsAtStart();
					if(!Settings.FIRST_SHARE){
						SimpleButton b = GameButtonContainer.findActiveButton(ButtonNames.moreLivesShine);
						b.startBlink(1.5f);
						b.startRotate(2);;
					}
					break;
				default:
					break;
			}
		}
	}

	private void updateMenu(float delta) {
		swipeSign.update(delta);
		menuExperienceBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		for(Enums.ButtonNames name : names){
			switch(name){
				case buttonFullScreen:
					Settings.stretch();
					menuScreen.resize(0, 0);
					break;
				case buttonSoundEnabled:
					Settings.enableSound();
					break;
				case buttonMusic:
					Settings.enableMusic();
					SoundLoader.playMusic(SoundLoader.musicIntro);
					break;
				case buttonCredits:
					getGestures = false;
					GameButtonContainer.disableAllButtons();
					menuScreen.fadeOut(new Runnable() {
						@Override
						public void run() {
							GameButtonContainer.setButtons(GameState.info);
							menuScreen.getGame().changeScreen(Screens.credits);
							menuScreen.dispose();
						}
					}, 0.4f);
					break;
				case hardwareBack:
					getGestures = false;
					GameButtonContainer.disableAllButtons();
					menuScreen.fadeOut(new Runnable() {
						@Override
						public void run() {
							Gdx.app.exit();
						}
					}, 0.4f);
					break;
				case buttonStartChoose:
					getGestures = false;
					GameButtonContainer.disableAllButtons();
					menuScreen.fadeOut(new Runnable() {
						@Override
						public void run() {
							GameButtonContainer.setButtons(GameState.menu2);
							menuScreen.getGame().changeScreen(Screens.menu2);
							menuScreen.dispose();
						}
					}, 0.4f);
					break;
				case buttonOptions:
					enableOptions(!optionsEnabled);
					break;
				case buttonEarnMoreLives:
					GameButtonContainer.setButtons(GameState.earnMoreLives);
					menuScreen.showEarnMoreLives(true);
					break;
				case buttonShareText:
					menuScreen.getGame().getCommonApiController().showShareProgressBar();
					menuScreen.getGame().getCommonApiController().shareText("Try wavy fish! \nAndroid: https://play.google.com/store/apps/details?id=com.stefanosiano.wavyfish.android",
							"Share this app with your friends!", new ShareCallback(){
						@Override
						public void onShareCancelled() {
							AnalyticsSender.sendGeneralEvent(menuScreen.getGame(), "Share Cancelled", "", 1);
						}

						@Override
						public void onShareClicked(String appPackage, ShareType type) {
							AnalyticsSender.sendShareClicked(menuScreen.getGame(), type, appPackage);
							switch(appPackage){
								case "facebook.katana":
								case "google.apps.plus":
								case "twitter":
									Settings.sharedFirstTime();
									//used to calculate the number of lives exactly
									Experience.calculateNewValues(0);
									GameButtonContainer.setButtons(GameState.menu);
									break;
							}
						}
						
					});
					break;
				case buttonBackground:
					GameButtonContainer.setButtons(GameState.menu);
					menuScreen.showEarnMoreLives(false);
					this.objectDisabler.disableMenuFish(Settings.imageType);
					break;
				default:
					break;
			}
		}
	}

	public void onSwipeLeft(){
		if(getGestures){
			changeImages(true);
			changeText();
		}
	}
	
	public void onSwipeRight(){
		if(getGestures){
			changeImages(false);
			changeText();
		}
	}
	
	private void changeImages(boolean next){
		Settings.changeImages(next);
		TextureLoader.changeTextures();
		objectDisabler.disableMenuFish(Settings.imageType);
		Initializer.updateTextures();
		menuScreen.restart();
	}
	
	private void enableOptions(boolean enabled){
		optionsEnabled = enabled;
		GameButtonContainer.setEnabledButton(ButtonNames.buttonOptions, false);
		if(!enabled){
			GameButtonContainer.findActiveButton(ButtonNames.buttonFullScreen).setupFadeOutTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonMusic).setupFadeOutTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonSoundEnabled).setupFadeOutTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonShareText).setupFadeOutTweens(new Runnable() {
				@Override
				public void run() {GameButtonContainer.setEnabledButton(ButtonNames.buttonOptions, true);}
			}, 0.5f);
		}
		else{
			GameButtonContainer.findActiveButton(ButtonNames.buttonFullScreen).setupFadeInTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonMusic).setupFadeInTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonSoundEnabled).setupFadeInTweens(0.5f);
			GameButtonContainer.findActiveButton(ButtonNames.buttonShareText).setupFadeInTweens(new Runnable() {
				@Override
				public void run() {GameButtonContainer.setEnabledButton(ButtonNames.buttonOptions, true);}
			}, 0.5f);
		}
	}
	
	private void disableOptionsAtStart(){
		optionsEnabled = false;
		GameButtonContainer.findActiveButton(ButtonNames.buttonFullScreen).setupFadeOutTweens(0);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonFullScreen, false);
		GameButtonContainer.findActiveButton(ButtonNames.buttonMusic).setupFadeOutTweens(0);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonMusic, false);
		GameButtonContainer.findActiveButton(ButtonNames.buttonSoundEnabled).setupFadeOutTweens(0);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonSoundEnabled, false);
		GameButtonContainer.findActiveButton(ButtonNames.buttonShareText).setupFadeOutTweens(0);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonShareText, false);
	}
	
	private void changeText(){
		ImageType imageType = Settings.imageType;
		textHigherLevel.updateTextCenteredHorizzontally("");

		switch(Experience.getLevel()){
			case 1:
			case 2:
				if(imageType.equals(Enums.ImageType.horror)){
					textHigherLevel.updateTextCenteredHorizzontally("You need level 3 to unlock this fish");
					break;
				}
			case 3:
			case 4:
			case 5:
				if(imageType.equals(Enums.ImageType.tema)){
					textHigherLevel.updateTextCenteredHorizzontally("You need level 6 to unlock this fish");
					break;
				}
			case 6:
			case 7:
			case 8:
				if(imageType.equals(Enums.ImageType.space)){
					textHigherLevel.updateTextCenteredHorizzontally("You need level 9 to unlock this fish");
					break;
				}
			case 9:
			case 10:
			default:
				break;
		}
	}
}
