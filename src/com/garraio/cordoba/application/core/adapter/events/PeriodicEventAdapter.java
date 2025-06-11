package com.garraio.cordoba.application.core.adapter.events;

import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;

public class PeriodicEventAdapter extends AbstractEventAdapter<TimingEvent> {

	public PeriodicEventAdapter(TimingEvent event) {
		super(event);
	}

	public String getPeriod() {
		return String.valueOf(element.getPeriod());
	}
	public Double NumericgetPeriod() {
		return element.getPeriod();
	}

	public boolean setPeriod(String periodStr) {
		try {
			double periodValue = Double.parseDouble(periodStr);
			element.setPeriod(periodValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getTypeName() {
		return TimingEvent.class.getSimpleName();
	}

}
