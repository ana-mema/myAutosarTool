package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.garraio.cordoba.application.core.model.os.OSTask;

public class OsNeedsGenerator {

	// Method to generate the OsNeeds.arxml
	public static void generateOsNeedsArxml(List<OSTask> osTasks) {
		try {
			// Step 1: Initialize the XML document
			XmlDocumentBuilder documentBuilder = new XmlDocumentBuilder();
			Document doc = documentBuilder.createDocument();
			Element root = documentBuilder.createRootElement(doc);
			doc.appendChild(root);

			// Step 2: Create AR-PACKAGES and AR-PACKAGE
			Element arPackages = XmlHelper.createElement(doc, root, "AR-PACKAGES");
			Element arPackage = XmlHelper.createElement(doc, arPackages, "AR-PACKAGE");
			arPackage.appendChild(XmlHelper.createTextElement(doc, "SHORT-NAME", "OsNeeds"));
			// Step 3: Create OS Configuration and Containers
			Element osConfig = documentBuilder.createOsConfig(doc, arPackage);
			Element containers = XmlHelper.createElement(doc, osConfig, "CONTAINERS");

			// Step 4: Add the counter reference for the schedule table
			String counterReferencePath = CounterGenerator.addCounter(doc, containers, "Rte_TickCounter");

			// Step 5: Generate Schedule Table
			ScheduleTableGenerator.addScheduleTable(doc, containers, counterReferencePath, osTasks);

			// Step 6: Handle Task Activations and Expiry Points
			// You can include the necessary logic to handle runnables and rte events for
			// activation and expiry point handling

			// Step 7: Save the XML to file
//			String userHome = System.getProperty("user.home");
//			String desktopPath = userHome + "/Desktop/OsNeeds.arxml";
			String path = "OsNeeds.arxml";
			XmlSaver.saveXmlToFile(doc, path);
			System.out.println("OsNeeds.arxml generated successfully.");
		} catch (Exception e) {
			System.err.println("Error generating OsNeeds.arxml: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
