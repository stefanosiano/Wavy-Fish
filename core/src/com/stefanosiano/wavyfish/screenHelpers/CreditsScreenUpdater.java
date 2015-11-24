package com.stefanosiano.wavyfish.screenHelpers;

import java.util.List;

import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.SwipeSign;
import com.stefanosiano.wavyfish.screens.CreditsScreen;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Enums.Screens;

public class CreditsScreenUpdater {
	private List<Enums.ButtonNames> names;
	private Runnable fadeOutR;
	private SwipeSign swipeSign;
	private CreditsScreen creditScreen;
	
	public CreditsScreenUpdater(final CreditsScreen creditsScreen){
		this.fadeOutR = new Runnable(){
			@Override
			public void run() {
				creditsScreen.getGame().changeScreen(Screens.menu);
				creditsScreen.dispose();
			}
		};
		this.creditScreen = creditsScreen;
		this.swipeSign = GameObjectContainer.swipeSignCredits;
	}

	
	public void update(float delta){
		updateCredits(delta);
	}

	public void onSwipeLeft(){
		creditScreen.setState(true);
	}
	
	public void onSwipeRight(){
		creditScreen.setState(false);
	}

	private void updateCredits(float delta) {
		swipeSign.update(delta);
		
		names = GameButtonContainer.getBtnPressed();
		for(Enums.ButtonNames name : names){
			switch(name){
				case hardwareBack:
				case buttonBack:
					GameButtonContainer.disableAllButtons();
					creditScreen.fadeOut(fadeOutR, 0.4f);
					break;
				default:
					break;
			}
		}
	}
}