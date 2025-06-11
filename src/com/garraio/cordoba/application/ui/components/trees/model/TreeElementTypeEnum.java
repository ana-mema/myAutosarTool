package com.garraio.cordoba.application.ui.components.trees.model;

public enum TreeElementTypeEnum {
	PROJECT_ROOT("Project Root", null), 
	BASE_DATA_TYPES("Base Data Types", null),
    SOFTWARE_BASE_TYPE_SINT8("sint8", null),
    SOFTWARE_BASE_TYPE_SINT16("sint16", null),
    SOFTWARE_BASE_TYPE_SINT32("sint32", null),
    SOFTWARE_BASE_TYPE_SINT64("sint64", null),
    SOFTWARE_BASE_TYPE_UINT8("uint8", null),
    SOFTWARE_BASE_TYPE_UINT16("uint16", null),
    SOFTWARE_BASE_TYPE_UINT32("uint32", null),
    SOFTWARE_BASE_TYPE_UINT64("uint64", null),
    SOFTWARE_BASE_TYPE_FLOAT32("float32", null),
    SOFTWARE_BASE_TYPE_FLOAT64("float64", null),
    SOFTWARE_BASE_TYPE_BOOLEAN("boolean", null),
	IMPLEMENTATION_DATA_TYPES("Implementation Data Types", null),
	IMPLEMENTATION_DATA_TYPE_VALUE("Value Implementation Data Type", "Value"),
	IMPLEMENTATION_DATA_TYPE_ARRAY("Array Implementation Data Type", "Array"),
	IMPLEMENTATION_DATA_TYPE_STRUCTURE("Structure Implementation Data Type", "Struct"),
	IMPLEMENTATION_DATA_TYPE_STRUCTURE_ELEMENT("Structure Element", "Element"), 
	INTERFACES("Interfaces", null),
	SENDER_RECEIVER_INTERFACE("Sender Receiver Interface", "SR"),
	SENDER_RECEIVER_DATA_ELEMENT("Sender Receiver Data Element", "DataElement"),
	CLIENT_SERVER_INTERFACE("Client Server Interface", "CS"),
	CLIENT_SERVER_OPERATION("Client Server Operation", "Operation"),
	CLIENT_SERVER_POSSIBLE_ERROR("Client Server Possible Error", "PossibleError"),
	CLIENT_SERVER_OPERATION_ARGUMENT("Operation Argument", "Argument"),
	CLIENT_SERVER_OPERATION_ERROR_REFERENCE("Operation Error Reference", "ErrorReference"),
	PARAMETER_INTERFACE("Parameter Interface", "PR"),
	PARAMETER_INTERFACE_PROTOTYPE("Parameter Interface Prototype", "Prototype"),
	SOFTWARE_COMPONENTS("Software Components", null), 
	APPLICATION_SOFTWARE_COMPONENT("Application Software Component", "Application_SWC"),
	SERVICE_SOFTWARE_COMPONENT("Service Software Component", "Service_SWC"),
	PARAMETER_SOFTWARE_COMPONENT("Parameter Software Component", "Parameter_SWC"),
	COMPLEX_SOFTWARE_COMPONENT("Complex Driver Software Component", "Complex_SWC"),
	PPORT("Provider Port", "ProviderPort"),
	RPORT("Require Port", "RequirePort"),
	INTERNAL_BEHAVIOR("Internal Behavior", "Internal_Behavior"),
	INIT_EVENT("Init Event", "Init_Event"),
    PERIODIC_EVENT("Periodic Event", "Periodic_Event"),
	OPERATION_INVOKED_EVENT("Operation Invoked Event", "Operation_Invoked_Event"),
	RUNNABLE_ENTITY("Runnable Entity", "Runnable"),
	DATA_READ_ACCESS("Data Read Access", "ReadAccess"), // TODO: add other types of access
	DATA_WRITE_ACCESS("Data Write Access", "WriteAccess"),
	SOFTWARE_COMPOSITIONS("Software Compositions", null),
	COMPOSITION_SOFTWARE_COMPONENT("Composition Software Component", "Composition_Component"),
	COMPONENT_PROTOTYPE("Component Prototype", "Component_Prototype"),
	ASSEMBLY_SOFTWARE_CONNECTOR("Assembly Software Connector", "Assembly_Connector"),
	DELEGATION_SOFTWARE_CONNECTOR("Delegation Software Connector", "Delegation_Connector"),
	EVENT_TO_TASK_MAPPINGS("Rte Event to Task Mappings", null);



	private final String displayName;
	private final String prefixName;

	TreeElementTypeEnum(String displayName, String prefixName) {
		this.displayName = displayName;
		this.prefixName = prefixName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public static TreeElementTypeEnum fromDisplayName(String displayName) {
		for (TreeElementTypeEnum type : values()) {
			if (type.getDisplayName().equals(displayName)) {
				return type;
			}
		}
		return null;
	}

}
