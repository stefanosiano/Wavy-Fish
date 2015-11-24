package com.stefanosiano.wavyfish.screens;

import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.CreditsScreenRenderer;
import com.stefanosiano.wavyfish.screenHelpers.CreditsScreenUpdater;
import com.stefanosiano.wavyfish.utilities.AnalyticsSender;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;
import com.stefanosiano.wavyfish.utilities.Enums.InfoState;

public class CreditsScreen extends SimpleScreen{
	private CreditsScreenUpdater updater;
	private InfoState state;
	private InfoState[] states;
	private int stateIndex;

	public CreditsScreen(WavyFishGame game){
		super(game, new CreditsScreenRenderer());
		setRendererScreen(this);
    	GameButtonContainer.setButtons(GameState.info);
		this.updater = new CreditsScreenUpdater(this);
		this.stateIndex = 0;
		this.states = InfoState.values();
		this.state = states[stateIndex];
		((CreditsScreenRenderer) renderer).setInfoState(state);

		AnalyticsSender.sendOpenInfo(game);
		fadeIn(0.4f);
	}
	
	public void setState(boolean nextState){
		if(nextState)
			stateIndex++;
		else
			stateIndex--;
		stateIndex += states.length;
		stateIndex = stateIndex % states.length;
		
		state = states[stateIndex];
		((CreditsScreenRenderer) renderer).setInfoState(state);
	}
	
	@Override
	public void render(float delta) {
		updater.update(delta);
		renderer.renderAll(delta);
	}
	

	@Override
	public void onSwipeLeft(){
		updater.onSwipeLeft();
	}
	
	@Override
	public void onSwipeRight(){
		updater.onSwipeRight();
	}
}
