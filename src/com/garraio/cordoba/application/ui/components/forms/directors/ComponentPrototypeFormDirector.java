package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.COMPONENT_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.garraio.cordoba.application.core.adapter.ComponentPrototypeAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.SwComponentPrototype;

public class ComponentPrototypeFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		SwComponentPrototype componentPrototype = (SwComponentPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (componentPrototype == null) {
			return;
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(componentPrototype);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, componentPrototypeAdapter.getShortName())
				.addReferenceField(COMPONENT_TYPE, getCompatibleComponentTypes(componentPrototypeAdapter),
						componentPrototypeAdapter.getComponentType())
				.refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		SwComponentPrototype componentPrototype = (SwComponentPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (componentPrototype == null) {
			return;
		}

		ComponentPrototypeAdapter componentPrototypeAdapter = new ComponentPrototypeAdapter(componentPrototype);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		SwComponentType referencedComponentType = builder.getFieldValue(COMPONENT_TYPE)
				.map(value -> (SwComponentType) value).orElse(null);

		componentPrototypeAdapter.setShortName(shortName);
		componentPrototypeAdapter.setComponentType(referencedComponentType);
	}

	public List<SwComponentType> getCompatibleComponentTypes(ComponentPrototypeAdapter componentPrototypeAdapter) {
		List<SwComponentType> atomicComponents = AutosarElementRegistry.getAtomicSwComponents();
		List<CompositionSwComponentType> compositionComponents = AutosarElementRegistry.getCompositionSwComponents();

		CompositionSwComponentType parentComposition = componentPrototypeAdapter.getParentComposition();
		Stream<CompositionSwComponentType> filteredCompositionStream = compositionComponents.stream()
				.filter(comp -> !Objects.equals(comp, parentComposition));

		// Concatenate the stream of atomic components with the filtered stream of
		// compositions
		List<SwComponentType> compatibleTypes = Stream.concat(atomicComponents.stream(), filteredCompositionStream)
				.collect(Collectors.toList());

		return compatibleTypes;
	}

}
