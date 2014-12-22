package org.sports.websearch.contoller;

import org.sports.websearch.model.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {

	@RequestMapping(value = "/{query}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Article> search(
			@RequestParam(value = "query", required = true) String query) {

		System.out.println(query);
		return null;
	}

}
