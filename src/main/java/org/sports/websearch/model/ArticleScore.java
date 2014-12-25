package org.sports.websearch.model;

public class ArticleScore extends Article {
	private double score;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public ArticleScore(String url, String title, String content,
			String category, double score) {
		super(url, title, content, category);
		
		this.setScore(score);
	}
}
