package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.SenderReceiverInterfaceActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class SenderReceiverInterfaceMenuProvider extends AbstractMenuProvider {

	@Override
	public void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add",
				SenderReceiverInterfaceActionHandler.createAddDataElementAction(element));
	}

}
