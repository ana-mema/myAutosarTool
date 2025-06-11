package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.InternalBehaviorActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class InternalBehaviorMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		MenuManager addSubMenu = new MenuManager("Add");
		menuManager.add(addSubMenu);

		createSubmenuWithActions(addSubMenu, "Events", InternalBehaviorActionHandler.createAddInitEventAction(element),
				InternalBehaviorActionHandler.createAddPeriodicEventAction(element),
				InternalBehaviorActionHandler.createAddOperationInvokedEventAction(element));

		createSubmenuWithActions(addSubMenu, "Runnables",
				InternalBehaviorActionHandler.createAddRunnableAction(element));
	}

}
