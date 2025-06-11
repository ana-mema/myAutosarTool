package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SOURCE_COMPONENT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SOURCE_PORT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.TARGET_COMPONENT;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.TARGET_PORT;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.garraio.cordoba.application.core.adapter.ComponentPrototypeAdapter;
import com.garraio.cordoba.application.core.adapter.connectors.AssemblyConnectorAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.forms.fields.ReferenceField;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.composition.AssemblySwConnector;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.SwComponentPrototype;

public class AssemblyConnectorFormDirector implements IFormDirector {
	private FormBuilder builder;
	private AssemblyConnectorAdapter connectorAdapter;

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		AssemblySwConnector assemblyConnector = (AssemblySwConnector) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (assemblyConnector == null) {
			return;
		}

		connectorAdapter = new AssemblyConnectorAdapter(assemblyConnector);
		this.builder = builder;

		builder.clearForm().addFormTitle(element.getType().getDisplayName()).addTextField(SHORT_NAME,
				connectorAdapter.getShortName());

		addSourceAndTargetFields();

		addSourceAndTargetChangeListeners();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		AssemblySwConnector assemblyConnector = (AssemblySwConnector) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (assemblyConnector == null) {
			return;
		}

		connectorAdapter = new AssemblyConnectorAdapter(assemblyConnector);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		SwComponentPrototype sourceComponent = builder.getFieldValue(SOURCE_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);
		PPortPrototype sourcePort = builder.getFieldValue(SOURCE_PORT).map(value -> (PPortPrototype) value)
				.orElse(null);

		SwComponentPrototype targetComponent = builder.getFieldValue(TARGET_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);

		RPortPrototype targetPort = builder.getFieldValue(TARGET_PORT).map(value -> (RPortPrototype) value)
				.orElse(null);

		connectorAdapter.setShortName(shortName);
		connectorAdapter.setSourceComponent(sourceComponent);
		connectorAdapter.setSourcePort(sourcePort);
		connectorAdapter.setTargetComponent(targetComponent);
		connectorAdapter.setTargetPort(targetPort);
	}

	private void addSourceAndTargetFields() {
		SwComponentPrototype sourceComponent = connectorAdapter.getSourceComponent();

		List<SwComponentPrototype> sourceComponetOptions = getAvailableComponentsExcluding(null);
		List<SwComponentPrototype> targetComponetOptions = (sourceComponent == null) ? List.of()
				: getAvailableComponentsExcluding(sourceComponent);

		builder.addReferenceField(SOURCE_COMPONENT, sourceComponetOptions, connectorAdapter.getSourceComponent())
				.addReferenceField(SOURCE_PORT, getAvailableSourcePorts(), connectorAdapter.getSourcePort())
				.addReferenceField(TARGET_COMPONENT, targetComponetOptions, connectorAdapter.getTargetComponent())
				.addReferenceField(TARGET_PORT, getAvailableTargetPorts(), connectorAdapter.getTargetPort())
				.refreshLayout();

	}

	private List<SwComponentPrototype> getAvailableComponentsExcluding(SwComponentPrototype excludedComponent) {
		CompositionSwComponentType parentComposition = connectorAdapter.getParentComposition();

		if (parentComposition == null) {
			return List.of();
		}

		List<SwComponentPrototype> allComponents = parentComposition.getComponents();

		if (excludedComponent == null) {
			return allComponents;
		}

		return allComponents.stream().filter(comp -> !Objects.equals(comp, excludedComponent))
				.collect(Collectors.toList());
	}

	private List<PPortPrototype> getAvailableSourcePorts() {
		SwComponentPrototype sourceComponent = connectorAdapter.getSourceComponent();
		if (sourceComponent == null) {
			return List.of();
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(sourceComponent);
		return componentPrototypeAdapter.getProvidedPorts();
	}

	private List<RPortPrototype> getAvailableTargetPorts() {
		SwComponentPrototype targetComponent = connectorAdapter.getTargetComponent();
		if (targetComponent == null) {
			return List.of();
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(targetComponent);
		return componentPrototypeAdapter.getRequiredPorts();
	}

	private void addSourceAndTargetChangeListeners() {
		ReferenceField sourceComponentField = builder.getField(SOURCE_COMPONENT).map(field -> (ReferenceField) field)
				.orElse(null);
		ReferenceField targetComponentField = builder.getField(TARGET_COMPONENT).map(field -> (ReferenceField) field)
				.orElse(null);

		if (sourceComponentField == null || targetComponentField == null) {
			return;
		}

		sourceComponentField.addChangeListener(() -> {
			updateSourcePorts();
			updateTargetComponets();

		});

		targetComponentField.addChangeListener(() -> {
			updateTargetPorts();
		});
	}

	private void updateSourcePorts() {
		ReferenceField sourcePortField = builder.getField(SOURCE_PORT).map(field -> (ReferenceField) field)
				.orElse(null);

		if (sourcePortField == null) {
			return;
		}

		SwComponentPrototype component = builder.getFieldValue(SOURCE_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);

		if (component == null) {
			sourcePortField.setOptions(List.of());
			return;
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(component);
		List<PPortPrototype> availableSourcePorts = componentPrototypeAdapter.getProvidedPorts();
		sourcePortField.setOptions(availableSourcePorts);
	}

	private void updateTargetComponets() {
		ReferenceField targetComponentField = builder.getField(TARGET_COMPONENT).map(field -> (ReferenceField) field)
				.orElse(null);

		if (targetComponentField == null) {
			return;
		}

		SwComponentPrototype source = builder.getFieldValue(SOURCE_COMPONENT).map(value -> (SwComponentPrototype) value)
				.orElse(null);

		if (source == null) {
			targetComponentField.setOptions(List.of());
			return;
		}

		List<SwComponentPrototype> availableComponents = getAvailableComponentsExcluding(source);
		targetComponentField.setOptions(availableComponents);
	}

	private void updateTargetPorts() {
		ReferenceField targetPortField = builder.getField(TARGET_PORT).map(field -> (ReferenceField) field)
				.orElse(null);

		if (targetPortField == null) {
			return;
		}

		SwComponentPrototype component = builder.getFieldValue(TARGET_COMPONENT)
				.map(value -> (SwComponentPrototype) value).orElse(null);

		if (component == null) {
			targetPortField.setOptions(List.of());
			return;
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(component);
		List<RPortPrototype> availableTargetPorts = componentPrototypeAdapter.getRequiredPorts();
		targetPortField.setOptions(availableTargetPorts);
	}
}
