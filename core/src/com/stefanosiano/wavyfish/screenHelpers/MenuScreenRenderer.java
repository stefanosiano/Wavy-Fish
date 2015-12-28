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
			if(showFishMoreLives) {
                drawFishMoreLives(delta);
                drawButtons(delta);
            }
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
        if(!Settings.FIRST_SHARE)
		    createExtraLifeFirstShareTexts();
        else
            createExtraLifeWatchRewardedVideo();
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
    
    public void createTutorialTexts(){
		tutorialTexts = new ArrayList<Text>();
		int textStartX = 550;
        int textWidth = 1000;
		int textStartY = 80;
		float textScaleX = 0.9f;
		float textScaleY = -0.9f;

		Text t1 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);

        t1.set(screen.getGame().getString("tut1_1"), textStartX, textStartY, textWidth, Align.center);
        t2.set(screen.getGame().getString("tut1_2"), textStartX, textStartY + (-t1.getHeight()) + 200, textWidth, Align.center);
        t3.set(screen.getGame().getString("tut1_3"), textStartX, textStartY + (-t1.getHeight()) * 6 + 260, textWidth, Align.center);
		
		tutorialTexts.add(t1);
		tutorialTexts.add(t2);
		tutorialTexts.add(t3);
    }

    private void createExtraLifeFirstShareTexts() {
        if(earnMoreLivesTexts == null)
            earnMoreLivesTexts = new ArrayList<Text>();
        else
            earnMoreLivesTexts.clear();

        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 80;
        float textScaleX = 0.9f;
        float textScaleY = -0.9f;

        Text t1 = new Text(TextureLoader.fontYellow, 1, -1, false);
        Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
        Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);

        t1.set(screen.getGame().getString("get_extra_life"), textStartX, textStartY, textWidth, Align.center);
        t2.set(screen.getGame().getString("extra_life_1"), textStartX, textStartY + (-t1.getHeight()) * 1 + 70, textWidth, Align.center);
        t3.set(screen.getGame().getString("extra_life_2"), textStartX, textStartY + (-t1.getHeight()) * 4 + 200, textWidth, Align.center);

        earnMoreLivesTexts.add(t1);
        earnMoreLivesTexts.add(t2);
        earnMoreLivesTexts.add(t3);
    }

    private void createExtraLifeWatchRewardedVideo() {
        if(earnMoreLivesTexts == null)
            earnMoreLivesTexts = new ArrayList<Text>();
        else
            earnMoreLivesTexts.clear();

        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 80;
        float textScaleX = 0.9f;
        float textScaleY = -0.9f;

        Text t1 = new Text(TextureLoader.fontYellow, 1, -1, false);
        Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);

        t1.set(screen.getGame().getString("get_extra_life"), textStartX, textStartY, textWidth, Align.center);
        t2.set(screen.getGame().getString("extra_life_2"), textStartX, textStartY + (-t1.getHeight()) * 1 + 70, textWidth, Align.center);

        earnMoreLivesTexts.add(t1);
        earnMoreLivesTexts.add(t2);
    }
}
