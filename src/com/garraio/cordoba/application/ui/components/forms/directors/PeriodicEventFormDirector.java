package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.PERIOD;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.RUNNABLE_REFERENCE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.events.PeriodicEventAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;

public class PeriodicEventFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		TimingEvent periodicEvent = (TimingEvent) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (periodicEvent == null) {
			return;
		}

		PeriodicEventAdapter periodicEventAdapter = new PeriodicEventAdapter(periodicEvent);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, periodicEventAdapter.getShortName())
				.addReferenceField(RUNNABLE_REFERENCE, periodicEventAdapter.getAvailableRunnables(),
						periodicEventAdapter.getRunnable())
				.addTextField(PERIOD, periodicEventAdapter.getPeriod()).refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		TimingEvent periodicEvent = (TimingEvent) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (periodicEvent == null) {
			return;
		}

		PeriodicEventAdapter periodicEventAdapter = new PeriodicEventAdapter(periodicEvent);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		RunnableEntity runnable = builder.getFieldValue(RUNNABLE_REFERENCE).map(value -> (RunnableEntity) value)
				.orElse(null);
		String period = builder.getFieldValue(PERIOD).map(value -> (String) value).orElse("1");

		periodicEventAdapter.setShortName(shortName);
		periodicEventAdapter.setRunnable(runnable);
		periodicEventAdapter.setPeriod(period);
	}
}
