package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.SoftwareComponentsActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class SoftwareComponentsMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add",
				SoftwareComponentsActionHandler.createAddApplicationSWCAction(element),
				SoftwareComponentsActionHandler.createAddServiceSWCAction(element),
				SoftwareComponentsActionHandler.createAddParameterSWCAction(element),
				SoftwareComponentsActionHandler.createAddComplexSWCAction(element));
	}

}
