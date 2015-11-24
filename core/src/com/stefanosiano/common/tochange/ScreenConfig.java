package com.stefanosiano.common.tochange;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.stefanosiano.wavyfish.utilities.Settings;

public class ScreenConfig {

    public static float HWWIDTH;
    public static float HWHEIGHT;
    public static float ASPECTRATIO;
    public static float HWASPECTRATIO;
    public static float SCALEX;
    public static float SCALEY;
    public static int VIRTUALWIDTH;
    public static int VIRTUALHEIGHT;
    public static float RENDERINGWIDTH;
    public static float RENDERINGHEIGHT;
    public static int MIDPOINTY;
    public static Rectangle VIEWPORT;
    
    public static void initialize(){
        ASPECTRATIO = (float)9 / 16; //ratio i want
        VIRTUALWIDTH = 1600; //width i want
        VIRTUALHEIGHT = 900; //height i want
        MIDPOINTY = 450;
        
        HWWIDTH = Gdx.graphics.getWidth(); //width and height of real screen
        HWHEIGHT = Gdx.graphics.getHeight();
        SCALEX = 1; //scale factor from hwwidth and virtual width and height (they are same if it's not stretched)
        SCALEY = 1;
        HWASPECTRATIO = (float)HWHEIGHT/HWWIDTH;
        VIEWPORT = new Rectangle(0, 0, VIRTUALWIDTH, VIRTUALHEIGHT);
    }
    
    public static void updateDimensions(){
        Vector2 newVirtualRes;
        Vector2 crop;

        HWWIDTH = Gdx.graphics.getWidth(); //width and height of real screen
        HWHEIGHT = Gdx.graphics.getHeight();

        if(!Settings.STRETCH){
	        newVirtualRes= new Vector2(0f, 0f);
	        crop = new Vector2(HWWIDTH, HWHEIGHT);
	        // get new screen size conserving the aspect ratio
	        newVirtualRes.set(Scaling.fit.apply(VIRTUALWIDTH, VIRTUALHEIGHT, HWWIDTH, HWHEIGHT));
	        // ensure our scene is centered in screen
	        crop.sub(newVirtualRes);
	        crop.scl(.5f);
	        // build the viewport for further application
	        RENDERINGWIDTH = newVirtualRes.x;
	        RENDERINGHEIGHT = newVirtualRes.y;
	        VIEWPORT = new Rectangle(crop.x, crop.y, RENDERINGWIDTH, RENDERINGHEIGHT);
	        SCALEX = HWWIDTH / VIRTUALWIDTH; //scale factor from hwwidth and virtual width and height (they are same if it's not stretched)
	        SCALEY = HWHEIGHT / VIRTUALHEIGHT;
	        SCALEX = Math.min(SCALEX, SCALEY);
	        SCALEY = SCALEX;
	        
        }
        else{
	        RENDERINGWIDTH = HWWIDTH;
	        RENDERINGHEIGHT = HWHEIGHT;
	        VIEWPORT = new Rectangle(0, 0, RENDERINGWIDTH, RENDERINGHEIGHT);
	        SCALEX = HWWIDTH / VIRTUALWIDTH; //scale factor from hwwidth and virtual width and height
	        SCALEY = HWHEIGHT / VIRTUALHEIGHT;
        }
    }
    
}
