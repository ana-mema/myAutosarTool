package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.ImplementationTypesActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class ImplementationTypesMenuProvider extends AbstractMenuProvider {

	@Override
	public void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add", ImplementationTypesActionHandler.createAddValueAction(element),
				ImplementationTypesActionHandler.createAddArrayAction(element),
				ImplementationTypesActionHandler.createAddStructAction(element));
	}

}
