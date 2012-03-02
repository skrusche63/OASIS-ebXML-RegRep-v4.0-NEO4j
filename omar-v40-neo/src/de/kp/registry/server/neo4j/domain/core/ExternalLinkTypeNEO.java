package de.kp.registry.server.neo4j.domain.core;

import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.oasis.ebxml.registry.bindings.rim.ExternalLinkType;
import org.oasis.ebxml.registry.bindings.rim.SimpleLinkType;


public class ExternalLinkTypeNEO extends RegistryObjectTypeNEO {

	public static Node toNode(EmbeddedGraphDatabase graphDB, Object binding, boolean checkReference) throws Exception {
		
		ExternalLinkType externalLinkType = (ExternalLinkType)binding;
		
		// - EXTERNAL-REF (1..1)
		SimpleLinkType externalRef = externalLinkType.getExternalRef();

		// the externalRef parameter is actually restricted to a URI
		String externalURI = externalRef.getHref();
		
		// - REGISTRY-OBJECT (0..1)
		String parent = externalLinkType.getRegistryObject();
		
		// TODO: here we have to determine whether we retrieve the respective node
		// from the database (if it exists) or through creation

		// create node from underlying RegistryObjectType
		Node externalLinkTypeNode = RegistryObjectTypeNEO.toNode(graphDB, binding, checkReference);
		
		// update the internal type to describe a ExternalLinkType
		externalLinkTypeNode.setProperty(NEO4J_TYPE, getNType());

		// - EXTERNAL-REF (1..1)
		externalLinkTypeNode.setProperty(OASIS_RIM_URI, externalURI);
		
		// - REGISTRY-OBJECT (0..1)
		if (parent != null) externalLinkTypeNode.setProperty(OASIS_RIM_PARENT, parent);

		return externalLinkTypeNode;
		
	}

	public static Object toBinding(Node node) {

		ExternalLinkType binding = factory.createExternalLinkType();
		binding = (ExternalLinkType)RegistryObjectTypeNEO.fillBinding(node, binding);

		// - EXTERNAL-REF (1..1)
		if (node.hasProperty(OASIS_RIM_URI)) {
		
			SimpleLinkType simpleLinkType = factory.createSimpleLinkType();
			simpleLinkType.setHref((String)node.getProperty(OASIS_RIM_URI));
			
			binding.setExternalRef(simpleLinkType);
		
		}

		// - REGISTRY-OBJECT (0..1)
		if (node.hasProperty(OASIS_RIM_PARENT)) binding.setRegistryObject((String)node.getProperty(OASIS_RIM_PARENT));
		
		return binding;
		
	}
	
	public static String getNType() {
		return "ExternalLinkType";
	}
	
}
