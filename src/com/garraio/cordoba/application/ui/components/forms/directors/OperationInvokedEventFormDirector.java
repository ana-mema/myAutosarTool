package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.CONTEXT_PROVIDED_PORT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.RUNNABLE_REFERENCE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.TARGET_PROVIDED_OPERATION;

import com.garraio.cordoba.application.core.adapter.events.OperationInvokedEventAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.rteevents.OperationInvokedEvent;

public class OperationInvokedEventFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		OperationInvokedEvent operationInvokedEvent = (OperationInvokedEvent) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (operationInvokedEvent == null) {
			return;
		}

		OperationInvokedEventAdapter operationInvokedEventAdapter = new OperationInvokedEventAdapter(
				operationInvokedEvent);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, operationInvokedEventAdapter.getShortName())
				.addReferenceField(RUNNABLE_REFERENCE, operationInvokedEventAdapter.getAvailableRunnables(),
						operationInvokedEventAdapter.getRunnable())
				.addReferenceField(CONTEXT_PROVIDED_PORT, operationInvokedEventAdapter.getAvailableProvidedPorts(),
						operationInvokedEventAdapter.getPort())
				.addReferenceField(TARGET_PROVIDED_OPERATION, operationInvokedEventAdapter.getAvailableOperations(),
						operationInvokedEventAdapter.getTargetOperation())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		OperationInvokedEvent operationInvokedEvent = (OperationInvokedEvent) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (operationInvokedEvent == null) {
			return;
		}

		OperationInvokedEventAdapter operationInvokedEventAdapter = new OperationInvokedEventAdapter(
				operationInvokedEvent);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");

		RunnableEntity runnable = builder.getFieldValue(RUNNABLE_REFERENCE).map(value -> (RunnableEntity) value)
				.orElse(null);

		PPortPrototype contextProvidedPort = builder.getFieldValue(CONTEXT_PROVIDED_PORT)
				.map(value -> (PPortPrototype) value).orElse(null);

		ClientServerOperation targetProvidedOperation = builder.getFieldValue(TARGET_PROVIDED_OPERATION)
				.map(value -> (ClientServerOperation) value).orElse(null);

		operationInvokedEventAdapter.setShortName(shortName);
		operationInvokedEventAdapter.setRunnable(runnable);
		operationInvokedEventAdapter.setPort(contextProvidedPort);
		operationInvokedEventAdapter.setTargetOperation(targetProvidedOperation);

	}

}
