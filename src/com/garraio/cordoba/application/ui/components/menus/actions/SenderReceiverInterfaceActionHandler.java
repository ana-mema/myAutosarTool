package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;

public class SenderReceiverInterfaceActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddDataElementAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SenderReceiverInterface senderReceiverInterface = (SenderReceiverInterface) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (senderReceiverInterface == null) {
				return null;
			}

			VariableDataPrototype dataElement = AutosarElementFactory.createSenderReceiverInterfaceDataElement(name,
					null);
			senderReceiverInterface.getDataElements().add(dataElement);
			return dataElement;

		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.SENDER_RECEIVER_DATA_ELEMENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

}
