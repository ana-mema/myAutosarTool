package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.CompositionSWCActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class CompositionSWCMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		MenuManager addSubMenu = new MenuManager("Add");
		menuManager.add(addSubMenu);

		createSubmenuWithActions(addSubMenu, "Components",
				CompositionSWCActionHandler.createAddComponentPrototypeAction(element));

		createSubmenuWithActions(addSubMenu, "Ports", CompositionSWCActionHandler.createAddPPortAction(element),
				CompositionSWCActionHandler.createAddRPortAction(element));

		createSubmenuWithActions(addSubMenu, "Connectors",
				CompositionSWCActionHandler.createAddAssemblyConnectorAction(element),
				CompositionSWCActionHandler.createAddDelegationConnectorAction(element));
	}

}
