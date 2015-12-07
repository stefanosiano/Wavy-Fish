package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.stefanosiano.common.FadingBackground;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.antiCheatSystem.ACS;
import com.stefanosiano.common.antiCheatSystem.SecureValue;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.Background;
import com.stefanosiano.wavyfish.gameObjects.Fish;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.HighScoreContainer;
import com.stefanosiano.wavyfish.gameObjects.LifeBar2;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.wavyfish.gameObjects.Piranha;
import com.stefanosiano.wavyfish.gameObjects.Score;
import com.stefanosiano.wavyfish.gameObjects.WallCouple;
import com.stefanosiano.common.tochange.DataSaver;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;
import com.stefanosiano.wavyfish.utilities.Enums.SecureValueName;

public class GameScreenCommonUpdater {
	private int passedWalls, speedUpStep, speedDownStep, numberOfWallsToFinish, oldScoreValue;
	protected Score score;
	protected LifeBar2 lifeBar;
	private int lives, scoreValue;
	private float multiplier, oldMultiplier;
	private boolean fishCollided;
	private GameState pausedGameState;
	protected Text text;
	protected Text obstaclesText;

	protected Fish fish;
	protected HighScoreContainer highScoreContainer;
	protected Background background, background2;
	protected List<Obstacle> obstacles;
	protected GameScreen gameScreen;
	protected GameState gameState;
	protected boolean highScoreStartedUpdating, highScoreFinishedUpdating, startWin, won;
	
	//to send the time that the user played the game
	protected float analyticsTime;
	
	//ad stuffs
	private CommonApiController adsController;
	private boolean adLoaded;
	private boolean adShown;
	protected float time;
    protected FadingBackground fadingBackground;
	
	//secure data
	private ACS acs;
	private SecureValue sLives, sScoreValue, sMultiplier, sObstacles;
	
	public GameScreenCommonUpdater(final GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, final CommonApiController adsController){
		this.fish = GameObjectContainer.fish;
		this.background = GameObjectContainer.background;
		this.background2 = GameObjectContainer.background2;
		this.score = GameObjectContainer.score;
		this.lifeBar = GameObjectContainer.lifeBar;
		this.highScoreContainer = GameObjectContainer.highScoreContainer;
		this.passedWalls = 0;
		this.speedUpStep = speedUpStep;
		this.speedDownStep = speedDownStep;
    	this.lives = Experience.getLives();
    	this.gameScreen = gameScreen;
    	this.fishCollided = false;
		this.text = new Text(TextureLoader.fontWhite, 1f, -1f, false);
		this.numberOfWallsToFinish = numberOfWallsToFinish;
		this.obstaclesText = new Text(TextureLoader.fontYellow, 1f, -1f, false);
		this.scoreValue = score.getScoreValue();
		this.multiplier = score.getMultiplier();
        this.fadingBackground = GameObjectContainer.fadingBackground;
		
		Runnable onCheat = new Runnable() {
			boolean shown = false;
			@Override
			public synchronized void run() {
				if(shown)
					return;
				shown = true;
				gameScreen.getGame().getCommonApiController().showOnCheat();
			}
		};
		this.acs = new ACS(onCheat);
    	this.sLives = new SecureValue(Experience.getLives(), acs, SecureValueName.lives);
    	this.sScoreValue = new SecureValue(scoreValue, acs, SecureValueName.scoreValue);
    	this.sMultiplier = new SecureValue(multiplier, acs, SecureValueName.scoreMultiplier);
    	this.sObstacles = new SecureValue(passedWalls, acs, SecureValueName.obstaclesPassed);
    	
		obstaclesText.setCenteredHorizzontally("", 1300, 20);
		obstaclesText.updateTextCenteredHorizzontally(0 + "/" + numberOfWallsToFinish);
		GameObjectContainer.clearTexts();
    	
    	this.adsController = adsController;
    	this.time = Settings.timeForAds;
    	this.adLoaded = false;
    	this.adShown = false;
		this.highScoreStartedUpdating = false;
		this.highScoreFinishedUpdating = false;
        this.startWin = false;
		this.won = false;

		this.obstacles = new ArrayList<Obstacle>();
		switch(Settings.gameObstacle){
			case wall:
				for(WallCouple wallCouple : GameObjectContainer.walls)
					this.obstacles.add(wallCouple);
				break;
			case piranha:
				for(Piranha piranha : GameObjectContainer.piranhas)
					this.obstacles.add(piranha);
				break;
			default:
				for(WallCouple wallCouple : GameObjectContainer.walls)
					this.obstacles.add(wallCouple);
		}
		GameObjectContainer.createObstacles(obstacles);
		loadInterstitial();
	}

	protected void saveHighScore(SavedItems item) {
		String savePath = item + "";
		AnalyticsSender.sendMeanScore(gameScreen.getGame(), score.getScoreValue());
		int tmp;
		try{tmp = Integer.parseInt(DataSaver.load(savePath));}catch(Exception e){tmp = 0;}
		if(tmp < score.getScoreValue()){
			AnalyticsSender.sendHighScore(gameScreen.getGame(), score.getScoreValue(), score.getScoreValue() - tmp);
			DataSaver.save(savePath, score.getScoreValue() + ""); 
		}
	}

	protected void speedDown(int x) {
		GameObjectContainer.speedDownToStart();
		//normally i get 15 speedUp (45/3)
		x = 7 * x;
		for(int i = 0; i < x; i++)
			GameObjectContainer.speedUp();
	}

	protected void fishCollide() {
		//fishCollided is false if it's never been called before! (but now fish collided if i'm in this function)
		if(!fishCollided){
			gameScreen.prepareDrawCollision();
			sLives.set(lives, lives - 1);
			lives--;
			oldMultiplier = score.getMultiplier();
			score.resetMultiplier();
			multiplier = score.getMultiplier();
			sMultiplier.set(oldMultiplier, multiplier);
			
			//life bar is decreased by the right percentage
			lifeBar.modifyLife(lifeBar.getMaxLife()/Experience.getLives(), 0.4f);
			if(Settings.SOUND_ENABLED)
				SoundLoader.slam.play();
			if(lives <= 0){
				fish.die();
				GameObjectContainer.stopAll();
				if(Settings.SOUND_ENABLED)
					SoundLoader.dead.play();
				this.highScoreStartedUpdating = false;
				this.highScoreFinishedUpdating = false;
				
				saveHighScore(SavedItems.highScore);

				int tmp;
				try{tmp = Integer.parseInt(DataSaver.load(SavedItems.highScore + ""));}catch(Exception e){tmp = 0;}
				this.highScoreContainer.initialize(score, Experience.getLevel(), Experience.getExperience(), Experience.getNextLevelExperience(Experience.getExperience()), 
						Experience.getPreviousLevelExperience(Experience.getExperience()), tmp);
				AnalyticsSender.sendPlayGameWithOptions(gameScreen.getGame(), Settings.difficulty, Settings.gameMode, Settings.gameControl, analyticsTime);
				GameButtonContainer.setButtons(GameState.noButtons);
				gameScreen.setState(GameState.highScore);
				Experience.calculateNewValues(score.getScoreValue());
				highScoreContainer.setNewValues(Experience.getExperience());
			}
		}
		fishCollided = true;
	}

	protected void wallPassed() {
		this.sObstacles.set(passedWalls, passedWalls + 1);
		passedWalls++;
		obstaclesText.updateTextCenteredHorizzontally(passedWalls + "/" + numberOfWallsToFinish);
		GameObjectContainer.removeObstacle(passedWalls, numberOfWallsToFinish, speedDownStep);
		if(passedWalls % speedUpStep == 0){
			GameObjectContainer.speedUp();
		}
		if(passedWalls % speedDownStep == 0){
			speedDown(passedWalls / speedDownStep);
		}
		
		if(!fishCollided){
			oldScoreValue = score.getScoreValue();
			oldMultiplier = score.getMultiplier();
			scoreValue = score.update(100);
			multiplier = score.getMultiplier();
			sMultiplier.set(oldMultiplier, multiplier);
			sScoreValue.set(oldScoreValue, scoreValue);
			if(Settings.SOUND_ENABLED)
				SoundLoader.bubble.play();
		}

		if(passedWalls >= numberOfWallsToFinish)
			startWinning();
	}

    protected void startWinning() {
        if(Settings.SOUND_ENABLED)
            SoundLoader.victory.play();
        SoundLoader.stopMusics();

        GameButtonContainer.setButtons(GameState.noButtons);
        gameScreen.setState(GameState.startWinning);
        fadingBackground.setValues(Color.WHITE, 0, 0, 1600, 900);
        fadingBackground.startFading(0, 1, 1.5f, new Runnable() {
            @Override
            public void run() {
                win();
            }
        });
    }

	protected void win() {
		won = true;
        GameObjectContainer.stopAll();
        GameButtonContainer.setButtons(GameState.noButtons);
        gameScreen.setState(GameState.finishWinning);
        fadingBackground.setValues(Color.WHITE, 0, 0, 1600, 900);
        fadingBackground.startFading(1, 0, 1.5f, null);
	}

    protected void finishedWin() {
        this.highScoreStartedUpdating = false;
        this.highScoreFinishedUpdating = false;

        saveHighScore(SavedItems.highScore);

        int tmp;
        try{tmp = Integer.parseInt(DataSaver.load(SavedItems.highScore + ""));}catch(Exception e){tmp = 0;}
        this.highScoreContainer.initialize(score, Experience.getLevel(), Experience.getExperience(), Experience.getNextLevelExperience(Experience.getExperience()),
                Experience.getPreviousLevelExperience(Experience.getExperience()), tmp);
        AnalyticsSender.sendPlayGameWithOptions(gameScreen.getGame(), Settings.difficulty, Settings.gameMode, Settings.gameControl, analyticsTime);
        GameButtonContainer.setButtons(GameState.noButtons);
        gameScreen.setState(GameState.highScoreWon);
        Experience.calculateNewValues(score.getScoreValue());
        highScoreContainer.setNewValues(Experience.getExperience());
    }

	protected void wallFinishedPassed() {
		fishCollided = false;
	}
	
	protected void reset(){
		int oldPassedWalls = passedWalls;
		oldScoreValue = score.getScoreValue();
		oldMultiplier = score.getMultiplier();
		GameObjectContainer.restart();
		gameScreen.restart();

		this.fishCollided = false;
		this.sLives.set(lives, Experience.getLives());
		this.lives = Experience.getLives();
		this.passedWalls = 0;
		this.analyticsTime = 0;
        this.fish = GameObjectContainer.fish;
        this.background = GameObjectContainer.background;
        this.background2 = GameObjectContainer.background2;
		this.score = GameObjectContainer.score;
		this.lifeBar = GameObjectContainer.lifeBar;
		this.highScoreContainer = GameObjectContainer.highScoreContainer;
		this.highScoreStartedUpdating = false;
		this.highScoreFinishedUpdating = false;
		
		this.scoreValue = score.getScoreValue();
		this.multiplier = score.getMultiplier();
		this.sScoreValue.set(oldScoreValue, scoreValue);
		this.sMultiplier.set(oldMultiplier, multiplier);
		this.sObstacles.set(oldPassedWalls, passedWalls);
		
		this.won = false;
		obstaclesText.updateTextCenteredHorizzontally(0 + "/" + numberOfWallsToFinish);
		adLoaded = false;
		loadInterstitial();

		obstacles.clear();
		switch(Settings.gameObstacle){
			case wall:
				for(WallCouple wallCouple : GameObjectContainer.walls)
					this.obstacles.add(wallCouple);
				break;
			case piranha:
				for(Piranha piranha : GameObjectContainer.piranhas)
					this.obstacles.add(piranha);
				break;
			default:
				for(WallCouple wallCouple : GameObjectContainer.walls)
					this.obstacles.add(wallCouple);
		}
		GameObjectContainer.createObstacles(obstacles);
	}
	
	protected void loadInterstitial(){
		//if(time > 0) //loading after 25 secs may slow the phone, so i load it after 5 sec (just to be "sure" that it's not a mistake)
			if(!adLoaded){
				adShown = false;
				adsController.loadInterstitialAd();
				adLoaded = true;
			}
	}
	
	protected void showInterstitial(){
		//adTime must be 10+ of loading (admob) (let's put 15 sec)
		if(time > 40)
			if(!adShown){
				adsController.showInterstitialAd();
				adShown = true;
				adLoaded = false;
				Settings.totalPlayTime += time;
				time = 0;
			}
	}
	
	public void pause(){
		GameObjectContainer.clearTexts();
		text.setScale(1.2f, -1.1f);
		text.setCenteredHorizzontally("Pause", 800, 320);
		GameObjectContainer.addText(text);
		GameObjectContainer.addText(obstaclesText);
		pausedGameState = gameState;
		gameScreen.prepareCount(3, TextureLoader.fontWhite, 1.2f, -1.1f, 800, 500, "Resuming in 3.00");
		GameButtonContainer.setButtons(GameState.pause);
		gameScreen.setState(GameState.pause);
	}
	
	protected void resume(){
		GameButtonContainer.setButtons(pausedGameState);
		gameScreen.setState(pausedGameState);
		GameObjectContainer.addText(obstaclesText);
	}
}
