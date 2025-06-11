package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.StructureTypeActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class StructureTypeMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add", StructureTypeActionHandler.createAddStructElementAction(element));
	}

}
