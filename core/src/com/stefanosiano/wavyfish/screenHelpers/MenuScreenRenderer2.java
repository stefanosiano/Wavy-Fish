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
import com.stefanosiano.wavyfish.gameObjects.Fish;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class MenuScreenRenderer2 extends SimpleRenderer{
    private List<SimpleButton> activeButtons;
    private SimpleButton buttonDifficultySelected, buttonControlSelected, buttonModeSelected;
    private int buttonDifficultyX, buttonDifficultyY, buttonDifficultyWidth, buttonDifficultyHeight;
    private int buttonControlX, buttonControlY, buttonControlWidth, buttonControlHeight;
    private int buttonModeX, buttonModeY, buttonModeWidth, buttonModeHeight, panelX, panelY, panelW, panelX2, panelX3;
    private Fish fish;
    private ArrayList<Text> texts, tutorialTexts;

    
    public MenuScreenRenderer2(){
    	super();
		this.activeButtons = GameButtonContainer.activeButtons;
		this.fish = GameObjectContainer.fish;
		this.texts = GameObjectContainer.texts;
    	
		
		SimpleButton button = GameButtonContainer.findActiveButton(ButtonNames.buttonEasy);
    	
    	panelX = button.getX() - 30;
    	panelY = button.getY();
    	panelW = button.getWidth();
    	panelX2 = panelX + panelW + 120;
    	panelX3 = panelX2 + panelW + 120;
    	
		if(Settings.TUT_MENU2)
			createTutorialTexts();
    }
    
    public void setSelectedDifficulty(ButtonNames name){
    	this.buttonDifficultySelected = GameButtonContainer.findActiveButton(name);
    	this.buttonDifficultyX = buttonDifficultySelected.getX();
    	this.buttonDifficultyY = buttonDifficultySelected.getY();
    	this.buttonDifficultyWidth = buttonDifficultySelected.getWidth();
    	this.buttonDifficultyHeight = buttonDifficultySelected.getHeight();
    }

    public void setSelectedControl(ButtonNames name){
    	this.buttonControlSelected = GameButtonContainer.findActiveButton(name);
    	this.buttonControlX = buttonControlSelected.getX();
    	this.buttonControlY = buttonControlSelected.getY();
    	this.buttonControlWidth = buttonControlSelected.getWidth();
    	this.buttonControlHeight = buttonControlSelected.getHeight();
    }

    public void setSelectedMode(ButtonNames name){
    	this.buttonModeSelected = GameButtonContainer.findActiveButton(name);
    	this.buttonModeX = buttonModeSelected.getX();
    	this.buttonModeY = buttonModeSelected.getY();
    	this.buttonModeWidth = buttonModeSelected.getWidth();
    	this.buttonModeHeight = buttonModeSelected.getHeight();
    }
    
    public void restart(){
		this.activeButtons = GameButtonContainer.activeButtons;
		this.fish = GameObjectContainer.fish;
	}
    
	public void render(float delta){
		super.render(delta);
        batcher.begin();
		
		if(Settings.TUT_MENU2){
	        drawTutorial(delta);
	        batcher.end();
	        return;
		}
		
	    drawBackground();
        drawFish();

	    drawPanels();
	    drawTexts(delta);
	    drawButtons(delta);
	    drawSelectedButtons();
        batcher.end();
	}
	
	private void drawButtons(float delta){
        batcher.enableBlending();
		for(SimpleButton button : activeButtons){
			button.render(batcher, delta);
		}
	}
	
	private void drawSelectedButtons(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.buttonSelected, buttonDifficultyX, buttonDifficultyY, buttonDifficultyWidth, buttonDifficultyHeight);
        batcher.draw(TextureLoader.buttonSelected, buttonControlX, buttonControlY, buttonControlWidth, buttonControlHeight);
        batcher.draw(TextureLoader.buttonSelected, buttonModeX, buttonModeY, buttonModeWidth, buttonModeHeight);
	}
	
	private void drawPanels(){
        batcher.enableBlending();
        
        batcher.draw(TextureLoader.panel, panelX, panelY - 100, panelW + 60, 520);
        batcher.draw(TextureLoader.panel, panelX2, panelY - 100, panelW + 60, 520);
        batcher.draw(TextureLoader.panel, panelX3, panelY - 100, panelW + 60, 520);
	}


	private void drawBackground(){
        batcher.disableBlending();
        batcher.draw(TextureLoader.background, 0, 0, virtualWidth, virtualHeight);
	}

	private void drawFish(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.fish0, fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight());
	}
	
	private void drawTexts(float delta){
        batcher.enableBlending();
        for(Text t : texts)
        	t.draw(batcher, delta);
	}
	
	private void drawTutorial(float delta){
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
		int textYGap = 15;
		int textStartY = 100;
		float textScaleX = 0.85f;
		float textScaleY = -0.85f;
		
		Text t1 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t8 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t9 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		
		t1.setCenteredHorizzontally("Now choose the game options!", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Difficulty", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 1 + 40);
		t3.setCenteredHorizzontally("Change the speed of the fish!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally("Controls", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 80);
		t5.setCenteredHorizzontally("Change how you move the fish!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 80);
		t6.setCenteredHorizzontally("Modes", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 120);
		t7.setCenteredHorizzontally("Choose one of these modes and", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 120);
		t8.setCenteredHorizzontally("discover new challenges!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 7 + 120);
		t9.setCenteredHorizzontally("Other modes will come soon!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 8 + 120);
		
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
}
