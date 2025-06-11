package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CounterGenerator {

	public static String addCounter(Document doc, Element containers, String counterShortName) {
		Element counter = doc.createElement("ECUC-CONTAINER-VALUE");
		counter.appendChild(XmlHelper.createTextElement(doc, "SHORT-NAME", counterShortName));
		counter.appendChild(XmlHelper.createDefinitionRefElement(doc, "ECUC-PARAM-CONF-CONTAINER-DEF",
				"/AUTOSAR/EcucDefs/Os/OsCounter"));
		containers.appendChild(counter);

		Element counterParams = doc.createElement("PARAMETER-VALUES");
		counter.appendChild(counterParams);

		// Enum param
		Element osCounterType = doc.createElement("ECUC-TEXTUAL-PARAM-VALUE");
		osCounterType.appendChild(XmlHelper.createDefinitionRefElement(doc, "ECUC-ENUMERATION-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsCounter/OsCounterType"));
		osCounterType.appendChild(XmlHelper.createTextElement(doc, "VALUE", "SOFTWARE"));
		counterParams.appendChild(osCounterType);

		// Integer params
		counterParams.appendChild(XmlHelper.createNumericElement(doc, "ECUC-INTEGER-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsCounter/OsCounterMaxAllowedValue", "65535"));
		counterParams.appendChild(XmlHelper.createNumericElement(doc, "ECUC-INTEGER-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsCounter/OsCounterMinCycle", "1"));
		counterParams.appendChild(XmlHelper.createNumericElement(doc, "ECUC-INTEGER-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsCounter/OsCounterTicksPerBase", "1"));

		return "/OS_CFG/" + counterShortName;
	}

}
