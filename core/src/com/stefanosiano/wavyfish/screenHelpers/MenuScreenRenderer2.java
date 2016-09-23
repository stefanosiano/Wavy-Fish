package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
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
	private ButtonNames[] buttonModeNames = {ButtonNames.buttonEndlessMode, ButtonNames.buttonBlinkingkMode, ButtonNames.buttonPiranhaMode};
	private ArrayList<ModeSelection> modeSelections;

    
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


		modeSelections = new ArrayList<>(buttonModeNames.length);
		for(ButtonNames name : buttonModeNames){
			this.buttonModeSelected = GameButtonContainer.findActiveButton(name);
			if(buttonModeSelected == null)
				continue;
			modeSelections.add(new ModeSelection(name, buttonModeSelected.getX(), buttonModeSelected.getY(), buttonModeSelected.getWidth(), buttonModeSelected.getHeight()));
		}
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
		for(ModeSelection mode : modeSelections){
			if(mode.name.equals(name)){
				mode.isSelected = !mode.isSelected;
			}
		}
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
		for(ModeSelection modeSelection : modeSelections) {
			if(modeSelection.isSelected)
				batcher.draw(TextureLoader.buttonSelected, modeSelection.x, modeSelection.y, modeSelection.width, modeSelection.height);
		}
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
    
    public void createTutorialTexts(){
		tutorialTexts = new ArrayList<Text>();
		int textYGap = 15;
		int textStartY = 100;
		float textScaleX = 0.85f;
		float textScaleY = -0.85f;
        int textStartX = 540;
        int textWidth = 1000;
		
		Text t1 = new Text(TextureLoader.fontOrange, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		
		
		t1.set(screen.getGame().getString("tut2_1"), textStartX, textStartY, textWidth, Align.center);
		t2.set(screen.getGame().getString("difficulty"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 1 + 40, textWidth, Align.center);
		t3.set(screen.getGame().getString("tut2_2"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 2 + 40, textWidth, Align.center);
		t4.set(screen.getGame().getString("controls"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 3 + 80, textWidth, Align.center);
		t5.set(screen.getGame().getString("tut2_3"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 4 + 80, textWidth, Align.center);
		t6.set(screen.getGame().getString("modes"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 5 + 120, textWidth, Align.center);
        t7.set(screen.getGame().getString("tut2_4"), textStartX, textStartY + (-t1.getHeight() + textYGap) * 6 + 120, textWidth, Align.center);

		tutorialTexts.add(t1);
		tutorialTexts.add(t2);
		tutorialTexts.add(t3);
		tutorialTexts.add(t4);
		tutorialTexts.add(t5);
		tutorialTexts.add(t6);
		tutorialTexts.add(t7);
    }

	private class ModeSelection {
		private ButtonNames name;
		private int x, y, width, height;
		private boolean isSelected;

		public ModeSelection(ButtonNames name, int x, int y, int width, int height) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.isSelected = false;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ModeSelection that = (ModeSelection) o;

			return name == that.name;

		}

		@Override
		public int hashCode() {
			return name != null ? name.hashCode() : 0;
		}
	}
}
