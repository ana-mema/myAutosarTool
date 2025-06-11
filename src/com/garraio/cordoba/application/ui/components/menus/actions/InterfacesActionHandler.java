package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.utils.StringUtils;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;

public class InterfacesActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	private static <T extends PortInterface> Action createAddInterfaceAction(final TreeElement parent,
			final Class<T> interfaceClass) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			PortInterface newInterface = AutosarElementFactory.createPortInterface(interfaceClass, name);
			return newInterface;
		};

		TreeElementTypeEnum elementType = TreeElementTypeEnum
				.fromDisplayName(StringUtils.formatCamelCase(interfaceClass.getSimpleName()));

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

	public static Action createAddSenderReceiverInterfaceAction(final TreeElement parent) {
		return createAddInterfaceAction(parent, SenderReceiverInterface.class);
	}

	public static Action createAddClientServerInterfaceAction(final TreeElement parent) {
		return createAddInterfaceAction(parent, ClientServerInterface.class);
	}

	public static Action createAddParameterInterfaceAction(final TreeElement parent) {
		return createAddInterfaceAction(parent, ParameterInterface.class);
	}
}
