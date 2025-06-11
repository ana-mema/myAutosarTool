
package com.garraio.cordoba.application.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.garraio.cordoba.application.core.generator.xml.XMLGenerator;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.parts.LoggerPart;

public class GenerateARXMLHandler {
	@Execute
	public void execute() {
		try {
			XMLGenerator xmlGenerator = new XMLGenerator();
			xmlGenerator.generateDataTypes(AutosarElementRegistry.getBaseTypes(),
					AutosarElementRegistry.getImplementationDataTypes());
			xmlGenerator.generateInterfaces(AutosarElementRegistry.getInterfaces());
			xmlGenerator.generateSwComponents(AutosarElementRegistry.getAtomicSwComponents());
			xmlGenerator.generateCompositions(AutosarElementRegistry.getCompositionSwComponents());
			xmlGenerator.generateOsNeeds(AutosarElementRegistry.getOsTasks());

			LoggerPart.log("ARXML files generated successfully.");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}