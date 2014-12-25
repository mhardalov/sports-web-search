package org.sports.websearch.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ArticlesResult {
	final int maxLength = 140;
	
	private List<Article> articles = new ArrayList<Article>();

	private long resultCount;

	private double elapsedTime;
	
	/**
	 * Get content truncated to certain length proper for visualization 
	 * @param content - the original content of the result
	 * @return - substring of the content truncated to certain length
	 */
	private String getContent(String content, int length) {
		String newContent = "";
		newContent = content.substring(0, Math.min(content.length(), length));
		if (newContent.length() < content.length()) {
			newContent += " ...";
		}
		
		return newContent;
	}
	
	public List<Article> getArticles() {
		return articles;
	}

	public long getResultCount() {
		return resultCount;
	}

	public ArticlesResult(QueryResponse rsp) {
		SolrDocumentList docs = rsp.getResults();
		this.elapsedTime = rsp.getElapsedTime() * 0.001;
		
		this.resultCount = docs.getNumFound();
		for (SolrDocument doc : docs) {
			Article entry = new Article(doc);
			entry.setContent(this.getContent(entry.getContent(), maxLength));

			this.articles.add(entry);
		}
	}

	public double getElapsedTime() {
		return elapsedTime;
	}
}
