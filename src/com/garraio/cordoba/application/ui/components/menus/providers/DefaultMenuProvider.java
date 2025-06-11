package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class DefaultMenuProvider extends AbstractMenuProvider {
	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		// No specific menu items needed - only delete action will be added by
		// AbstractMenuProvider
	}

}
