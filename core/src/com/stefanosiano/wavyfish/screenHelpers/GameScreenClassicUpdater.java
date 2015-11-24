package com.stefanosiano.wavyfish.screenHelpers;

import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.screens.GameScreen;

public class GameScreenClassicUpdater extends GameScreenUpdater{
	public GameScreenClassicUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController) {
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
	}
}
