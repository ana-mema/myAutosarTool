package com.garraio.cordoba.application.core.adapter.events;

import java.util.ArrayList;
import java.util.List;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.swcomponent.components.AtomicSwComponentType;
import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.SwcInternalBehavior;
import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;

public abstract class AbstractEventAdapter<T extends RTEEvent> extends AbstractAutosarElementAdapter<T> {

	public AbstractEventAdapter(T event) {
		super(event);
	}

	public RunnableEntity getRunnable() {
		return element.getStartOnEvent();
	}

	public void setRunnable(RunnableEntity runnable) {
		element.setStartOnEvent(runnable);
	}

	public SwcInternalBehavior getParentInternalBehavior() {
		return element.getSwcInternalBehavior();
	}

	public List<RunnableEntity> getAvailableRunnables() {
		SwcInternalBehavior behavior = getParentInternalBehavior();
		if (behavior == null) {
			return new ArrayList<>();
		}

		return behavior.getRunnables();
	}

	public AtomicSwComponentType getParentComponent() {
		return getParentInternalBehavior().getAtomicSwComponentType();
	}

	public abstract String getTypeName();
}

