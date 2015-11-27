package com.stefanosiano.wavyfish.screenHelpers;

import com.badlogic.gdx.Gdx;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.common.tochange.ScreenConfig;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class GameScreenWavyUpdater extends GameScreenUpdater{
	private float touchY;

	public GameScreenWavyUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController) {
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
	}


	@Override
	protected void updateRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(Obstacle wallCouple : obstacles)
			wallCouple.update(delta);
		
		for(ButtonNames name : names){
			switch(name){
				case hardwareBack:
					pause();
					break;
				default:
					break;
			}
		}

		touchY = Gdx.input.getY();

		touchY = touchY - (int)(ScreenConfig.HWHEIGHT - ScreenConfig.RENDERINGHEIGHT) / 2;
		touchY =  touchY / ScreenConfig.SCALEY;
		
		fish.updateWavy(delta, touchY);
		
		if(GameObjectContainer.fishCollide()){
			fishCollide();
		}
		
		if(GameObjectContainer.wallPassed()){
			wallPassed();
		}
		
		if(GameObjectContainer.wallFinishedPassed()){
			wallFinishedPassed();
		}
		
	}
	protected void reset(){
		super.reset();
	}
}
