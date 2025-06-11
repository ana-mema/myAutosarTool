package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.CAN_BE_INVOKED_CONCURRENTLY;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.MIN_START_INTERVAL;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.RunnableEntityAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;

public class RunnableEntityFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		RunnableEntity runnable = (RunnableEntity) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (runnable == null) {
			return;
		}

		RunnableEntityAdapter runnableAdapter = new RunnableEntityAdapter(runnable);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, runnableAdapter.getShortName())
				.addTextField(MIN_START_INTERVAL, runnableAdapter.getMinimumStartInterval())
				.addBooleanField(CAN_BE_INVOKED_CONCURRENTLY, runnableAdapter.canBeInvokedConcurrently())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		RunnableEntity runnable = (RunnableEntity) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (runnable == null) {
			return;
		}

		RunnableEntityAdapter runnableAdapter = new RunnableEntityAdapter(runnable);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");

		String minStartInterval = builder.getFieldValue(MIN_START_INTERVAL).map(value -> (String) value).orElse("0");

		Boolean canBeInvokedConcurrently = builder.getFieldValue(CAN_BE_INVOKED_CONCURRENTLY)
				.map(value -> (Boolean) value).orElse(false);

		runnableAdapter.setShortName(shortName);
		runnableAdapter.setMinimumStartInterval(minStartInterval);
		runnableAdapter.setCanBeInvokedConcurrently(canBeInvokedConcurrently);
	}

}
