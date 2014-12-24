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
		this.resultCount = docs.getNumFound();
		for (SolrDocument doc : docs) {
			String url = doc.getFieldValue("url").toString();
			String title = doc.getFieldValue("title").toString();
			String content = this.getContent(doc.getFieldValue("content").toString(), maxLength);
			String category = "";
			
			Article entry = new Article(url, title, content, category);

			this.articles.add(entry);
		}
	}
}
