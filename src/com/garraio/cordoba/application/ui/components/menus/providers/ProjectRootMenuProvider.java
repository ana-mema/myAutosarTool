package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.ProjectRootActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class ProjectRootMenuProvider extends AbstractMenuProvider {

	@Override
	public void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add", ProjectRootActionHandler.createAddBaseTypesAction(element),
				ProjectRootActionHandler.createAddImplementationTypesAction(element),
				ProjectRootActionHandler.createAddInterfacesAction(element),
				ProjectRootActionHandler.createAddComponentsAction(element),
				ProjectRootActionHandler.createAddCompositionsAction(element),
				ProjectRootActionHandler.createAddEventToTaskMappingsAction(element));
	}

}
