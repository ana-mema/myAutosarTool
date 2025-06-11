package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.SwComponentPrototype;
import autosar40.swcomponent.composition.SwConnector;

public class CompositionSWCActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddComponentPrototypeAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			CompositionSwComponentType composition = (CompositionSwComponentType) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (composition == null) {
				return null;

			}

			SwComponentPrototype componentPrototype = AutosarElementFactory.createSwComponentPrototype(name);
			composition.getComponents().add(componentPrototype);

			return componentPrototype;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.COMPONENT_PROTOTYPE, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddPPortAction(final TreeElement parent) {
		return createAddPortAction(TreeElementTypeEnum.PPORT, parent);
	}

	public static Action createAddRPortAction(final TreeElement parent) {
		return createAddPortAction(TreeElementTypeEnum.RPORT, parent);
	}

	public static Action createAddAssemblyConnectorAction(final TreeElement parent) {
		return createAddConnectorAction(TreeElementTypeEnum.ASSEMBLY_SOFTWARE_CONNECTOR, parent);
	}

	public static Action createAddDelegationConnectorAction(final TreeElement parent) {
		return createAddConnectorAction(TreeElementTypeEnum.DELEGATION_SOFTWARE_CONNECTOR, parent);
	}

	private static Action createAddPortAction(final TreeElementTypeEnum elementType, final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			CompositionSwComponentType composition = (CompositionSwComponentType) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());
			PortPrototype portPrototype;

			if (composition == null) {
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

			composition.getPorts().add(portPrototype);
			return portPrototype;
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}

	private static Action createAddConnectorAction(final TreeElementTypeEnum elementType, final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			CompositionSwComponentType composition = (CompositionSwComponentType) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());
			SwConnector connector;

			if (composition == null) {
				return null;
			}

			switch (elementType) {
			case ASSEMBLY_SOFTWARE_CONNECTOR:
				connector = AutosarElementFactory.createAssemblyConnector(name);
				break;
			case DELEGATION_SOFTWARE_CONNECTOR:
				connector = AutosarElementFactory.createDelegationConnector(name);
				break;
			default:
				return null;
			}

			composition.getConnectors().add(connector);
			return connector;
		};

		return CommonActionHandler.createAddElementAction(elementType, parent, autosarCreatorFunction,
				CHECK_DUPLICATES);
	}
}
