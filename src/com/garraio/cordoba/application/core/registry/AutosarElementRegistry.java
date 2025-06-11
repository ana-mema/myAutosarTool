package com.garraio.cordoba.application.core.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.garraio.cordoba.application.core.model.os.OSTask;

import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable;
import autosar40.swcomponent.components.SwComponentType;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.portinterface.PortInterface;
import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;

public class AutosarElementRegistry {
	private static final Map<String, EObject> elementsById = new HashMap<>();

	private static final List<SwBaseType> baseTypes = new ArrayList<>();
	private static final List<ImplementationDataType> implementationDataTypes = new ArrayList<>();
	private static final List<PortInterface> interfaces = new ArrayList<>();
	private static final List<SwComponentType> atomicSwComponents = new ArrayList<>();
	private static final List<CompositionSwComponentType> compositionSwComponents = new ArrayList<>();
	private static final Map<String, RTEEvent> unmappedRTEEvents = new HashMap<>();

	private static final List<OSTask> osTasks = new ArrayList<>();

	public static List<SwBaseType> getBaseTypes() {
		return Collections.unmodifiableList(baseTypes);
	}

	public static List<ImplementationDataType> getImplementationDataTypes() {
		return Collections.unmodifiableList(implementationDataTypes);
	}

	public static List<PortInterface> getInterfaces() {
		return Collections.unmodifiableList(interfaces);
	}

	public static List<SwComponentType> getAtomicSwComponents() {
		return Collections.unmodifiableList(atomicSwComponents);
	}

	public static List<CompositionSwComponentType> getCompositionSwComponents() {
		return Collections.unmodifiableList(compositionSwComponents);
	}

	public static List<RTEEvent> getUnmappedRTEEvents() {
		return new ArrayList<>(unmappedRTEEvents.values());
	}

	public static List<OSTask> getOsTasks() {
		return Collections.unmodifiableList(osTasks);
	}

	public static EObject getElementById(String id) {
		return elementsById.get(id);
	}

	public static void register(EObject element) {
		if (element == null) {
			return;
		}

		if (element instanceof Identifiable) {
			String id = ((Identifiable) element).getUuid();
			elementsById.put(id, element);
		}

		if (element instanceof SwBaseType) {
			baseTypes.add((SwBaseType) element);

		} else if (element instanceof ImplementationDataType) {
			implementationDataTypes.add((ImplementationDataType) element);

		} else if (element instanceof PortInterface) {
			interfaces.add((PortInterface) element);

		} else if (element instanceof CompositionSwComponentType) {
			compositionSwComponents.add((CompositionSwComponentType) element);

		} else if (element instanceof SwComponentType) {
			atomicSwComponents.add((SwComponentType) element);

		} else if (element instanceof RTEEvent) {
			unmappedRTEEvents.put(((RTEEvent) element).getUuid(), (RTEEvent) element);

		} else if (element instanceof OSTask) {
			osTasks.add((OSTask) element);
		}

	}

	public static void unregister(EObject element) {
		if (element == null) {
			return;
		}

		if (element instanceof Identifiable) {
			String id = ((Identifiable) element).getUuid();
			elementsById.remove(id);
		}

		if (element instanceof SwBaseType) {
			baseTypes.remove(element);

		} else if (element instanceof ImplementationDataType) {
			implementationDataTypes.remove(element);

		} else if (element instanceof PortInterface) {
			interfaces.remove(element);

		} else if (element instanceof CompositionSwComponentType) {
			compositionSwComponents.remove(element);

		} else if (element instanceof SwComponentType) {
			atomicSwComponents.remove(element);

		} else if (element instanceof RTEEvent) {
			unmappedRTEEvents.remove(((RTEEvent) element).getUuid());

		} else if (element instanceof OSTask) {
			osTasks.remove(element);
		}

	}

	public static void addUnmappedRTEEvent(RTEEvent event) {
		if (event != null) {
			unmappedRTEEvents.put(event.getUuid(), event);
		}
	}

	public static void removeUnmappedRTEEvent(RTEEvent event) {
		if (event != null) {
			unmappedRTEEvents.remove(event.getUuid());
		}
	}

	public static void clear() {
		elementsById.clear();
		baseTypes.clear();
		implementationDataTypes.clear();
		interfaces.clear();
		atomicSwComponents.clear();
		compositionSwComponents.clear();
		unmappedRTEEvents.clear();
		osTasks.clear();
	}

}
