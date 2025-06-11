package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.INNER_COMPONENT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.INNER_PORT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.OUTER_PORT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import java.util.List;

import com.garraio.cordoba.application.core.adapter.ComponentPrototypeAdapter;
import com.garraio.cordoba.application.core.adapter.connectors.DelegationConnectorAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.forms.fields.ReferenceField;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.DelegationSwConnector;
import autosar40.swcomponent.composition.SwComponentPrototype;

public class DelegationConnectorFormDirector implements IFormDirector {
	private FormBuilder builder;
	private DelegationConnectorAdapter connectorAdapter;

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		DelegationSwConnector delegationConnector = (DelegationSwConnector) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (delegationConnector == null) {
			return;
		}

		this.connectorAdapter = new DelegationConnectorAdapter(delegationConnector);
		this.builder = builder;

		builder.clearForm().addFormTitle(element.getType().getDisplayName()).addTextField(SHORT_NAME,
				connectorAdapter.getShortName());

		addInnerAndOuterFields();

		addInnerAndOuterChangeListeners();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		DelegationSwConnector delegationConnector = (DelegationSwConnector) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (delegationConnector == null) {
			return;
		}

		this.connectorAdapter = new DelegationConnectorAdapter(delegationConnector);
		this.builder = builder;

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		PortPrototype outerPort = builder.getFieldValue(OUTER_PORT).map(value -> (PortPrototype) value).orElse(null);
		SwComponentPrototype innerComponent = builder.getFieldValue(INNER_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);
		PortPrototype innerPort = builder.getFieldValue(INNER_PORT).map(value -> (PortPrototype) value).orElse(null);

		connectorAdapter.setShortName(shortName);
		connectorAdapter.setOuterPort(outerPort);
		connectorAdapter.setInnerComponent(innerComponent);
		connectorAdapter.setInnerPort(innerPort);

	}

	private void addInnerAndOuterFields() {
		builder.addReferenceField(OUTER_PORT, getAvailableOuterPorts(), connectorAdapter.getOuterPort())
				.addReferenceField(INNER_COMPONENT, getAvailableComponents(), connectorAdapter.getInnerComponent())
				.addReferenceField(INNER_PORT, getCompatibleInnerPorts(), connectorAdapter.getInnerPort())
				.refreshLayout();

	}

	private List<PortPrototype> getAvailableOuterPorts() {
		CompositionSwComponentType parentComposition = connectorAdapter.getParentComposition();

		if (parentComposition == null) {
			return List.of();
		}

		return parentComposition.getPorts();
	}

	private List<SwComponentPrototype> getAvailableComponents() {
		CompositionSwComponentType parentComposition = connectorAdapter.getParentComposition();

		if (parentComposition == null) {
			return List.of();
		}

		return parentComposition.getComponents();
	}

	private List<? extends PortPrototype> getCompatibleInnerPorts() {
		SwComponentPrototype innerComponent = connectorAdapter.getInnerComponent();
		PortPrototype outerPort = connectorAdapter.getOuterPort();

		if (innerComponent == null || outerPort == null) {
			return List.of();
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(innerComponent);

		if (outerPort instanceof RPortPrototype) {
			return componentPrototypeAdapter.getRequiredPorts();
		} else if (outerPort instanceof PPortPrototype) {
			return componentPrototypeAdapter.getProvidedPorts();
		} else {
			return List.of();
		}

	}

	private void addInnerAndOuterChangeListeners() {
		ReferenceField outerPortField = builder.getField(OUTER_PORT).map(field -> (ReferenceField) field).orElse(null);
		ReferenceField innerComponentField = builder.getField(INNER_COMPONENT).map(field -> (ReferenceField) field)
				.orElse(null);

		if (outerPortField == null || innerComponentField == null) {
			return;
		}

		outerPortField.addChangeListener(() -> {
			updateInnerPorts();
		});

		innerComponentField.addChangeListener(() -> {
			updateInnerPorts();
		});
	}

	private void updateInnerPorts() {
		ReferenceField innerPortField = builder.getField(INNER_PORT).map(field -> (ReferenceField) field).orElse(null);

		if (innerPortField == null) {
			return;
		}

		PortPrototype outerPort = builder.getFieldValue(OUTER_PORT).map(value -> (PortPrototype) value).orElse(null);
		SwComponentPrototype innerComponent = builder.getFieldValue(INNER_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);

		if (outerPort == null || innerComponent == null) {
			innerPortField.setOptions(List.of());
			return;

		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(innerComponent);

		if (outerPort instanceof RPortPrototype) {
			innerPortField.setOptions(componentPrototypeAdapter.getRequiredPorts());
		} else if (outerPort instanceof PPortPrototype) {
			innerPortField.setOptions(componentPrototypeAdapter.getProvidedPorts());
		} else {
			innerPortField.setOptions(List.of());
		}

	}

}
