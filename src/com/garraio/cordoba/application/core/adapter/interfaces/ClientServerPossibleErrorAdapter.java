package com.garraio.cordoba.application.core.adapter.interfaces;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.swcomponent.portinterface.ApplicationError;

public class ClientServerPossibleErrorAdapter extends AbstractAutosarElementAdapter<ApplicationError> {

	public ClientServerPossibleErrorAdapter(ApplicationError error) {
		super(error);
	}

	public String getErrorCode() {
		return String.valueOf(element.getErrorCode());
	}

	public boolean setErrorCode(String errorCode) {
		try {
			element.setErrorCode(Integer.parseInt(errorCode));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
