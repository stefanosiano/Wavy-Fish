package com.stefanosiano.common.utils;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stefanosiano.wavyfish.utilities.Settings;

public class ScreenshotFactory {
    public static String saveScreenshot(Pixmap pixmap, boolean yDown){
        boolean folderExist;
        FileHandle fileHandle;
        int counter = Settings.screenshotCounter;

        DecimalFormat df = new DecimalFormat("0000");
        String folderPath = "wavyFish/";
        String filePath = "score_" +  df.format(counter) + ".png";
        
        if(Gdx.files.isExternalStorageAvailable()){
    		fileHandle = Gdx.files.external(folderPath);
        }
        else{
            if(Gdx.files.isLocalStorageAvailable()){
            	fileHandle = Gdx.files.local(folderPath);
            }
        	else{
        		System.out.println("ERRORE!!! NON C'E' MEMORIA INTERNA NE ESTERNA!");
        		return "";
        	}
        }
    	folderExist = fileHandle.exists();
		if(!folderExist)
			fileHandle.mkdirs();
		
		fileHandle = fileHandle.child(filePath);
        
        try{
            filePath = "score.png";
            if(Gdx.files.isExternalStorageAvailable()){
        		fileHandle = Gdx.files.external(folderPath).child(filePath);
            }
            else{
            	fileHandle = Gdx.files.local(folderPath).child(filePath);
            }
            
            counter++;
            Settings.setScreenshotCounter(counter);
            pixmap = flipScreenshot(pixmap, yDown);
            PixmapIO.writePNG(fileHandle, pixmap);
            pixmap.dispose();
        }catch (Exception e){
        	e.printStackTrace();
        	return "";
        }
        
        String returnPath;
        if(Gdx.files.isExternalStorageAvailable()){
    		returnPath = Gdx.files.getExternalStoragePath();
        }
        else{
        	returnPath = Gdx.files.getLocalStoragePath();
        }
        
        return returnPath + fileHandle.path();
    }

    public static Pixmap getScreenshot(){
        return ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private static Pixmap flipScreenshot(Pixmap pixmap, boolean yDown){
    	int w = Gdx.graphics.getWidth();
    	int h = Gdx.graphics.getHeight();

        if (yDown) {
            // Flip the pixmap upside down
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
            pixels.clear();
        }

        return pixmap;
    }
}