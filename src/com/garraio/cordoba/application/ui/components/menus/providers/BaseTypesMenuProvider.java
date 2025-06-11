package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.BaseTypesActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class BaseTypesMenuProvider extends AbstractMenuProvider {

	@Override
	public void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		createSubmenuWithActions(menuManager, "Add", BaseTypesActionHandler.createAddSint8Action(element),
				BaseTypesActionHandler.createAddSint16Action(element),
				BaseTypesActionHandler.createAddSint32Action(element),
				BaseTypesActionHandler.createAddSint64Action(element),
				BaseTypesActionHandler.createAddUint8Action(element),
				BaseTypesActionHandler.createAddUint16Action(element),
				BaseTypesActionHandler.createAddUint32Action(element),
				BaseTypesActionHandler.createAddUint64Action(element),
				BaseTypesActionHandler.createAddFloat32Action(element),
				BaseTypesActionHandler.createAddFloat64Action(element),
				BaseTypesActionHandler.createAddBooleanAction(element));
	}

}
