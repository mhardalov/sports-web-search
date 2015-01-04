package org.sports.websearch.model;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.solr.common.SolrDocument;
import org.sports.websearch.utils.CategoryCssHelper;

public class Article {

	private String url;

	private String title;

	private String content;

	private String category;

	private String publishDate;
	
	private String cssCategory;

	public Article(SolrDocument doc) {
		String url = doc.getFieldValue("url").toString();
		String title = doc.getFieldValue("title").toString();
		String content = doc.getFieldValue("content").toString();
		String category = doc.getFieldValue("category").toString();
		Date date = null;

		try {
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			df.setTimeZone(tz);
			String dateAsISO = doc.getFieldValue("tstamp").toString();
			date = df.parse(dateAsISO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setContent(content);
		this.setTitle(title);
		this.setUrl(url);
		this.setCategory(category);
		this.setPublishDate(date);
		this.setCssCategory(CategoryCssHelper.getCssClass(category));
	}

	public Article(String url, String title, String content, String category,
			Date publishDate) {
		this.setContent(content);
		this.setTitle(title);
		this.setUrl(url);
		this.setCategory(category);
		this.setPublishDate(publishDate);
		this.setCssCategory(CategoryCssHelper.getCssClass(category));
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

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {

		Format formatter = new SimpleDateFormat("dd.MM.YYYY HH:mm");
		this.publishDate = formatter.format(publishDate);
	}

	public String getCssCategory() {
		return this.cssCategory;
	}

	public void setCssCategory(String cssCategory) {
		this.cssCategory = cssCategory;
	}
}
