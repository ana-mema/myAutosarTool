package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlDocumentBuilder {

	// Method to create a new XML Document
	public Document createDocument() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.newDocument();
	}

	// Method to create the root element for the XML document
	public Element createRootElement(Document doc) {
		Element root = doc.createElement("AUTOSAR");
		root.setAttribute("xmlns", "http://autosar.org/schema/r4.0");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "http://autosar.org/schema/r4.0 AUTOSAR_4-0-3.xsd");
		return root;
	}

	// Method to create OS configuration
	public Element createOsConfig(Document doc, Element arPackage) {
		Element osConfig = doc.createElement("ECUC-MODULE-CONFIGURATION-VALUES");
		osConfig.appendChild(createTextElement(doc, "SHORT-NAME", "OS_CFG"));
		osConfig.appendChild(createTextElement(doc, "DEFINITION-REF", "/AUTOSAR_Os/EcucModuleDefs/Os"));
		arPackage.appendChild(osConfig);
		return osConfig;
	}

	// Helper method to create a text element
	private Element createTextElement(Document doc, String tagName, String value) {
		Element element = doc.createElement(tagName);
		element.appendChild(doc.createTextNode(value));
		return element;
	}
}
