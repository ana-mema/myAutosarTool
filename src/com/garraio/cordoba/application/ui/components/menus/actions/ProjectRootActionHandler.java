package com.garraio.cordoba.application.ui.components.menus.actions;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

public class ProjectRootActionHandler {
	private static final boolean CHECK_DUPLICATES = true;

	public static Action createAddBaseTypesAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.BASE_DATA_TYPES, parent, null,
				CHECK_DUPLICATES);
	}

	public static Action createAddImplementationTypesAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPES, parent, null,
				CHECK_DUPLICATES);
	}

	public static Action createAddInterfacesAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.INTERFACES, parent, null,
				CHECK_DUPLICATES);
	}

	public static Action createAddComponentsAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.SOFTWARE_COMPONENTS, parent, null,
				CHECK_DUPLICATES);
	}

	public static Action createAddCompositionsAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.SOFTWARE_COMPOSITIONS, parent, null,
				CHECK_DUPLICATES);
	}

	public static Action createAddEventToTaskMappingsAction(final TreeElement parent) {
		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.EVENT_TO_TASK_MAPPINGS, parent, null,
				CHECK_DUPLICATES);
	}

}