package com.garraio.cordoba.application.ui.components.menus.actions;

import java.util.function.Function;

import org.eclipse.jface.action.Action;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.SwcInternalBehavior;
import autosar40.swcomponent.swcinternalbehavior.rteevents.InitEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.OperationInvokedEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;

public class InternalBehaviorActionHandler {
	private static final boolean CHECK_DUPLICATES = false;

	public static Action createAddInitEventAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwcInternalBehavior internalBehavior = (SwcInternalBehavior) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (internalBehavior == null) {
				return null;
			}

			InitEvent initEvent = AutosarElementFactory.createInitEvent(name);
			internalBehavior.getEvents().add(initEvent);

			return initEvent;

		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.INIT_EVENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddPeriodicEventAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwcInternalBehavior internalBehavior = (SwcInternalBehavior) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (internalBehavior == null) {
				return null;
			}

			TimingEvent periodicEvent = AutosarElementFactory.createPeriodicEvent(name);
			internalBehavior.getEvents().add(periodicEvent);

			return periodicEvent;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.PERIODIC_EVENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddOperationInvokedEventAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwcInternalBehavior internalBehavior = (SwcInternalBehavior) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (internalBehavior == null) {
				return null;
			}

			OperationInvokedEvent operationInvokedEvent = AutosarElementFactory.createOperationInvokedEvent(name);
			internalBehavior.getEvents().add(operationInvokedEvent);

			return operationInvokedEvent;

		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.OPERATION_INVOKED_EVENT, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}

	public static Action createAddRunnableAction(final TreeElement parent) {
		Function<String, ARObject> autosarCreatorFunction = (name) -> {
			SwcInternalBehavior internalBehavior = (SwcInternalBehavior) AutosarElementRegistry
					.getElementById(parent.getAutosarElementId());

			if (internalBehavior == null) {
				return null;
			}

			RunnableEntity runnable = AutosarElementFactory.createRunnable(name);
			internalBehavior.getRunnables().add(runnable);

			return runnable;
		};

		return CommonActionHandler.createAddElementAction(TreeElementTypeEnum.RUNNABLE_ENTITY, parent,
				autosarCreatorFunction, CHECK_DUPLICATES);
	}
}
