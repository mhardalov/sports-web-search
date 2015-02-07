package org.sports.websearch.contoller;

import java.net.URLDecoder;
import java.util.Date;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.sports.ontology.model.OntologyResult;
import org.sports.websearch.model.Article;
import org.sports.websearch.model.ArticlesResult;
import org.sports.websearch.model.CategoryQuery;
import org.sports.websearch.model.ScoreResult;
import org.sports.websearch.model.SearchQueryResult;
import org.sports.websearch.model.SpellSuggestion;
import org.sports.websearch.utils.OntologyConnection;
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
	final String urlSolr = "http://localhost:8983/solr/sports";

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

	private QueryResponse doQuery(SolrServer server, SolrQuery solrQuery)
			throws SolrServerException {
		QueryResponse rsp;
		rsp = server.query(solrQuery);

		return rsp;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<SearchQueryResult> search(
			@RequestParam(value = "query", required = true) String query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		try {
			SolrServer server = this.getSolrServer();
			SolrQuery solrQuery = new SolrQuery();

			String queryStr = URLDecoder.decode(query, "UTF-8");

			solrQuery.setQuery("text:\"" + queryStr + "\"");
			solrQuery.set("fl", "category,content,title,url,tstamp,score,key");

			// Avoiding paging with invalid values < 1
			solrQuery.set("start", Math.abs(Math.max(page, 1) - 1)
					* rowsPerPage);
			solrQuery.setSort("score", ORDER.desc);
			QueryResponse rsp = this.doQuery(server, solrQuery);

			ArticlesResult articles = new ArticlesResult(rsp);

			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("qt", "/spell");
			params.set("q", queryStr);
			params.set("spellcheck", "on");
			// params.set("spellcheck.build", "true");
			QueryResponse response = server.query(params);
			SpellSuggestion sc = new SpellSuggestion(response);

			SearchQueryResult result = new SearchQueryResult(sc, articles);
			return new ResponseEntity<SearchQueryResult>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<SearchQueryResult>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ScoreResult> category(
			@RequestBody CategoryQuery query) {
		try {
			SolrServer server = this.getSolrServer();
			SolrQuery solrQuery = new SolrQuery();

			String queryStr = URLDecoder.decode(query.getContent(), "UTF-8");

			// Escape : solr things it's field query
			queryStr = queryStr.replace(":", "\\:");
			solrQuery.setQuery(queryStr);
			solrQuery.setQueryType("mlt");
			solrQuery.setParam("mtl", "true");
			solrQuery.setParam("mtl.boost", "true");
			solrQuery.setParam("mlt.maxqt", "10");
			solrQuery.setParam("mlt.mindf", "0");
			solrQuery.setParam("mtl.fl", "content_idx");
			solrQuery.setParam("fl", "content,title,url,score,category,tstamp");
			solrQuery.setParam("df", "content_idx");

			QueryResponse rsp = this.doQuery(server, solrQuery);
			ScoreResult result = new ScoreResult(rsp);

			return new ResponseEntity<ScoreResult>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ScoreResult>((ScoreResult) null,
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Article> getArticle(
			@RequestParam(value = "url", required = true) String url) {
		try {
			SolrServer server = this.getSolrServer();
			SolrQuery solrQuery = new SolrQuery();

			String queryStr = URLDecoder.decode(url.toString(), "UTF-8");

			solrQuery.setQuery("url:\"" + queryStr + "\"");
			solrQuery.set("fl", "category,content,title,url,tstamp,score");

			QueryResponse rsp = this.doQuery(server, solrQuery);
			rsp.getResults();
			SolrDocumentList docs = rsp.getResults();

			Article result = null;
			if (docs != null && docs.getNumFound() > 0) {
				result = new Article(docs.get(0));
				OntologyResult ontoResult = OntologyConnection.connection.query(result.getUrl());
				result.setOntoResult(ontoResult);
			} else {
				return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Article>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Article>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	

	@RequestMapping(value = "/entities", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<OntologyResult> getEntities(
			@RequestParam(value = "entity", required = false) String entity,
			@RequestParam(value = "afterDate", required = false) Date afterDate,
			@RequestParam(value = "beforeDate", required = false) Date beforeDate) {
		try {
			OntologyResult result = new OntologyResult();
			result.setQuotes(OntologyConnection.connection.queryQuotes(entity, afterDate, beforeDate));
			result.setResults(OntologyConnection.connection.queryResults(entity, afterDate, beforeDate));					
			
			return new ResponseEntity<OntologyResult>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<OntologyResult>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
