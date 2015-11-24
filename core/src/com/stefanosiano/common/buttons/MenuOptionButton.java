package com.stefanosiano.common.buttons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class MenuOptionButton extends SimpleButton{
	private TextureRegion buttonUpEnabled;
	private TextureRegion buttonDownEnabled;
	private TextureRegion buttonUpDisabled;
	private TextureRegion buttonDownDisabled;
	//other option buttons linked to the same variable, used to maintain consistency
	private ArrayList<MenuOptionButton> linkedButtons;

	public MenuOptionButton(int x, int y, int width, int height, TextureRegion buttonUpEnabled, TextureRegion buttonDownEnabled,
			ButtonNames name, TextureRegion buttonUpDisabled, TextureRegion buttonDownDisabled, TextureRegion buttonDisabled) {
		super(x, y, width, height, buttonUpEnabled, buttonDownEnabled, buttonDisabled, name);
		this.buttonUpDisabled = buttonUpDisabled;
		this.buttonDownDisabled = buttonDownDisabled;
		this.buttonUpEnabled = buttonUpEnabled;
		this.buttonDownEnabled = buttonDownEnabled;
		this.linkedButtons = new ArrayList<MenuOptionButton>();
	}
    
    public void changeTextures(){
    	if(this.buttonDown == null || this.buttonUp == null)
    		return;
        if(this.buttonDown.equals(buttonDownDisabled)){
        	this.buttonDown = buttonDownEnabled;
            this.buttonUp = buttonUpEnabled;
        }
        else {
            this.buttonDown = buttonDownDisabled;
            this.buttonUp = buttonUpDisabled;
        }
    }
    
    public void addLinkedButton(MenuOptionButton button){
    	linkedButtons.add(button);
    }
    
    public ArrayList<MenuOptionButton> getLinkedButtons(){
    	return linkedButtons;
    }
}
