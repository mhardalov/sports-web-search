package org.sports.websearch.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;

public class SpellSuggestion {
	private List<String> suggestions;

	public SpellSuggestion() {
		this.setSuggestions(new ArrayList<String>());
	}

	public SpellSuggestion(QueryResponse response) {
		this.setSuggestions(new ArrayList<String>());

		SpellCheckResponse spellCheckResponse = response
				.getSpellCheckResponse();

		if (!spellCheckResponse.isCorrectlySpelled() && response.getSpellCheckResponse()
				.getCollatedResults() != null) {

			for (Collation collation : response.getSpellCheckResponse()
					.getCollatedResults()) {
				this.suggestions.add(collation.getCollationQueryString());
			}
		}
	}

	public String getSuggestionQuery() {
		return this.suggestions.size() > 0 ? this.suggestions.get(0) : "";
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
}
