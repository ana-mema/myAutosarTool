package com.garraio.cordoba.application.core.adapter.ports;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;

public abstract class AbstractPortAdapter<T extends PortPrototype> extends AbstractAutosarElementAdapter<T> {

	protected AbstractPortAdapter(T port) {
		super(port);
	}

	public void setupCommSpecs() {
		PortInterface portInterface = getInterface();

		if (portInterface == null) {
			return;
		}

		clearComSpecs();

		if (portInterface instanceof SenderReceiverInterface) {
			setupSenderReceiverComSpecs((SenderReceiverInterface) portInterface);
		} else if (portInterface instanceof ClientServerInterface) {
			setupClientServerComSpecs((ClientServerInterface) portInterface);
		} else if (portInterface instanceof ParameterInterface) {
			setupParameterComSpecs((ParameterInterface) portInterface);
		}

	}

	public SwComponentType getParentComponent() {
		return element.getSwComponentType();
	}

	public abstract PortInterface getInterface();

	public abstract void setInterface(PortInterface portInterface);

	protected abstract void clearComSpecs();

	protected abstract void setupSenderReceiverComSpecs(SenderReceiverInterface srInterface);

	protected abstract void setupClientServerComSpecs(ClientServerInterface csInterface);

	protected abstract void setupParameterComSpecs(ParameterInterface paramInterface);
}
