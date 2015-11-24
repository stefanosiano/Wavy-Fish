package com.stefanosiano.common.antiCheatSystem;

import com.stefanosiano.wavyfish.utilities.Enums.SecureValueName;

public class SecureValue{
	private String value;
	private ACS acs;
	private SecureValueName name;
	
	public SecureValue(int value, ACS acs, SecureValueName name) {
		this.value = value + "";
		this.acs = acs;
		this.name = name;
		acs.addValue(this);
	}
	
	public SecureValue(float value, ACS acs, SecureValueName name) {
		this.value = value + "";
		this.acs = acs;
		this.name = name;
		acs.addValue(this);
	}
	
	public void set(int oldValue, int newValue){
		if(!(oldValue + "").equals(value)){
			acs.onCheat();
			return;
		}
		value = newValue + "";
		return;
	}
	
	public void set(float oldValue, float newValue){
		if(!(oldValue + "").equals(value)){
			acs.onCheat();
			return;
		}
		value = newValue + "";
		return;
	}
	
	public String getValue(){
		return value;
	}
	
	public boolean equals(Object ob){
		if(!(ob instanceof SecureValue))
			return false;
		return ((SecureValue)ob).name.equals(name);
	}
}