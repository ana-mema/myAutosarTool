package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.RUNNABLE_REFERENCE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.events.InitEventAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.rteevents.InitEvent;

public class InitEventFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		InitEvent initEvent = (InitEvent) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (initEvent == null) {
			return;
		}

		InitEventAdapter initEventAdapter = new InitEventAdapter(initEvent);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, initEventAdapter.getShortName()).addReferenceField(RUNNABLE_REFERENCE,
						initEventAdapter.getAvailableRunnables(), initEventAdapter.getRunnable())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		InitEvent initEvent = (InitEvent) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (initEvent == null) {
			return;
		}

		InitEventAdapter initEventAdapter = new InitEventAdapter(initEvent);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		RunnableEntity runnable = builder.getFieldValue(RUNNABLE_REFERENCE).map(value -> (RunnableEntity) value)
				.orElse(null);

		initEventAdapter.setShortName(shortName);
		initEventAdapter.setRunnable(runnable);

	}
}
