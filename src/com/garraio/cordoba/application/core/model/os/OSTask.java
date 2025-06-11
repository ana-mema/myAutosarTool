package com.garraio.cordoba.application.core.model.os;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;

public class OSTask implements EObject {
	private String id;
	private String name;
	private LinkedHashMap<String, RTEEvent> events = new LinkedHashMap<>();

	public OSTask(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<RTEEvent> getEvents() {
		return new ArrayList<>(events.values());
	}

	public void addEvent(RTEEvent event) {
		if (event != null) {
			events.put(event.getUuid(), event);
		}
	}

	public void removeEvent(RTEEvent event) {
		if (event != null) {
			events.remove(event.getUuid());
		}
	}

	public boolean hasEvent(RTEEvent event) {
		return event != null && events.containsKey(event.getUuid());
	}

	public void moveEventTo(String eventId, int targetIndex) {
		RTEEvent event = events.remove(eventId);
		if (event == null) {
			return;
		}

		List<RTEEvent> eventList = new ArrayList<>(events.values());

		if (targetIndex < 0 || targetIndex >= eventList.size()) {
			targetIndex = eventList.size();
		}
		eventList.add(targetIndex, event);

		events.clear();
		eventList.forEach(this::addEvent);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public EList<Adapter> eAdapters() {
		return null;
	}

	@Override
	public boolean eDeliver() {
		return false;
	}

	@Override
	public void eNotify(Notification arg0) {
	}

	@Override
	public void eSetDeliver(boolean arg0) {
	}

	@Override
	public EClass eClass() {
		return null;
	}

	@Override
	public Resource eResource() {
		return null;
	}

	@Override
	public EObject eContainer() {
		return null;
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		return null;
	}

	@Override
	public EReference eContainmentFeature() {
		return null;
	}

	@Override
	public EList<EObject> eContents() {
		return null;
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		return null;
	}

	@Override
	public boolean eIsProxy() {
		return false;
	}

	@Override
	public EList<EObject> eCrossReferences() {
		return null;
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		return null;
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return null;
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue) {
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature) {
		return false;
	}

	@Override
	public void eUnset(EStructuralFeature feature) {
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return null;
	}

}