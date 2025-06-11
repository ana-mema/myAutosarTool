package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public class BaseTypesActionHandler {
	private static final boolean CHECK_DUPLICATES = true;

	private static Action createAddBaseDataTypeAction(TreeElementTypeEnum elementType, Long baseTypeSize,
			final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwBaseType baseType = AutosarElementFactory.createBaseType(name, baseTypeSize);

			return baseType;
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

	// Signed integer types
	public static Action createAddSint8Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_SINT8, 8L, parent);
	}

	public static Action createAddSint16Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_SINT16, 16L, parent);
	}

	public static Action createAddSint32Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_SINT32, 32L, parent);
	}

	public static Action createAddSint64Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_SINT64, 64L, parent);
	}

	// Unsigned integer types
	public static Action createAddUint8Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_UINT8, 8L, parent);
	}

	public static Action createAddUint16Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_UINT16, 16L, parent);
	}

	public static Action createAddUint32Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_UINT32, 32L, parent);
	}

	public static Action createAddUint64Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_UINT64, 64L, parent);
	}

	// Floating point types
	public static Action createAddFloat32Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_FLOAT32, 32L, parent);
	}

	public static Action createAddFloat64Action(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_FLOAT64, 64L, parent);
	}

	// Boolean type
	public static Action createAddBooleanAction(final TreeElement parent) {
		return createAddBaseDataTypeAction(TreeElementTypeEnum.SOFTWARE_BASE_TYPE_BOOLEAN, 32L, parent);
	}

}
