package com.garraio.cordoba.application.core.adapter.events;

import autosar40.swcomponent.swcinternalbehavior.rteevents.InitEvent;

public class InitEventAdapter extends AbstractEventAdapter<InitEvent> {

	public InitEventAdapter(InitEvent event) {
		super(event);
	}

	@Override
	public String getTypeName() {
		return InitEvent.class.getSimpleName();
	}

}
