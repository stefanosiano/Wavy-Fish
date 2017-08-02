package com.stefanosiano.wavyfish.screenHelpers;

import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class GameScreenBouncingUpdater extends GameScreenUpdater{
	public GameScreenBouncingUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController) {
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
		fish.startBounce();
	}

	@Override
	protected void reset(){
		super.reset();
		fish.startBounce();
	}


	@Override
	protected void updateRunning(float delta) {
		time += delta;
		background.update(delta);
		background2.update(delta);
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();

		for(Obstacle ob : obstacles)
			ob.update(delta);

		for(ButtonNames name : names){
			switch(name){
				case buttonFlappyBackground:
					fish.changeBounceDirection();
					break;
				case hardwareBack:
					pause();
					break;
				default:
					break;
			}
		}
		fish.updateBounce(delta);

		//Called everytime if the fish collides
		if(!fishCollided && GameObjectContainer.fishCollide()){
			fishCollide();
			return;
		}

		//Called once when half of fish pass the left border of wall
		if(GameObjectContainer.wallPassed()){
			wallPassed();
			return;
		}

		//Called once when the fish pass the right border of wall
		if(!fishPassed && GameObjectContainer.wallFinishedPassed()){
			wallFinishedPassed();
		}

	}
}
