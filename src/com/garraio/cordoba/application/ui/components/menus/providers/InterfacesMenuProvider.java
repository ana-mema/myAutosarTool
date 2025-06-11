package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.InterfacesActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class InterfacesMenuProvider extends AbstractMenuProvider {

	@Override
	public void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add",
				InterfacesActionHandler.createAddSenderReceiverInterfaceAction(element),
				InterfacesActionHandler.createAddClientServerInterfaceAction(element),
				InterfacesActionHandler.createAddParameterInterfaceAction(element));
	}

}
