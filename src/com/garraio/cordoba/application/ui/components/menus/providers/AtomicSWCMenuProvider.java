package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.menus.actions.AtomicSWCActionHandler;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

public class AtomicSWCMenuProvider extends AbstractMenuProvider {

	@Override
	protected void addSpecificMenuItems(IMenuManager menuManager, TreeElement element) {
		if (element.getType() == TreeElementTypeEnum.PARAMETER_SOFTWARE_COMPONENT) {
			createSubmenuWithActions(menuManager, "Add", AtomicSWCActionHandler.createAddPPortAction(element));
		} else {
			createSubmenuWithActions(menuManager, "Add", AtomicSWCActionHandler.createAddPPortAction(element),
					AtomicSWCActionHandler.createAddRPortAction(element),
					AtomicSWCActionHandler.createAddInternalBehaviorAction(element));
		}
	}

}
