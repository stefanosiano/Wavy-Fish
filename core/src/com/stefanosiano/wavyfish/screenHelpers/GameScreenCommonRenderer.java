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
import com.stefanosiano.common.FadingBackground;
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
    private FadingBackground fadingBackground;
    
    private FloatValue floatCollisionValue;
    private TweenManager tweenCollisionManager;
	protected boolean drawCollision;
	private SpriteBatch batcherCollision;
	private Color c;
	protected Text difficultyText;
    
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
        this.fadingBackground = GameObjectContainer.fadingBackground;
        this.difficultyText = new Text(TextureLoader.fontOrange, 1f, -1f, false);

        batcherCollision.setProjectionMatrix(cam.combined);

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

    public void setDifficultyText(){
        switch (Settings.difficulty){
            case easy:
                difficultyText.setCenteredHorizzontally(screen.getGame().getString("easy"), 800, 20);
                break;
            case medium:
                difficultyText.setCenteredHorizzontally(screen.getGame().getString("medium"), 800, 20);
                break;
            case hard:
                difficultyText.setCenteredHorizzontally(screen.getGame().getString("hard"), 800, 20);
                break;
            case crazy:
                difficultyText.setCenteredHorizzontally(screen.getGame().getString("crazy"), 800, 20);
                break;
        }
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
            case won:
                drawWon(delta);
                break;
	    	case lost:
	            drawLost(delta);
	    		break;
            case startWinning:
                drawWinning(delta);
                break;
            case finishWinning:
                drawFinishWinning(delta);
                break;
            case highScoreWon:
                drawHighScoreWon(delta);
                break;
    		default:
    			break;
        }
        batcher.end();
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
        drawFadingBackground(delta);
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
        drawFadingBackground(delta);
		drawWallAndFish(0, delta);
		drawLives(delta);
		drawScore(delta);
		drawPanelPause();
		drawButtons(delta);
		drawDifficulty(delta);
		drawTexts(delta);
		
		drawReverseCount(delta, screen.getGame().getString("resuming_in") + " ");
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

    protected void drawHighScoreWon(float delta){
        drawCup();
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

    protected void drawWon(float delta){
        drawCup();
        drawLives(delta);
        drawScore(delta);
        drawPanelLost();
        drawTexts(delta);
        drawButtons(delta);
        if(drawCollision)
            drawFishCollided(delta);
    }

    protected void drawWinning(float delta){
        drawWallAndFish(runTime, delta);
        drawLives(delta);
        drawScore(delta);
        drawButtons(delta);
        drawDifficulty(delta);
        if(drawCollision)
            drawFishCollided(delta);
        drawTexts(delta);
        drawFadingBackground(delta);
        //drawCollisions();
    }

    protected void drawFinishWinning(float delta){
        drawCup();
        drawFadingBackground(delta);
        //drawCollisions();
    }
	
	protected void drawWallAndFish(float runTime, float delta){
        drawWalls(runTime);
        drawFish(runTime, delta);
	}

    protected void drawCup(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.cup, 350, 50, 900, 800);
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

    protected void drawFadingBackground(float delta){
        batcher.end();
        fadingBackground.draw(shapeRenderer);
        batcher.begin();
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
