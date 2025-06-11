package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.OS_TASK;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import com.garraio.cordoba.application.core.model.os.OSTask;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.forms.fields.ReferenceField;
import com.garraio.cordoba.application.ui.components.tables.EventTable;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;

public class EventToTaskMappingsFormDirector implements IFormDirector {
	private EventTable taskEventsTable;
	private EventTable unmappedEventsTable;
	private OSTask selectedTask;

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		initializeMockData();

		builder.clearForm().addFormTitle(element.getType().getDisplayName());

		selectedTask = null;

		Composite formContainer = builder.getFormContainer();

		createAllEventsSection(formContainer);

		createTaskEventsSection(formContainer);

		builder.refreshLayout();
	}

	private void initializeMockData() {
		if (!AutosarElementRegistry.getOsTasks().isEmpty()) {
			return;
		}

		OSTask task10ms = new OSTask("Task_10ms");
		OSTask task100ms = new OSTask("Task_100ms");
		OSTask task1000ms = new OSTask("Task_1000ms");
		OSTask taskEvent = new OSTask("Task_EventDriven");

		AutosarElementRegistry.register(task10ms);
		AutosarElementRegistry.register(task100ms);
		AutosarElementRegistry.register(task1000ms);
		AutosarElementRegistry.register(taskEvent);

	}

	private void createAllEventsSection(Composite parent) {
		unmappedEventsTable = new EventTable(parent, "Available Events");

		unmappedEventsTable.setRows(AutosarElementRegistry.getUnmappedRTEEvents());

		unmappedEventsTable.setupDragSource();

		unmappedEventsTable.setupDropTarget((eventId, sourceTableId) -> {
			if (taskEventsTable == null || !sourceTableId.equals(taskEventsTable.getTableId())) {
				return;
			}

			RTEEvent eventToMove = (RTEEvent) AutosarElementRegistry.getElementById(eventId);

			if (eventToMove == null || selectedTask == null) {
				return;
			}

			selectedTask.removeEvent(eventToMove);
			AutosarElementRegistry.addUnmappedRTEEvent(eventToMove);
			updateTables();
		});
	}

	private void createTaskEventsSection(Composite parent) {
		taskEventsTable = new EventTable(parent, "Events in Selected Task");

		Composite contentArea = taskEventsTable.getContentArea();

		addTaskSelectionField(contentArea);

		taskEventsTable.setupDragSource();

		taskEventsTable.setupDropTarget((eventId, sourceTableId) -> {
			if (taskEventsTable != null && sourceTableId.equals(taskEventsTable.getTableId())) {
				// reordering operation within the task table
				int targetIndex = taskEventsTable.determineDropTargetIndex();
				selectedTask.moveEventTo(eventId, targetIndex);
				taskEventsTable.setRows(selectedTask.getEvents());

			} else {
				// a move from available events
				RTEEvent eventToMove = (RTEEvent) AutosarElementRegistry.getElementById(eventId);

				if (eventToMove == null || selectedTask == null) {
					return;
				}

				selectedTask.addEvent(eventToMove);
				AutosarElementRegistry.removeUnmappedRTEEvent(eventToMove);
				updateTables();
			}

		});

	}

	private void addTaskSelectionField(Composite contentArea) {
		ReferenceField taskSelectionField = new ReferenceField(OS_TASK, selectedTask,
				AutosarElementRegistry.getOsTasks());

		taskSelectionField.createControl(contentArea);

		taskSelectionField.addChangeListener(() -> {
			EObject value = taskSelectionField.getValue();
			if (value instanceof OSTask) {
				selectedTask = (OSTask) value;
				taskEventsTable.setRows(selectedTask.getEvents());
			} else {
				selectedTask = null;
				taskEventsTable.setRows(new ArrayList<>());
			}

		});
	}

	private void updateTables() {
		taskEventsTable.setRows(selectedTask == null ? new ArrayList<>() : selectedTask.getEvents());
		unmappedEventsTable.setRows(AutosarElementRegistry.getUnmappedRTEEvents());
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
	}
}
