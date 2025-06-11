package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;
import autosar40.swcomponent.portinterface.ParameterInterface;

public class ParameterInterfaceActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddParameterPrototypeAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ParameterInterface pi = (ParameterInterface) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (pi == null) {
				return null;
			}

			ParameterDataPrototype prototype = AutosarElementFactory.createParameterInterfacePrototype(name);

			pi.getParameters().add(prototype);

			return prototype;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.PARAMETER_INTERFACE_PROTOTYPE, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

}
