package com.stefanosiano.common.buttons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.stefanosiano.common.SimpleItem;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;
import com.stefanosiano.wavyfish.utilities.Enums;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class SimpleButton extends SimpleItem {

    private int x, y;
    private int width;
    private int height;
    private Enums.ButtonNames name;

    protected TextureRegion buttonUp;
    protected TextureRegion buttonDown;
    protected TextureRegion buttonDisabled;

    private Rectangle bounds;
    private GlyphLayout layout;
    private String text;
    private float textX, textY;
    private BitmapFont font;
    private float fontScaleX, fontScaleY;

    protected boolean isPressed;
    protected boolean drawDisabled;

    //Tween stuffs
    
    public SimpleButton(int x, int y, int width, int height, TextureRegion buttonUp, TextureRegion buttonDown, TextureRegion buttonDisabled, Enums.ButtonNames name) {
		this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.offsetX = width/2;
        this.offsetY = height/2;
        this.scaleX = 1;
        this.scaleY = 1;
        this.rotation = 0;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonDisabled = buttonDisabled;
        this.text = "";
        this.isPressed = false;
        this.enabled = true;
        this.alpha = new FloatValue(1);

        bounds = new Rectangle(x, y, width, height);
		layout = new GlyphLayout();
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }


	/**
	 * Calls the draw method and the fade-in/fade-out
	 * transitions. This is the method you need to call!
	 */
    @Override
    public final void render(SpriteBatch batcher, float delta) {
    	if(buttonUp == null || buttonDown == null)
    		return;

    	super.render(batcher, delta);
    }

	/**
	 * Overridable method to draw the object.
	 */
	@Override
	protected void draw(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, offsetX, offsetY, width, height, scaleX, scaleY, rotation);
        } else {
            batcher.draw(buttonUp, x, y, offsetX, offsetY, width, height, scaleX, scaleY, rotation);
        }
        if(text != ""){
        	font.getData().setScale(fontScaleX, fontScaleY);
    		font.draw(batcher, text, textX, textY, width, Align.center, false);
        }
        
    	if (drawDisabled){
    		batcher.draw(buttonDisabled, x, y, offsetX, offsetY, width, height, scaleX, scaleY, rotation);
    	}
    }
	
	@Override
	protected void draw(SpriteBatch batcher, float runTime) {}

    
    public boolean touchDown(int screenX, int screenY) {
        if (enabled && bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }
    
    public boolean touchUp(int screenX, int screenY) {
        // Whenever a finger is released, we will cancel any presses. Then we return true if this button was released
        isPressed = false;
        if (enabled && bounds.contains(screenX, screenY)) {
            return true;
        }
        return false;
    }
	
	/**
	 * Method to set a text that will be drawn at the center of the button, with provided scale, that will be drawn in renderAll
	 * 
	 * @param text
	 * @param font
	 */
	public void setText(String text, BitmapFont font, float scaleX, float scaleY){
		this.text = text;
		this.font = font;
		this.fontScaleX = scaleX;
		this.fontScaleY = scaleY;
		this.font.getData().setScale(scaleX, scaleY);
		this.textX = x;
		layout.setText(font, text);
		this.textY = y + height/2 + layout.height/2;
	}
	
	

	@Override
	public boolean equals(Object o){
		if(this == null || o == null)
			return false;
		if(o instanceof SimpleButton){
			if(name.equals(((SimpleButton)o).getName()))
				return true;
		}
		return false;
	}

    public void setEnabled(boolean enabled){
    	this.enabled = enabled;
    }

    public boolean isEnabled(){
    	return enabled;
    }
    
    public boolean getIsPressed(){
    	return isPressed;
    }
    
    public ButtonNames getName(){
    	return name;
    }

    public void setDrawDisabled(boolean drawDisabled){
    	this.drawDisabled = drawDisabled;
    }
    
    public boolean getDrawDisabled(){
    	return drawDisabled;
    }

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
