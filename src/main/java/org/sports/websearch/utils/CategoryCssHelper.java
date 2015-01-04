package org.sports.websearch.utils;

import org.sports.websearch.enums.SportTypes;

public final class CategoryCssHelper {
	public final static String getCssClass(String category) {
		SportTypes se = SportTypes.findByText(category.toLowerCase());
		
		return (se != null) ? se.toString() : "article-default" ;
	}
}
