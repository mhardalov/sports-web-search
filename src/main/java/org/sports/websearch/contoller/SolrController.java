package org.sports.websearch.contoller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.sports.websearch.model.ArticlesResult;
import org.sports.websearch.model.CategoryQuery;
import org.sports.websearch.model.ScoreResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {
	
	final int rowsPerPage = 10;
	final String urlSolr = "http://localhost:8983/solr/nutch";

	private SolrServer getSolrServer() {
		HttpSolrServer server = new HttpSolrServer(urlSolr);
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
	public @ResponseBody ResponseEntity<ArticlesResult> search(
			@RequestParam(value = "query", required = true) String query,
			@RequestParam(value = "page", required = false, defaultValue="1") int page)
			throws UnsupportedEncodingException {

		SolrServer server = this.getSolrServer();
		SolrQuery solrQuery = new SolrQuery();
	
		String queryStr = URLDecoder.decode(query, "UTF-8");

		solrQuery.setQuery("text:\"" + queryStr + "\"");
		solrQuery.set("fl", "content,title,url,tstamp,score");
		
		//Avoiding paging with invalid values < 1
		solrQuery.set("start", Math.abs(Math.max(page, 1) - 1) * rowsPerPage);
		solrQuery.setSort("score", ORDER.desc);

		QueryResponse rsp = null;
		try {
			rsp = server.query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
			return new ResponseEntity<ArticlesResult>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ArticlesResult result = new ArticlesResult(rsp);
		return new ResponseEntity<ArticlesResult>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ScoreResult> category(
			@RequestBody CategoryQuery query) throws UnsupportedEncodingException {

		SolrServer server = this.getSolrServer();
		SolrQuery solrQuery = new SolrQuery();
		
		String queryStr = URLDecoder.decode(query.getContent(), "UTF-8");

		solrQuery.setQuery("\"" + queryStr + "\"");
		solrQuery.setParam("mtl", "true");
		solrQuery.setParam("mtl.fl", "content_idx");
		solrQuery.setParam("mlt.mindf", "1");
		solrQuery.setParam("mtl.qf", "contet_idx^100.00");
		solrQuery.set("fl", "content,title,url,score");

		QueryResponse rsp = null;
		try {
			rsp = server.query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
			return new ResponseEntity<ScoreResult>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ScoreResult result = new ScoreResult(rsp);
		return new ResponseEntity<ScoreResult>(result, HttpStatus.OK);
	}

}
