package org.sports.websearch.utils;

import org.sports.ontology.OntologyHandler;

public class OntologyConnection {
	private static String ontologyFile = "/home/momchil/Projects/spring-demo/gate-sports-processor/src/main/resources/gate/sports_terms/ontology/sports.owl";
	public static OntologyHandler connection = new OntologyHandler();
	
	static {
		connection.open(ontologyFile);
	}

}
