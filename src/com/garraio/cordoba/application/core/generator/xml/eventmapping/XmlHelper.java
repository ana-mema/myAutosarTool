package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlHelper {
	public static Element createTextElement(Document doc, String tagName, String value) {
		Element element = doc.createElement(tagName);
		element.appendChild(doc.createTextNode(value));
		return element;
	}

	public static Element createTextElementWithDest(Document doc, String tagName, String destValue,
			String textContent) {
		Element element = doc.createElement(tagName);
		element.setAttribute("DEST", destValue);
		element.setTextContent(textContent);
		return element;
	}

	public static Element createNumericElement(Document doc, String dest, String ref, String value) {
		Element element = doc.createElement("ECUC-NUMERICAL-PARAM-VALUE");
		element.appendChild(createDefinitionRefElement(doc, dest, ref));
		element.appendChild(createTextElement(doc, "VALUE", value));
		return element;
	}

	public static Element createBooleanElement(Document doc, String ref, String value) {
		Element element = doc.createElement("ECUC-BOOLEAN-PARAM-VALUE");
		element.appendChild(createTextElement(doc, "DEFINITION-REF", ref));
		element.appendChild(createTextElement(doc, "VALUE", value));
		return element;
	}

	public static Element createDefinitionRefElement(Document doc, String dest, String refValue) {
		Element defRef = doc.createElement("DEFINITION-REF");
		defRef.setAttribute("DEST", dest);
		defRef.appendChild(doc.createTextNode(refValue));
		return defRef;
	}

	public static Element createElement(Document doc, Element parent, String tagName) {
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
		return child;
	}
}