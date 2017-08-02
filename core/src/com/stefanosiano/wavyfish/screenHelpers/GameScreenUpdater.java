package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.wavyfish.game.AnalyticsEnums.ShareType;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.utils.ScreenshotFactory;
import com.stefanosiano.common.utils.SimpleCallback;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Enums.EnumPermissions;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class GameScreenUpdater extends GameScreenCommonUpdater {
	protected List<ButtonNames> names;
	private float resumeTime, highScoreTime, finishingTime;
	private Music music;
	private boolean shareButtonShown;
	
	public GameScreenUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController){
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
		this.names = new ArrayList<ButtonNames>();
		this.resumeTime = 0;
		this.highScoreTime = 0;
        this.analyticsTime = 0;
        this.finishingTime = 0;
		this.shareButtonShown = false;
		GameObjectContainer.clearTexts();
		text.setScale(1f, -1f);
		text.setCentered(gameScreen.getGame().getString("tap_start"), 800, 450);
		GameObjectContainer.addText(text);
		GameObjectContainer.addText(obstaclesText);
		
		switch(Settings.imageType){
			case original:
				music = SoundLoader.musicOriginal;
				break;
			case horror:
				music = SoundLoader.musicHorror;
				break;
			case tema:
				music = SoundLoader.musicTema;
				break;
			case space:
				music = SoundLoader.musicSpace;
				break;
			default:
				music = SoundLoader.musicOriginal;
				break;
		}
		SoundLoader.playMusic(music);
	}
	
	@Override
	protected void reset(){
		super.reset();
		this.shareButtonShown = false;
        if(Settings.SOUND_ENABLED && !music.isPlaying())
            SoundLoader.playMusic(music);
	}
	
	public void update(float delta){
		analyticsTime += delta;
		gameState = gameScreen.getState();
		switch(gameState){
			case ready:
				updateReady(delta);
				break;
			case running:
				updateRunning(delta);
				break;
            case highScoreWon:
			case highScore:
				updateHighScore(delta);
				break;
            case won:
			case lost:
				updateLost(delta);
				break;
			case pause:
				updatePause(delta);
				break;
			case resuming:
				updateResuming(delta);
				break;
            case startWinning:
                updateWinning(delta);
                break;
            case finishWinning:
                updateFinishWinning(delta);
                break;
			default:
				break;
		}
	}

	private void updateReady(float delta) {
		time += delta;
		names = GameButtonContainer.getBtnPressed();
		for(ButtonNames name : names){
			//System.out.println(name);
			switch(name){
				case buttonBackground:
					GameObjectContainer.clearTexts();
					GameObjectContainer.addText(obstaclesText);
					GameButtonContainer.setButtons(GameState.running);
					gameScreen.setState(GameState.running);
				default:
					break;
			}
		}
	}

	protected void updateRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(Obstacle ob : obstacles)
			ob.update(delta);
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFishUp:
					fish.goUp(delta);
					break;
				case buttonFishDown:
					fish.goDown(delta);
					break;
				case hardwareBack:
					pause();
					break;
				default:
					break;
			}
		}
		if(names.size() == 0)
			fish.update(delta);

		//Called everytime if the fish collides
		if(!fishCollided && GameObjectContainer.fishCollide()){
			fishCollide();
			return;
		}

		//Called once when half of fish pass the left border of wall
		if(GameObjectContainer.wallPassed()){
			wallPassed();
			return;
		}
		
		//Called once when the fish pass the right border of wall
		if(!fishPassed && GameObjectContainer.wallFinishedPassed()){
			wallFinishedPassed();
		}
		
	}

	private void updateHighScore(float delta) {
		showInterstitial();
		names = GameButtonContainer.getBtnPressed();
		lifeBar.update(delta);
		if(!highScoreStartedUpdating){
			highScoreTime += delta;
			if(highScoreTime >= 1f){
				highScoreStartedUpdating = true;
				highScoreTime = 0;
				highScoreContainer.getContinueText().startBlink(0.6f);
			}
			return;
		}
		if(!highScoreFinishedUpdating && (score.getScoreValue() > 0) && Experience.getLevel() < Experience.getMaxLevel()){
			this.highScoreContainer.update(delta);
			highScoreTime += delta;
			if(highScoreTime >= 2.5f){
				highScoreFinishedUpdating = true;
				highScoreTime = 0;
			}
			return;
		}

		if(!shareButtonShown){
			highScoreContainer.setShowContinueText(true);
			GameButtonContainer.setButtons(GameState.highScore);
			SimpleButton b = GameButtonContainer.findActiveButton(ButtonNames.shareScoreShine);
			b.startBlink(1.5f);
			b.startRotate(2);
			shareButtonShown = true;
		}
		
		for(ButtonNames name : names){
			switch(name){
				case hardwareBack:
				case buttonBackground:
					GameObjectContainer.clearTexts();
					if(won){
						text.setScale(1.2f, -1.1f);
						text.setCenteredHorizzontally(gameScreen.getGame().getString("you_win"), 800, 320);
					}
					else{
						text.setScale(1.2f, -1.1f);
						text.setCenteredHorizzontally(gameScreen.getGame().getString("you_lost"), 800, 320);
					}
					
					GameObjectContainer.addText(text);
					GameButtonContainer.setButtons(GameState.lost);
                    if(won)
                        gameScreen.setState(GameState.won);
                    else
                        gameScreen.setState(GameState.lost);
					break;
				case buttonShareScore:
					final Pixmap pixmap = ScreenshotFactory.getScreenshot();
					gameScreen.getGame().getCommonApiController().showShareProgressBar();	
					gameScreen.getGame().getCommonApiController().askPermission(EnumPermissions.shareScore, new SimpleCallback() {
						@Override
						public void onActionCompleted(boolean result) {
							if(result){
								String path = ScreenshotFactory.saveScreenshot(pixmap, true);
								if(path != null && !path.equals("")){
									gameScreen.getGame().getCommonApiController().shareImage(gameScreen.getGame().getString("share_image"), "Share your score!", path, null);
									AnalyticsSender.sendShare(gameScreen.getGame(), ShareType.score, null);			
								}
							}
							gameScreen.getGame().getCommonApiController().hideShareProgressBar();
						}
					});
					break;
				default:
					break;
			}
		}
	}

	private void updateLost(float delta) {
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();

		for(ButtonNames name : names){
			switch(name){
				case buttonRestart:
					reset();
					GameObjectContainer.clearTexts();
					text.setScale(1f, -1f);
					text.setCentered(gameScreen.getGame().getString("tap_start"), 800, 450);
					GameObjectContainer.addText(text);
					GameObjectContainer.addText(obstaclesText);
					GameButtonContainer.setButtons(GameState.ready);
					gameScreen.setState(GameState.ready);
					break;
				case hardwareBack:
				case buttonLostBackToMenu:
					GameButtonContainer.disableAllButtons();
					SoundLoader.stopMusics();
					Settings.timeForAds = time;
					gameScreen.fadeOut(new Runnable() {
                        @Override
                        public void run() {
                            gameScreen.getGame().changeScreen(Screens.menu);
                            gameScreen.dispose();
                            GameObjectContainer.restart();
                        }
                    }, 0.5f);
					break;
				default:
					break;
			}
		}
	}

    private void updateWinning(float delta) {
        time += delta;
        background.update(delta);
        background2.update(delta);
        lifeBar.update(delta);
        fadingBackground.update(delta);
        names = GameButtonContainer.getBtnPressed();

        for(Obstacle ob : obstacles)
            ob.update(delta);
    }

    private void updateFinishWinning(float delta) {
        time += delta;
        fadingBackground.update(delta);
        names = GameButtonContainer.getBtnPressed();

        finishingTime += delta;
        if(finishingTime >= 3f){
            finishingTime = 0;
            finishedWin();
        }
    }

	private void updatePause(float delta) {
		time += delta;
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		for(ButtonNames name : names){
			switch(name){
				case buttonResume:
				case hardwareBack:
					GameObjectContainer.clearTexts();
					GameObjectContainer.addText(obstaclesText);
					resumeTime = 0;
					GameButtonContainer.setButtons(GameState.resuming);
					gameScreen.setState(GameState.resuming);
					break;
				case buttonBackToMenu:
					GameButtonContainer.disableAllButtons();
					Settings.timeForAds = time;
					SoundLoader.stopMusics();
					gameScreen.fadeOut(new Runnable(){
						@Override
						public void run() {
							GameObjectContainer.clearTexts();
							gameScreen.getGame().changeScreen(Screens.menu);
							gameScreen.dispose();
							GameObjectContainer.restart();
						}
					}, 0.5f);
					break;
				case buttonPauseFullScreen:
					Settings.stretch();
					gameScreen.resize(0, 0);
					break;
				case buttonPauseSoundEnabled:
					Settings.enableSound();
					break;
				case buttonPauseMusic:
					Settings.enableMusic();
					SoundLoader.playMusic(music);
					break;
				default:
					break;
			}
		}
	}

	private void updateResuming(float delta) {
		time += delta;
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		resumeTime += delta;
		if(resumeTime >= 3)
			resume();
	}
}
