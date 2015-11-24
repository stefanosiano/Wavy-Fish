package com.stefanosiano.common.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class OneTouchButton extends SimpleButton {

	public OneTouchButton(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, ButtonNames name, TextureRegion buttonDisabled) {
		super(x, y, width, height, buttonUp, buttonDown, buttonDisabled, name);
	}
}
