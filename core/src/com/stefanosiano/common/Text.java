package com.stefanosiano.common;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.stefanosiano.wavyfish.tweenAccessors.FloatAccessor;
import com.stefanosiano.wavyfish.tweenAccessors.FloatValue;

public class Text {
	private BitmapFont font;
	private float scaleX, scaleY, x, y;
	private float originalX, originalY;
	private String text;
    private GlyphLayout layout;
    private int align;
    private boolean wrap;
    private float width;
	
	//blinkin tween stuff
	private FloatValue alpha;
	private TweenManager tweenManager;
	private boolean blinking;
	private Color c;
	
	/**
	 * creates the text object with scale parameters.
	 * Remember to call set(...) after this, to give the text the 
	 * position and to center it (in case). Use update(...) to
	 * change the text preserving all other parameters.
	 * 
	 * @param font
	 * @param scaleX
	 * @param scaleY
	 */
	public Text(BitmapFont font, float scaleX, float scaleY, boolean wrap){
		this.font = font;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.alpha = new FloatValue(0);
		this.blinking = false;
		this.layout = new GlyphLayout();
		this.align = Align.center;
		this.text = "";
		this.width = 0;
		this.wrap = wrap;
	}
	
	/**
	 * set the new text to be displayed. the scaleX and scaleY used are specified in the constructor
	 * 
	 * @param text
	 * @param x
	 * @param y
	 */
	public void set(String text, float x, float y){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.width = layout.width;
	}
	
	/**
	 * Set the new text to be displayed. the scaleX and scaleY used are specified in the constructor.
	 * The text will be rendered in the width with the align passed to this method
	 * 
	 * @param text
	 * @param x
	 * @param y
	 */
	public void set(String text, float x, float y, float width, int align){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = align;
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.width = width;
	}

	
	/**
	 * Set the new text to be displayed centered vertically. the scaleX and scaleY used are specified in the constructor.
	 * The text will be rendered in the width with the align passed to this method
	 * 
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 * @param x
	 * @param y
	 */
	public void setCenteredVertically(String text, float x, float y, float width, int align){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = align;
		this.x = x;
		this.y = y + layout.height/2;
		this.originalX = x;
		this.originalY = y;
		this.width = width;
	}

	
	/**
	 * set the new text to be displayed centered. the scaleX and scaleY used are specified in the constructor
	 * 
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 * @param x
	 * @param y
	 */
	public void setCentered(String text, float x, float y){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = x - layout.width/2;
		this.y = y + layout.height/2;
		this.originalX = x;
		this.originalY = y;
		this.width = layout.width;
	}
	
	/**
	 * set the new text to be displayed centered horizontally. the scaleX and scaleY used are specified in the constructor
	 * 
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 * @param x
	 * @param y
	 */
	public void setCenteredHorizzontally(String text, float x, float y){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = x - layout.width/2;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.width = layout.width;
	}
	
	/**
	 * set the text to the left of the given point. the scaleX and scaleY used are specified in the constructor
	 * 
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 * @param x
	 * @param y
	 */
	public void setToLeftOf(String text, float x, float y){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = x - layout.width;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.width = layout.width;
	}
	
	/**
	 * set the text to the left of the given point, centered vertically. the scaleX and scaleY used are specified in the constructor
	 * 
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 * @param x
	 * @param y
	 */
	public void setCenteredToLeftOf(String text, float x, float y){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = x - layout.width;
		this.y = y + layout.height/2;
		this.originalX = x;
		this.originalY = y;
		this.width = layout.width;
	}


    public void updateOnlyText(String text){
        this.text = text;
    }
	
	/**
	 * updates the text, preserving all the other parameters
	 * @param text
	 */
	public void updateText(String text){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.width = layout.width;
	}
	
	/**
	 * updates the text, preserving all the other parameters and re-centering it
	 * 
	 * @param text
	 */
	public void updateTextCentered(String text){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = originalX - layout.width/2;
		this.y = originalY + layout.height/2;
		this.width = layout.width;
	}
	
	/**
	 * updates the text, preserving all the other parameters and re-centering it horizontally
	 * 
	 * @param text
	 */
	public void updateTextCenteredHorizzontally(String text){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = originalX - layout.width/2;
		this.y = originalY;
		this.width = layout.width;
	}
	
	/**
	 * updates the text, preserving all the other parameters and resetting it to left of the point given before
	 * 
	 * @param text
	 */
	public void updateTextToLeftOf(String text){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = originalX - layout.width;
		this.y = originalY;
		this.width = layout.width;
	}
	
	/**
	 * updates the text, preserving all the other parameters and resetting it to left of the point given before and centered vertically
	 * 
	 * @param text
	 */
	public void updateTextCenteredToLeftOf(String text){
		this.text = text;
		this.font.getData().setScale(scaleX, scaleY);
		this.layout.setText(font, text);
		this.align = Align.center;
		this.x = originalX - layout.width;
		this.y = y + layout.height/2;
		this.width = layout.width;
	}
	
	/**
	 * draws the text with location and scale set through constructor 
	 * 
	 * @param batcher
	 */
	public void draw(SpriteBatch batcher, float delta){
		if(this.blinking){
            tweenManager.update(delta);
        	c = font.getColor();
        	font.setColor(c.r, c.g, c.b, alpha.getValue());
		}
		else{
			//the font is always the same for all the texts, so i have to change the alpha of the texts that are not changing
			c = font.getColor();
			font.setColor(c.r, c.g, c.b, 1);
		}
		//i need to scale the font in draw method because the font is always the same for all the texts, so i have to do it
		font.getData().setScale(scaleX, scaleY);
		font.draw(batcher, text, x, y, width, align, wrap);
		
		if(this.blinking){
        	font.setColor(c);
		}
	}
	
	public void setColor(float r, float g, float b, float a){
		font.setColor(r, g, b, a);
	}
	
	/**
	 * change the x values of the text by adding the specified offset
	 * 
	 * @param difference
	 */
	public void addX(float offset){
		this.x = x + offset;
		this.originalX = x;
	}
	
	/**
	 * change the y values of the text by adding the specified offset
	 * 
	 * @param difference
	 */
	public void addY(float offset){
		this.y = y + offset;
		this.originalY = y;
	}

	
	public void setScale(float scaleX, float scaleY){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.font.getData().setScale(scaleX, scaleY);
	}

	/**
	 * sets if the text should be wrapped (default is given in constructor)
	 */
	public void setWrap(boolean wrap){
		this.wrap = wrap;
	}


	/**
	 * Start the blink of the text.
	 * You can stop it with stopBlink()
	 *
	 * 
	 * @param  duration 	duration of the blinking (from opaque to transparent and vice versa)
	 */
	public void startBlink(float duration) {
		alpha.setValue(1);
        Tween.registerAccessor(FloatValue.class, new FloatAccessor());
        tweenManager = new TweenManager();

        Tween.to(alpha, -1, duration).target(0f)
        .ease(TweenEquations.easeNone).repeatYoyo(Tween.INFINITY, duration)
        .start(tweenManager);
        this.blinking = true;
    }

	/**
	 * Stop the blinking of the text.
	 */
	public void stopBlink() {
        this.blinking = false;
    }
	
	public float getWidth(){
		return layout.width;
	}
	
	public float getHeight(){
		return layout.height;
	}
}
