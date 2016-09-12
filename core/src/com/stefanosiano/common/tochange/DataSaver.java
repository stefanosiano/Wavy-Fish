package com.stefanosiano.common.tochange;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.stefanosiano.wavyfish.Keys;
import com.stefanosiano.wavyfish.utilities.Enums.SavedItems;

public class DataSaver {
	private static String version;
	private static Preferences prefs;
	private static final byte[] key = Keys.DATA_KEY;
	
	public static void load(){
		prefs = Gdx.app.getPreferences("WavyFish");
		version = prefs.getString(SavedItems.saveVersion + "", "0");
		try{
			Integer.parseInt(version);
		}catch (Exception e){
			version = "0";
		}
		switch(Integer.parseInt(version)){
			case 1:
				break;
			case 0:
			default:
				ArrayList<String> temps = new ArrayList<String>();
				ArrayList<SavedItems> saveds = new ArrayList<SavedItems>();
				for(SavedItems si : SavedItems.values()){
					if(si != SavedItems.saveVersion){
						temps.add(load(si + ""));
						saveds.add(si);
					}
				}
				
				version = "1";
				prefs.putString(SavedItems.saveVersion + "", version);
				prefs.flush();

				for(int i = 0; i < saveds.size(); i++){
						save(saveds.get(i) + "", temps.get(i));
				}
				//save(SavedItems.experience + "", temp);
				break;
		}
	}

	/**
	 * This method encrypt the values and saves them in the preferences
	 * 
	 * @param name
	 * @param value
	 */
	public static void save(String name, String value){
		String temp, finalValue;
		switch(Integer.parseInt(version)){
			case 1:
				temp = encrypt(value + name);
				if(temp == null)
					finalValue = value + name;
				else
					finalValue = temp;
				break;
			case 0:
			default:
				finalValue = value;
				break;
		}
		prefs.putString(name, finalValue);
		prefs.flush();
	}

	/**
	 * This method decrypts the values saved in the preferences
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String load(String name){
		String temp, finalValue;
		String defaultValue = SavedItems.valueOf(name).getDefault();
		
		try{
			Integer.parseInt(version);
		}
		catch(Exception e){
			e.printStackTrace();
			version = "0";
		}
		
		switch(Integer.parseInt(version)){
			case 1:
				temp = prefs.getString(name, defaultValue);
				temp = decrypt(temp);
				if(temp == null || temp.lastIndexOf(name) < 0){
					finalValue = defaultValue;
				}
				else{
					finalValue = temp.substring(0, temp.lastIndexOf(name));
				}
				break;
			case 0:
			default:
				try{
					finalValue = prefs.getString(name, defaultValue);
				}catch (ClassCastException e) {
					e.printStackTrace();
					try{
						finalValue = prefs.getInteger(name, Integer.parseInt(defaultValue)) + "";
					} catch (Exception e2){
						e2.printStackTrace();
						try{
							finalValue = prefs.getBoolean(name, Boolean.parseBoolean(defaultValue)) + "";
						}catch(Exception e3){
							e3.printStackTrace();
							try{
								finalValue = prefs.getFloat(name, Float.parseFloat(defaultValue)) + "";
							}catch(Exception e4){
								e4.printStackTrace();
								try{
									finalValue = prefs.getLong(name, Long.parseLong(defaultValue)) + "";
								}catch(Exception e5){
									e5.printStackTrace();
									finalValue = defaultValue;
								}
							}
						}
					}
				}
				break;
		}
		System.out.println(name + " = " + finalValue);
		return finalValue;
	}
	
	
    private static String encrypt(String text) 
    {
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes(Charset.forName("UTF-16")));
            char[] encryptedValue = Base64Coder.encode(encrypted);
            return new String(encryptedValue);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    
    private static String decrypt(String encrypted) 
    {
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = Base64Coder.decode(encrypted);
            byte[] decValue = cipher.doFinal(decryptedBytes);
            String decrypted = new String(decValue, Charset.forName("UTF-16"));
            return decrypted;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
