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
				String url = doc.getFieldValue("url").toString();
				String title = doc.getFieldValue("title").toString();
				String content = doc.getFieldValue("content").toString();
				String category = "";
				double score = Double.parseDouble(doc.getFieldValue("score").toString());
				
				ArticleScore entry = new ArticleScore(url, title, content, category, score);
	
				this.articles.add(entry);
			}
		}
}
