package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;

public class ClientServerInterfaceActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddClientServerOperationAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ClientServerInterface clientServerInterface = (ClientServerInterface) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (clientServerInterface == null) {
				return null;
			}

			ClientServerOperation csOperation = AutosarElementFactory.createClientServerOperation(name);

			clientServerInterface.getOperations().add(csOperation);

			return csOperation;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.CLIENT_SERVER_OPERATION, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddClientServerPossibleErrorAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			ClientServerInterface clientServerInterface = (ClientServerInterface) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (clientServerInterface == null) {
				return null;
			}

			ApplicationError applicationError = AutosarElementFactory.createClientServerPossibleError(name);
			clientServerInterface.getPossibleErrors().add(applicationError);

			return applicationError;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.CLIENT_SERVER_POSSIBLE_ERROR, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

}
