package com.stefanosiano.wavyfish.screenHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;
import com.stefanosiano.wavyfish.utilities.Initializer;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;
import com.stefanosiano.wavyfish.utilities.Settings;

public class TutorialGameScreenUpdater extends GameScreenUpdater {
	private boolean clickedButtonFishUp, clickedButtonFishDown, clickedButtonFlappy, clickedButtonWavy, startButtonShowing;
	private float touchY, timeToStart;
	private Text instruction1, readyToGo;
    private I18NBundle tutorialStrings;

	public TutorialGameScreenUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController) {
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
        FileHandle baseFileHandle = Gdx.files.internal("data/tutorial_strings");
        tutorialStrings = I18NBundle.createBundle(baseFileHandle);
		GameObjectContainer.clearTexts();
		
		clickedButtonFishUp = false;
		clickedButtonFishDown = false;
		clickedButtonFlappy = false;
		clickedButtonWavy = false;
		startButtonShowing = false;
		timeToStart = 0;
		setValues();
		instruction1 = new Text(TextureLoader.fontWhite, 0.8f, -0.8f, true);
		readyToGo = new Text(TextureLoader.fontWhite, 0.8f, -0.8f, false);
		readyToGo.setCenteredHorizzontally(tutorialStrings.format("start"), 800, 830);

        float textY = 390;
        float textX = 310;
        float textW = 980;
		switch (Settings.gameControl){
			case classic:
				this.instruction1.set(tutorialStrings.format("classic1"), textX, textY, textW, Align.center);
				break;
			case flappy:
                this.instruction1.set(tutorialStrings.format("flappy"), textX, textY, textW, Align.center);
				break;
			case wavy:
                this.instruction1.set(tutorialStrings.format("wavy"), textX, textY, textW, Align.center);
				break;
			case bouncing:
                this.instruction1.set(tutorialStrings.format("bouncy"), textX, textY, textW, Align.center);
				fish.startBounce();
				break;
			default:
                this.instruction1.set(tutorialStrings.format("classic1"), textX, textY, textW, Align.center);
                break;
		}
		GameObjectContainer.addText(instruction1);
	}

	
	@Override
	public void update(float delta){
		gameState = gameScreen.getState();
		switch(gameState){
			case running:
				switch (Settings.gameControl){
					case classic:
						updateClassicRunning(delta);
						break;
					case flappy:
						updateFlappyRunning(delta);
						break;
					case wavy:
						updateWavyRunning(delta);
						break;
					case bouncing:
						updateBouncyRunning(delta);
						break;
					default:
						updateClassicRunning(delta);
						break;
				}
				//updateRunning(delta);
				break;
			default:
				break;
		}
	}

	protected void updateClassicRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFishUp:
					if(!clickedButtonFishUp){
						clickedButtonFishUp = true;
						this.instruction1.updateOnlyText(tutorialStrings.format("classic2"));
					}
					fish.goUp(delta);
					break;
				case buttonFishDown:
					if(clickedButtonFishUp){
						fish.goDown(delta);
						if(!clickedButtonFishDown){
							gameScreen.stopShowingTextBackground();
							this.instruction1.updateOnlyText("");
							GameObjectContainer.clearTexts();
							GameObjectContainer.addText(readyToGo);
							clickedButtonFishDown = true;
							GameButtonContainer.setButtons(GameState.newTutorialFinishing);
						}
					}
					break;
				case buttonTutorialStartGame:
					if(clickedButtonFishDown){
						start();
					}
					break;
				default:
					break;
			}
		}
		if(names.size() == 0)
			fish.update(delta);
	}

	protected void updateFlappyRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFlappyBackground:
					fish.flap();
					if(Settings.SOUND_ENABLED)
						SoundLoader.flap.play();
					if(!clickedButtonFlappy){
						gameScreen.stopShowingTextBackground();
						this.instruction1.updateOnlyText("");
						GameObjectContainer.clearTexts();
						GameObjectContainer.addText(readyToGo);
						clickedButtonFlappy = true;
						GameButtonContainer.setButtons(GameState.newTutorialFinishing);
					}
				break;
					
				case buttonTutorialStartGame:
					if(clickedButtonFlappy){
						start();
					}
					break;
					
				default:
					break;
			}
		}
		fish.updateFlappy(delta);
	}

	protected void updateBouncyRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFlappyBackground:
					fish.changeBounceDirection();
					if(!clickedButtonFlappy){
						gameScreen.stopShowingTextBackground();
						this.instruction1.updateOnlyText("");
						GameObjectContainer.clearTexts();
						GameObjectContainer.addText(readyToGo);
						clickedButtonFlappy = true;
						GameButtonContainer.setButtons(GameState.newTutorialFinishing);
					}
				break;
					
				case buttonTutorialStartGame:
					if(clickedButtonFlappy){
						start();
					}
					break;
					
				default:
					break;
			}
		}

		fish.updateBounce(delta);
	}

	protected void updateWavyRunning(float delta) {
		time += delta;
		timeToStart += delta;
		background.update(delta);
		background2.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFlappyBackground:
					if(!clickedButtonWavy){
						clickedButtonWavy = true;
						timeToStart = 0;
					}
				break;
				case buttonTutorialStartGame:
					if(clickedButtonWavy){
						start();
					}
					break;
				default:
					break;
			}
		}

		touchY = Gdx.input.getY();
		touchY = touchY - (int)(ScreenConfig.HWHEIGHT - ScreenConfig.RENDERINGHEIGHT) / 2;
		touchY =  touchY / ScreenConfig.SCALEY;
		fish.updateWavy(delta, touchY);
		
		if(!startButtonShowing && timeToStart > 2 && clickedButtonWavy){
			gameScreen.stopShowingTextBackground();
			this.instruction1.updateOnlyText("");
			GameObjectContainer.clearTexts();
			GameObjectContainer.addText(readyToGo);
			startButtonShowing = true;
			GameButtonContainer.setButtons(GameState.newTutorialFinishing);
		}
	}
	
	private void start(){
		switch (Settings.gameControl){
			case classic:
				Settings.tutorialShown(SavedItems.showNewTutClassic);
				break;
			case flappy:
				Settings.tutorialShown(SavedItems.showNewTutFlappy);
				break;
			case wavy:
				Settings.tutorialShown(SavedItems.showNewTutWavy);
				break;
			case bouncing:
				Settings.tutorialShown(SavedItems.showNewTutBouncing);
				break;
			default:
				Settings.tutorialShown(SavedItems.showNewTutClassic);
				break;
		}
		GameButtonContainer.disableAllButtons();
		Initializer.updateTextures();
		Initializer.updateDifficulty();
		gameScreen.fadeOut(new Runnable() {
			@Override
			public void run() {
				gameScreen.getGame().changeScreen(Screens.game);
				gameScreen.dispose();
			}
		}, 0.4f);
	}
	
	private void setValues(){
		switch(Settings.difficulty){
			case easy:
				this.fish.setFlapValues(2000, -600);
				break;
			case medium:
				this.fish.setFlapValues(2350, -700);
				break;
			case hard:
				this.fish.setFlapValues(2700, -800);
				break;
			case crazy:
				this.fish.setFlapValues(3050, -900);
				break;
			default:
				this.fish.setFlapValues(2000, -600);
				break;
		}
	}
	
	@Override
	public void pause(){}
	
	@Override
	protected void resume(){}
}
