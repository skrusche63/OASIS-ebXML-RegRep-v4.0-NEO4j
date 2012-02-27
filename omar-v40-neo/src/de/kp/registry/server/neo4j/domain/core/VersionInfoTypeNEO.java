package de.kp.registry.server.neo4j.domain.core;

import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.oasis.ebxml.registry.bindings.rim.VersionInfoType;

import de.kp.registry.server.neo4j.domain.AbstractTypeNEO;

public class VersionInfoTypeNEO extends AbstractTypeNEO {

	public static Node toNode(EmbeddedGraphDatabase graphDB, Object binding) {
		
		VersionInfoType versionInfoType = (VersionInfoType)binding;
		
		// - USER VERSION NAME (0..1)
		String userVersionName = versionInfoType.getUserVersionName();
		
		// - VERSION NAME (0..1)
		String versionName = versionInfoType.getVersionName();
		
		// build versionInfoType node
		Node versionInfoTypeNode = graphDB.createNode();
		
		// add internal administration properties
		versionInfoTypeNode.setProperty(NEO4J_UID, getNID());		
		versionInfoTypeNode.setProperty(NEO4J_TYPE, getNType());

		// - USER VERSION NAME (0..1)
		if (userVersionName != null) versionInfoTypeNode.setProperty(OASIS_RIM_VERSION_USER_NAME, userVersionName);
		
		// - VERSION NAME (0..1)
		if (versionName != null) versionInfoTypeNode.setProperty(OASIS_RIM_VERSION_NAME, versionName);
		
		return versionInfoTypeNode;
		
	}

	public static String getNType() {
		return "VersionType";
	}
}
