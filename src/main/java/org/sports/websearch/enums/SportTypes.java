package org.sports.websearch.enums;

public enum SportTypes {
	FOOTBALL("футбол", "article-football"),
	BGFOOTBALL("бг футбол", "article-football"),
	BASKETBALL("баскетбол", "article-basketball"),
	BASEBALL("бейзбол", "article-baseball"),
	FORMULA("формула 1", "article-cars"),
	MOTOR("моторни спортове", "article-cars"),
	AUTOMANIA("авто мания", "article-cars"),
	GOLF("голф", "article-golf"),
	VOLLEYBALL("волейбол", "article-volleyball"),
	AMERICANFOOTBALL("американски футбол", "article-americalfootball"),
	FROMTHESHEL("от скрина", "article-fromtheshelf"),
	SNOOKER("снукър", "article-snooker");
	
	
	private final String text;
	private final String css;

	private SportTypes(final String text, final String css) {
		this.text = text;
		this.css = css;
	}
	
	public static SportTypes findByText(String text){
	    for(SportTypes v : values()){
	        if( v.getText().equals(text)){
	            return v;
	        }
	    }
	    return null;
	}

	@Override
	public String toString() {
		return css;
	}

	public String getText() {
		return text;
	}
}