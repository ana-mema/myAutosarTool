package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;
import com.garraio.cordoba.application.ui.parts.ElementTreePart;
import com.garraio.cordoba.application.ui.parts.LoggerPart;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable;

public class CommonActionHandler {

	public static Action createAddElementAction(TreeElementTypeEnum elementType, TreeElement parent,
			Function<String, ARObject> autosarCreatorFunction, boolean checkDuplicates) {

		return new Action(elementType.getDisplayName()) {
			@Override
			public void run() {
				try {
					addNewElement(parent, elementType, autosarCreatorFunction, checkDuplicates);
				} catch (Exception e) {
					LoggerPart.log("Error adding new element: " + e.getMessage());
				}
			}
		};

	}

	public static Action createDeleteElementAction(final TreeElement element) {
		return new Action("Delete") {
			@Override
			public void run() {
				if (element == null || element.getParent() == null) {
					return;
				}

				String autosarElementId = element.getAutosarElementId();

				if (autosarElementId != null) {
					EObject autosarElement = AutosarElementRegistry.getElementById(autosarElementId);

					if (autosarElement != null) {
						AutosarElementRegistry.unregister(autosarElement);
					}
				}

				TreeElement parent = element.getParent();
				parent.removeChild(element);

				ElementTreePart.refreshTreeAfterChange(parent, parent);

				LoggerPart.log("Deleted element " + element.getName());
			}
		};
	}

	private static void addNewElement(TreeElement parent, TreeElementTypeEnum elementType,
			Function<String, ARObject> autosarCreatorFunction, boolean checkDuplicates) {
		String elementName = generateElementName(parent, elementType);

		if (checkDuplicates && parent.hasChildWithName(elementName)) {
			return;
		}

		TreeElement newChild = new TreeElement(elementName, elementType);

		if (autosarCreatorFunction != null) {
			ARObject autosarElement = autosarCreatorFunction.apply(elementName);
			if (autosarElement != null) {
				newChild.setAutosarElementId(((Identifiable) autosarElement).getUuid());
				AutosarElementRegistry.register(autosarElement);
			}
		}

		parent.addChild(newChild);
		ElementTreePart.refreshTreeAfterChange(parent, newChild);

		LoggerPart.log("Added new " + elementName);
	}

	private static String generateElementName(TreeElement parent, TreeElementTypeEnum elementType) {
		return (elementType.getPrefixName() == null) ? elementType.getDisplayName()
				: elementType.getPrefixName() + "_" + (parent.getChildren().size() + 1);
	}

}
