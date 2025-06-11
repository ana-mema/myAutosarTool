package com.garraio.cordoba.application.core.generator.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.artop.aal.common.resource.impl.AutosarResourceFactoryImpl;
import org.artop.aal.common.resource.impl.AutosarResourceSetImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.garraio.cordoba.application.core.generator.xml.eventmapping.OsNeedsGenerator;
import com.garraio.cordoba.application.core.model.os.OSTask;
import com.garraio.cordoba.application.ui.parts.LoggerPart;

import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.util.Autosar40Factory;
import autosar40.util.Autosar40ResourceFactoryImpl;

public class XMLGenerator {
	AutosarResourceFactoryImpl resourceFactory;
	AutosarResourceSetImpl resourceSet;

	public XMLGenerator() {
		this.resourceFactory = new Autosar40ResourceFactoryImpl();
		this.resourceSet = new AutosarResourceSetImpl();
		this.resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("arxml", this.resourceFactory);
	}

	public void generateDataTypes(List<SwBaseType> baseTypes, List<ImplementationDataType> implementationDataTypes) {
		if (baseTypes.isEmpty() && implementationDataTypes.isEmpty()) {
			return;
		}

		LoggerPart.log("Generating data types...");

		ARPackage baseTypesPackage = createBaseTypesPackage(baseTypes);
		ARPackage implementationTypesPackage = createImplementationTypesPackage(implementationDataTypes);

		AUTOSAR autosar = createAutosarModel();
		autosar.getArPackages().add(baseTypesPackage);
		autosar.getArPackages().add(implementationTypesPackage);

		saveToFile(autosar, "datatypes.arxml");
	}

	public void generateInterfaces(List<PortInterface> interfaces) {
		if (interfaces.isEmpty()) {
			return;
		}

		LoggerPart.log("Generating interfaces...");

		ARPackage interfacesPackage = createInterfacesPackage(interfaces);

		AUTOSAR autosar = createAutosarModel();
		autosar.getArPackages().add(interfacesPackage);

		saveToFile(autosar, "interfaces.arxml");

	}

	public void generateSwComponents(List<SwComponentType> atomicSwComponents) {
		if (atomicSwComponents.isEmpty()) {
			return;
		}

		LoggerPart.log("Generating atomic software components...");

		for (SwComponentType atomicSwComponent : atomicSwComponents) {
			generateComponentFile(atomicSwComponent);
		}
	}

	public void generateCompositions(List<CompositionSwComponentType> compositions) {
		if (compositions.isEmpty()) {
			return;
		}

		LoggerPart.log("Generating composition software components...");

		for (CompositionSwComponentType composition : compositions) {
			generateComponentFile(composition);
		}
	}

	public void generateOsNeeds(List<OSTask> osTasks) {
		if (osTasks.isEmpty()) {
			return;
		}

		LoggerPart.log("Generating OS needs...");
		OsNeedsGenerator.generateOsNeedsArxml(osTasks);
	}

	private ARPackage createBaseTypesPackage(List<SwBaseType> baseTypes) {
		ARPackage baseTypesPackage = createPackage("AUTOSAR_Base_Types");

		for (SwBaseType baseType : baseTypes) {
			baseTypesPackage.getElements().add(baseType);
		}

		return baseTypesPackage;
	}

	private ARPackage createImplementationTypesPackage(List<ImplementationDataType> implementationDataTypes) {
		ARPackage implementationTypesPackage = createPackage("AUTOSAR_Implementation_Types");

		Map<String, ARPackage> categoryPackages = new HashMap<>();
		categoryPackages.put("VALUE", createPackage("VALUE"));
		categoryPackages.put("ARRAY", createPackage("ARRAY"));
		categoryPackages.put("STRUCTURE", createPackage("STRUCTURE"));

		for (ARPackage categoryPackage : categoryPackages.values()) {
			implementationTypesPackage.getArPackages().add(categoryPackage);
		}

		for (ImplementationDataType implementationDataType : implementationDataTypes) {
			String category = implementationDataType.getCategory();
			ARPackage targetPackage = categoryPackages.get(category);
			if (targetPackage != null) {
				targetPackage.getElements().add(implementationDataType);
			}
		}

		return implementationTypesPackage;
	}

	private ARPackage createInterfacesPackage(List<PortInterface> interfaces) {
		ARPackage interfacesPackage = createPackage("AUTOSAR_Interfaces");

		for (PortInterface portInterface : interfaces) {
			interfacesPackage.getElements().add(portInterface);
		}
		return interfacesPackage;
	}

	private void generateComponentFile(SwComponentType component) {
		ARPackage componentPackage = createPackage(component.getShortName() + "_Package");
		componentPackage.getElements().add(component);

		AUTOSAR autosar = createAutosarModel();
		autosar.getArPackages().add(componentPackage);

		String filename = component.getShortName() + ".arxml";
		saveToFile(autosar, filename);
	}

	private ARPackage createPackage(String name) {
		ARPackage arPackage = Autosar40Factory.eINSTANCE.createARPackage();
		arPackage.setShortName(name);
		return arPackage;
	}

	private AUTOSAR createAutosarModel() {
		return Autosar40Factory.eINSTANCE.createAUTOSAR();
	}

	private void saveToFile(AUTOSAR autosar, String fileName) {
		try {
			Resource resource = resourceSet.createResource(URI.createFileURI(fileName));
			resource.getContents().add(autosar);
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
