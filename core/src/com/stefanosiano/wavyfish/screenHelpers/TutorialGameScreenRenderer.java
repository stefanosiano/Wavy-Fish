package com.stefanosiano.wavyfish.screenHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.stefanosiano.wavyfish.utilities.Settings;

public class TutorialGameScreenRenderer extends GameScreenCommonRenderer {
	private boolean showTextBackground;
	
    public TutorialGameScreenRenderer(){
    	super();
    	difficultyText.updateTextCenteredHorizzontally("Tutorial - " + Settings.gameControl);
    	showTextBackground = true;
    }

    @Override
	protected void drawRunning(float delta){
		drawWallAndFish(runTime, delta);
		//drawLives(delta);
		//drawScore(delta);
		drawButtons(delta);
		drawDifficulty(delta);
		if(showTextBackground)
			drawtextBackground();
		drawTexts(delta);
	}
    
    private void drawtextBackground(){
        batcher.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.rect(300, 350, 1000, 200);
        shapeRenderer.end();
        batcher.begin();
    }
    
    public void stopShowingTextBackground(){
    	showTextBackground = false;
    }
}
