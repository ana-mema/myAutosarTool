package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.composition.CompositionSwComponentType;

public class SoftwareCompositionsActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddCompositionSWCAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			CompositionSwComponentType composition = AutosarElementFactory.createCompositionSwComponentType(name);
			return composition;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.COMPOSITION_SOFTWARE_COMPONENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

}
