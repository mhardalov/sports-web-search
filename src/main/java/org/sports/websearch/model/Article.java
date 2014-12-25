package org.sports.websearch.model;

import java.text.ParseException;
import java.util.Date;

import org.apache.solr.common.SolrDocument;
import org.sports.websearch.utils.ISO8601DateParser;

public class Article {

	private String url;

	private String title;

	private String content;

	private String category;

	private Date publishDate;

	public Article(SolrDocument doc) {
		String url = doc.getFieldValue("url").toString();
		String title = doc.getFieldValue("title").toString();
		String content = doc.getFieldValue("content").toString();
		String category = "";
		Date date = null;
		
		try {			
			date = ISO8601DateParser.parse(doc.getFieldValue("tstamp").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.setContent(content);
		this.setTitle(title);
		this.setUrl(url);
		this.setCategory(category);
		this.setPublishDate(date);
	}

	public Article(String url, String title, String content, String category,
			Date publishDate) {
		this.setContent(content);
		this.setTitle(title);
		this.setUrl(url);
		this.setCategory(category);
		this.setPublishDate(publishDate);
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

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
}
