package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.components.AtomicSwComponentType;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.swcinternalbehavior.SwcInternalBehavior;

public class AtomicSWCActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddPPortAction(final TreeElement parent) {
		return createAddPortAction(TreeElementTypeEnum.PPORT, parent);
	}

	public static Action createAddRPortAction(final TreeElement parent) {
		return createAddPortAction(TreeElementTypeEnum.RPORT, parent);
	}

	public static Action createAddInternalBehaviorAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwComponentType SWC = (SwComponentType) AutosarElementRegistry.getElementById(parent.getAutosarElementId());

			if (SWC == null || !(SWC instanceof AtomicSwComponentType)) {
				return null;
			}

			SwcInternalBehavior internalBehavior = AutosarElementFactory.createSwcInternalBehavior(name);
			((AtomicSwComponentType) SWC).getInternalBehaviors().add(internalBehavior);

			return internalBehavior;

		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.INTERNAL_BEHAVIOR, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	private static Action createAddPortAction(final TreeElementTypeEnum elementType, final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwComponentType SWC = (SwComponentType) AutosarElementRegistry.getElementById(parent.getAutosarElementId());
			PortPrototype portPrototype;

			if (SWC == null) {
				return null;
			}

			switch (elementType) {
			case PPORT:
				portPrototype = AutosarElementFactory.createProviderPort(name);
				break;
			case RPORT:
				portPrototype = AutosarElementFactory.createRequirePort(name);
				break;
			default:
				return null;
			}

			SWC.getPorts().add(portPrototype);
			return portPrototype;
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

}
