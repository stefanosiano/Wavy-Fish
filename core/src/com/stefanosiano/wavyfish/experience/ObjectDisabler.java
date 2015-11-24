package com.stefanosiano.wavyfish.experience;

import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class ObjectDisabler {

	/* 
	 * exp - level
	 * 0 - 1
	 * 200 - 2 - medium
	 * 500 - 3 - fish
	 * 900 - 4 - life
	 * 1400 - 5 - hard
	 * 2000 - 6 - fish
	 * 2700 - 7 - life
	 * 3500 - 8 - crazy
	 * 4400 - 9 - fish
	 * 5400 - 10 - life
	 */
	//THIS DISABLES MENU START BUTTONS BASED ON CURRENT LEVEL
	public void disableMenuButtons(){
		int level = Experience.getLevel();

		GameButtonContainer.setEnabledButton(ButtonNames.buttonMedium, true);
		GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonMedium, false);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonHard, true);
		GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonHard, false);
		GameButtonContainer.setEnabledButton(ButtonNames.buttonCrazy, true);
		GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonCrazy, false);
		
		switch(level){
			case 1:
				GameButtonContainer.setEnabledButton(ButtonNames.buttonMedium, false);
				GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonMedium, true);
			case 2:
			case 3:
			case 4:
				GameButtonContainer.setEnabledButton(ButtonNames.buttonHard, false);
				GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonHard, true);
			case 5:
			case 6:
			case 7:
				GameButtonContainer.setEnabledButton(ButtonNames.buttonCrazy, false);
				GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonCrazy, true);
			case 8:
			case 9:
			case 10:
			default:
				break;
		}
	}

	//THIS DISABLES ALL MENU START BUTTONS FOR FISH THAT CAN'T BE USED BASED ON CURRENT LEVEL
	public void disableMenuFish(Enums.ImageType imageType){
		int level = Experience.getLevel();

		enableStart(true);
		disableMenuButtons();
		switch(level){
			case 1:
			case 2:
				if(imageType.equals(Enums.ImageType.horror))
					enableStart(false);
				
			case 3:
			case 4:
			case 5:
				if(imageType.equals(Enums.ImageType.tema))
					enableStart(false);
			case 6:
			case 7:
			case 8:
				if(imageType.equals(Enums.ImageType.space))
					enableStart(false);
			case 9:
			case 10:
			default:
				break;
		}
	}
	
	
	private void enableStart(boolean enabled){
		GameButtonContainer.setEnabledButton(ButtonNames.buttonStartChoose, enabled);
		GameButtonContainer.setDrawDisabledButton(ButtonNames.buttonStartChoose, !enabled);
	}
}
