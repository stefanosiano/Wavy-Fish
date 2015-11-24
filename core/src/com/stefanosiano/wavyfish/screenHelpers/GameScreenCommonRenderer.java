package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.stefanosiano.common.SimpleRenderer;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.gameObjects.Background;
import com.stefanosiano.wavyfish.gameObjects.Fish;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.HighScoreContainer;
import com.stefanosiano.wavyfish.gameObjects.LifeBar2;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.wavyfish.gameObjects.Piranha;
import com.stefanosiano.wavyfish.gameObjects.Score;
import com.stefanosiano.wavyfish.gameObjects.WallCouple;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.tweenAccessors.FloatAccessor;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;

public class GameScreenCommonRenderer extends SimpleRenderer{
	protected float runTime;
	protected GameState gameState;

    private Fish fish;
    private Score score;
    private HighScoreContainer highScoreContainer;
    private ArrayList<Text> texts;
    private Background background, background2;
    private List<Obstacle> obstacles;
    private List<SimpleButton> activeButtons;
    private LifeBar2 lifeBar;
    
    private FloatValue floatCollisionValue;
    private TweenManager tweenCollisionManager;
	protected boolean drawCollision;
	private SpriteBatch batcherCollision;
	private Color c;
	protected Text difficultyText;
	
	//tutorial variables
	private ArrayList<Text> tutorialTexts;
	private boolean showTutorial;
    
    public GameScreenCommonRenderer(){
    	super();
        initialize();
    }
    
    public void initialize(){
        this.fish = GameObjectContainer.fish;
        this.background = GameObjectContainer.background;
        this.background2 = GameObjectContainer.background2;
		this.score = GameObjectContainer.score;
		this.highScoreContainer = GameObjectContainer.highScoreContainer;
    	this.runTime = 0;
		this.activeButtons = GameButtonContainer.activeButtons;
		this.lifeBar = GameObjectContainer.lifeBar;
		this.drawCollision = false;
		this.batcherCollision = new SpriteBatch();
        this.texts = GameObjectContainer.texts;
        this.difficultyText = new Text(TextureLoader.fontOrange, 1f, -1f, false);
        	
        difficultyText.setCenteredHorizzontally(Settings.difficulty + "", 800, 20);
        batcherCollision.setProjectionMatrix(cam.combined);

        tutorialTexts = null;
    	showTutorial = false;

    	if(obstacles == null)
    		obstacles = new ArrayList<Obstacle>();
		obstacles.clear();

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
    }
    
    public void stateChanged(){
        this.texts = GameObjectContainer.texts;
    }
    
	public void render(float delta){
        batcher.begin();
		runTime += delta;
		
        drawBackground(delta);
        
		gameState = ((GameScreen) screen).getState();
        switch(gameState){
	    	case menu:
	    		drawMenu(delta);
	    		break;
	    	case ready:
	    		drawReady(delta);
	    		break;
	    	case running:
	            drawRunning(delta);
	    		break;
	    	case pause:
	            drawPause(delta);
	    		break;
	    	case resuming:
	            drawResume(delta);
	    		break;
	    	case highScore:
	            drawHighScore(delta);
	    		break;
	    	case lost:
	            drawLost(delta);
	    		break;
    		default:
    			break;
        }
        batcher.end();
	}

	
	private void drawTutorial(float delta){
	    drawBackground(delta);
        batcher.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.rect(0, 0, virtualWidth, virtualHeight);
        shapeRenderer.end();
        batcher.begin();
        batcher.enableBlending();
        batcher.draw(TextureLoader.fishTutor, 0, 0, 450, 900);
        batcher.draw(TextureLoader.cloudTutor, 450, 50, 1100, 800);
        for(Text t : tutorialTexts)
        	t.draw(batcher, delta);
	}

	protected void drawMenu(float delta){
		drawWallAndFish(0, delta);
		drawButtons(delta);
	}

	protected void drawReady(float delta){
		drawWallAndFish(runTime, delta);
		drawLives(delta);
		drawScore(delta);
		drawButtons(delta);
		drawTexts(delta);
		drawDifficulty(delta);
		//drawCollisions();
	}

	protected void drawRunning(float delta){
		drawWallAndFish(runTime, delta);
		drawLives(delta);
		drawScore(delta);
		drawButtons(delta);
		drawDifficulty(delta);
		if(drawCollision)
			drawFishCollided(delta);
		drawTexts(delta);
		//drawCollisions();
	}

	protected void drawPause(float delta){
		drawWallAndFish(0, delta);
		drawLives(delta);
		drawScore(delta);
		drawPanelPause();
		drawButtons(delta);
		drawDifficulty(delta);
		drawTexts(delta);
		if(drawCollision)
			drawFishCollided(delta);
		//drawCollisions();
	}

	protected void drawResume(float delta){
		drawWallAndFish(0, delta);
		drawLives(delta);
		drawScore(delta);
		drawPanelPause();
		drawButtons(delta);
		drawDifficulty(delta);
		drawTexts(delta);
		
		drawReverseCount(delta, "Resuming in ");
		if(drawCollision)
			drawFishCollided(delta);
		//drawCollisions();
	}

	protected void drawHighScore(float delta){
		drawWallAndFish(0, delta);
		drawLives(delta);
		drawDifficulty(delta);
		drawHighScoreContainer(delta);
		drawButtons(delta);
		drawTexts(delta);
		if(drawCollision)
			drawFishCollided(delta);
	}

	protected void drawLost(float delta){
        drawWalls(0);
		drawLives(delta);
		drawFish(0, delta);
		drawScore(delta);
		drawPanelLost();
		drawTexts(delta);
		drawButtons(delta);
		if(drawCollision)
			drawFishCollided(delta);
	}
	
	protected void drawWallAndFish(float runTime, float delta){
        drawWalls(runTime);
        drawFish(runTime, delta);
	}
	
	protected void drawBackground(float delta){
        batcher.disableBlending();
        background.render(batcher, delta);
        background2.render(batcher, delta);
	}
	
	protected void drawWalls(float runTime){
        batcher.enableBlending();
		for(Obstacle ob : obstacles){
			ob.draw(batcher, runTime);
		}
	}
	
	protected void drawFish(float runTime, float delta){
        batcher.enableBlending();
        fish.render(batcher, runTime, delta);
	}
	
	protected void drawScore(float delta){
        batcher.enableBlending();
        score.draw(batcher, delta);
	}
	
	protected void drawLives(float delta){
        batcher.enableBlending();
        lifeBar.render(batcher, delta);
	}
	
	protected void drawPanelPause(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.panel, 350, 250, 900, 600);
	}
	
	protected void drawPanelLost(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.panel, 350, 250, 900, 500);
	}
	
	protected void drawButtons(float delta){
        batcher.enableBlending();
		for(SimpleButton button : activeButtons){
			button.render(batcher, delta);
		}
	}
	
	protected void drawTexts(float delta){
		batcher.enableBlending();
		for(Text t : texts)
			t.draw(batcher, delta);
	}
	
	protected void drawDifficulty(float delta){
		batcher.enableBlending();
		difficultyText.draw(batcher, delta);
	}
	
	protected void drawHighScoreContainer(float delta){
		batcher.enableBlending();
		highScoreContainer.draw(batcher, delta);
	}


	protected void drawCollisions(){
		for(Obstacle ob : obstacles){
			ob.drawCollision(shapeRenderer);
		}
        batcher.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(fish.getCircle().x, fish.getCircle().y, fish.getCircle().radius);
		shapeRenderer.end();
        batcher.begin();
	}
	

	public void prepareFishCollision(){
		setupCollisionTween(0.4f);
	}

	protected void drawFishCollided(float delta){
		tweenCollisionManager.update(delta);
		c = batcherCollision.getColor();
        batcher.end();
		batcherCollision.begin();
		batcherCollision.enableBlending();
		batcherCollision.setColor(c.r, c.g, c.b, floatCollisionValue.getValue());
		batcherCollision.draw(TextureLoader.redScreen, 0, 0, virtualWidth, virtualHeight);
		batcherCollision.end();
        batcher.begin();
	}

	private void setupCollisionTween(float duration){
		floatCollisionValue = new FloatValue(0.7f);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        tweenCollisionManager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				drawCollision = false;
			}
        };
        
        Tween.to(floatCollisionValue, -1, duration).target(0)
        .ease(TweenEquations.easeNone)
        .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
        .start(tweenCollisionManager);
		drawCollision = true;
	}

	public void speedUp(){};
	public void speedDown(){};
}
