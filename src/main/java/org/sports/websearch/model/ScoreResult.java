package org.sports.websearch.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ScoreResult {
	private List<ArticleScore> articles = new ArrayList<ArticleScore>();

	private String category;

	private double maxScore;

	public String getCategory() {
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
