package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;
import autosar40.swcomponent.portinterface.ClientServerOperation;

public class ClientServerOperationActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddOperationArgumentAction(TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ClientServerOperation csOperation = (ClientServerOperation) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (csOperation == null) {
				return null;
			}

			ArgumentDataPrototype arg = AutosarElementFactory.createClientServerOperationArgument(name);
			csOperation.getArguments().add(arg);

			return arg;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.CLIENT_SERVER_OPERATION_ARGUMENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddOperationErrorReferenceAction(TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.CLIENT_SERVER_OPERATION_ERROR_REFERENCE,
				parent, null, CHECK_DUPLICATES);
	}

}
