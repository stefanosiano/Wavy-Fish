package com.stefanosiano.common.tochange;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stefanosiano.common.buttons.ContinuousTouchButton;
import com.stefanosiano.common.buttons.MenuOptionButton;
import com.stefanosiano.common.buttons.OneTouchButton;
import com.stefanosiano.common.buttons.SimpleButton;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;
import com.stefanosiano.wavyfish.utilities.Enums.GameState;

public class GameButtonContainer {
	private static List<SimpleButton> buttons = new ArrayList<SimpleButton>();
	public static List<SimpleButton> activeButtons = new ArrayList<SimpleButton>();
	private static List<Enums.ButtonNames> allButtonsReturned = new ArrayList<Enums.ButtonNames>();
	private static List<Enums.ButtonNames> buttonsReturned = new ArrayList<Enums.ButtonNames>();
	private static List<Enums.ButtonNames> buttonsOnceReturned = new ArrayList<Enums.ButtonNames>();
	private static boolean backPressed = false;
	private static boolean buttonsEnabled = true;
	//private static boolean buttonsJustChanged = false;//used to understand when i click on a button so that the "allButtonsReturned" has to be refreshed
	//private static boolean removeButtonsOnceReturned = false;//used to understand when i have to remove the one-click buttons from the returnd list

	/**
	 * used to change the list of buttons more efficiently: when i click on a button i check for another frame,
	 * and if i check for a oneTouch/menu button i check the list the next two frames 
	 */
	private static int checkForOtherFrames = 0; 

	public static List<Enums.ButtonNames> getBtnPressed(){
		if(checkForOtherFrames <= 0)
			return allButtonsReturned;
		
		checkForOtherFrames--;
		allButtonsReturned.clear();
		allButtonsReturned.addAll(buttonsReturned);
		allButtonsReturned.addAll(buttonsOnceReturned);
		buttonsOnceReturned.clear();
		if(backPressed && buttonsEnabled){
			allButtonsReturned.add(ButtonNames.hardwareBack);
			backPressed = false;
			//after handling the back, i have to remove it!
			checkForOtherFrames++;
		}	
		return allButtonsReturned;
	}
	
	

	public static void setButtons(GameState gameState){
		activeButtons.clear();
		switch(gameState){
			case info:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBack)));
				break;
				
			case menu:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonFullScreen)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonSoundEnabled)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonMusic)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonCredits)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonStartChoose)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonOptions)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonShareText)));
				if(!Settings.FIRST_SHARE){
					activeButtons.add(buttons.get(findButton(Enums.ButtonNames.moreLivesShine)));
					activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonEarnMoreLives)));
					buttons.get(findButton(Enums.ButtonNames.moreLivesShine)).setEnabled(false);
				}
				break;
	
			case menu2:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonContinue)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonEasy)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonMedium)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonHard)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonCrazy)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonClassicControl)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonFlappyControl)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonWavyControl)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBouncingControl)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonNormalMode)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBlinkingkMode)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonPiranhaMode)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBack)));
				break;
				
			case tutorial:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBackgroundTutorial)));
				break;
	
			case ready:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBackground)));
				break;

			case newTutorialFinishing:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonTutorialStartGame)));
				
			case running:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonFishDown)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonFishUp)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonFlappyBackground)));
				break;
	
			case highScore:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBackground)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.shareScoreShine)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonShareScore)));
				buttons.get(findButton(Enums.ButtonNames.shareScoreShine)).setEnabled(false);
				break;
	
			case lost:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonRestart)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonLostBackToMenu)));
				break;
	
			case resuming:
				break;
	
			case pause:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonResume)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBackToMenu)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonPauseMusic)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonPauseSoundEnabled)));
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonPauseFullScreen)));
				break;
				
			case earnMoreLives:
				activeButtons.add(buttons.get(findButton(Enums.ButtonNames.buttonBackground)));
				break;
				
			case noButtons:
				
			default:
				break;
		}
		enableAllButtons();
	}
	
	
	
	
	public static void addMenuOptionBtn(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, TextureRegion buttonDisabled, 
			TextureRegion buttonUpDisabled, TextureRegion buttonDownDisabled, Enums.ButtonNames name, boolean enabled) {
		removeBtn(name);

		if(enabled)
			buttons.add(new MenuOptionButton(x, y, width, height, buttonUp, buttonDown, name, buttonUpDisabled, buttonDownDisabled, buttonDisabled));
		else
			buttons.add(new MenuOptionButton(x, y, width, height, buttonUpDisabled, buttonDownDisabled, name, buttonUp, buttonDown, buttonDisabled));
	}
	
	public static void addOneTouchBtn(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, TextureRegion buttonDisabled, 
			Enums.ButtonNames name) {
		removeBtn(name);
		buttons.add(new OneTouchButton(x, y, width, height, buttonUp, buttonDown, name, buttonDisabled));
	}
	
	
	public static void addContinuousTouchBtn(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, TextureRegion buttonDisabled, 
			Enums.ButtonNames name) {
		removeBtn(name);
		buttons.add(new ContinuousTouchButton(x, y, width, height, buttonUp, buttonDown, name, buttonDisabled));
	}
	
	
	private static void removeBtn(Enums.ButtonNames name) {
		int position = findButton(name);
		if(position != -1)
			buttons.remove(position);
	}
	
	
	
	public static boolean touchDown(int x, int y){
		boolean touched = false;
		for(SimpleButton button : activeButtons){
			if(button.touchDown(x, y)){
				checkForOtherFrames = 2;
				touched = true;
				if(button instanceof OneTouchButton){
					if(!buttonsOnceReturned.contains(button.getName()))
						buttonsOnceReturned.add(button.getName());
					checkForOtherFrames = 2;
				}
				if(button instanceof MenuOptionButton){
					if(!buttonsOnceReturned.contains(button.getName())){
						buttonsOnceReturned.add(button.getName());
						for(MenuOptionButton b : ((MenuOptionButton) button).getLinkedButtons())
							b.changeTextures();
						((MenuOptionButton) button).changeTextures();
					}
					checkForOtherFrames = 2;
				}
				if(button instanceof ContinuousTouchButton){
					if(!buttonsReturned.contains(button.getName()))
						buttonsReturned.add(button.getName());

					if(buttonsReturned.contains(ButtonNames.buttonFishDown) && buttonsReturned.contains(ButtonNames.buttonFishUp)){
						buttonsReturned.remove(ButtonNames.buttonFishDown);
						buttonsReturned.remove(ButtonNames.buttonFishUp);
						buttonsReturned.add(button.getName());
					}
				}
				
				if(buttonsReturned.size() + buttonsOnceReturned.size() > 1){
					buttonsOnceReturned.remove(ButtonNames.buttonBackground);
				}
			}
		}
		return touched;
	}
	
	public static void touchUp(int x, int y){
		for(SimpleButton button : buttons){
			if(button.touchUp(x, y)){
				checkForOtherFrames = 1;
				buttonsReturned.remove(button.getName());
				buttonsOnceReturned.remove(button.getName());
			}
		}
		
	}
	
	public static void backPressed(){
		if(buttonsEnabled){
			checkForOtherFrames = 1;
			backPressed = true;
		}
	}
	
	
	private static int findButton(Enums.ButtonNames name){
		for(int i = 0; i < buttons.size(); i++){
			if(buttons.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}
	
	public static SimpleButton findActiveButton(Enums.ButtonNames name){
		for(int i = 0; i < activeButtons.size(); i++){
			if(activeButtons.get(i).getName().equals(name))
				return activeButtons.get(i);
		}
		return null;
	}
	
	
	
	public static void disableAllButtons(){
		buttonsEnabled = false;
		for(SimpleButton button : activeButtons){
			button.setEnabled(false);
		}
		//when i disable all buttons, i also remove any button that was pressed, and i check for other 2 frames to be sure to delete all the buttons pressed
		buttonsOnceReturned.clear();
		buttonsReturned.clear();
		checkForOtherFrames = 2;
	}
	
	public static void enableAllButtons(){
		buttonsEnabled = true;
		for(SimpleButton button : activeButtons){
			button.setEnabled(true);
		}
	}
	
	public static void linkMenuOptionButtons(Enums.ButtonNames name, Enums.ButtonNames name2){
		((MenuOptionButton)buttons.get(findButton(name))).addLinkedButton((MenuOptionButton)buttons.get(findButton(name2)));
		((MenuOptionButton)buttons.get(findButton(name2))).addLinkedButton((MenuOptionButton)buttons.get(findButton(name)));
	}
	
	public static void setEnabledButton(Enums.ButtonNames name, boolean enabled){
		SimpleButton button;
		button = findActiveButton(name);
		if(button != null)
			button.setEnabled(enabled);
	}
	
	public static void setText(Enums.ButtonNames name, String text, BitmapFont font, float scaleX, float scaleY){
		SimpleButton button;
		button = buttons.get(findButton(name));
		if(button != null)
			button.setText(text, font, scaleX, scaleY);
	}
	
	public static void setDrawDisabledButton(Enums.ButtonNames name, boolean drawDisabled){
		SimpleButton button;
		button = findActiveButton(name);
		if(button != null)
			button.setDrawDisabled(drawDisabled);
	}
	
	public static boolean getIsEnabledButton(Enums.ButtonNames name){
		SimpleButton button;
		button = findActiveButton(name);
		if(button != null)
			return button.isEnabled();
		return false;
	}
	
	public static boolean getDrawDisabledButton(Enums.ButtonNames name){
		SimpleButton button;
		button = findActiveButton(name);
		if(button != null)
			return button.getDrawDisabled();
		return false;
	}
}
