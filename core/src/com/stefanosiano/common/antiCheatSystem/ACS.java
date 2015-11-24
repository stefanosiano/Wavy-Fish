package com.stefanosiano.common.antiCheatSystem;

import java.util.ArrayList;

/**
 * this is the Anti Cheat System! will be used to prevent cheats (unless the apk is changed)
 * @author Stefano
 *
 */
public class ACS{
	private ArrayList<SecureValue> values;
	private String check;
	private Runnable onCheat;
	
	public ACS(Runnable r){
		this.onCheat = r;
		this.values = new ArrayList<SecureValue>();
	}
	
	public String buildCheckString(){
		String string = "";
		for(SecureValue sv : values){
			string = string + sv.getValue();
		}
		return string;
	}
	
	
	/**
	 * This MUST be called BEFORE changing the values
	 * 
	 * @return
	 */
	public void checkValues(){
		String newCheck = buildCheckString();
		if(!check.equals(newCheck)){
			onCheat.run();
			return;
		}
		
		return;
	}
	
	public void onCheat(){
		this.onCheat.run();
	}
	
	public void addValue(SecureValue sv){
		if(this.values.contains(sv))
			this.values.remove(sv);
		this.values.add(sv);
	}

	/**
	 * This MUST be called AFTER changing ALL the values
	 * 
	 * @return
	 */
	public void updateCheck(){
		check = buildCheckString();
	}
	
	public void clear(){
		values.clear();
	}
}
