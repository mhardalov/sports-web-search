package org.sports.websearch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ScoreResult {
	private List<ArticleScore> articles = new ArrayList<ArticleScore>();

	private double maxScore;

	public String getCategory() {
		if (articles.size() == 0)
			return "";

		Map<String, Integer> categorizer = new HashMap<String, Integer>();
		for (ArticleScore article : this.articles) {
			String category = article.getCategory();
			int count = 0;
			if (categorizer.containsKey(category)) {
				count = categorizer.get(category);
			}
			categorizer.put(category, count + 1);
		}

		int maxCount = 0;
		String category = "";

		for (Entry<String, Integer> entry : categorizer.entrySet()) {
			int itemCount = entry.getValue().intValue();
			if (itemCount > maxCount) {
				maxCount = itemCount;
				category = entry.getKey().toString();
			}
		}

		return category;
	}

	public List<ArticleScore> getArticles() {
		return articles;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public ScoreResult(QueryResponse rsp) {
		SolrDocumentList docs = rsp.getResults();
		this.maxScore = docs.getMaxScore();
		for (SolrDocument doc : docs) {
			ArticleScore entry = new ArticleScore(doc);

			this.articles.add(entry);
		}
	}
}
