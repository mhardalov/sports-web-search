package org.sports.websearch.model;


public class Article {

	private String url;
	
	private String title;
	
	private String content;
	
	private String category;
	
	public Article() {
		this.setContent("");
		this.setTitle("");
		this.setUrl("");
		this.setCategory("");
	}
	
	public Article(String url, String title, String content, String category) {
		this.setContent(content);
		this.setTitle(title);
		this.setUrl(content);
		this.setCategory(category);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
