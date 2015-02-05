package org.sports.websearch.model;

import java.util.Date;

import org.apache.solr.common.SolrDocument;

public class ArticleScore extends Article {
	private double score;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public ArticleScore(SolrDocument doc) {
		super(doc);

		double score = Double.parseDouble(doc.getFieldValue("score").toString());
		this.setScore(score);
	}

	public ArticleScore(String url, String title, String content,
			String category, Date publishDate, double score, String key) {
		super(url, title, content, category, publishDate, key);
		
		this.setScore(score);
	}
}
