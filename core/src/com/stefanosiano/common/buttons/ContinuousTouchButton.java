package com.stefanosiano.common.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.wavyfish.utilities.Enums;

public class ContinuousTouchButton extends SimpleButton{
    
    public ContinuousTouchButton(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, Enums.ButtonNames name,
    		TextureRegion buttonDisabled) {
    	super(x, y, width, height, buttonUp, buttonDown, buttonDisabled, name);
    }
    

	@Override
	public boolean equals(Object o){
		if(this == null || o == null)
			return false;
		if(o instanceof ContinuousTouchButton){
			if(getName().equals(((ContinuousTouchButton)o).getName()))
				return true;
		}
		return false;
	}
}
