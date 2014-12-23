package org.sports.websearch.contoller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.sports.websearch.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {

	private SolrServer getSolrServer() {
		String url = "http://localhost:8983/solr/nutch";
		HttpSolrServer server = new HttpSolrServer(url);
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
		server.setConnectionTimeout(5000); // 5 seconds to establish TCP
		// Setting the XML response parser is only required for cross
		// version compatibility and only when one side is 1.4.1 or
		// earlier and the other side is 3.1 or later.
		server.setParser(new XMLResponseParser()); // binary parser is used by
													// default
		// The following settings are provided here for completeness.
		// They will not normally be required, and should only be used
		// after consulting javadocs to know whether they are truly required.
		server.setSoTimeout(1000); // socket read timeout
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		// allowCompression defaults to false.
		// Server side must support gzip or deflate for this to have any effect.
		server.setAllowCompression(true);

		return server;
	}

	@RequestMapping(value = "/{query}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Article>> search(
			@RequestParam(value = "query", required = true) String query) {

		List<Article> result = new ArrayList<Article>();
		SolrServer server = this.getSolrServer();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query);
		// solrQuery.addSort(new SortClause("tstamp", ORDER.desc));

		QueryResponse rsp = null;
		try {
			rsp = server.query(solrQuery);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SolrDocumentList docs = rsp.getResults();
		for (SolrDocument doc : docs) {
			String url = doc.getFieldValue("url").toString();
			String title = doc.getFieldValue("title").toString();
			String content = doc.getFieldValue("content").toString();
			Article entry = new Article(url, title, content, "");
			result.add(entry);
		}

		System.out.println(query);
		return new ResponseEntity<List<Article>>(result, HttpStatus.OK);
	}

}
