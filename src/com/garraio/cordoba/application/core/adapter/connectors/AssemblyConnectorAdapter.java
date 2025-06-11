package com.garraio.cordoba.application.core.adapter.connectors;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.swcomponent.components.AbstractProvidedPortPrototype;
import autosar40.swcomponent.components.AbstractRequiredPortPrototype;
import autosar40.swcomponent.composition.AssemblySwConnector;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.SwComponentPrototype;
import autosar40.swcomponent.composition.instancerefs.PPortInCompositionInstanceRef;
import autosar40.swcomponent.composition.instancerefs.RPortInCompositionInstanceRef;

public class AssemblyConnectorAdapter extends AbstractAutosarElementAdapter<AssemblySwConnector> {

	public AssemblyConnectorAdapter(AssemblySwConnector connector) {
		super(connector);
	}

	public SwComponentPrototype getSourceComponent() {
		PPortInCompositionInstanceRef provider = element.getProvider();
		if (provider == null) {
			return null;
		}

		return provider.getContextComponent();
	}

	public void setSourceComponent(SwComponentPrototype component) {
		if (element.getProvider() == null) {
			PPortInCompositionInstanceRef pPortInCompositionInstanceRef = AutosarElementFactory
					.createPPortInCompositionInstanceRef();
			element.setProvider(pPortInCompositionInstanceRef);
		}

		element.getProvider().setContextComponent(component);
	}

	public SwComponentPrototype getTargetComponent() {
		RPortInCompositionInstanceRef requester = element.getRequester();
		if (requester == null) {
			return null;
		}

		return element.getRequester().getContextComponent();
	}

	public void setTargetComponent(SwComponentPrototype component) {
		if (element.getRequester() == null) {
			RPortInCompositionInstanceRef rPortInCompositionInstanceRef = AutosarElementFactory
					.createRPortInCompositionInstanceRef();
			element.setRequester(rPortInCompositionInstanceRef);
		}
		element.getRequester().setContextComponent(component);
	}

	public AbstractProvidedPortPrototype getSourcePort() {
		PPortInCompositionInstanceRef provider = element.getProvider();
		if (provider == null) {
			return null;
		}

		return element.getProvider().getTargetPPort();
	}

	public void setSourcePort(AbstractProvidedPortPrototype port) {
		if (element.getProvider() == null) {
			PPortInCompositionInstanceRef pPortInCompositionInstanceRef = AutosarElementFactory
					.createPPortInCompositionInstanceRef();
			element.setProvider(pPortInCompositionInstanceRef);
		}

		element.getProvider().setTargetPPort(port);
	}

	public AbstractRequiredPortPrototype getTargetPort() {
		RPortInCompositionInstanceRef requester = element.getRequester();
		if (requester == null) {
			return null;
		}
		return element.getRequester().getTargetRPort();
	}

	public void setTargetPort(AbstractRequiredPortPrototype port) {
		if (element.getRequester() == null) {
			RPortInCompositionInstanceRef rPortInCompositionInstanceRef = AutosarElementFactory
					.createRPortInCompositionInstanceRef();
			element.setRequester(rPortInCompositionInstanceRef);
		}

		element.getRequester().setTargetRPort(port);
	}

	public CompositionSwComponentType getParentComposition() {
		return element.getCompositionSwComponentType();
	}

}
