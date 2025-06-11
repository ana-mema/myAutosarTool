package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.INTERFACE_REFERENCE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import java.util.List;
import java.util.stream.Collectors;

import com.garraio.cordoba.application.core.adapter.ports.AbstractPortAdapter;
import com.garraio.cordoba.application.core.adapter.ports.PPortAdapter;
import com.garraio.cordoba.application.core.adapter.ports.RPortAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.ParameterSwComponentType;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;

public class PortFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		PortPrototype port = (PortPrototype) AutosarElementRegistry.getElementById(element.getAutosarElementId());
		if (port == null) {
			return;
		}

		AbstractPortAdapter<?> portAdapter;
		if (port instanceof PPortPrototype) {
			portAdapter = new PPortAdapter((PPortPrototype) port);
		} else {
			portAdapter = new RPortAdapter((RPortPrototype) port);
		}

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, portAdapter.getShortName()).addReferenceField(INTERFACE_REFERENCE,
						getCompatibleInterfaces(portAdapter), portAdapter.getInterface())
				.refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		PortPrototype port = (PortPrototype) AutosarElementRegistry.getElementById(element.getAutosarElementId());
		if (port == null) {
			return;
		}

		AbstractPortAdapter<?> portAdapter;
		if (port instanceof PPortPrototype) {
			portAdapter = new PPortAdapter((PPortPrototype) port);
		} else {
			portAdapter = new RPortAdapter((RPortPrototype) port);
		}

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		PortInterface referencedInterface = builder.getFieldValue(INTERFACE_REFERENCE)
				.map(value -> (PortInterface) value).orElse(null);

		portAdapter.setShortName(shortName);
		portAdapter.setInterface(referencedInterface);
		portAdapter.setupCommSpecs();

	}

	private List<PortInterface> getCompatibleInterfaces(AbstractPortAdapter portAdapter) {
		SwComponentType parentComponent = portAdapter.getParentComponent();
		List<PortInterface> allInterfaces = AutosarElementRegistry.getInterfaces();

		if (parentComponent instanceof ParameterSwComponentType) {
			return allInterfaces.stream().filter(iface -> iface instanceof ParameterInterface)
					.collect(Collectors.toList());
		}

		return allInterfaces;
	}
}
