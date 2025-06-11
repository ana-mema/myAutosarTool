package com.garraio.cordoba.application.core.adapter;

import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;

public class RunnableEntityAdapter extends AbstractAutosarElementAdapter<RunnableEntity> {

	public RunnableEntityAdapter(RunnableEntity runnable) {
		super(runnable);
	}

	public String getMinimumStartInterval() {
		return String.valueOf(element.getMinimumStartInterval());
	}

	public boolean setMinimumStartInterval(String intervalStr) {
		try {
			double interval = Double.parseDouble(intervalStr);
			element.setMinimumStartInterval(interval);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean canBeInvokedConcurrently() {
		return element.getCanBeInvokedConcurrently();
	}

	public void setCanBeInvokedConcurrently(boolean canBeInvoked) {
		element.setCanBeInvokedConcurrently(canBeInvoked);
	}

}
