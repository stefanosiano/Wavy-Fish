package com.stefanosiano.wavyfish.utilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.ImageType;

public class Initializer {
    private static int midY;
    private static int width;
    private static int height;

	//fish variables
    private static TextureRegion fish0 = TextureLoader.fish0;
    private static TextureRegion fish1 = TextureLoader.fish1;
    private static TextureRegion fish2 = TextureLoader.fish2;
    private static TextureRegion fish3 = TextureLoader.fish3;
    private static TextureRegion fish4 = TextureLoader.fish4;
    private static TextureRegion fish5 = TextureLoader.fish5;
    private static TextureRegion fish6 = TextureLoader.fish6;
    private static TextureRegion fish7 = TextureLoader.fish7;
    private static PlayMode playmode = PlayMode.LOOP;
    private static float fishFrameDuration = 0.1f;
    private static int fishX = 90;
    private static int fishY = ScreenConfig.MIDPOINTY - 50;
    private static int fishWidth = 200;
    private static int fishHeight = 100;
    private static int fishMinY = 100;
    private static int fishMaxY = 700;
    private static int fishMaxSpeed = 1000;
    private static int fishCurrentAcceleration = 1500;
    private static float fishSpeedUpFactor = 1.05f;
    private static int fishSpeedX = 0;
    private static int fishSpeedY = 0;
    private static float fishCircleXGap = 150;
    private static float fishCircleYGap = 50;
    private static float fishCircleRadius = 40;
    private static float fishMaxSpeedSpeedUpFactor = 0;
	
	//backgrounds variables
	private static float bgX = 0;
	private static float bgY = 0;
	private static float bgSpeedY= 0;
	private static float bgSpeedUpFactor = 1.05f;
	private static int bgWidth = ScreenConfig.VIRTUALWIDTH;
	private static int bgHeight = ScreenConfig.VIRTUALHEIGHT;
	private static int bgSpeedX = -500;
	private static float bgMaxSpeed = -3000;
	
	//walls variables
	private static float wallX = 2000;
	private static float wallY = 0;
	private static float wallSpeedUpFactor = 1.05f;
	private static int wallSpeedX = -500;
	private static int wallWidth = 50;
	private static int wallYGap = 220;
	private static int wallXGap = 700;
	private static int wallNumber = 3;
	private static int wallMinHeight = 150;
	//wallMaxHeight represents how much the wall can increase his height: the max height of the wall is min + max = 53
	private static int wallMaxHeight = 380;
	private static int incrementStep = 1;
	private static float wallMaxSpeed = -3000;
	
	//piranhas variables
	private static TextureRegion piranha0, piranha1, piranha2, piranha3, piranha4, piranha5, piranha6, piranha7; 
	private static TextureRegion piranhaFlipped0, piranhaFlipped1, piranhaFlipped2, piranhaFlipped3, piranhaFlipped4, piranhaFlipped5, piranhaFlipped6, piranhaFlipped7;
	private static Animation.PlayMode piranhaPlayMode;
	private static float piranhaFrameDuration, piranhaX, piranhaY, piranhaWidth, piranhaHeight, piranhaSpeedX, piranhaSpeedY, piranhaMaxSpeedX, piranhaMaxSpeedY, piranhaCircleXGap;
	private static float piranhaSpeedUpFactorX, piranhaSpeedUpFactorY, piranhaSpeedUpFactorFrameDuration, piranhaCircleYGap, piranhaCircleRadius;
	private static int piranhaXGap, piranhaNumber, piranhaIncrementStep;
	
	//score variables
	private static BitmapFont scoreFont = TextureLoader.fontBlue;
	private static BitmapFont scoreShadowFont = TextureLoader.fontYellow;
	private static int scoreX = 850;
	private static int scoreY = 140;
	private static float scoreScalePointX = 1.7f;
	private static float scoreScalePointY = -1.6f;
	private static float scoreMultiplierX = 1.2f;
	private static float scoreMultiplierY = -1f;
	
	//life bar variables
    private static TextureRegion lifeBarLeft = TextureLoader.lifeBarLeft;
    private static TextureRegion lifeBarMid = TextureLoader.lifeBarMid;
    private static TextureRegion lifeBarRight = TextureLoader.lifeBarRight;
    private static float barX = 30;
    private static float barY = 30;
    private static float barWidth = 400;
    private static float barHeight = 70;
    private static float maxBarLife = 1000;
    private static float borderWidth = 50;
	
	//swipe sign variables
	private static TextureRegion swipeBackground = TextureLoader.swipeBackground;
	private static float swipeX = -300;
	private static float swipeY = 800;
	private static int swipeWidth = 200;
	private static int swipeHeight = 60;
	private static float swipeSpeedX = 700;
	private static float swipeMaxX;
	
	//swipe sign credits variables
	private static TextureRegion swipeBackground2 = TextureLoader.swipeSignInfo;
	private static float swipeX2 = -300;
	private static float swipeY2 = 800;
	private static int swipeWidth2 = 200;
	private static int swipeHeight2 = 60;
	private static float swipeSpeedX2 = 700;
	private static float swipeMaxX2;
	
	//experience bar variables
    private static TextureRegion expBarBackground;
    private static TextureRegion expTexture;
    private static TextureRegion leftExpTexture, rightExpTexture, topExpTexture, bottomExpTexture;
    private static float expBarX;
    private static float expBarY;
    private static float expBarWidth;
    private static float expBarHeight;
    private static float expBarMaxValue, expTextureWidth, expTextureHeight;
	
	//menu1 experience bar variables
    private static TextureRegion expBarBackground1;
    private static TextureRegion expTexture1;
    private static TextureRegion leftExpTexture1, rightExpTexture1, topExpTexture1, bottomExpTexture1;
    private static float expBarX1;
    private static float expBarY1;
    private static float expBarWidth1;
    private static float expBarHeight1;
    private static float expBarMaxValue1, expTextureWidth1, expTextureHeight1;

	//HighScoreContainer variables
	private static float hscX, hscY, hscW, hscH, hscWordsScaleX, hscWordsScaleY;
	private static float hscScoreValueLeftX, hscScoreValueLeftY, hscBestScoreValueLeftX, hscBestScoreValueY, hscLevelWordX, hscLevelWordY;
	private static float hscExperienceWordX, hscExperienceWordY, hscContinueWordX, hscContinueWordY, hscUnlockedWordX, hscUnlockedWordY;
	
	
	public static void initializeButtons(){
		midY = ScreenConfig.MIDPOINTY;
    	width = ScreenConfig.VIRTUALWIDTH;
    	height = ScreenConfig.VIRTUALHEIGHT;
    	swipeMaxX = width;
    	int x, y, w, h;
        
		//CREATION OF GAME BUTTONS
		GameButtonContainer.addOneTouchBtn(100, 50, 180, 70, TextureLoader.back, TextureLoader.back, TextureLoader.buttonDisabled, ButtonNames.buttonBack);
		
		//MENU 1
		GameButtonContainer.addOneTouchBtn(390, 550, 400, 110, TextureLoader.buttonYellow, TextureLoader.buttonYellow, TextureLoader.buttonDisabled, ButtonNames.buttonStartChoose);
		GameButtonContainer.setText(ButtonNames.buttonStartChoose, "PLAY", TextureLoader.fontWhite, 1.1f, -0.9f);
		GameButtonContainer.addOneTouchBtn(810, 550, 400, 110, TextureLoader.buttonYellow, TextureLoader.buttonYellow, TextureLoader.buttonDisabled, ButtonNames.buttonCredits);
		GameButtonContainer.setText(ButtonNames.buttonCredits, "INFO", TextureLoader.fontWhite, 1.1f, -0.9f);

		GameButtonContainer.addOneTouchBtn(1450, 30, 140, 140, TextureLoader.options, TextureLoader.options, TextureLoader.buttonDisabled, ButtonNames.buttonOptions);
		GameButtonContainer.addMenuOptionBtn(1460, 180, 120, 120, TextureLoader.musicOn, TextureLoader.musicOn, null, TextureLoader.musicOff, TextureLoader.musicOff, ButtonNames.buttonMusic, Settings.MUSIC_ENABLED);
		GameButtonContainer.addMenuOptionBtn(1460, 310, 120, 120, TextureLoader.speakerOn, TextureLoader.speakerOn, null, TextureLoader.speakerOff, TextureLoader.speakerOff, ButtonNames.buttonSoundEnabled, Settings.SOUND_ENABLED);
		GameButtonContainer.addOneTouchBtn(1460, 440, 120, 120, TextureLoader.shareText, TextureLoader.shareText, TextureLoader.buttonDisabled, ButtonNames.buttonShareText);
		GameButtonContainer.addOneTouchBtn(710, 350, 200, 200, TextureLoader.moreLives, TextureLoader.moreLives, null, ButtonNames.buttonEarnMoreLives);
		GameButtonContainer.addOneTouchBtn(680, 320, 235, 235, TextureLoader.shroud, TextureLoader.shroud, null, ButtonNames.moreLivesShine);
		
		
		//if aspect ratio is already the one i want, i don't want to show the fullscreen button
		if(ScreenConfig.ASPECTRATIO == ScreenConfig.HWASPECTRATIO)
			GameButtonContainer.addMenuOptionBtn(1460, 570, 0, 0, TextureLoader.fullScreenOff, TextureLoader.fullScreenOff, null, TextureLoader.fullScreenOn, TextureLoader.fullScreenOn, ButtonNames.buttonFullScreen, Settings.STRETCH);
		else
			GameButtonContainer.addMenuOptionBtn(1460, 570, 120, 120, TextureLoader.fullScreenOff, TextureLoader.fullScreenOff, null, TextureLoader.fullScreenOn, TextureLoader.fullScreenOn, ButtonNames.buttonFullScreen, Settings.STRETCH);
		
		//MENU 2
		GameButtonContainer.addOneTouchBtn(525, 50, 550, 120, TextureLoader.buttonYellow, TextureLoader.buttonYellow, TextureLoader.buttonDisabled, ButtonNames.buttonContinue);
		GameButtonContainer.setText(ButtonNames.buttonContinue, "START GAME", TextureLoader.fontWhite, 0.9f, -0.85f);
		
		x = 250;
		y = 380;
		w = 280; //width
		h = 80; //height

		x = (width - w)/2 - w - 120;
		float optionScaleX = 0.7f;
		float optionScaleY = -0.6f;
		
		GameButtonContainer.addOneTouchBtn(x, y, w, h, TextureLoader.buttonGreen, TextureLoader.buttonGreen, TextureLoader.buttonDisabled, ButtonNames.buttonEasy);
		GameButtonContainer.setText(ButtonNames.buttonEasy, "EASY", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+100, w, h, TextureLoader.buttonGreen, TextureLoader.buttonGreen, TextureLoader.buttonDisabled, ButtonNames.buttonMedium);
		GameButtonContainer.setText(ButtonNames.buttonMedium, "MEDIUM", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+200, w, h, TextureLoader.buttonGreen, TextureLoader.buttonGreen, TextureLoader.buttonDisabled, ButtonNames.buttonHard);
		GameButtonContainer.setText(ButtonNames.buttonHard, "HARD", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+300, w, h, TextureLoader.buttonGreen, TextureLoader.buttonGreen, TextureLoader.buttonDisabled, ButtonNames.buttonCrazy);
		GameButtonContainer.setText(ButtonNames.buttonCrazy, "CRAZY", TextureLoader.fontWhite, optionScaleX, optionScaleY);

		x = (width - w)/2;
		GameButtonContainer.addOneTouchBtn(x, y, w, h, TextureLoader.buttonAzure, TextureLoader.buttonAzure, null, ButtonNames.buttonWavyControl);
		GameButtonContainer.setText(ButtonNames.buttonWavyControl, "WAVY", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+100, w, h, TextureLoader.buttonAzure, TextureLoader.buttonAzure, null, ButtonNames.buttonFlappyControl);
		GameButtonContainer.setText(ButtonNames.buttonFlappyControl, "FLAPPY", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+200, w, h, TextureLoader.buttonAzure, TextureLoader.buttonAzure, null, ButtonNames.buttonBouncingControl);
		GameButtonContainer.setText(ButtonNames.buttonBouncingControl, "BOUNCY", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+300, w, h, TextureLoader.buttonAzure, TextureLoader.buttonAzure, null, ButtonNames.buttonClassicControl);
		GameButtonContainer.setText(ButtonNames.buttonClassicControl, "CLASSIC", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		
		x = (width + w)/2 + 120;
		GameButtonContainer.addOneTouchBtn(x, y, w, h, TextureLoader.buttonRed, TextureLoader.buttonRed, null, ButtonNames.buttonNormalMode);
		GameButtonContainer.setText(ButtonNames.buttonNormalMode, "NORMAL", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+100, w, h, TextureLoader.buttonRed, TextureLoader.buttonRed, null, ButtonNames.buttonBlinkingkMode);
		GameButtonContainer.setText(ButtonNames.buttonBlinkingkMode, "BLINKING", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		GameButtonContainer.addOneTouchBtn(x, y+200, w, h, TextureLoader.buttonRed, TextureLoader.buttonRed, null, ButtonNames.buttonPiranhaMode);
		GameButtonContainer.setText(ButtonNames.buttonPiranhaMode, "PIRANHA", TextureLoader.fontWhite, optionScaleX, optionScaleY);
		
		//GAMESCREEN
		GameButtonContainer.addContinuousTouchBtn(0, 0, width, midY, null, null, null, ButtonNames.buttonFishUp);
		GameButtonContainer.addContinuousTouchBtn(0, midY, width, midY, null, null, null, ButtonNames.buttonFishDown);
		GameButtonContainer.addOneTouchBtn(0, 0, width, height, null, null, null, ButtonNames.buttonBackground);
		GameButtonContainer.addOneTouchBtn(0, 0, width, height, null, null, null, ButtonNames.buttonBackgroundTutorial);
		GameButtonContainer.addOneTouchBtn(0, 0, width, height, null, null, null, ButtonNames.buttonFlappyBackground);
		GameButtonContainer.addOneTouchBtn(930, 470, 200, 200, TextureLoader.restart, TextureLoader.restart, TextureLoader.buttonDisabled, ButtonNames.buttonRestart);
		GameButtonContainer.addOneTouchBtn(480, 470, 200, 200, TextureLoader.menu, TextureLoader.menu, TextureLoader.buttonDisabled, ButtonNames.buttonLostBackToMenu);
		GameButtonContainer.addOneTouchBtn(480, 600, 200, 200, TextureLoader.menu, TextureLoader.menu, TextureLoader.buttonDisabled, ButtonNames.buttonBackToMenu);
		GameButtonContainer.addOneTouchBtn(930, 600, 200, 200, TextureLoader.resume, TextureLoader.resume, TextureLoader.buttonDisabled, ButtonNames.buttonResume);
		GameButtonContainer.addOneTouchBtn(700, 350, 200, 200, TextureLoader.resume, TextureLoader.resume, null, ButtonNames.buttonTutorialStartGame);
		
		
		//if aspect ratio is already the one i want, i don't want to show the fullscreen button in the "pause" menu and i move the other 2 buttons there
		if(ScreenConfig.ASPECTRATIO == ScreenConfig.HWASPECTRATIO){
			GameButtonContainer.addMenuOptionBtn(550, 400, 170, 170, TextureLoader.musicOn, TextureLoader.musicOn, null, TextureLoader.musicOff, TextureLoader.musicOff, ButtonNames.buttonPauseMusic, Settings.MUSIC_ENABLED);
			GameButtonContainer.addMenuOptionBtn(930, 400, 170, 170, TextureLoader.speakerOn, TextureLoader.speakerOn, null, TextureLoader.speakerOff, TextureLoader.speakerOff, ButtonNames.buttonPauseSoundEnabled, Settings.SOUND_ENABLED);
			GameButtonContainer.addMenuOptionBtn(1020, 400, 0, 0, TextureLoader.fullScreenOff, TextureLoader.fullScreenOff, null, TextureLoader.fullScreenOn, TextureLoader.fullScreenOn, ButtonNames.buttonPauseFullScreen, Settings.STRETCH);
		}
		else{
			GameButtonContainer.addMenuOptionBtn(460, 400, 150, 150, TextureLoader.musicOn, TextureLoader.musicOn, null, TextureLoader.musicOff, TextureLoader.musicOff, ButtonNames.buttonPauseMusic, Settings.MUSIC_ENABLED);
			GameButtonContainer.addMenuOptionBtn(750, 400, 150, 150, TextureLoader.speakerOn, TextureLoader.speakerOn, null, TextureLoader.speakerOff, TextureLoader.speakerOff, ButtonNames.buttonPauseSoundEnabled, Settings.SOUND_ENABLED);
			GameButtonContainer.addMenuOptionBtn(1020, 400, 150, 150, TextureLoader.fullScreenOff, TextureLoader.fullScreenOff, null, TextureLoader.fullScreenOn, TextureLoader.fullScreenOn, ButtonNames.buttonPauseFullScreen, Settings.STRETCH);
		}
		
		
		GameButtonContainer.addOneTouchBtn(1125, 320, 200, 200, TextureLoader.shareScore, TextureLoader.shareScore, TextureLoader.buttonDisabled, ButtonNames.buttonShareScore);
		GameButtonContainer.addOneTouchBtn(1110, 305, 205, 205, TextureLoader.shroud, TextureLoader.shroud, null, ButtonNames.shareScoreShine);
		
		//LINKING MENUOPTIONBUTTONS
		GameButtonContainer.linkMenuOptionButtons(ButtonNames.buttonSoundEnabled, ButtonNames.buttonPauseSoundEnabled);
		GameButtonContainer.linkMenuOptionButtons(ButtonNames.buttonFullScreen, ButtonNames.buttonPauseFullScreen);
		GameButtonContainer.linkMenuOptionButtons(ButtonNames.buttonMusic, ButtonNames.buttonPauseMusic);
	}
	
	public static void createGameObjects(){
	    TextureRegion[] fishTextures = {fish0, fish1, fish2, fish3, fish4, fish5, fish6, fish7};
	    TextureRegion[] piranhaTextures = {piranha0, piranha1, piranha2, piranha3, piranha4, piranha5, piranha6, piranha7};
	    TextureRegion[] piranhaFlippedTextures = {piranhaFlipped0, piranhaFlipped1, piranhaFlipped2, piranhaFlipped3, piranhaFlipped4, piranhaFlipped5, piranhaFlipped6, piranhaFlipped7};
		GameObjectContainer.createFish(fishTextures, playmode, fishFrameDuration, fishX, fishY, fishWidth, fishHeight, 
        		fishMinY, fishMaxY, fishMaxSpeed, fishCurrentAcceleration, fishSpeedUpFactor, fishSpeedX, fishSpeedY, fishCircleXGap, fishCircleYGap, 
        		fishCircleRadius, fishMaxSpeedSpeedUpFactor);

		GameObjectContainer.createLifeBar(lifeBarLeft, lifeBarMid, lifeBarRight, barX, barY, barWidth, barHeight, maxBarLife, borderWidth);
		GameObjectContainer.createBackgrounds(TextureLoader.background, TextureLoader.background2, bgX, bgY, bgWidth, bgHeight, bgSpeedX, bgSpeedY, bgSpeedUpFactor, bgMaxSpeed);
		GameObjectContainer.createWalls(TextureLoader.net, TextureLoader.net2, wallX, wallY, wallWidth, wallSpeedX, wallYGap, height, wallXGap, wallNumber, wallMinHeight, 
				wallMaxHeight, wallSpeedUpFactor, incrementStep, wallMaxSpeed);
		
		GameObjectContainer.createPiranhas(piranhaTextures, piranhaFlippedTextures, piranhaPlayMode, piranhaFrameDuration, piranhaX, piranhaY, piranhaWidth, piranhaHeight, piranhaSpeedX, piranhaSpeedY, 
				height, piranhaXGap, piranhaNumber, piranhaSpeedUpFactorX, piranhaSpeedUpFactorY, piranhaSpeedUpFactorFrameDuration, piranhaIncrementStep,
				piranhaMaxSpeedX, piranhaMaxSpeedY, piranhaCircleXGap, piranhaCircleYGap, piranhaCircleRadius);
		
		GameObjectContainer.createScore(TextureLoader.fontBlue, TextureLoader.fontYellow, scoreScalePointX, scoreScalePointY, scoreMultiplierX, scoreMultiplierY, 
				scoreX, scoreY, Settings.difficulty);
		GameObjectContainer.createSwipe(TextureLoader.swipeBackground, TextureLoader.swipeFlippedBackground, swipeX, swipeY, swipeWidth, swipeHeight, swipeSpeedX, swipeMaxX);
		GameObjectContainer.createSwipeCredits(TextureLoader.swipeSignInfo, TextureLoader.swipeSignInfoFlipped, swipeX2, swipeY2, swipeWidth2, swipeHeight2, swipeSpeedX2, swipeMaxX2);
		
		GameObjectContainer.createExperienceBar(expBarBackground, expTexture, leftExpTexture, rightExpTexture, topExpTexture, bottomExpTexture, expTextureWidth,
				expTextureHeight, expBarX, expBarY, expBarWidth, expBarHeight);
		GameObjectContainer.createMenuExperienceBar(expBarBackground1, expTexture1, leftExpTexture1, rightExpTexture1, topExpTexture1, bottomExpTexture1, expTextureWidth1,
				expTextureHeight1, expBarX1, expBarY1, expBarWidth1, expBarHeight1);
		GameObjectContainer.createHighScoreContainer(TextureLoader.highScoreBackground, TextureLoader.star, TextureLoader.blackStar, hscX, hscY, hscW, hscH, hscScoreValueLeftX, 
				hscScoreValueLeftY, hscBestScoreValueLeftX, hscBestScoreValueY, hscLevelWordX, hscLevelWordY, hscExperienceWordX, hscExperienceWordY, 
				hscContinueWordX, hscContinueWordY, hscUnlockedWordX, hscUnlockedWordY, TextureLoader.fontWhite, hscWordsScaleX, hscWordsScaleY, 5);
	}
	
	public static void initValues(){
		midY = ScreenConfig.MIDPOINTY;
    	width = ScreenConfig.VIRTUALWIDTH;
    	height = ScreenConfig.VIRTUALHEIGHT;

		//fish variables
		fish0 = TextureLoader.fish0;
	    fish1 = TextureLoader.fish1;
	    fish2 = TextureLoader.fish2;
	    fish3 = TextureLoader.fish3;
	    fish4 = TextureLoader.fish4;
	    fish5 = TextureLoader.fish5;
	    fish6 = TextureLoader.fish6;
	    fish7 = TextureLoader.fish7;
	    playmode = PlayMode.LOOP;
	    fishFrameDuration = 0.1f;
	    fishX = 100;
	    fishY = midY - 47;
	    fishWidth = 200;
	    fishHeight = 94;
	    fishMinY = 100;
	    fishMaxY = 700;
	    fishMaxSpeed = 400;
	    fishCurrentAcceleration = 1500;
	    fishSpeedX = 0;
	    fishSpeedY = 0;
	    fishCircleXGap = 109;
	    fishCircleYGap = fishHeight/2;
	    fishCircleRadius = 27;
		
		//backgrounds variables
		bgX = 0;
		bgY = 0;
		bgSpeedY= 0;
		bgWidth = width + 600;
		bgHeight = height;
		bgSpeedX = -500;
		bgMaxSpeed = -3000;
		
		//walls variables
		wallX = 2000;
		wallY = 0;
		wallSpeedX = -500;
		wallWidth = 50;
		wallYGap = 220;
		wallXGap = 700;
		wallNumber = 3;
		wallMinHeight = 150;
		//wallMaxHeight represents how much the wall can increase his height: the max height of the wall is min + max = 530
		wallMaxHeight = 380;
		incrementStep = 1;
		wallMaxSpeed = -3000;
		
		//score variables
		scoreFont = TextureLoader.fontBlue;
		scoreShadowFont = TextureLoader.fontYellow;
		scoreX = 850;
		scoreY = 140;
		scoreScalePointX = 1.7f;
		scoreScalePointY = -1.6f;
		scoreMultiplierX = 1.2f;
		scoreMultiplierY = -1f;
		
		//life bar variables
	    lifeBarLeft = TextureLoader.lifeBarLeft;
	    lifeBarMid = TextureLoader.lifeBarMid;
	    lifeBarRight = TextureLoader.lifeBarRight;
	    barX = 30;
	    barY = 30;
	    barWidth = 400;
	    barHeight = 30;
	    maxBarLife = 1000;
	    borderWidth = 25;
		
		//swipe sign variables
		swipeBackground = TextureLoader.swipeBackground;
		swipeX = -300;
		swipeY = 450;
		swipeWidth = 200;
		swipeHeight = 60;
		swipeSpeedX = 700;
    	swipeMaxX = width;
		
		//swipe sign credits variables
		swipeBackground2 = TextureLoader.swipeSignInfo;
		swipeX2 = -300;
		swipeY2 = 800;
		swipeWidth2 = 200;
		swipeHeight2 = 60;
		swipeSpeedX2 = 700;
    	swipeMaxX2 = width;

		//experience bar variables
        expBarBackground = TextureLoader.experienceBarBackground;
        expTexture = TextureLoader.experienceBarFront;
        leftExpTexture = TextureLoader.experienceLeft;
        rightExpTexture = TextureLoader.experienceRight;
        topExpTexture = TextureLoader.experienceTop;
        bottomExpTexture = TextureLoader.experienceBottom;
        expBarX = 300;
        expBarY = 620;
	    expBarWidth = 1000;
	    expBarHeight = 50;
	    expBarMaxValue = 0;
	    expTextureWidth = 5;
	    expTextureHeight = 5;

		//menu1 experience bar variables
        expBarBackground1 = expBarBackground;
        leftExpTexture1 = leftExpTexture;
        rightExpTexture1 = rightExpTexture;
        topExpTexture1 = topExpTexture;
        bottomExpTexture1 = bottomExpTexture;
        expTexture1 = expTexture;
        expBarX1 = 200;
        expBarY1 = 840;
	    expBarWidth1 = 1200;
	    expBarHeight1 = 40;
	    expBarMaxValue1 = 0;
	    expTextureWidth1 = 5;
	    expTextureHeight1 = 5;
	    
    	//HighScoreContainer variables
    	hscX = 200;
    	hscY = 150;
    	hscW = 1200;
    	hscH = 700;
    	hscScoreValueLeftX = 1100;
    	hscScoreValueLeftY = 330;
    	hscBestScoreValueLeftX = 1100;
    	hscBestScoreValueY = 460;
    	hscLevelWordX = 1300;
    	hscLevelWordY = 550;
    	hscExperienceWordX = 300;
    	hscExperienceWordY = 550;
    	hscWordsScaleX = 0.8f;
    	hscWordsScaleY = -0.8f;
    	hscContinueWordX = width/2;
    	hscContinueWordY = 690;
    	hscUnlockedWordX = width/2;
    	hscUnlockedWordY = 760;

    	
    	//piranhas variables
    	piranha0 = TextureLoader.piranha0;
    	piranha1 = TextureLoader.piranha1;
    	piranha2 = TextureLoader.piranha2;
    	piranha3 = TextureLoader.piranha3;
    	piranha4 = TextureLoader.piranha4;
    	piranha5 = TextureLoader.piranha5;
    	piranha6 = TextureLoader.piranha6;
    	piranha7 = TextureLoader.piranha7;
    	piranhaFlipped0 = TextureLoader.piranhaFlipped0;
    	piranhaFlipped1 = TextureLoader.piranhaFlipped1;
    	piranhaFlipped2 = TextureLoader.piranhaFlipped2;
    	piranhaFlipped3 = TextureLoader.piranhaFlipped3;
    	piranhaFlipped4 = TextureLoader.piranhaFlipped4;
    	piranhaFlipped5 = TextureLoader.piranhaFlipped5;
    	piranhaFlipped6 = TextureLoader.piranhaFlipped6;
    	piranhaFlipped7 = TextureLoader.piranhaFlipped7;
    	
	    piranhaPlayMode = PlayMode.LOOP;
    	piranhaFrameDuration = 0.05f;
    	piranhaX = 2000;
    	piranhaY = 0;
    	piranhaWidth = 70;
		piranhaHeight = 140;
    	piranhaSpeedX = -500;
    	piranhaSpeedY = 1000;
    	piranhaMaxSpeedX = -3000;
    	piranhaMaxSpeedY = 5000;
    	piranhaSpeedUpFactorFrameDuration = 1.05f;
    	piranhaCircleXGap = 33;
    	piranhaCircleYGap = 40;
    	piranhaCircleRadius = 40;
    	piranhaXGap = 650;
    	piranhaNumber = 3;
    	piranhaIncrementStep = 1;
	}
    	
	
	public static void updateTextures(){
	    fish0 = TextureLoader.fish0;
	    fish1 = TextureLoader.fish1;
	    fish2 = TextureLoader.fish2;
	    fish3 = TextureLoader.fish3;
	    fish4 = TextureLoader.fish4;
	    fish5 = TextureLoader.fish5;
	    fish6 = TextureLoader.fish6;
	    fish7 = TextureLoader.fish7;
	    
    	piranha0 = TextureLoader.piranha0;
    	piranha1 = TextureLoader.piranha1;
    	piranha2 = TextureLoader.piranha2;
    	piranha3 = TextureLoader.piranha3;
    	piranha4 = TextureLoader.piranha4;
    	piranha5 = TextureLoader.piranha5;
    	piranha6 = TextureLoader.piranha6;
    	piranha7 = TextureLoader.piranha7;

    	piranhaFlipped0 = TextureLoader.piranhaFlipped0;
    	piranhaFlipped1 = TextureLoader.piranhaFlipped1;
    	piranhaFlipped2 = TextureLoader.piranhaFlipped2;
    	piranhaFlipped3 = TextureLoader.piranhaFlipped3;
    	piranhaFlipped4 = TextureLoader.piranhaFlipped4;
    	piranhaFlipped5 = TextureLoader.piranhaFlipped5;
    	piranhaFlipped6 = TextureLoader.piranhaFlipped6;
    	piranhaFlipped7 = TextureLoader.piranhaFlipped7;
        
		ImageType imageType = Settings.imageType;
		switch(imageType){
			case original:
				fishWidth = 150;
				break;
			case horror:
				fishWidth = 150;
				break;
			case tema:
				fishWidth = 150;
				break;
			default:
				fishWidth = 150;
				break;
		}
		GameObjectContainer.fish.setWidth(fishWidth);
		GameObjectContainer.fish.setCircleXGap(fishCircleXGap);
		
		createGameObjects();
    }

	public static void updateDifficulty(){
		switch (Settings.difficulty){
			case easy:
				fishFrameDuration = 0.1f;
				piranhaSpeedY = 1000;
		    	piranhaSpeedX = -500;
				bgSpeedX = -250;
				wallSpeedX = -500;
				fishCurrentAcceleration = 1500;
			    fishMaxSpeed = 425;

			    
			    fishMaxSpeedSpeedUpFactor = 45;
			    fishSpeedUpFactor = 85f;
				wallSpeedUpFactor = -31;
		    	piranhaSpeedUpFactorX = -31;
		    	piranhaSpeedUpFactorY = 15;
				bgSpeedUpFactor = -15;
				break;
			case medium:
				fishFrameDuration = 0.09f;
				piranhaSpeedY = 1100;
		    	piranhaSpeedX = -600;
				bgSpeedX = -300;
				wallSpeedX = -600;
				fishCurrentAcceleration = 1800;
			    fishMaxSpeed = 475;
				

			    fishMaxSpeedSpeedUpFactor = 50;
			    fishSpeedUpFactor = 100f;
				wallSpeedUpFactor = -34;
		    	piranhaSpeedUpFactorX = -34;
		    	piranhaSpeedUpFactorY = 17;
				bgSpeedUpFactor = -17;
				break;
			case hard:
				fishFrameDuration = 0.075f;
				piranhaSpeedY = 1200;
		    	piranhaSpeedX = -700;
				bgSpeedX = -350;
				wallSpeedX = -700;
				fishCurrentAcceleration = 2200;
			    fishMaxSpeed = 525;
				

			    fishMaxSpeedSpeedUpFactor = 55;
			    fishSpeedUpFactor = 115f;
				wallSpeedUpFactor = -37;
		    	piranhaSpeedUpFactorX = -37;
		    	piranhaSpeedUpFactorY = 18;
				bgSpeedUpFactor = -18;
				break;
			case crazy:
				fishFrameDuration = 0.065f;
				piranhaSpeedY = 1300;
		    	piranhaSpeedX = -800;
				bgSpeedX = -400;
				wallSpeedX = -800;
				fishCurrentAcceleration = 2600;
			    fishMaxSpeed = 600;

			    
			    fishMaxSpeedSpeedUpFactor = 60;
			    fishSpeedUpFactor = 130f;
				wallSpeedUpFactor = -40;
		    	piranhaSpeedUpFactorX = -40;
		    	piranhaSpeedUpFactorY = 20;
				bgSpeedUpFactor = -20;
				break;
			default:
				break;
		}
		createGameObjects();
	}
}
