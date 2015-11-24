package com.stefanosiano.wavyfish.utilities;

public interface Enums {
	public enum ButtonNames{
		//menu 1
		buttonStartChoose, buttonCredits, buttonSoundEnabled, buttonFullScreen, buttonMusic, buttonOptions, buttonShareText, buttonBackgroundTutorial,
		buttonEarnMoreLives, moreLivesShine,
		//menu 2
		buttonContinue, 
		buttonEasy, buttonMedium, buttonHard, buttonCrazy,  
		buttonClassicControl, buttonFlappyControl, buttonWavyControl, buttonBouncingControl,
		buttonNormalMode, buttonBlinkingkMode, buttonPiranhaMode,
		//game
		buttonFishUp, buttonFishDown, buttonBackground, buttonRestart, buttonBackToMenu, buttonResume, buttonFlappyBackground, shareScoreShine,
		arrowUp, arrowDown, hardwareBack, buttonPauseMusic, buttonPauseSoundEnabled, buttonPauseFullScreen, buttonLostBackToMenu, buttonShareScore,
		buttonTutorialStartGame, 
		//info
		buttonBack, buttonInfoArrowLeft, buttonInfoArrowRight;
	}
	
	public enum GameControl{
		classic, flappy, wavy, bouncing;
	}
	
	public enum GameMode{
		normal, blinking, piranha;
	}
	
	public enum GameObstacle{
		wall, piranha;
	}
	
	public enum FishState{
		ready, alive, dead;
	}
	
	public enum GameState{
		info, menu, menu2, tutorial, ready, running, lost, highScore, pause, resuming, noButtons, earnMoreLives, newTutorialFinishing;
	}
	
	public enum InfoState{
		levelInfo, flappyInfo, wavyInfo, bouncyInfo, classicInfo, credits;
	}
	
	public enum Difficulty{
		easy, medium, hard, crazy;
	}
	
	public enum SavedItems{
		highScore("0"),
		stretch("false"),
		soundEnabled("true"),
		musicEnabled("true"),
		imageTypeIndex("0"),
		experience("0"), 
		lives("1"), 
		level("1"),
		screenshotCounter("0"),
		showTutMenu1("true"), 
		showTutMenu2("true"), 
		showNewTutClassic("true"), 
		showNewTutFlappy("true"), 
		showNewTutWavy("true"),
		showNewTutBouncing("true"),
		firstShare("false"),
		saveVersion("0");
		
		String defaultValue;
		SavedItems(String x){
			defaultValue = x;
		}
		public String getDefault(){
			return defaultValue;
		}
		//old, but i cannot reuse them!
		//showTutClassic, showTutFlappy, showTutWavy, oldLevel, install
	}
	
	public enum Screens{
		splash, menu, menu2, game, credits;
	}
	
	//when i add the image type i have to change MenuScreenRenderer, MenuScreenUpdater, ObjectDisabler
	public enum ImageType{
		original, horror, tema, space;
	}
	
	public enum Gestures{
		left, top, tight, bottom;
	}
	
	public enum EnumPermissions{
		shareScore(0);

        private int requestCode;
        EnumPermissions(int requestCode){this.requestCode = requestCode;}
        public int getRequestCode(){return requestCode;}
	}
	
	public enum SecureValueName{
		lives, scoreValue, scoreMultiplier, obstaclesPassed;
	}
}
