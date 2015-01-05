package org.sports.websearch.model;

public class SearchQueryResult {
	private SpellSuggestion spellSuggestion;
	private ArticlesResult queryData;

	public SearchQueryResult(SpellSuggestion spellSuggestion, ArticlesResult articles) {
		this.setQueryData(articles);
		this.setSpellSuggestion(spellSuggestion);
	}
	
	public SpellSuggestion getSpellSuggestion() {
		return spellSuggestion;
	}

	public void setSpellSuggestion(SpellSuggestion spellSuggestion) {
		this.spellSuggestion = spellSuggestion;
	}

	public ArticlesResult getQueryData() {
		return queryData;
	}

	public void setQueryData(ArticlesResult queryData) {
		this.queryData = queryData;
	}
}
