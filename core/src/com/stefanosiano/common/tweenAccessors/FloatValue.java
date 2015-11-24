package com.stefanosiano.common.tweenAccessors;

public class FloatValue {
    private float val;

    public FloatValue(float value){
    	this.val = value;
    }
    
    public float getValue() {
        return val;
    }

    public void setValue(float newVal) {
        val = newVal;
    }

}
