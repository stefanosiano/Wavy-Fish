package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.stefanosiano.common.SimpleRenderer;
import com.stefanosiano.common.Text;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.experience.Experience;
import com.stefanosiano.wavyfish.gameObjects.ExperienceBar;
import com.stefanosiano.wavyfish.gameObjects.Fish;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.Piranha;
import com.stefanosiano.wavyfish.gameObjects.SwipeSign;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ImageType;

public class MenuScreenRenderer extends SimpleRenderer{
    private List<SimpleButton> activeButtons;
    private Fish fish;
    private SwipeSign swipeSign;
    private ArrayList<Text> texts;
    private ExperienceBar menuExperienceBar;
    private boolean showFishMoreLives;
    private ArrayList<Text> tutorialTexts, earnMoreLivesTexts;
    Piranha piranha;
    
    public MenuScreenRenderer(){
    	super();
		this.fish = GameObjectContainer.fish;
		this.swipeSign = GameObjectContainer.swipeSign;
		this.activeButtons = GameButtonContainer.activeButtons;
		this.texts = GameObjectContainer.texts;
		this.menuExperienceBar = GameObjectContainer.menuExperienceBar;
		piranha = GameObjectContainer.piranhas.get(0);
		this.showFishMoreLives = false;
		if(Settings.TUT_MENU1)
			createTutorialTexts();
    }
    
    public void restart(){
		this.activeButtons = GameButtonContainer.activeButtons;
		this.fish = GameObjectContainer.fish;
		this.swipeSign = GameObjectContainer.swipeSign;
		this.texts = GameObjectContainer.texts;
		this.menuExperienceBar = GameObjectContainer.menuExperienceBar;
		this.showFishMoreLives = false;
    }
    
	public void render(float delta){
		super.render(delta);
        batcher.begin();
		if(Settings.TUT_MENU1){
	        drawTutMenu1(delta);
		}
		else{
			if(showFishMoreLives)
		        drawFishMoreLives(delta);
			else{
			    drawBackground();
			    
		        drawFish();
		        drawFishDisabled();
		        drawTexts(delta);
		        drawExperienceBar(delta);
		
			    drawSwipe(delta);
			    drawWavyFish();
			    drawButtons(delta);
			}
		}
        batcher.end();
	}

	public void showFishMoreLives(boolean showFish){
		showFishMoreLives = showFish;
		createEarnMoreLivesTexts();
	}

	private void drawButtons(float delta){
        batcher.enableBlending();
		for(SimpleButton button : activeButtons){
			button.render(batcher, delta);
		}
	}
	
	private void drawWavyFish(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.wavyFish, 300, 70, 1000, 330);
	}

	private void drawBackground(){
        batcher.disableBlending();
        batcher.draw(TextureLoader.background, 0, 0, virtualWidth, virtualHeight);
	}

	private void drawFish(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.fish0, fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight());
        
        /*
        batcher.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(fish.getCircle().x, fish.getCircle().y, fish.getCircle().radius);
		shapeRenderer.circle(piranha.getCircle().x - 1600, piranha.getCircle().y, piranha.getCircle().radius);
		shapeRenderer.end();
        batcher.begin();
        batcher.enableBlending();
        batcher.draw(TextureLoader.fish0, fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight());
        batcher.draw(TextureLoader.piranha0, piranha.getX()- 1600, piranha.getY(), piranha.getWidth(), piranha.getHeight());*/
	}
	
	private void drawFishDisabled(){
		ImageType imageType = Settings.imageType;

		switch(Experience.getLevel()){
			case 1:
			case 2:
				if(imageType.equals(Enums.ImageType.horror)){
					break;
				}
			case 3:
			case 4:
			case 5:
				if(imageType.equals(Enums.ImageType.tema)){
					break;
				}
			case 6:
			case 7:
			case 8:
				if(imageType.equals(Enums.ImageType.space)){
					break;
				}
			case 9:
			case 10:
				return;
			default:
				break;
		}
		batcher.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.6f);
        shapeRenderer.rect(0, 0, virtualWidth, virtualHeight);
        shapeRenderer.end();
		batcher.begin();
	}
	
	private void drawTexts(float delta){
        batcher.enableBlending();
        for(Text t : texts)
        	t.draw(batcher, delta);
	}
	
	private void drawExperienceBar(float delta){
        batcher.enableBlending();
        menuExperienceBar.render(batcher, delta);
	}
	
	private void drawSwipe(float delta){
        batcher.enableBlending();
        swipeSign.render(batcher, delta);
	}
	
	private void drawFishMoreLives(float delta){
	    drawBackground();
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
        for(Text t : earnMoreLivesTexts)
        	t.draw(batcher, delta);
	}
	
	private void drawTutMenu1(float delta){
	    drawBackground();
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
    
    private void createTutorialTexts(){
		tutorialTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 10;
		int textStartY = 80;
		float textScaleX = 0.9f;
		float textScaleY = -0.9f;
		
		Text t1 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t8 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t9 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);

		t1.setCenteredHorizzontally("Hi! Let me explain you some", textCenterX, textStartY);
		t2.setCenteredHorizzontally("things, just for this time.", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 1);
		t3.setCenteredHorizzontally("On top right there are the", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 50);
		t4.setCenteredHorizzontally("options for music, sounds,", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 50);
		t5.setCenteredHorizzontally("fullscreen and sharing!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 50);
		t6.setCenteredHorizzontally("Each new level unlocks", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 100);
		t7.setCenteredHorizzontally("new fish, new difficulties", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 100);
		t8.setCenteredHorizzontally("and new lives!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 7 + 100);
		t9.setCenteredHorizzontally("Swipe to select other fish!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 8 + 150);
		
		tutorialTexts.add(t1);
		tutorialTexts.add(t2);
		tutorialTexts.add(t3);
		tutorialTexts.add(t4);
		tutorialTexts.add(t5);
		tutorialTexts.add(t6);
		tutorialTexts.add(t7);
		tutorialTexts.add(t8);
		tutorialTexts.add(t9);
    }
	
	private void createEarnMoreLivesTexts() {
		if(earnMoreLivesTexts == null)
			earnMoreLivesTexts = new ArrayList<Text>();
		else
			earnMoreLivesTexts.clear();
		
		int textCenterX = 1040;
		int textYGap = 15;
		int textStartY = 80;
		float textScaleX = 0.9f;
		float textScaleY = -0.9f;

		Text t1 = new Text(TextureLoader.fontYellow, 1, -1, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t8 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);

		t1.setCenteredHorizzontally("Get an extra life!", textCenterX, textStartY);
		t2.setCenteredHorizzontally("You can get an extra life", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 1 + 60);
		t3.setCenteredHorizzontally("by simply sharing me on", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 60);
		t4.setCenteredHorizzontally("Facebook, Twitter or Google+", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 60);
		t5.setCenteredHorizzontally("To share me, just click on", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 120);
		t6.setCenteredHorizzontally("the options on the top-right", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 120);
		t7.setCenteredHorizzontally("corner and then select", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 120);
		t8.setCenteredHorizzontally("the share button!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 7 + 120);
		
		earnMoreLivesTexts.add(t1);
		earnMoreLivesTexts.add(t2);
		earnMoreLivesTexts.add(t3);
		earnMoreLivesTexts.add(t4);
		earnMoreLivesTexts.add(t5);
		earnMoreLivesTexts.add(t6);
		earnMoreLivesTexts.add(t7);
		earnMoreLivesTexts.add(t8);
	}
}
