package com.garraio.cordoba.application.core.adapter.ports;

import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.swcomponent.communication.NonqueuedSenderComSpec;
import autosar40.swcomponent.communication.ParameterProvideComSpec;
import autosar40.swcomponent.communication.ServerComSpec;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;

public class PPortAdapter extends AbstractPortAdapter<PPortPrototype> {

	public PPortAdapter(PPortPrototype port) {
		super(port);
	}

	@Override
	public PortInterface getInterface() {
		return element.getProvidedInterface();
	}

	@Override
	public void setInterface(PortInterface portInterface) {
		element.setProvidedInterface(portInterface);
	}

	@Override
	protected void clearComSpecs() {
		element.getProvidedComSpecs().clear();
	}

	@Override
	protected void setupSenderReceiverComSpecs(SenderReceiverInterface srInterface) {
		for (VariableDataPrototype dataElement : srInterface.getDataElements()) {
			System.out.print(dataElement.getShortName());
			NonqueuedSenderComSpec comSpec = AutosarElementFactory.createNonqueuedSenderComSpec(dataElement, "0");
			element.getProvidedComSpecs().add(comSpec);
		}

	}

	@Override
	protected void setupClientServerComSpecs(ClientServerInterface csInterface) {
		for (ClientServerOperation operation : csInterface.getOperations()) {
			ServerComSpec comSpec = AutosarElementFactory.createServerComSpec(operation);
			element.getProvidedComSpecs().add(comSpec);
		}
	}

	@Override
	protected void setupParameterComSpecs(ParameterInterface paramInterface) {
		for (ParameterDataPrototype parameter : paramInterface.getParameters()) {
			ParameterProvideComSpec comSpec = AutosarElementFactory.createParameterProvideComSpec(parameter, "0");
			element.getProvidedComSpecs().add(comSpec);
		}

	}

}
