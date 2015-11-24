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
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.SwipeSign;
import com.stefanosiano.common.tochange.TextureLoader;
import com.stefanosiano.wavyfish.utilities.Enums.InfoState;

public class CreditsScreenRenderer extends SimpleRenderer{
    private List<SimpleButton> activeButtons;
    private InfoState state;
    private SwipeSign swipeSign;
    private ArrayList<Text> classicInfoTexts, flappyInfoTexts, wavyInfoTexts, bouncyInfoTexts, creditsTexts, levelTexts;
    
    public CreditsScreenRenderer(){
    	super();
		this.activeButtons = GameButtonContainer.activeButtons;
		this.swipeSign = GameObjectContainer.swipeSignCredits;
		createClassicInfoTexts();
		createFlappyTutorialTexts();
		createWavyTutorialTexts();
		createLevelsTexts();
		createCreditsTexts();
		createBouncyTutorialTexts();
    }
	
	public void render(float delta){
		super.render(delta);
		batcher.begin();
		drawInfos();

        batcher.enableBlending();
	    switch(state){
	    	case levelInfo:
	            for(Text t : levelTexts)
	            	t.draw(batcher, delta);
	    		break;
	    	case classicInfo:
	            for(Text t : classicInfoTexts)
	            	t.draw(batcher, delta);
	    		break;
	    	case flappyInfo:
	            for(Text t : flappyInfoTexts)
	            	t.draw(batcher, delta);
	    		break;
	    	case wavyInfo:
	            for(Text t : wavyInfoTexts)
	            	t.draw(batcher, delta);
	    		break;
	    	case bouncyInfo:
	            for(Text t : bouncyInfoTexts)
	            	t.draw(batcher, delta);
	    		break;
	    	case credits:
	            for(Text t : creditsTexts)
	            	t.draw(batcher, delta);
	    		break;
    		default:
	            for(Text t : creditsTexts)
	            	t.draw(batcher, delta);
    			break;
	    }
	    
	    drawSwipeSign(delta);
	    drawButtons(delta);
		batcher.end();
	}
	
	private void drawInfos(){
        batcher.enableBlending();
        batcher.draw(TextureLoader.background, 0,  0, virtualWidth, virtualHeight);
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
	}
	
	private void drawButtons(float delta){
        batcher.enableBlending();
		for(SimpleButton button : activeButtons){
			button.render(batcher, delta);
		}
	}
	
	private void drawSwipeSign(float delta){
        batcher.enableBlending();
        swipeSign.render(batcher, delta);
	}
	
	public void setInfoState(InfoState state){
		this.state = state;
	}
	
	

    
    private void createClassicInfoTexts(){
    	classicInfoTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 20;
		int textStartY = 90;
		float textScaleX = 0.95f;
		float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Classic mode", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Tap the upper part of the", textCenterX, textStartY  + (- t1.getHeight() + textYGap) + 50);
		t3.setCenteredHorizzontally("screen to move up the fish", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 50);
		t4.setCenteredHorizzontally("Tap the lower part of the", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 100);
		t5.setCenteredHorizzontally("screen to move it down", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 100);
		t6.setCenteredHorizzontally("You can also hold your", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 150);
		t7.setCenteredHorizzontally("finger on the screen!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 150);
		
		classicInfoTexts.add(t1);
		classicInfoTexts.add(t2);
		classicInfoTexts.add(t3);
		classicInfoTexts.add(t4);
		classicInfoTexts.add(t5);
		classicInfoTexts.add(t6);
		classicInfoTexts.add(t7);
    }
    
    private void createFlappyTutorialTexts(){
		flappyInfoTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 20;
		int textStartY = 90;
		float textScaleX = 0.95f;
		float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Flappy mode", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Tap anywhere to make", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 1 + 40);
		t3.setCenteredHorizzontally("the fish 'jump'!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally("It doesn't matter if", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 80);
		t5.setCenteredHorizzontally("the fish touches the", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 80);
		t6.setCenteredHorizzontally("bottom of the screen:", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 80);
		t7.setCenteredHorizzontally("Just avoid the obstacles!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 120);
				
		flappyInfoTexts.add(t1);
		flappyInfoTexts.add(t2);
		flappyInfoTexts.add(t3);
		flappyInfoTexts.add(t4);
		flappyInfoTexts.add(t5);
		flappyInfoTexts.add(t6);
		flappyInfoTexts.add(t7);
    }
    
    private void createWavyTutorialTexts(){
    	wavyInfoTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 20;
		int textStartY = 90;
		float textScaleX = 0.95f;
		float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Wavy mode", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Touch the screen and", textCenterX, textStartY - t1.getHeight() + textYGap + 40);
		t3.setCenteredHorizzontally("don't move your", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally("finger from it!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 40);
		t5.setCenteredHorizzontally("Your fish will follow", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 100);
		t6.setCenteredHorizzontally("your finger and then", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 100);
		t7.setCenteredHorizzontally("will start to wave!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 100);
		
		wavyInfoTexts.add(t1);
		wavyInfoTexts.add(t2);
		wavyInfoTexts.add(t3);
		wavyInfoTexts.add(t4);
		wavyInfoTexts.add(t5);
		wavyInfoTexts.add(t6);
		wavyInfoTexts.add(t7);
    }
    
    private void createBouncyTutorialTexts(){
    	bouncyInfoTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 20;
		int textStartY = 90;
		float textScaleX = 0.95f;
		float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Bouncy mode", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Tap the screen and", textCenterX, textStartY - t1.getHeight() + textYGap + 40);
		t3.setCenteredHorizzontally("change the fish", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally("direction!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 40);
		t5.setCenteredHorizzontally("When the fish reaches", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 100);
		t6.setCenteredHorizzontally("the end of the", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 100);
		t7.setCenteredHorizzontally("screen will bounce!", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 100);
		
		bouncyInfoTexts.add(t1);
		bouncyInfoTexts.add(t2);
		bouncyInfoTexts.add(t3);
		bouncyInfoTexts.add(t4);
		bouncyInfoTexts.add(t5);
		bouncyInfoTexts.add(t6);
		bouncyInfoTexts.add(t7);
    }
    
    private void createLevelsTexts(){
		levelTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 30;
		int textStartY = 120;
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
		Text t9 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t10 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t11 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Levels and achievements", textCenterX, textStartY);
		textCenterX = 570;
		
		t2.set("1) Start", textCenterX, textStartY - t1.getHeight() + textYGap + 70);
		t3.set("2) Medium", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 70);
		t4.set("3) New Fish", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 70);
		t5.set("4) Extra life", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 70);
		t6.set("5) Hard", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 70);
		
		textCenterX = 1070;
		t7.set("6) New Fish", textCenterX, textStartY - t1.getHeight() + textYGap + 70);
		t8.set("7) Extra life", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 70);
		t9.set("8) Crazy", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 70);
		t10.set("9) New Fish", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 70);
		t11.set("10) Extra life", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 70);
		
		
		levelTexts.add(t1);
		levelTexts.add(t2);
		levelTexts.add(t3);
		levelTexts.add(t4);
		levelTexts.add(t5);
		levelTexts.add(t6);
		levelTexts.add(t7);
		levelTexts.add(t8);
		levelTexts.add(t9);
		levelTexts.add(t10);
		levelTexts.add(t11);
    }
    
    private void createCreditsTexts(){
		creditsTexts = new ArrayList<Text>();
		int textCenterX = 1040;
		int textYGap = 20;
		int textStartY = 90;
		float textScaleX = 1f;
		float textScaleY = -1f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t3 = new Text(TextureLoader.fontOrange, textScaleX, textScaleY, false);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t5 = new Text(TextureLoader.fontOrange, textScaleX, textScaleY, false);
		Text t6 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, false);
		Text t7 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, false);
		
		t1.setCenteredHorizzontally("Credits", textCenterX, textStartY);
		t2.setCenteredHorizzontally("Developed by", textCenterX, textStartY - t1.getHeight() + textYGap + 40);
		t3.setCenteredHorizzontally("Stefano Siano", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally("Graphics by", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 80);
		t5.setCenteredHorizzontally("Andrea Ditex", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 80);
		t6.setCenteredHorizzontally("Powered by", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 120);
		t7.setCenteredHorizzontally("LibGDX", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 6 + 120);
		
		creditsTexts.add(t1);
		creditsTexts.add(t2);
		creditsTexts.add(t3);
		creditsTexts.add(t4);
		creditsTexts.add(t5);
		creditsTexts.add(t6);
		creditsTexts.add(t7);
    }
}
