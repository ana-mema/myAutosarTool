package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.CommonActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

public abstract class AbstractMenuProvider implements ITreeElementMenuProvider {

	protected abstract void addSpecificMenuItems(IMenuManager menuManager, TreeElement element);

	@Override
	public void fillContextMenu(IMenuManager menuManager, TreeElement element) {
		addSpecificMenuItems(menuManager, element);

		if (canDelete(element)) {
			menuManager.add(CommonActionHandler.createDeleteElementAction(element));

		}
	}

	protected boolean canDelete(TreeElement element) {
		return element != null && element.getType() != TreeElementTypeEnum.PROJECT_ROOT;
	}

	protected MenuManager createSubmenuWithActions(IMenuManager parentMenu, String submenuLabel, Action... actions) {
		MenuManager submenu = new MenuManager(submenuLabel);

		for (Action action : actions) {
			submenu.add(action);
		}

		parentMenu.add(submenu);

		return submenu;
	}

}