package com.stefanosiano.wavyfish.gameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.FadingBackground;
import com.stefanosiano.common.Text;
import com.stefanosiano.wavyfish.utilities.Enums.Difficulty;

public class GameObjectContainer {
	private static Obstacle collisionOb;
	public static Fish fish;
	public static Background background;
	public static Background background2;
	public static List<Obstacle> obstacles;
	public static List<WallCouple> walls;
	public static List<Piranha> piranhas;
	public static Score score;
	public static ArrayList<Text> texts;
	public static int passedWalls;
	public static LifeBar2 lifeBar;
	public static SwipeSign swipeSign, swipeSignCredits;
	public static HighScoreContainer highScoreContainer;
	public static ExperienceBar experienceBar, menuExperienceBar;
	private static int collisionObstacleIndex = 0;
	private static int obstacleNumber;
	
	private static int screenHeight;
	
	//fish variables
	private static TextureRegion[] fishTextures;
	private static Animation.PlayMode fishPlayMode;
	private static float fishFrameDuration, fishX, fishY, fishMaxSpeed, fishCurrentAcceleration, fishSpeedUpFactor, 
		fishSpeedX, fishSpeedY, fishCircleXGap, fishCircleYGap, fishCircleRadius, fishHalfWidth, fishMaxSpeedSpeedUpFactor;
	private static int fishWidth, fishHeight, fishMinY, fishMaxY;
	
	//backgrounds variables
	private static TextureRegion backgroundTexture;
	private static float bgX, bgY, bgSpeedY, bgSpeedUpFactor, bgMaxSpeed;
	private static int bgWidth, bgHeight, bgSpeedX;
	
	//walls variables
	private static TextureRegion wallTexture, wallTexture2;
	private static float wallX, wallY, wallSpeedUpFactor, wallMaxSpeed;
	private static int wallSpeedX, wallWidth, wallYGap, wallXGap, wallNumber, wallMinHeight, wallMaxHeight, incrementStep;
	
	//piranhas variables
	private static TextureRegion[] piranhaTextures, piranhaFlippedTextures;
	private static Animation.PlayMode piranhaPlayMode;
	private static float piranhaFrameDuration, piranhaX, piranhaY, piranhaWidth, piranhaHeight, piranhaSpeedX, piranhaSpeedY, piranhaMaxSpeedX, piranhaMaxSpeedY, piranhaCircleXGap;
	private static float piranhaSpeedUpFactorX, piranhaSpeedUpFactorY, piranhaSpeedUpFactorFrameDuration, piranhaCircleYGap, piranhaCircleRadius;
	private static int piranhaXGap, piranhaNumber, piranhaIncrementStep;

	//score variables
	private static BitmapFont scoreFontPoints, scoreFontMultiplier;
	private static float scoreScalePointX, scoreScalePointY, scoreScaleMultiplierX, scoreScaleMultiplierY;
	private static int scoreX, scoreY;
	private static Difficulty difficulty;
	
	//life bar variables
	private static TextureRegion lifeBarLeft;
	private static TextureRegion lifeBarMid;
	private static TextureRegion lifeBarRight;
	private static float barX;
	private static float barY;
	private static float barWidth;
	private static float barHeight;
	private static float maxBarLife;
	private static float borderWidth;
	
	//swipe sign variables
	private static TextureRegion swipeBackground, swipeFlippedBackground;
	private static float swipeX;
	private static float swipeY;
	private static float swipeWidth;
	private static float swipeHeight;
	private static float swipeSpeedX;
	private static float swipeMaxX;
	
	//swipe sign credits variables
	private static TextureRegion swipeBackground2, swipeFlippedBackground2;
	private static float swipeX2;
	private static float swipeY2;
	private static float swipeWidth2;
	private static float swipeHeight2;
	private static float swipeSpeedX2;
	private static float swipeMaxX2;
	
	//experience bar variables
	private static TextureRegion expBarBackgroundTexture;
	private static TextureRegion expBarExpTexture; 
	private static float expBarX;
	private static float expBarY;
	private static float expBarW;
	private static float expBarH;
	private static float expBarMaxValue;
	private static float textureWidth, textureHeight;
	
	
	//highScoreContainer variables
	private static TextureRegion highScoreContainerTexture;
	private static TextureRegion starTexture;
	private static TextureRegion blackStarTexture;
	private static float highScoreContainerX;
	private static float highScoreContainerY;
	private static float highScoreContainerW;
	private static float highScoreContainerH;
	private static float highScoreContainerScoreValueLeftX;
	private static float highScoreContainerScoreValueY;
	private static float highScoreContainerBestScoreValueLeftX;
	private static float highScoreContainerBestScoreValueY;
	private static float highScoreContainerLevelWordX;
	
	private static float highScoreContainerLevelWordY;
	private static float highScoreContainerExperienceWordX;
	private static float highScoreContainerExperienceWordY;
	private static float highScoreContainerContinueWordX, highScoreContainerContinueWordY;
	private static BitmapFont highScoreContainerWordsFont;
	private static float highScoreContainerWordsScaleX;
	private static float highScoreContainerWordsScaleY;
	private static float highScoreContainerUnlockedWordX, highScoreContainerUnlockedWordY;
	private static int highScoreContainerStarNumber;

    public static FadingBackground fadingBackground;
	

	
	public static void createFish(TextureRegion[] fishTextures, Animation.PlayMode playMode, float frameDuration, float x, float y, int width, int height, 
			int minY, int maxY, float maxSpeed, float currentAcceleration, float speedUpFactor, float speedX, float speedY,
			float circleXGap, float circleYGap, float circleRadius, float maxSpeedSpeedUpFactor){
		fish = new Fish(fishTextures, playMode, frameDuration, x, y, width, height, 
				minY, maxY, maxSpeed, currentAcceleration, speedUpFactor, speedX, speedY, circleXGap, circleYGap, circleRadius, maxSpeedSpeedUpFactor);
		GameObjectContainer.fishTextures = fishTextures;
		GameObjectContainer.fishPlayMode = playMode;
		GameObjectContainer.fishFrameDuration = frameDuration;
		GameObjectContainer.fishX = x;
		GameObjectContainer.fishY = y;
		GameObjectContainer.fishWidth = width;
		GameObjectContainer.fishHalfWidth = width/2;
		GameObjectContainer.fishHeight = height;
		GameObjectContainer.fishMaxSpeed = maxSpeed;
		GameObjectContainer.fishCurrentAcceleration = currentAcceleration;
		GameObjectContainer.fishSpeedUpFactor = speedUpFactor;
		GameObjectContainer.fishSpeedX = speedX;
		GameObjectContainer.fishSpeedY = speedY;
		GameObjectContainer.fishCircleXGap = circleXGap;
		GameObjectContainer.fishCircleYGap = circleYGap;
		GameObjectContainer.fishCircleRadius = circleRadius;
		GameObjectContainer.fishMinY = minY;
		GameObjectContainer.fishMaxY = maxY;
		GameObjectContainer.fishMaxSpeedSpeedUpFactor = maxSpeedSpeedUpFactor;
	}
	
	public static void createBackgrounds(TextureRegion backgroundTexture, TextureRegion backgroundTexture2, float x, float y, int width, int height, int speedX, float speedY, float speedUpFactor, float bgMaxSpeed){
		background = new Background(backgroundTexture, x, y, width, height, speedX, speedY, speedUpFactor, bgMaxSpeed);
		background2 = new Background(backgroundTexture2, x + width, y, width, height, speedX, speedY, speedUpFactor, bgMaxSpeed);
		
		GameObjectContainer.bgSpeedUpFactor = speedUpFactor;
		GameObjectContainer.backgroundTexture = backgroundTexture;
		GameObjectContainer.bgX = x;
		GameObjectContainer.bgY = y;
		GameObjectContainer.bgSpeedY = speedY;
		GameObjectContainer.bgWidth = width;
		GameObjectContainer.bgHeight = height;
		GameObjectContainer.bgSpeedX = speedX;
		GameObjectContainer.bgMaxSpeed = bgMaxSpeed;
	}
	
	public static void createWalls(TextureRegion wallTexture, TextureRegion wallTexture2, float x, float y, int width, int speedX,
			int yGap, int screenHeight, int xGap, int wallNumber, int minHeight, int maxHeight, float speedUpFactor, int incrementStep, float wallMaxSpeed){
		//yGap represents how long the hole between the walls is
		walls = new ArrayList<WallCouple>();
		for(int i = 0; i < wallNumber; i++){
			walls.add(new WallCouple(wallTexture, wallTexture2, x + (i * xGap), y, width, speedX, yGap, screenHeight, xGap, wallNumber, minHeight, maxHeight, speedUpFactor, incrementStep, bgMaxSpeed));
		}

		GameObjectContainer.wallSpeedUpFactor = speedUpFactor;
		GameObjectContainer.wallTexture = wallTexture;
		GameObjectContainer.wallTexture2 = wallTexture2;
		GameObjectContainer.wallX = x;
		GameObjectContainer.wallY = y;
		GameObjectContainer.wallWidth = width;
		GameObjectContainer.wallSpeedX = speedX;
		GameObjectContainer.wallYGap = yGap;
		GameObjectContainer.wallXGap = xGap;
		GameObjectContainer.wallNumber = wallNumber;
		GameObjectContainer.wallMinHeight = minHeight;
		GameObjectContainer.wallMaxHeight = maxHeight;
		GameObjectContainer.screenHeight = screenHeight;
		GameObjectContainer.incrementStep = incrementStep;
		GameObjectContainer.wallMaxSpeed = wallMaxSpeed;
	}

	public static void createPiranhas(TextureRegion[] textures, TextureRegion[] flippedTextures, Animation.PlayMode playMode, float frameDuration, float x, float y, float width, float height, float speedX, 
			float speedY, int screenHeight, int xGap, int piranhaNumber, float speedUpFactorX, float speedUpFactorY, float speedUpFactorFrameDuration,
			int incrementStep, float maxSpeedX, float maxSpeedY, float circleXGap, float circleYGap, float circleRadius) {
		
		piranhas = new ArrayList<Piranha>();
		Random r = new Random();
		for(int i = 0; i < piranhaNumber; i++){
			if(i % 2 == 0)
				piranhas.add(new Piranha(textures, flippedTextures, playMode, frameDuration, x + (i * xGap), y + r.nextInt(screenHeight), width, height, speedX, -speedY, screenHeight, xGap, 
						piranhaNumber, speedUpFactorX, speedUpFactorY, speedUpFactorFrameDuration, incrementStep, maxSpeedX, maxSpeedY, circleXGap, circleYGap, circleRadius));
			else
				piranhas.add(new Piranha(textures, flippedTextures, playMode, frameDuration, x + (i * xGap), y + r.nextInt(screenHeight), width, height, speedX, speedY, screenHeight, xGap,
						piranhaNumber, speedUpFactorX, speedUpFactorY, speedUpFactorFrameDuration, incrementStep, maxSpeedX, maxSpeedY, circleXGap, circleYGap, circleRadius));
		}

		GameObjectContainer.screenHeight = screenHeight;
		GameObjectContainer.piranhaTextures = textures;
		GameObjectContainer.piranhaFlippedTextures = flippedTextures;
		GameObjectContainer.piranhaPlayMode = playMode;
		GameObjectContainer.piranhaFrameDuration = frameDuration;
		GameObjectContainer.piranhaX = x;
		GameObjectContainer.piranhaY = y;
		GameObjectContainer.piranhaWidth = width;
		GameObjectContainer.piranhaHeight = height;
		GameObjectContainer.piranhaSpeedX = speedX;
		GameObjectContainer.piranhaSpeedY = speedY;
		GameObjectContainer.piranhaXGap = xGap;
		GameObjectContainer.piranhaNumber = piranhaNumber;
		GameObjectContainer.piranhaSpeedUpFactorX = speedUpFactorX;
		GameObjectContainer.piranhaSpeedUpFactorY = speedUpFactorY;
		GameObjectContainer.piranhaSpeedUpFactorFrameDuration = speedUpFactorFrameDuration;
		GameObjectContainer.piranhaIncrementStep = incrementStep;
		GameObjectContainer.piranhaMaxSpeedX = maxSpeedX;
		GameObjectContainer.piranhaMaxSpeedY = maxSpeedY;
		GameObjectContainer.piranhaCircleXGap = circleXGap;
		GameObjectContainer.piranhaCircleYGap = circleYGap;
		GameObjectContainer.piranhaCircleRadius = circleRadius;
	}
	
	public static void addText(Text text){
		if(texts == null)
			 texts = new ArrayList<Text>();
		texts.add(text);
	}

	public static void clearTexts(){
		texts.clear();
	}
	
	public static void createLifeBar(TextureRegion lifeBarLeft, TextureRegion lifeBarMid, TextureRegion lifeBarRight, float barX, float barY, float barWidth, float barHeight, float maxBarLife, float borderWidth){
		GameObjectContainer.lifeBar = new LifeBar2(lifeBarLeft, lifeBarMid, lifeBarRight, barX, barY, barWidth, barHeight, maxBarLife, borderWidth);
		GameObjectContainer.lifeBarLeft = lifeBarLeft;
		GameObjectContainer.lifeBarMid = lifeBarMid;
		GameObjectContainer.lifeBarRight = lifeBarRight;
		GameObjectContainer.barX = barX;
		GameObjectContainer.barY = barY;
		GameObjectContainer.barWidth = barWidth;
		GameObjectContainer.barHeight = barHeight;
		GameObjectContainer.maxBarLife = maxBarLife;
		GameObjectContainer.borderWidth = borderWidth;
	}
	
	public static void createScore(BitmapFont fontPoints, BitmapFont fontMultiplier, float scalePointX, float scalePointY, float scaleMultiplierX, float scaleMultiplierY, 
			int x, int y, Difficulty difficulty){
		GameObjectContainer.score = new Score(fontPoints, fontMultiplier, scalePointX, scalePointY, scaleMultiplierX, scaleMultiplierY, x, y, difficulty);
		GameObjectContainer.difficulty = difficulty;
		GameObjectContainer.scoreX = x;
		GameObjectContainer.scoreY = y;
		GameObjectContainer.scoreFontPoints = fontPoints;
		GameObjectContainer.scoreFontMultiplier = fontMultiplier;
		GameObjectContainer.scoreScalePointX = scalePointX;
		GameObjectContainer.scoreScalePointY = scalePointY;
		GameObjectContainer.scoreScaleMultiplierX = scaleMultiplierX;
		GameObjectContainer.scoreScaleMultiplierY = scaleMultiplierY;
		
	}
	
	public static void createSwipe(TextureRegion swipeBackground, TextureRegion swipeFlippedBackground, float x, float y, int width, int height, float speedX, float maxX){
		GameObjectContainer.swipeSign = new SwipeSign(swipeBackground, swipeFlippedBackground, x, y, width, height, speedX, maxX);
		GameObjectContainer.swipeBackground = swipeBackground;
		GameObjectContainer.swipeFlippedBackground = swipeFlippedBackground;
		GameObjectContainer.swipeX = x;
		GameObjectContainer.swipeY = y;
		GameObjectContainer.swipeWidth = width;
		GameObjectContainer.swipeHeight = height;
		GameObjectContainer.swipeSpeedX = speedX;
		GameObjectContainer.swipeMaxX = maxX;
	}
	
	public static void createSwipeCredits(TextureRegion swipeBackground, TextureRegion swipeFlippedBackground, float x, float y, int width, int height, float speedX, float maxX){
		GameObjectContainer.swipeSignCredits = new SwipeSign(swipeBackground, swipeFlippedBackground, x, y, width, height, speedX, maxX);
		GameObjectContainer.swipeBackground2 = swipeBackground;
		GameObjectContainer.swipeFlippedBackground2 = swipeFlippedBackground;
		GameObjectContainer.swipeX2 = x;
		GameObjectContainer.swipeY2 = y;
		GameObjectContainer.swipeWidth2 = width;
		GameObjectContainer.swipeHeight2 = height;
		GameObjectContainer.swipeSpeedX2 = speedX;
		GameObjectContainer.swipeMaxX2 = maxX;
	}
	
	
	public static void createExperienceBar(TextureRegion expBarBackgroundTexture, TextureRegion expTexture, TextureRegion leftTexture, TextureRegion rightTexture, 
			TextureRegion topTexture, TextureRegion bottomTexture, float textureWidth, float textureHeight, float x, float y, float width, float height){
		GameObjectContainer.experienceBar = new ExperienceBar(expBarBackgroundTexture, expTexture, leftTexture, rightTexture, topTexture, bottomTexture,
				textureWidth, textureHeight, x, y, width, height);
		GameObjectContainer.expBarBackgroundTexture = expBarBackgroundTexture;
		GameObjectContainer.expBarExpTexture = expTexture; 
		GameObjectContainer.expBarX = x;
		GameObjectContainer.expBarY = y;
		GameObjectContainer.expBarW = width;
		GameObjectContainer.expBarH = height;
	}
	
	public static void createMenuExperienceBar(TextureRegion expBarBackgroundTexture, TextureRegion expTexture, TextureRegion leftTexture, TextureRegion rightTexture, 
			TextureRegion topTexture, TextureRegion bottomTexture, float textureWidth, float textureHeight, float x, float y, float width, float height){
		GameObjectContainer.menuExperienceBar = new ExperienceBar(expBarBackgroundTexture, expTexture, leftTexture, rightTexture, topTexture, bottomTexture,
				textureWidth, textureHeight, x, y, width, height);
		GameObjectContainer.expBarBackgroundTexture = expBarBackgroundTexture;
		GameObjectContainer.expBarExpTexture = expTexture; 
		GameObjectContainer.expBarX = x;
		GameObjectContainer.expBarY = y;
		GameObjectContainer.expBarW = width;
		GameObjectContainer.expBarH = height;
	}
	
	public static void createHighScoreContainer(TextureRegion texture, TextureRegion star, TextureRegion blackStar, float x, float y, float width, float height,
			float scoreValueLeftX, float scoreValueY, float bestScoreValueLeftX, float bestScoreValueY, float levelWordX, float levelWordY, float experienceWordX, 
			float experienceWordY, float continueWordX, float continueWordY, float unlockedWordX, float unlockedWordY, BitmapFont wordsFont, float wordsScaleX, 
			float wordsScaleY, int starNumber){
		
		GameObjectContainer.highScoreContainer = new HighScoreContainer(texture, star, blackStar, x, y, width, height, 
				scoreValueLeftX, scoreValueY, bestScoreValueLeftX, bestScoreValueY, levelWordX, levelWordY, experienceWordX, experienceWordY, continueWordX, continueWordY, unlockedWordX, unlockedWordY,
				wordsFont, wordsScaleX, wordsScaleY, starNumber, GameObjectContainer.experienceBar);
		

		GameObjectContainer.highScoreContainerBestScoreValueLeftX = bestScoreValueLeftX;
		GameObjectContainer.highScoreContainerBestScoreValueY = bestScoreValueY;
		GameObjectContainer.highScoreContainerTexture = texture;
		GameObjectContainer.starTexture = star;
		GameObjectContainer.blackStarTexture = blackStar;
		GameObjectContainer.highScoreContainerX = x;
		GameObjectContainer.highScoreContainerY = y;
		GameObjectContainer.highScoreContainerW = width;
		GameObjectContainer.highScoreContainerH = height;
		GameObjectContainer.highScoreContainerScoreValueLeftX = scoreValueLeftX;
		GameObjectContainer.highScoreContainerScoreValueY = scoreValueY;
		GameObjectContainer.highScoreContainerLevelWordX = levelWordX;
		GameObjectContainer.highScoreContainerLevelWordY = levelWordY;
		GameObjectContainer.highScoreContainerExperienceWordX = experienceWordX;
		GameObjectContainer.highScoreContainerExperienceWordY = experienceWordY;
		GameObjectContainer.highScoreContainerContinueWordX = continueWordX;
		GameObjectContainer.highScoreContainerContinueWordY = continueWordY;
		GameObjectContainer.highScoreContainerUnlockedWordX = unlockedWordX;
		GameObjectContainer.highScoreContainerUnlockedWordY = unlockedWordY;
		GameObjectContainer.highScoreContainerWordsFont = wordsFont;
		GameObjectContainer.highScoreContainerWordsScaleX = wordsScaleX;
		GameObjectContainer.highScoreContainerWordsScaleY = wordsScaleY;
		GameObjectContainer.highScoreContainerStarNumber = starNumber;
	}
	
	public static void createObstacles(List<Obstacle> obstacles){
		GameObjectContainer.obstacles = obstacles;
		obstacleNumber = obstacles.size();
	}
	
	public static void createArrows(TextureRegion arrowUpTexture, float x, float y, int width, int height,
			int yGap, int screenHeight, int xGap, int wallNumber, int minHeight, int maxHeight, float speedUpFactor){
	}
	
	public static boolean fishCollide(){
		collisionOb = obstacles.get(collisionObstacleIndex);
		if(collisionOb.overlaps(fish.getCircle()))
			return true;
		return false;
	}
	
	public static boolean wallPassed(){
		collisionOb = obstacles.get(collisionObstacleIndex);
		if(collisionOb.passed((int) (fish.getX() + (fishHalfWidth) )))
			return true;
		return false;
	}
	
	public static void removeObstacle(int passedWalls, int numberOfWallsToFinish, int speedDownStep){
		if(passedWalls > numberOfWallsToFinish - obstacleNumber){

			collisionOb = obstacles.get(collisionObstacleIndex);
			collisionOb.stopComing();
		}
		else{
			if(passedWalls < numberOfWallsToFinish - obstacleNumber){
				postponeObstacle(passedWalls, speedDownStep);
			}
		}
	}

	/**
	 * Used to move further away the obstacle when slowing down, so the user has more space to react to the new slowliness
	 * 
	 * @param passedWalls
	 * @param speedDownStep
	 */
	public static void postponeObstacle(int passedWalls, int speedDownStep){
		int number = passedWalls + obstacleNumber - 1;
		
		if(number % speedDownStep == 0){
			for(Obstacle ob : obstacles)
				ob.postponeFurther();
		}
	}
	
	public static boolean wallFinishedPassed(){
		collisionOb = obstacles.get(collisionObstacleIndex);
		
		if(collisionOb.finishPassed((int) fish.getX())){
			collisionObstacleIndex++;
			collisionObstacleIndex = collisionObstacleIndex % obstacleNumber;
			return true;
		}
		return false;
	}
	
	public static void stopAll(){
		background.setSpeed(0, 0);
		background2.setSpeed(0, 0);
		fish.stop();
		for(Obstacle ob : obstacles){
			ob.stop();
		}
	}
	
	public static void restart(){
		fish = new Fish(fishTextures, fishPlayMode, fishFrameDuration, fishX, fishY, fishWidth, fishHeight, fishMinY, fishMaxY, fishMaxSpeed, fishCurrentAcceleration, 
				fishSpeedUpFactor, fishSpeedX, fishSpeedY, fishCircleXGap, fishCircleYGap, fishCircleRadius, fishMaxSpeedSpeedUpFactor);
		
		background = new Background(backgroundTexture, bgX, bgY, bgWidth, bgHeight, bgSpeedX, bgSpeedY, bgSpeedUpFactor, bgMaxSpeed);
		background2 = new Background(backgroundTexture, bgX + bgWidth, bgY, bgWidth, bgHeight, bgSpeedX, bgSpeedY, bgSpeedUpFactor, bgMaxSpeed);
		
		lifeBar = new LifeBar2(lifeBarLeft, lifeBarMid, lifeBarRight, barX, barY, barWidth, barHeight, maxBarLife, borderWidth);
		
		walls.clear();
		WallCouple.increment = 0;
		for(int i = 0; i < wallNumber; i++){
			walls.add(new WallCouple(wallTexture, wallTexture2, wallX + (i * wallXGap), wallY, wallWidth, wallSpeedX, wallYGap, screenHeight, wallXGap, wallNumber, wallMinHeight, 
					wallMaxHeight, wallSpeedUpFactor, incrementStep, wallMaxSpeed));
		}

		Piranha.increment = 0;
		piranhas.clear();
		for(int i = 0; i < piranhaNumber; i++){
			piranhas.add(new Piranha(piranhaTextures, piranhaFlippedTextures, piranhaPlayMode, piranhaFrameDuration, piranhaX + (i * piranhaXGap), piranhaY, piranhaWidth, piranhaHeight, piranhaSpeedX, piranhaSpeedY, 
					screenHeight, piranhaXGap, piranhaNumber, piranhaSpeedUpFactorX, piranhaSpeedUpFactorY, piranhaSpeedUpFactorFrameDuration, piranhaIncrementStep,
					piranhaMaxSpeedX, piranhaMaxSpeedY, piranhaCircleXGap, piranhaCircleYGap, piranhaCircleRadius));
		}

		GameObjectContainer.score = new Score(scoreFontPoints, scoreFontMultiplier, scoreScalePointX, scoreScalePointY, scoreScaleMultiplierX, scoreScaleMultiplierY, scoreX, scoreY, difficulty);
		GameObjectContainer.highScoreContainer = new HighScoreContainer(highScoreContainerTexture, starTexture, blackStarTexture, highScoreContainerX, highScoreContainerY,
				highScoreContainerW, highScoreContainerH, highScoreContainerScoreValueLeftX, highScoreContainerScoreValueY, highScoreContainerBestScoreValueLeftX, 
				highScoreContainerBestScoreValueY, highScoreContainerLevelWordX, highScoreContainerLevelWordY, highScoreContainerExperienceWordX, highScoreContainerExperienceWordY, 
				highScoreContainerContinueWordX, highScoreContainerContinueWordY, highScoreContainerUnlockedWordX, highScoreContainerUnlockedWordY, highScoreContainerWordsFont, 
				highScoreContainerWordsScaleX, highScoreContainerWordsScaleY, highScoreContainerStarNumber, GameObjectContainer.experienceBar);
		collisionObstacleIndex = 0;
	}

	public static void speedUp() {
		fish.speedUp();
		background.speedUp();
		background2.speedUp();
		for(Obstacle ob : obstacles){
			ob.speedUp();
		}
	}

	public static void speedDownToStart() {
		fish.speedDownTo(fishCurrentAcceleration, fishFrameDuration);
		background.setSpeed(bgSpeedX, 0);
		background2.setSpeed(bgSpeedX, 0);
		
		for(WallCouple wall : walls){
			wall.speedDownTo(wallSpeedX, 0, 0);
		}
		
		for(Piranha piranha : piranhas){
			piranha.speedDownTo(piranhaSpeedX, piranhaSpeedY, 0);
		}
	}
	
	public static void initialize(){
        fadingBackground = new FadingBackground(Color.BLACK, 0, 0, 0, 0);
        texts = new ArrayList<Text>();
	}
	
	public static void dispose(){
		fishTextures = null;
		texts.clear();
	}
}
