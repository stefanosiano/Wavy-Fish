package com.stefanosiano.common.inputs;

import com.badlogic.gdx.input.GestureDetector;

public class SimpleDirectionGestureDetector extends GestureDetector {

	/**
	 * The InputMultiplexer will hand any new events to the first InputProcessor that was added to it.
	 * If that processor returns false from the method invoked to handle the event, this indicates 
	 * the event was not handled and the multiplexer will hand the event to the next processor in the chain
	 * 
	 * @param directionListener
	 */
	public SimpleDirectionGestureDetector(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}
	
	private static class DirectionGestureListener extends GestureAdapter{
		DirectionListener directionListener;
		
		public DirectionGestureListener(DirectionListener directionListener){
			this.directionListener = directionListener;
		}
		
		@Override
        public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
				if(velocityX>0){
						directionListener.onSwipeRight();
				}else{
						directionListener.onSwipeLeft();
				}
			}else{
				if(velocityY>0){
						directionListener.onSwipeDown();
				}else{                                  
						directionListener.onSwipeUp();
				}
			}
			return super.fling(velocityX, velocityY, button);
        }
	}
	
	public interface DirectionListener {
		void onSwipeLeft();
		void onSwipeRight();
		void onSwipeUp();
		void onSwipeDown();
	}
}