package com.stefanosiano.wavyfish.android;

import java.util.HashMap;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;
import com.stefanosiano.wavyfish.android.R;

public class AnalyticsHelper {
    
   	/**
   	 * Enum used to identify the tracker that needs to be used for tracking.
   	 * 
   	 * A single tracker is usually enough for most purposes. In case you do need
   	 * multiple trackers, storing them all in Application object helps ensure
   	 * that they are created only once per application instance.
   	 */
   	public enum TrackerName {
   		APP_TRACKER, // Tracker used only in this app.
   		/*GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
   		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
   		*/
   	}
    
   	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    
   	synchronized Tracker getTracker(TrackerName trackerId, Context context) {
   		if (!mTrackers.containsKey(trackerId)) {
    
   			GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
   			analytics.setLocalDispatchPeriod(600);
   			analytics.setDryRun(false);
   			analytics.getLogger().setLogLevel(LogLevel.VERBOSE);

   			Tracker t;
   			
   			switch(trackerId){
   				case APP_TRACKER:
   					t = analytics.newTracker(R.xml.app_tracker);
   					break;
   					/* i don't need/have these trackers for now, so i don't want to use them
   				case GLOBAL_TRACKER:
   					t = analytics.newTracker(R.xml.global_tracker);
   					break;
   				case ECOMMERCE_TRACKER:
   					t = analytics.newTracker(R.xml.ecommerce_tracker);
   					break;*/
				default:
					return null;
   			}
   			t.setAppName("Wavy Fish - Android");
   			mTrackers.put(trackerId, t);
   		}
   		return mTrackers.get(trackerId);
   	}
   	
   	public void sendEvent(TrackerName trackerId, Context context, String screenName, String category, String action, String label, long value){
   		Tracker tracker = getTracker(trackerId, context);
   		if(tracker == null){
   			System.out.println("tracker is null!");
   			return;
   		}

   		tracker.setScreenName(screenName);
		tracker.send(new HitBuilders.EventBuilder()
		    .setCategory(category)
		    .setAction(action)
		    .setLabel(label)
		    .setValue(value)
		    .build());
   	}
   	
   	public void sendScreen(TrackerName trackerId, Context context, String screenName){
   		Tracker tracker = getTracker(trackerId, context);
   		if(tracker == null){
   			System.out.println("tracker is null!");
   			return;
   		}

   		tracker.setScreenName(screenName);
		tracker.send(new HitBuilders.AppViewBuilder().build());
   	}
   	
   	public void sendTiming(TrackerName trackerId, Context context, String screenName, String category, String variableName, String label, long time){
   		Tracker tracker = getTracker(trackerId, context);
   		if(tracker == null){
   			System.out.println("tracker is null!");
   			return;
   		}

   		tracker.setScreenName(screenName);
   		tracker.send(new HitBuilders.TimingBuilder()
		    .setCategory(category)
	   	    .setValue(time)
	   	    .setVariable(variableName)
		    .setLabel(label)
	   	    .build());
   	}
}
