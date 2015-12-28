package com.stefanosiano.wavyfish.screenHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
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
    private I18NBundle info_strings;
    
    public CreditsScreenRenderer(){
    	super();
		this.activeButtons = GameButtonContainer.activeButtons;
		this.swipeSign = GameObjectContainer.swipeSignCredits;

        FileHandle baseFileHandle = Gdx.files.internal("data/info_strings");
        info_strings = I18NBundle.createBundle(baseFileHandle);

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
        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 90;
        float textScaleX = 0.95f;
        float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, true);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);

		t1.set(info_strings.format("classic_mode"), textStartX, textStartY, textWidth, Align.center);
		t2.set(info_strings.format("classic_mode1"), textStartX, textStartY + (-t1.getHeight()) + 50, textWidth, Align.center);
		t3.set(info_strings.format("classic_mode2"), textStartX, textStartY + (-t1.getHeight()) * 3 + 150, textWidth, Align.center);
		t4.set(info_strings.format("classic_mode3"), textStartX, textStartY + (-t1.getHeight()) * 5 + 250, textWidth, Align.center);
		
		classicInfoTexts.add(t1);
		classicInfoTexts.add(t2);
		classicInfoTexts.add(t3);
		classicInfoTexts.add(t4);
    }
    
    private void createFlappyTutorialTexts(){
		flappyInfoTexts = new ArrayList<Text>();
        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 90;
        float textScaleX = 0.95f;
        float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, true);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t4 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		
		t1.set(info_strings.format("flappy_mode"), textStartX, textStartY, textWidth, Align.center);
		t2.set(info_strings.format("flappy_mode1"), textStartX, textStartY + (-t1.getHeight()) * 1 + 70, textWidth, Align.center);
		t3.set(info_strings.format("flappy_mode2"), textStartX, textStartY + (-t1.getHeight()) * 3 + 200, textWidth, Align.center);

		flappyInfoTexts.add(t1);
		flappyInfoTexts.add(t2);
		flappyInfoTexts.add(t3);
    }
    
    private void createWavyTutorialTexts(){
    	wavyInfoTexts = new ArrayList<Text>();
        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 90;
        float textScaleX = 0.95f;
        float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, true);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		
		t1.set(info_strings.format("wavy_mode"), textStartX, textStartY, textWidth, Align.center);
		t2.set(info_strings.format("wavy_mode1"), textStartX, textStartY - t1.getHeight() + 100, textWidth, Align.center);
		t3.set(info_strings.format("wavy_mode2"), textStartX, textStartY + (-t1.getHeight()) * 4 + 180, textWidth, Align.center);

		wavyInfoTexts.add(t1);
		wavyInfoTexts.add(t2);
		wavyInfoTexts.add(t3);
    }
    
    private void createBouncyTutorialTexts(){
    	bouncyInfoTexts = new ArrayList<Text>();
        int textStartX = 550;
        int textWidth = 1000;
        int textStartY = 90;
        float textScaleX = 0.95f;
        float textScaleY = -0.95f;
		
		Text t1 = new Text(TextureLoader.fontYellow, textScaleX, textScaleY, true);
		Text t2 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		Text t3 = new Text(TextureLoader.fontBlue, textScaleX, textScaleY, true);
		
		t1.set(info_strings.format("bouncy_mode"), textStartX, textStartY, textWidth, Align.center);
		t2.set(info_strings.format("bouncy_mode1"), textStartX, textStartY - t1.getHeight() + 100, textWidth, Align.center);
		t3.set(info_strings.format("bouncy_mode2"), textStartX, textStartY + (-t1.getHeight()) * 4 + 180, textWidth, Align.center);
		
		bouncyInfoTexts.add(t1);
		bouncyInfoTexts.add(t2);
		bouncyInfoTexts.add(t3);
    }
    
    private void createLevelsTexts(){
		levelTexts = new ArrayList<Text>();
		int textYGap = 30;
		int textStartY = 120;
		float textScaleX = 0.9f;
		float textScaleY = -0.9f;

        int textStartX = 550;
        int textWidth = 1000;

		Text t1 = new Text(TextureLoader.fontYellow, 1, -1, true);
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

        t1.set(info_strings.format("lvls"), textStartX, textStartY, textWidth, Align.center);

        textWidth = 475;
        textStartX = 565;

		t2.set(info_strings.format("lvls1"), textStartX, textStartY - t1.getHeight() + textYGap + 70, textWidth, Align.left);
		t3.set(info_strings.format("lvls2"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 70, textWidth, Align.left);
		t4.set(info_strings.format("lvls3"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 70, textWidth, Align.left);
		t5.set(info_strings.format("lvls4"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 70, textWidth, Align.left);
		t6.set(info_strings.format("lvls5"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 70, textWidth, Align.left);

        textStartX = 1060;
		t7.set(info_strings.format("lvls6"), textStartX, textStartY - t1.getHeight() + textYGap + 70, textWidth, Align.left);
		t8.set(info_strings.format("lvls7"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 70, textWidth, Align.left);
		t9.set(info_strings.format("lvls8"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 70, textWidth, Align.left);
		t10.set(info_strings.format("lvls9"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 70, textWidth, Align.left);
		t11.set(info_strings.format("lvls10"), textStartX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 70, textWidth, Align.left);
		
		
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
		
		t1.setCenteredHorizzontally(info_strings.format("credits"), textCenterX, textStartY);
		t2.setCenteredHorizzontally(info_strings.format("credits1"), textCenterX, textStartY - t1.getHeight() + textYGap + 40);
		t3.setCenteredHorizzontally("Stefano Siano", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 2 + 40);
		t4.setCenteredHorizzontally(info_strings.format("credits2"), textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 3 + 80);
		t5.setCenteredHorizzontally("Andrea Ditex", textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 4 + 80);
		t6.setCenteredHorizzontally(info_strings.format("credits3"), textCenterX, textStartY  + (- t1.getHeight() + textYGap) * 5 + 120);
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
