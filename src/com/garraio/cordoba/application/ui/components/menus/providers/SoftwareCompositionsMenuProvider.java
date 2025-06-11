package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.SoftwareCompositionsActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class SoftwareCompositionsMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add",
				SoftwareCompositionsActionHandler.createAddCompositionSWCAction(element));
	}

}
