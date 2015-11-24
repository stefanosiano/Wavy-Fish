package com.stefanosiano.common.inputs;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.common.tochange.ScreenConfig;

public class InputHandler implements InputProcessor {
	//flag to check whenever we press a button before releasing the finger from the screen (useful to know if data should be passed to gesture detector)
	private boolean touchedDown;
	
	public InputHandler (){
		super();
		touchedDown = false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK){
			GameButtonContainer.backPressed();
		}
		if(keycode == Keys.SPACE){
			GameButtonContainer.backPressed();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Scaling X and Y coordinates for different resolutions
		screenX = adjustX(screenX);
		screenY = adjustY(screenY);
		
		//this touchUp prevents multitouch!
		//GameButtonContainer.touchUp(screenX, screenY);
		//if there are multiple touches, we don't let swipe work if even only one of the touches are on a button.
		//so, if there is another touch not on a button, it doesn't turno touchedDown to false
		if(GameButtonContainer.touchDown(screenX, screenY))
			touchedDown = true;
		return touchedDown;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = adjustX(screenX);
		screenY = adjustY(screenY);
		if(touchedDown){
			touchedDown = false;
			GameButtonContainer.touchUp(screenX, screenY);
			return true;
		}
		GameButtonContainer.touchUp(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	private int adjustX(int screenX){
		screenX = screenX - (int)(ScreenConfig.HWWIDTH - ScreenConfig.RENDERINGWIDTH) / 2;
		return (int) (screenX / ScreenConfig.SCALEX);
	}
	
	private int adjustY(int screenY){
		screenY = screenY - (int)(ScreenConfig.HWHEIGHT - ScreenConfig.RENDERINGHEIGHT) / 2;
		return (int) (screenY / ScreenConfig.SCALEY);
	}

}
