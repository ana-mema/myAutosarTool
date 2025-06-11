package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public class SoftwareComponentsActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	private static Action createAddSoftwareComponentAction(final TreeElementTypeEnum elementType,
			final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			switch (elementType) {
			case APPLICATION_SOFTWARE_COMPONENT:
				return AutosarElementFactory.createApplicationSwComponentType(name);
			case SERVICE_SOFTWARE_COMPONENT:
				return AutosarElementFactory.createServiceSwComponentType(name);
			case PARAMETER_SOFTWARE_COMPONENT:
				return AutosarElementFactory.createParameterSwComponentType(name);
			case COMPLEX_SOFTWARE_COMPONENT:
				return AutosarElementFactory.createComplexDeviceDriverSwComponentType(name);
			default:
				return null;
			}
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

	public static Action createAddApplicationSWCAction(final TreeElement parent) {
		return createAddSoftwareComponentAction(TreeElementTypeEnum.APPLICATION_SOFTWARE_COMPONENT, parent);
	}

	public static Action createAddServiceSWCAction(final TreeElement parent) {
		return createAddSoftwareComponentAction(TreeElementTypeEnum.SERVICE_SOFTWARE_COMPONENT, parent);
	}

	public static Action createAddParameterSWCAction(final TreeElement parent) {
		return createAddSoftwareComponentAction(TreeElementTypeEnum.PARAMETER_SOFTWARE_COMPONENT, parent);
	}

	public static Action createAddComplexSWCAction(final TreeElement parent) {
		return createAddSoftwareComponentAction(TreeElementTypeEnum.COMPLEX_SOFTWARE_COMPONENT, parent);
	}
}
