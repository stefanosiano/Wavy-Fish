package com.stefanosiano.wavyfish.screens;

import com.stefanosiano.common.SimpleScreen;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.WavyFishGame;
import com.stefanosiano.wavyfish.screenHelpers.MenuScreenRenderer;
import com.stefanosiano.wavyfish.screenHelpers.MenuScreenUpdater;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;

public class MenuScreen extends SimpleScreen{
	private MenuScreenUpdater updater;
    private boolean showingFish;

	public MenuScreen(WavyFishGame game){
		super(game, new MenuScreenRenderer());
		setRendererScreen(this);
		if(Settings.TUT_MENU1){
            ((MenuScreenRenderer)renderer).createTutorialTexts();
	    	GameButtonContainer.setButtons(GameState.tutorial);
		}
		else
			GameButtonContainer.setButtons(GameState.menu);

		this.updater = new MenuScreenUpdater(this);
        showingFish = false;
		fadeIn(new Runnable() {
			@Override
			public void run() {updater.startUpdate();}
		}, 0.4f);
		restart();
	}
	
	public void showEarnMoreLives(boolean showFish){
		((MenuScreenRenderer) renderer).showFishMoreLives(showFish);
        showingFish = showFish;
	}

    public boolean isShowingFish() {
        return showingFish;
    }

    @Override
	public void render(float delta) {
		updater.update(delta);
		renderer.renderAll(delta);
	}

	public void restart(){
		updater.restart();
		((MenuScreenRenderer) renderer).restart();
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
