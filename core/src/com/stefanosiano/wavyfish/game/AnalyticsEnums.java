package com.stefanosiano.wavyfish.game;

public interface AnalyticsEnums {
	
	public enum ShareType{
		link("Link"),
		score("Score");
		
		String type;
		ShareType(String type){this.type = type;}
		public String getTypeString(){return type;}
	}
	
	public enum Category{
		game("Game"),
		navigation("User Navigation"),
		share("Share"),
		engagement("User engagement"),
		general("General Event");

		String category;
		Category(String category){this.category = category;}
		public String getString(){return category;}
	}
}
