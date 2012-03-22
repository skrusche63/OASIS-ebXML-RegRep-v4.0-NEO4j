package de.kp.registry.client.service.impl;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.oasis.ebxml.registry.bindings.spi.CatalogObjectsRequest;
import org.oasis.ebxml.registry.bindings.spi.CatalogObjectsResponse;

import de.kp.registry.client.service.CatalogerSOAPService;
import de.kp.registry.common.ConnectionImpl;
import de.kp.registry.server.neo4j.service.Cataloger;
import de.kp.registry.server.neo4j.service.MsgRegistryException;

public class CatalogerImpl {

	private static String SAML_USER_ASSERTION = "urn:oasis:names:tc:ebxml-regrep:saml:user:assertion";
	private static QName QNAME = new QName("urn:oasis:names:tc:ebxml-regrep:wsdl:registry:services:4.0", "NotificationListener");
	
	private CatalogerSOAPService service;
	private Cataloger port;
	
	private ConnectionImpl connection;
	
	public CatalogerImpl(ConnectionImpl connection) {

		// register connection object
		this.connection = connection;
		URL wsdlLocation = this.connection.getLifecyleManagerURL();
		
		service = new CatalogerSOAPService(wsdlLocation, QNAME);
		port = service.getCatalogerPort();
		
	}

    public CatalogObjectsResponse catalogObjects(CatalogObjectsRequest request) throws MsgRegistryException {

		// TODO:
		
		// assign SAML credentials to request context; this is a mechanism
		// to share the respective assertion with the SOAP message handler
		
		Map<String, Object> context = ((BindingProvider) port).getRequestContext();
		context.put(SAML_USER_ASSERTION, this.connection.getAssertion());

		return port.catalogObjects(request);

    }

}
