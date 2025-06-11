package com.garraio.cordoba.application.core.factory;

import autosar40.commonstructure.basetypes.BaseTypeDirectDefinition;
import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.constants.NumericalValueSpecification;
import autosar40.commonstructure.datadefproperties.SwDataDefProps;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataTypeElement;
import autosar40.genericstructure.varianthandling.attributevaluevariationpoints.NumericalValueVariationPoint;
import autosar40.genericstructure.varianthandling.attributevaluevariationpoints.PositiveIntegerValueVariationPoint;
import autosar40.swcomponent.communication.ClientComSpec;
import autosar40.swcomponent.communication.NonqueuedReceiverComSpec;
import autosar40.swcomponent.communication.NonqueuedSenderComSpec;
import autosar40.swcomponent.communication.ParameterProvideComSpec;
import autosar40.swcomponent.communication.ParameterRequireComSpec;
import autosar40.swcomponent.communication.ServerComSpec;
import autosar40.swcomponent.components.ApplicationSwComponentType;
import autosar40.swcomponent.components.ComplexDeviceDriverSwComponentType;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.ParameterSwComponentType;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.components.ServiceSwComponentType;
import autosar40.swcomponent.components.instancerefs.POperationInAtomicSwcInstanceRef;
import autosar40.swcomponent.composition.AssemblySwConnector;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.composition.DelegationSwConnector;
import autosar40.swcomponent.composition.SwComponentPrototype;
import autosar40.swcomponent.composition.instancerefs.PPortInCompositionInstanceRef;
import autosar40.swcomponent.composition.instancerefs.RPortInCompositionInstanceRef;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.swcomponent.portinterface.ModeSwitchInterface;
import autosar40.swcomponent.portinterface.ParameterInterface;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.portinterface.SenderReceiverInterface;
import autosar40.swcomponent.swcinternalbehavior.RunnableEntity;
import autosar40.swcomponent.swcinternalbehavior.SwcInternalBehavior;
import autosar40.swcomponent.swcinternalbehavior.rteevents.InitEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.OperationInvokedEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;
import autosar40.util.Autosar40Factory;

public class AutosarElementFactory {

	public static SwBaseType createBaseType(String shortName, Long size) {
		BaseTypeDirectDefinition gBaseTypeDefinition = Autosar40Factory.eINSTANCE.createBaseTypeDirectDefinition();
		gBaseTypeDefinition.setBaseTypeSize(size);

		SwBaseType baseType = Autosar40Factory.eINSTANCE.createSwBaseType();
		baseType.setShortName(shortName);
		baseType.gSetBaseTypeDefinitionType(gBaseTypeDefinition);

		return baseType;
	}

	public static ImplementationDataType createImplementationType(String shortName, String category,
			SwBaseType baseType) {
		ImplementationDataType implementationType = Autosar40Factory.eINSTANCE.createImplementationDataType();
		implementationType.setShortName(shortName);
		implementationType.setCategory(category);

		if (category.equals("VALUE")) {
			SwDataDefProps swDataDefProps = createSwDataDefProps(baseType);
			implementationType.setSwDataDefProps(swDataDefProps);
		}

		if (category.equals("ARRAY")) {
			ImplementationDataTypeElement implementationTypeElement = createImplementationTypeElement(null, null, null);
			implementationType.getSubElements().add(implementationTypeElement);
		}

		return implementationType;
	}

	public static ImplementationDataTypeElement createImplementationTypeElement(String shortName) { //
		ImplementationDataTypeElement implementationTypeElement = Autosar40Factory.eINSTANCE
				.createImplementationDataTypeElement();
		implementationTypeElement.setShortName(shortName);
		return implementationTypeElement;
	}

	public static ImplementationDataTypeElement createImplementationTypeElement(String shortName, String category,
			SwBaseType baseType) {
		ImplementationDataTypeElement implementationTypeElement = Autosar40Factory.eINSTANCE
				.createImplementationDataTypeElement();
		implementationTypeElement.setShortName(shortName);
		implementationTypeElement.setCategory(category);

		SwDataDefProps swDataDefProps = createSwDataDefProps(baseType);
		implementationTypeElement.setSwDataDefProps(swDataDefProps);

		return implementationTypeElement;
	}

	public static SwDataDefPropsConditional createSwDataDefPropsConditional() { //
		SwDataDefPropsConditional swDataDefPropsConditional = Autosar40Factory.eINSTANCE
				.createSwDataDefPropsConditional();
		return swDataDefPropsConditional;
	}

	public static SwDataDefProps createSwDataDefProps() { //
		SwDataDefProps swDataDefProps = Autosar40Factory.eINSTANCE.createSwDataDefProps();
		return swDataDefProps;
	}

	public static SwDataDefProps createSwDataDefProps(SwBaseType baseType) {
		SwDataDefPropsConditional swDataDefPropsConditional = Autosar40Factory.eINSTANCE
				.createSwDataDefPropsConditional();
		swDataDefPropsConditional.setBaseType(baseType);

		SwDataDefProps swDataDefProps = Autosar40Factory.eINSTANCE.createSwDataDefProps();
		swDataDefProps.getSwDataDefPropsVariants().add(swDataDefPropsConditional);

		return swDataDefProps;
	}

	public static PositiveIntegerValueVariationPoint createPositiveIntegerValueVariationPoint(String value) {
		PositiveIntegerValueVariationPoint positiveIntegerValueVariationPoint = Autosar40Factory.eINSTANCE
				.createPositiveIntegerValueVariationPoint();
		positiveIntegerValueVariationPoint.setMixedText(value);
		return positiveIntegerValueVariationPoint;
	}

	public static <T extends PortInterface> PortInterface createPortInterface(final Class<T> interfaceType,
			String shortName) {
		switch (interfaceType.getSimpleName()) {
		case "SenderReceiverInterface":
			return createSenderReceiverInterface(shortName);
		case "ClientServerInterface":
			return createClientServerInterface(shortName);
		case "ParameterInterface":
			return createParameterInterface(shortName);
		default:
			return null;
		}

	}

	public static SenderReceiverInterface createSenderReceiverInterface(String shortName) {
		SenderReceiverInterface sr = Autosar40Factory.eINSTANCE.createSenderReceiverInterface();
		sr.setShortName(shortName);
		return sr;
	}

	public static ClientServerInterface createClientServerInterface(String shortName) {
		ClientServerInterface cs = Autosar40Factory.eINSTANCE.createClientServerInterface();
		cs.setShortName(shortName);
		return cs;
	}

	public static ParameterInterface createParameterInterface(String shortName) {
		ParameterInterface pi = Autosar40Factory.eINSTANCE.createParameterInterface();
		pi.setShortName(shortName);
		return pi;
	}

	public static ModeSwitchInterface createModeSwitchInterface(String shortName) {
		ModeSwitchInterface ms = Autosar40Factory.eINSTANCE.createModeSwitchInterface();
		ms.setShortName(shortName);
		return ms;
	}

	public static ApplicationSwComponentType createApplicationSwComponentType(String shortName) {
		ApplicationSwComponentType app = Autosar40Factory.eINSTANCE.createApplicationSwComponentType();
		app.setShortName(shortName);
		return app;
	}

	public static ServiceSwComponentType createServiceSwComponentType(String shortName) {
		ServiceSwComponentType service = Autosar40Factory.eINSTANCE.createServiceSwComponentType();
		service.setShortName(shortName);
		return service;
	}

	public static ParameterSwComponentType createParameterSwComponentType(String shortName) {
		ParameterSwComponentType parameter = Autosar40Factory.eINSTANCE.createParameterSwComponentType();
		parameter.setShortName(shortName);
		return parameter;
	}

	public static ComplexDeviceDriverSwComponentType createComplexDeviceDriverSwComponentType(String shortName) {
		ComplexDeviceDriverSwComponentType cdd = Autosar40Factory.eINSTANCE.createComplexDeviceDriverSwComponentType();
		cdd.setShortName(shortName);
		return cdd;
	}

	public static CompositionSwComponentType createCompositionSwComponentType(String shortName) {
		CompositionSwComponentType composition = Autosar40Factory.eINSTANCE.createCompositionSwComponentType();
		composition.setShortName(shortName);
		return composition;
	}

	public static SwComponentPrototype createSwComponentPrototype(String shortName) {
		SwComponentPrototype prototype = Autosar40Factory.eINSTANCE.createSwComponentPrototype();
		prototype.setShortName(shortName);
		return prototype;
	}

	public static AssemblySwConnector createAssemblyConnector(String shortName) {
		AssemblySwConnector assemblySwConnector = Autosar40Factory.eINSTANCE.createAssemblySwConnector();
		assemblySwConnector.setShortName(shortName);
		return assemblySwConnector;
	}

	public static DelegationSwConnector createDelegationConnector(String shortName) {
		DelegationSwConnector delegationSwConnector = Autosar40Factory.eINSTANCE.createDelegationSwConnector();
		delegationSwConnector.setShortName(shortName);
		return delegationSwConnector;
	}

	public static VariableDataPrototype createSenderReceiverInterfaceDataElement(String shortName,
			ImplementationDataType dataType) {
		VariableDataPrototype dataElement = Autosar40Factory.eINSTANCE.createVariableDataPrototype();
		dataElement.setShortName(shortName);
		dataElement.setType(dataType);
		return dataElement;
	}

	public static ClientServerOperation createClientServerOperation(String shortName) {
		ClientServerOperation op = Autosar40Factory.eINSTANCE.createClientServerOperation();
		op.setShortName(shortName);
		return op;
	}

	public static ApplicationError createClientServerPossibleError(String shortName) {
		ApplicationError error = Autosar40Factory.eINSTANCE.createApplicationError();
		error.setShortName(shortName);
		return error;
	}

	public static ArgumentDataPrototype createClientServerOperationArgument(String shortName) {
		ArgumentDataPrototype arg = Autosar40Factory.eINSTANCE.createArgumentDataPrototype();
		arg.setShortName(shortName);
		return arg;
	}

//	public static ArgumentDataPrototype createClientServerOperationArgument(String shortName, ClientServerOperation op,
//			ImplementationDataType dataType, ArgumentDirectionEnum direction) {
//		ArgumentDataPrototype arg = Autosar40Factory.eINSTANCE.createArgumentDataPrototype();
//		arg.setClientServerOperation(op);
//		arg.setDirection(direction);
//		arg.setType(dataType);
//		return arg;
//	}

//	public static EList<ApplicationError> createClientServerOperationErrorRef(ClientServerOperation op,
//			ApplicationError errorRef) {
//		op.getPossibleErrors().add(errorRef);
//		return op.getPossibleErrors();
//	}

	public static ParameterDataPrototype createParameterInterfacePrototype(String shortName) {
		ParameterDataPrototype prototype = Autosar40Factory.eINSTANCE.createParameterDataPrototype();
		prototype.setShortName(shortName);
		return prototype;
	}

	public static PPortPrototype createProviderPort(String shortName) {
		PPortPrototype portPrototype = Autosar40Factory.eINSTANCE.createPPortPrototype();
		portPrototype.setShortName(shortName);
		return portPrototype;
	}

	public static RPortPrototype createRequirePort(String shortName) {
		RPortPrototype portPrototype = Autosar40Factory.eINSTANCE.createRPortPrototype();
		portPrototype.setShortName(shortName);
		return portPrototype;
	}

	public static SwcInternalBehavior createSwcInternalBehavior(String shortName) {
		SwcInternalBehavior internalBehavior = Autosar40Factory.eINSTANCE.createSwcInternalBehavior();
		internalBehavior.setShortName(shortName);
		return internalBehavior;
	}

	public static InitEvent createInitEvent(String shortName) {
		InitEvent initEvent = Autosar40Factory.eINSTANCE.createInitEvent();
		initEvent.setShortName(shortName);
		return initEvent;
	}

	public static TimingEvent createPeriodicEvent(String shortName) {
		TimingEvent periodicEvent = Autosar40Factory.eINSTANCE.createTimingEvent();
		periodicEvent.setShortName(shortName);
		return periodicEvent;
	}

	public static OperationInvokedEvent createOperationInvokedEvent(String shortName) {
		OperationInvokedEvent opEvent = Autosar40Factory.eINSTANCE.createOperationInvokedEvent();
		opEvent.setShortName(shortName);
		return opEvent;
	}

	public static RunnableEntity createRunnable(String shortName) {
		RunnableEntity runnable = Autosar40Factory.eINSTANCE.createRunnableEntity();
		runnable.setShortName(shortName);
		return runnable;
	}

	public static POperationInAtomicSwcInstanceRef createPOperationInAtomicSwcInstanceRef() {
		POperationInAtomicSwcInstanceRef operationInAtomicSwcInstanceRef = Autosar40Factory.eINSTANCE
				.createPOperationInAtomicSwcInstanceRef();
		return operationInAtomicSwcInstanceRef;
	}

	public static NumericalValueVariationPoint createNumericalValueVariationPoint(String value) {
		NumericalValueVariationPoint numericalValueVariationPoint = Autosar40Factory.eINSTANCE
				.createNumericalValueVariationPoint();
		numericalValueVariationPoint.setMixedText(value);
		return numericalValueVariationPoint;
	}

	public static NumericalValueSpecification createNumericalValueSpecification(String lable, String value) {
		NumericalValueSpecification numericalValueSpecification = Autosar40Factory.eINSTANCE
				.createNumericalValueSpecification();

		numericalValueSpecification.setShortLabel(lable);

		numericalValueSpecification.setValue(createNumericalValueVariationPoint(value));
		return numericalValueSpecification;
	}

	public static NonqueuedSenderComSpec createNonqueuedSenderComSpec(VariableDataPrototype dataElement,
			String initValue) {
		NonqueuedSenderComSpec nonqueuedSenderComSpec = Autosar40Factory.eINSTANCE.createNonqueuedSenderComSpec();
		nonqueuedSenderComSpec.setDataElement(dataElement);
		nonqueuedSenderComSpec.setInitValue(createNumericalValueSpecification(dataElement.getShortName(), initValue));
		return nonqueuedSenderComSpec;
	}

	public static NonqueuedReceiverComSpec createNonqueuedReceiverComSpec(VariableDataPrototype dataElement,
			String initValue) {
		NonqueuedReceiverComSpec nonqueuedReceiverComSpec = Autosar40Factory.eINSTANCE.createNonqueuedReceiverComSpec();
		nonqueuedReceiverComSpec.setDataElement(dataElement);
		nonqueuedReceiverComSpec.setInitValue(createNumericalValueSpecification(dataElement.getShortName(), initValue));
		return nonqueuedReceiverComSpec;
	}

	public static ServerComSpec createServerComSpec(ClientServerOperation operation) {
		ServerComSpec comSpec = Autosar40Factory.eINSTANCE.createServerComSpec();
		comSpec.setOperation(operation);
		return comSpec;
	}

	public static ClientComSpec createClientComSpec(ClientServerOperation operation) {
		ClientComSpec comSpec = Autosar40Factory.eINSTANCE.createClientComSpec();
		comSpec.setOperation(operation);
		return comSpec;
	}

	public static ParameterProvideComSpec createParameterProvideComSpec(ParameterDataPrototype parameter,
			String initialValue) {
		ParameterProvideComSpec comSpec = Autosar40Factory.eINSTANCE.createParameterProvideComSpec();

		comSpec.setParameter(parameter);
		comSpec.setInitValue(createNumericalValueSpecification(parameter.getShortName(), initialValue));

		return comSpec;

	}

	public static ParameterRequireComSpec createParameterRequireComSpec(ParameterDataPrototype parameter,
			String initialValue) {
		ParameterRequireComSpec comSpec = Autosar40Factory.eINSTANCE.createParameterRequireComSpec();

		comSpec.setParameter(parameter);
		comSpec.setInitValue(createNumericalValueSpecification(parameter.getShortName(), initialValue));

		return comSpec;
	}

	public static PPortInCompositionInstanceRef createPPortInCompositionInstanceRef() {
		PPortInCompositionInstanceRef pPortInCompositionInstanceRef = Autosar40Factory.eINSTANCE
				.createPPortInCompositionInstanceRef();
		return pPortInCompositionInstanceRef;
	}

	public static RPortInCompositionInstanceRef createRPortInCompositionInstanceRef() {
		RPortInCompositionInstanceRef rPortInCompositionInstanceRef = Autosar40Factory.eINSTANCE
				.createRPortInCompositionInstanceRef();
		return rPortInCompositionInstanceRef;
	}

}
