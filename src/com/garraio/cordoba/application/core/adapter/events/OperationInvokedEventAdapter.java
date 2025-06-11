package com.garraio.cordoba.application.core.adapter.events;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.swcomponent.components.AtomicSwComponentType;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.instancerefs.POperationInAtomicSwcInstanceRef;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.swcomponent.swcinternalbehavior.SwcInternalBehavior;
import autosar40.swcomponent.swcinternalbehavior.rteevents.OperationInvokedEvent;

public class OperationInvokedEventAdapter extends AbstractEventAdapter<OperationInvokedEvent> {

	public OperationInvokedEventAdapter(OperationInvokedEvent event) {
		super(event);
	}

	public PPortPrototype getPort() {
		if (element.getOperation() == null) {
			return null;
		}

		return (PPortPrototype) element.getOperation().getContextPPort();
	}

	public void setPort(PPortPrototype port) {
		if (element.getOperation() == null) {
			POperationInAtomicSwcInstanceRef operationRef = AutosarElementFactory
					.createPOperationInAtomicSwcInstanceRef();
			element.setOperation(operationRef);
		}

		element.getOperation().setContextPPort(port);
	}

	public ClientServerOperation getTargetOperation() {
		if (element.getOperation() == null) {
			return null;
		}

		return element.getOperation().getTargetProvidedOperation();
	}

	public void setTargetOperation(ClientServerOperation operation) {
		if (element.getOperation() == null) {
			POperationInAtomicSwcInstanceRef operationRef = AutosarElementFactory
					.createPOperationInAtomicSwcInstanceRef();
			element.setOperation(operationRef);
		}

		element.getOperation().setTargetProvidedOperation(operation);
	}

	public List<PPortPrototype> getAvailableProvidedPorts() {
		SwcInternalBehavior behavior = getParentInternalBehavior();
		if (behavior == null) {
			return new ArrayList<>();
		}

		AtomicSwComponentType swc = behavior.getAtomicSwComponentType();
		if (swc == null) {
			return new ArrayList<>();
		}

		return swc.getPorts().stream()
				.filter(p -> p instanceof PPortPrototype
						&& ((PPortPrototype) p).getProvidedInterface() instanceof ClientServerInterface)
				.map(p -> (PPortPrototype) p).collect(Collectors.toList());
	}

	public List<ClientServerOperation> getAvailableOperations() {
		PPortPrototype port = getPort();
		if (port == null || !(port.getProvidedInterface() instanceof ClientServerInterface)) {
			return new ArrayList<>();
		}

		return ((ClientServerInterface) port.getProvidedInterface()).getOperations();
	}

	@Override
	public String getTypeName() {
		return OperationInvokedEvent.class.getSimpleName();
	}

}
