package com.garraio.cordoba.application.ui.components.forms;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.garraio.cordoba.application.ui.components.forms.directors.ArrayImplementationDataTypeFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.AssemblyConnectorFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ClientServerOperationArgumentFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ClientServerOperationErrorReferenceFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ClientServerPossibleErrorFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.CommonFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ComponentPrototypeFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.DefaultFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.DelegationConnectorFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.EventToTaskMappingsFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.IFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.InitEventFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.OperationInvokedEventFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ParameterPrototypeFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.PeriodicEventFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.PortFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.RunnableEntityFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.SenderReceiverDataElementFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.StructElementFormDirector;
import com.garraio.cordoba.application.ui.components.forms.directors.ValueImplementationDataTypeFormDirector;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;
import com.garraio.cordoba.application.ui.parts.PropertiesPart;

public class FormManager {
	private final FormBuilder builder;
	private final Map<TreeElementTypeEnum, IFormDirector> directors;
	private static final IFormDirector DEFAULT_DIRECTOR = new DefaultFormDirector();
	private TreeElement currentElement;
	private static FormManager managerInstance;
	private final PropertiesPart propertiesPart;

	public FormManager(Composite parent, PropertiesPart propertiesPart) {
		this.builder = new FormBuilder(parent, this);
		this.directors = new HashMap<>();
		this.propertiesPart = propertiesPart;
		this.managerInstance = this;
		registerFormDirectors();
	}

	private void registerFormDirectors() {
		CommonFormDirector commonDirector = new CommonFormDirector();
		PortFormDirector portDirector = new PortFormDirector();

		directors.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_VALUE,
				new ValueImplementationDataTypeFormDirector());
		directors.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_ARRAY,
				new ArrayImplementationDataTypeFormDirector());
		directors.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_STRUCTURE, commonDirector);
		directors.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_STRUCTURE_ELEMENT, new StructElementFormDirector());
		directors.put(TreeElementTypeEnum.SENDER_RECEIVER_INTERFACE, commonDirector);
		directors.put(TreeElementTypeEnum.SENDER_RECEIVER_DATA_ELEMENT, new SenderReceiverDataElementFormDirector());
		directors.put(TreeElementTypeEnum.CLIENT_SERVER_INTERFACE, commonDirector);
		directors.put(TreeElementTypeEnum.CLIENT_SERVER_OPERATION, commonDirector);
		directors.put(TreeElementTypeEnum.CLIENT_SERVER_OPERATION_ARGUMENT,
				new ClientServerOperationArgumentFormDirector());
		directors.put(TreeElementTypeEnum.CLIENT_SERVER_OPERATION_ERROR_REFERENCE,
				new ClientServerOperationErrorReferenceFormDirector());
		directors.put(TreeElementTypeEnum.CLIENT_SERVER_POSSIBLE_ERROR, new ClientServerPossibleErrorFormDirector());
		directors.put(TreeElementTypeEnum.PARAMETER_INTERFACE, commonDirector);
		directors.put(TreeElementTypeEnum.PARAMETER_INTERFACE_PROTOTYPE, new ParameterPrototypeFormDirector());
		directors.put(TreeElementTypeEnum.APPLICATION_SOFTWARE_COMPONENT, commonDirector);
		directors.put(TreeElementTypeEnum.SERVICE_SOFTWARE_COMPONENT, commonDirector);
		directors.put(TreeElementTypeEnum.PARAMETER_SOFTWARE_COMPONENT, commonDirector);
		directors.put(TreeElementTypeEnum.COMPLEX_SOFTWARE_COMPONENT, commonDirector);
		directors.put(TreeElementTypeEnum.PPORT, portDirector);
		directors.put(TreeElementTypeEnum.RPORT, portDirector);
		directors.put(TreeElementTypeEnum.INTERNAL_BEHAVIOR, commonDirector);
		directors.put(TreeElementTypeEnum.RUNNABLE_ENTITY, new RunnableEntityFormDirector());
		directors.put(TreeElementTypeEnum.INIT_EVENT, new InitEventFormDirector());
		directors.put(TreeElementTypeEnum.PERIODIC_EVENT, new PeriodicEventFormDirector());
		directors.put(TreeElementTypeEnum.OPERATION_INVOKED_EVENT, new OperationInvokedEventFormDirector());
		directors.put(TreeElementTypeEnum.COMPOSITION_SOFTWARE_COMPONENT, commonDirector);
		directors.put(TreeElementTypeEnum.COMPONENT_PROTOTYPE, new ComponentPrototypeFormDirector());
		directors.put(TreeElementTypeEnum.ASSEMBLY_SOFTWARE_CONNECTOR, new AssemblyConnectorFormDirector());
		directors.put(TreeElementTypeEnum.DELEGATION_SOFTWARE_CONNECTOR, new DelegationConnectorFormDirector());
		directors.put(TreeElementTypeEnum.EVENT_TO_TASK_MAPPINGS, new EventToTaskMappingsFormDirector());
	}

	private IFormDirector getFormDirector(TreeElement selectedElement) {
		TreeElementTypeEnum type = (selectedElement == null) ? null : selectedElement.getType();
		return directors.getOrDefault(type, DEFAULT_DIRECTOR);

	}

	public void buildForm(TreeElement selectedElement) {
		propertiesPart.resetDirty();
		currentElement = selectedElement;
		IFormDirector director = getFormDirector(selectedElement);
		director.buildForm(builder, selectedElement);
	}

	public void markDirtyForm() {
		propertiesPart.markDirty();
	}

	public boolean isDirtyForm() {
		return propertiesPart.isDirty();
	}

	public void saveForm() {
		if (currentElement == null) {
			return;
		}

		IFormDirector director = getFormDirector(currentElement);
		director.saveForm(builder, currentElement);
		propertiesPart.resetDirty();
	}

	public static FormManager getInstance() {
		return managerInstance;
	}
}
