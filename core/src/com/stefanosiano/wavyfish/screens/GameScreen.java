package com.stefanosiano.wavyfish.screens;

import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenBlinkingRenderer;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenClassicUpdater;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenCommonRenderer;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenFlappyUpdater;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenBouncingUpdater;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenUpdater;
import com.stefanosiano.wavyfish.screenHelpers.GameScreenWavyUpdater;
import com.stefanosiano.wavyfish.screenHelpers.TutorialGameScreenRenderer;
import com.stefanosiano.wavyfish.screenHelpers.TutorialGameScreenUpdater;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;

public class GameScreen extends SimpleScreen{
	private GameScreenUpdater updater;
    private GameState gameState;

	public GameScreen(WavyFishGame game){
		super(game, null);
		AnalyticsSender.sendPlayGame(game);

		this.renderer = null;
		int numberOfWallsToFinish = 100;
		int speedUpStep = 2;
		int speedDownStep = 12;

		if(Settings.gameEndless)
			numberOfWallsToFinish = Integer.MAX_VALUE;

		switch (Settings.gameControl){
			case classic:
				if(Settings.NEW_TUT_CLASSIC){
					this.updater = new TutorialGameScreenUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.running;
					this.renderer = new TutorialGameScreenRenderer();
				}
				else{
					this.updater = new GameScreenClassicUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.ready;
				}
				break;
			case flappy:
				if(Settings.NEW_TUT_FLAPPY){
					this.updater = new TutorialGameScreenUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.running;
					this.renderer = new TutorialGameScreenRenderer();
				}
				else{
					this.updater = new GameScreenFlappyUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.ready;
				}
				break;
			case wavy:
				if(Settings.NEW_TUT_WAVY){
					this.updater = new TutorialGameScreenUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.running;
					this.renderer = new TutorialGameScreenRenderer();
				}
				else{
					this.updater = new GameScreenWavyUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.ready;
				}
				break;
			case bouncing:
				if(Settings.NEW_TUT_BOUNCING){
					this.updater = new TutorialGameScreenUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.running;
					this.renderer = new TutorialGameScreenRenderer();
				}
				else{
					this.updater = new GameScreenBouncingUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.ready;
				}
				break;
			default:
				if(Settings.NEW_TUT_CLASSIC){
					this.updater = new TutorialGameScreenUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.running;
					this.renderer = new TutorialGameScreenRenderer();
				}
				else{
					this.updater = new GameScreenClassicUpdater(this, speedUpStep, speedDownStep, numberOfWallsToFinish, game.getCommonApiController());
			    	this.gameState = GameState.ready;
				}
				break;
		}
		
		if(renderer == null){
			if(Settings.gameMode.equals(Enums.GameMode.blinking))
				this.renderer = new GameScreenBlinkingRenderer();
			else
				this.renderer = new GameScreenCommonRenderer();
		}
		setRendererScreen(this);
        ((GameScreenCommonRenderer)renderer).setDifficultyText();
        
    	GameButtonContainer.setButtons(gameState);
		fadeIn(0.4f);
	}
	
	public void prepareDrawCollision(){
		((GameScreenCommonRenderer)renderer).prepareFishCollision();
	}
    
    public void stopShowingTextBackground(){
		((TutorialGameScreenRenderer)renderer).stopShowingTextBackground();
    }

	@Override
	public void render(float delta) {
		updater.update(delta);
		renderer.renderAll(delta);
	}
	
	public GameState getState(){
		return gameState;
	}
	
	public void setState(GameState gameState){
		this.gameState = gameState;
		if(gameState == GameState.running){
			switch(Settings.gameControl){
				case classic:
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFlappyBackground));
					break;
				case flappy:
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishUp));
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishDown));
					break;
				case bouncing:
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishUp));
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishDown));
					break;
				case wavy:
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishUp));
					GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFishDown));
                    GameButtonContainer.activeButtons.remove(GameButtonContainer.findActiveButton(Enums.ButtonNames.buttonFlappyBackground));
					break;
			}
		}
		
		((GameScreenCommonRenderer) this.renderer).stateChanged();
	}
	
	public void restart(){
        ((GameScreenCommonRenderer) renderer).initialize();
		((GameScreenCommonRenderer) renderer).setDifficultyText();
	}
	
	@Override
	public void pause(){
		if(gameState.equals(GameState.running))
			updater.pause();
		super.pause();
	}
}
