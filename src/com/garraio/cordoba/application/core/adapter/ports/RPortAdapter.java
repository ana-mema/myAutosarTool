package com.garraio.cordoba.application.core.adapter.ports;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.swcomponent.communication.ClientComSpec;
import autosar40.swcomponent.communication.NonqueuedReceiverComSpec;
import autosar40.swcomponent.communication.ParameterRequireComSpec;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;

public class RPortAdapter extends AbstractPortAdapter<RPortPrototype> {

	public RPortAdapter(RPortPrototype port) {
		super(port);
	}

	@Override
	public PortInterface getInterface() {
		return element.getRequiredInterface();

	}

	@Override
	public void setInterface(PortInterface portInterface) {
		element.setRequiredInterface(portInterface);
	}

	@Override
	protected void clearComSpecs() {
		element.getRequiredComSpecs().clear();
	}

	@Override
	protected void setupSenderReceiverComSpecs(SenderReceiverInterface srInterface) {
		for (VariableDataPrototype dataElement : srInterface.getDataElements()) {
			NonqueuedReceiverComSpec comSpec = AutosarElementFactory.createNonqueuedReceiverComSpec(dataElement, "0");
			element.getRequiredComSpecs().add(comSpec);
		}
	}

	@Override
	protected void setupClientServerComSpecs(ClientServerInterface csInterface) {
		for (ClientServerOperation operation : csInterface.getOperations()) {
			ClientComSpec comSpec = AutosarElementFactory.createClientComSpec(operation);
			element.getRequiredComSpecs().add(comSpec);
		}
	}

	@Override
	protected void setupParameterComSpecs(ParameterInterface paramInterface) {
		for (ParameterDataPrototype parameter : paramInterface.getParameters()) {
			ParameterRequireComSpec comSpec = AutosarElementFactory.createParameterRequireComSpec(parameter, "0");
			element.getRequiredComSpecs().add(comSpec);
		}

	}

}
