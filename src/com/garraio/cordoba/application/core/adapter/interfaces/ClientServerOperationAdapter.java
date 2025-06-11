package com.garraio.cordoba.application.core.adapter.interfaces;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;

public class ClientServerOperationAdapter extends AbstractAutosarElementAdapter<ClientServerOperation> {
	public ClientServerOperationAdapter(ClientServerOperation operation) {
		super(operation);
	}

	public ClientServerInterface getParentInterface() {
		return element.getClientServerInterface();
	}

	public ApplicationError getPossibleErrorReference() {
		if (element.getPossibleErrors().isEmpty()) {
			return null;
		}

		return element.getPossibleErrors().get(0);
	}

	public void setPossibleErrorReference(ApplicationError error) {
		element.getPossibleErrors().clear();
		element.getPossibleErrors().add(error);
	}

}
