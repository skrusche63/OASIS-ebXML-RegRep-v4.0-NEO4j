package de.kp.registry.server.neo4j.database;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.oasis.ebxml.registry.bindings.query.QueryResponse;
import org.oasis.ebxml.registry.bindings.query.ResponseOptionType;
import org.oasis.ebxml.registry.bindings.rim.ObjectRefType;
import org.oasis.ebxml.registry.bindings.rim.QueryType;

import de.kp.registry.server.neo4j.domain.NEOBase;

public class ReadManager {

	private static ReadManager instance = new ReadManager();
	
	// reference to the Cipher execution engine
	ExecutionEngine engine;
	
	private ReadManager() {
		
		EmbeddedGraphDatabase graphDB = Database.getInstance().getGraphDB();
		engine = new ExecutionEngine(graphDB);
		
	}

	public static ReadManager getInstance() {
		if (instance == null) instance = new ReadManager();
		return instance;
	}

	public List<ObjectRefType> getObjectRefsByQuery(QueryType query) {
		return null;
	}
	
	// this is a common method to retrieve a certain node
	// from the index, identified by its 'id' property
	
	// note, that 'id' refers to the OASIS
 	public Node findNodeByID(String id) {
		
 		/*
 		StringBuffer sb = new StringBuffer(); 		
 		sb.append("START ro=node:node_auto_index(id='" + id + "' RETURN ro");
		
 		ExecutionResult result = engine.execute(sb.toString());
 		Iterator<Node> nodes = result.columnAs("ro");
 		
 		Node match = null;
 		
 		while (nodes.hasNext()) {
 			match = nodes.next();
 		}
 		
 		return match;

 		*/
 		
 		// the node index is built from the OASIS ebRIM 'id'
 		return Database.getInstance().getNodeIndex().get(NEOBase.OASIS_RIM_ID, id).getSingle();
 		
	}
 	
	/**
	 * Generic wrapper node -> rim binding
	 * 
	 * @param node
	 * @return
	 */

 	public Object toBinding(Node node) throws Exception {

		String rimClassName = (String) node.getProperty(NEOBase.NEO4J_TYPE);
		Class<?> clazz = NEOBase.getClassNEOByName(rimClassName);
		
		// call toBinding()
		Method method = clazz.getMethod("toBinding", Node.class);
		return method.invoke(null, node);

	}
 	
 	public QueryResponse executeQuery(ResponseOptionType responseOption, 
 			String comment,
 			QueryType query, 
 			BigInteger queryDepth,
 			String language,
 			Boolean matchOlderVersions,
 			BigInteger maxResults,
 			BigInteger startIndex,
 			QueryResponse queryResponse) {
 		
 		query.getQueryDefinition();
 		
 		// language :: not relevant for query, but relevant for node.toBinding() calls
 		// __DESIGN__ :: no default language is specified as part of specification InternationalStringType
 		
 		
 		
 		return queryResponse;
 		
 	}
}
