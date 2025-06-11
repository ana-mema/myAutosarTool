package com.garraio.cordoba.application.core.adapter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.SwComponentPrototype;

public class ComponentPrototypeAdapter extends AbstractAutosarElementAdapter<SwComponentPrototype> {

	public ComponentPrototypeAdapter(SwComponentPrototype component) {
		super(component);
	}

	public SwComponentType getComponentType() {
		return element.getType();
	}

	public void setComponentType(SwComponentType type) {
		element.setType(type);
	}

	public CompositionSwComponentType getParentComposition() {
		return element.getCompositionSwComponentType();
	}

	public void setParentComposition(CompositionSwComponentType parent) {
		element.setCompositionSwComponentType(parent);
	}

	public List<PPortPrototype> getProvidedPorts() {
		if (element.getType() == null) {
			return Collections.emptyList();
		}

		List<PortPrototype> allPorts = element.getType().getPorts();

		return allPorts.stream().filter(port -> port instanceof PPortPrototype).map(port -> (PPortPrototype) port)
				.collect(Collectors.toList());
	}

	public List<RPortPrototype> getRequiredPorts() {
		if (element.getType() == null) {
			return Collections.emptyList();
		}

		List<PortPrototype> allPorts = element.getType().getPorts();

		return allPorts.stream().filter(port -> port instanceof RPortPrototype).map(port -> (RPortPrototype) port)
				.collect(Collectors.toList());
	}
}
