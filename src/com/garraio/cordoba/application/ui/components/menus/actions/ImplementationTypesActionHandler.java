package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public class ImplementationTypesActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	private static Action createAddImplementationDataTypeAction(final String category,
			final TreeElementTypeEnum elementType, final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ImplementationDataType implementationType = AutosarElementFactory.createImplementationType(name, category,
					null);

			return implementationType;
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

	public static Action createAddValueAction(final TreeElement parent) {
		return createAddImplementationDataTypeAction("VALUE", TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_VALUE,
				parent);
	}

	public static Action createAddArrayAction(final TreeElement parent) {
		return createAddImplementationDataTypeAction("ARRAY", TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_ARRAY,
				parent);
	}

	public static Action createAddStructAction(final TreeElement parent) {
		return createAddImplementationDataTypeAction("STRUCTURE",
				TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_STRUCTURE, parent);
	}

}
