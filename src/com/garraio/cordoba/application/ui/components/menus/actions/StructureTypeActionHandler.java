package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataTypeElement;
import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public class StructureTypeActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddStructElementAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ImplementationDataTypeElement implementationTypeElement = AutosarElementFactory
					.createImplementationTypeElement(name, null, null);

			ImplementationDataType parentType = (ImplementationDataType) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());
			parentType.getSubElements().add(implementationTypeElement);

			return implementationTypeElement;
		};

		return CommonActionHandler.createAddElementAction(
				TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_STRUCTURE_ELEMENT, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

}
