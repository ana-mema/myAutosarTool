package com.garraio.cordoba.application.core.adapter.connectors;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.swcomponent.components.AbstractProvidedPortPrototype;
import autosar40.swcomponent.components.AbstractRequiredPortPrototype;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.DelegationSwConnector;
import autosar40.swcomponent.composition.SwComponentPrototype;
import autosar40.swcomponent.composition.instancerefs.PPortInCompositionInstanceRef;
import autosar40.swcomponent.composition.instancerefs.PortInCompositionTypeInstanceRef;
import autosar40.swcomponent.composition.instancerefs.RPortInCompositionInstanceRef;

public class DelegationConnectorAdapter extends AbstractAutosarElementAdapter<DelegationSwConnector> {

	public DelegationConnectorAdapter(DelegationSwConnector connector) {
		super(connector);
	}

	public PortPrototype getOuterPort() {
		return element.getOuterPort();
	}

	public void setOuterPort(PortPrototype port) {
		element.setOuterPort(port);
	}

	public SwComponentPrototype getInnerComponent() {
		PortInCompositionTypeInstanceRef portRef = element.getInnerPort();

		if (portRef == null) {
			return null;
		}

		if (portRef instanceof PPortInCompositionInstanceRef) {
			return ((PPortInCompositionInstanceRef) portRef).getContextComponent();
		} else if (portRef instanceof RPortInCompositionInstanceRef) {
			return ((RPortInCompositionInstanceRef) portRef).getContextComponent();
		} else {
			return null;
		}

	}

	public void setInnerComponent(SwComponentPrototype component) {
		PortInCompositionTypeInstanceRef portRef = getOrCreateInnerPortReference();
		if (portRef == null) {
			return;
		}

		if (portRef instanceof PPortInCompositionInstanceRef) {
			((PPortInCompositionInstanceRef) portRef).setContextComponent(component);
		} else if (portRef instanceof RPortInCompositionInstanceRef) {
			((RPortInCompositionInstanceRef) portRef).setContextComponent(component);
		}

	}

	public PortPrototype getInnerPort() {
		PortInCompositionTypeInstanceRef portRef = element.getInnerPort();

		if (portRef == null) {
			return null;
		}

		if (portRef instanceof PPortInCompositionInstanceRef) {
			return ((PPortInCompositionInstanceRef) portRef).getTargetPPort();
		} else if (portRef instanceof RPortInCompositionInstanceRef) {
			return ((RPortInCompositionInstanceRef) portRef).getTargetRPort();
		} else {
			return null;
		}

	}

	public void setInnerPort(PortPrototype port) {
		PortInCompositionTypeInstanceRef portRef = getOrCreateInnerPortReference();

		if (portRef == null) {
			return;
		}

		if (portRef instanceof PPortInCompositionInstanceRef) {
			((PPortInCompositionInstanceRef) portRef).setTargetPPort((AbstractProvidedPortPrototype) port);
		} else if (portRef instanceof RPortInCompositionInstanceRef) {
			((RPortInCompositionInstanceRef) portRef).setTargetRPort((AbstractRequiredPortPrototype) port);
		}
	}

	public CompositionSwComponentType getParentComposition() {
		return element.getCompositionSwComponentType();
	}

	private PortInCompositionTypeInstanceRef getOrCreateInnerPortReference() {
		PortPrototype outerPort = element.getOuterPort();

		if (outerPort == null) {
			return null;
		}

		PortInCompositionTypeInstanceRef portRef = null;

		if (element.getInnerPort() == null || !isCompatiblePortReference(outerPort, element.getInnerPort())) {
			if (outerPort instanceof PPortPrototype) {
				portRef = AutosarElementFactory.createPPortInCompositionInstanceRef();
			} else if (outerPort instanceof RPortPrototype) {
				portRef = AutosarElementFactory.createRPortInCompositionInstanceRef();
			}
			element.setInnerPort(portRef);
		} else {
			portRef = element.getInnerPort();
		}

		return portRef;
	}

	private boolean isCompatiblePortReference(PortPrototype outerPort, PortInCompositionTypeInstanceRef innerPortRef) {
		if (outerPort instanceof PPortPrototype && innerPortRef instanceof PPortInCompositionInstanceRef) {
			return true;
		} else if (outerPort instanceof RPortPrototype && innerPortRef instanceof RPortInCompositionInstanceRef) {
			return true;
		} else {
			return false;
		}
	}

}
