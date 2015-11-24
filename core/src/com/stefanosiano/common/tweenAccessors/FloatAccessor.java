package com.stefanosiano.common.tweenAccessors;

import aurelienribon.tweenengine.TweenAccessor;

public class FloatAccessor  implements TweenAccessor<FloatValue> {
    @Override
    public int getValues(FloatValue target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getValue();
		return 1;
    }

    @Override
    public void setValues(FloatValue target, int tweenType, float[] newValues) {
        target.setValue(newValues[0]);
    }

}