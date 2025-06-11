package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.garraio.cordoba.application.core.adapter.events.PeriodicEventAdapter;
import com.garraio.cordoba.application.core.model.os.OSTask;

import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;

public class ScheduleTableGenerator {

	public static void addScheduleTable(Document doc, Element containers, String counterReferencePath,
			List<OSTask> osTasks) {
		// Filter out tasks with invalid periods
		List<Integer> taskLevelPeriods = new ArrayList<>();
		List<OSTask> periodicTasks = new ArrayList<>();

		for (OSTask task : osTasks) {
			List<Integer> eventPeriods = new ArrayList<>();
			for (RTEEvent event : task.getEvents()) {
				if (event instanceof TimingEvent) {
					PeriodicEventAdapter adapter = new PeriodicEventAdapter((TimingEvent) event);
					Double period = adapter.NumericgetPeriod();
					if (period != null && period > 0) {
						eventPeriods.add(period.intValue());
					}
				}
			}
			if (!eventPeriods.isEmpty()) {
				int taskGCD = eventPeriods.stream().reduce(ScheduleTableGenerator::calculateGCD).orElse(0);
				if (taskGCD > 0) {
					taskLevelPeriods.add(taskGCD);
					periodicTasks.add(task); // Keep track of periodic tasks
				}
			}
		}

		if (taskLevelPeriods.isEmpty()) {
			throw new IllegalArgumentException("No valid periodic tasks found.");
		}

		// Calculate schedule table duration using LCM of GCDs
		int scheduleTableDuration = taskLevelPeriods.stream().reduce(ScheduleTableGenerator::calculateLCM)
				.orElseThrow(() -> new IllegalArgumentException("Failed to calculate schedule table duration."));
		periodicTasks = periodicTasks.stream().distinct().collect(Collectors.toList());

		if (periodicTasks.isEmpty()) {
			throw new IllegalArgumentException("No valid periodic tasks with period found.");
		}

		// Calculate the LCM of all valid task periods to determine the total duration
		// of the schedule table

		if (scheduleTableDuration == 0) {
			throw new IllegalArgumentException("Failed to calculate valid schedule table duration.");
		}

		// Create the schedule table container
		Element scheduleTable = doc.createElement("ECUC-CONTAINER-VALUE");
		scheduleTable.appendChild(XmlHelper.createTextElement(doc, "SHORT-NAME", "Rte_ScheduleTable"));
		scheduleTable.appendChild(XmlHelper.createDefinitionRefElement(doc, "ECUC-PARAM-CONF-CONTAINER-DEF",
				"/AUTOSAR/EcucDefs/Os/OsScheduleTable"));
		// scheduleTable.appendChild(XmlHelper.createTextElement(doc, "DEFINITION-REF",
		// "/AUTOSAR/EcucDefs/Os/OsScheduleTable"));
		containers.appendChild(scheduleTable);

		// Add Parameter Values for the Schedule Table
		Element paramValues = doc.createElement("PARAMETER-VALUES");
		paramValues.appendChild(XmlHelper.createBooleanElement(doc,
				"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableRepeating", "true"));
		paramValues.appendChild(XmlHelper.createNumericElement(doc, "ECUC-INTEGER-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableDuration",
				String.format("%.4f", scheduleTableDuration / 1000.0)));
		scheduleTable.appendChild(paramValues);

		// Add Reference Values for Counter
		Element referenceValues = doc.createElement("REFERENCE-VALUES");
		Element referenceValue = doc.createElement("ECUC-REFERENCE-VALUE");
		referenceValue.appendChild(XmlHelper.createDefinitionRefElement(doc, "ECUC-REFERENCE-DEF",
				"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableCounterRef"));
		// referenceValue.appendChild(XmlHelper.createTextElement(doc, "DEFINITION-REF",
		// "/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableCounterRef"));
		referenceValue.appendChild(
				XmlHelper.createTextElementWithDest(doc, "VALUE-REF", "ECUC-CONTAINER-VALUE", counterReferencePath));
		// referenceValue.appendChild(XmlHelper.createTextElement(doc, "VALUE-REF",
		// counterReferencePath));
		referenceValues.appendChild(referenceValue);
		scheduleTable.appendChild(referenceValues);

		// Map to track expiry points by offset and task activations at each offset
		Map<Integer, Map<String, Boolean>> expiryPointActivations = new HashMap<>();

		// Generate Expiry Points dynamically based on the task durations
		int expiryPointIndex = 1;
		for (OSTask osMappedTask : periodicTasks) {
			addExpiryPointsForTask(doc, containers, osMappedTask, expiryPointIndex, scheduleTableDuration,
					expiryPointActivations);
			expiryPointIndex++;
		}

		// Add expiry points to the XML document based on the sorted offsets
		expiryPointActivations.keySet().stream().sorted().forEach(offset -> {
			Map<String, Boolean> activatedTasks = expiryPointActivations.get(offset);
			String expiryPointName = "Rte_ExpiryPoint_" + offset;
			addExpiryPoint(doc, containers, expiryPointName, activatedTasks, offset);
		});

	}

	// Helper method to add expiry points dynamically for each task
	private static void addExpiryPointsForTask(Document doc, Element containers, OSTask osTask, int expiryPointIndex,
			int scheduleTableDuration, Map<Integer, Map<String, Boolean>> expiryPointActivations) {

		List<Integer> eventPeriods = new ArrayList<>();
		for (RTEEvent event : osTask.getEvents()) {
			if (event instanceof TimingEvent) {
				PeriodicEventAdapter periodic = new PeriodicEventAdapter((TimingEvent) event);
				Double period = periodic.NumericgetPeriod();
				if (period != null && period > 0) {
					eventPeriods.add(period.intValue());
				}
			}
		}

		if (eventPeriods.isEmpty()) {
			return;
		}

		// Compute GCD of all periods for this task
		int taskGCD = eventPeriods.stream().reduce(ScheduleTableGenerator::calculateGCD).orElse(0);
		if (taskGCD <= 0) {
			return;
		}

		List<Integer> expiryOffsets = new ArrayList<>();
		for (int offset = taskGCD; offset <= scheduleTableDuration; offset += taskGCD) {
			expiryOffsets.add(offset);
		}

		for (Integer offset : expiryOffsets) {
			expiryPointActivations.computeIfAbsent(offset, k -> new HashMap<>()).putIfAbsent(osTask.getName(), true);
		}
	}

	// Helper method to add an expiry point to the XML
	private static void addExpiryPoint(Document doc, Element containers, String expiryPointName,
			Map<String, Boolean> taskNames, int offsetValue) {
		if (taskNames.isEmpty()) {
			return;
		}

		Element expiryPointElement = doc.createElement("ECUC-CONTAINER-VALUE");
		expiryPointElement.appendChild(XmlHelper.createTextElement(doc, "SHORT-NAME", expiryPointName));
		expiryPointElement.appendChild(XmlHelper.createTextElementWithDest(doc, "DEFINITION-REF",
				"ECUC-PARAM-CONF-CONTAINER-DEF", "/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableExpiryPoint"));
		containers.appendChild(expiryPointElement);

		Element expiryPointParams = doc.createElement("PARAMETER-VALUES");
		expiryPointParams.appendChild(XmlHelper.createNumericElement(doc, "ECUC-INTEGER-PARAM-DEF",
				"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableExpiryPoint/OsScheduleTblExpPointOffset",
				String.format("%.4f", offsetValue / 1000.0)));
		expiryPointElement.appendChild(expiryPointParams);

		Element subContainers = doc.createElement("SUB-CONTAINERS");
		expiryPointElement.appendChild(subContainers);

		// Track how many times we create a TaskActivation with the same base name
		Map<String, Integer> taskActivationNameCounter = new HashMap<>();

		for (String taskName : taskNames.keySet()) {
			Element taskActivation = doc.createElement("ECUC-CONTAINER-VALUE");

			String baseName = "TaskActivation" + offsetValue;
			int count = taskActivationNameCounter.getOrDefault(baseName, 0) + 1;
			taskActivationNameCounter.put(baseName, count);

			String finalName = (count == 1) ? baseName : baseName + "_" + count;

			taskActivation.appendChild(XmlHelper.createTextElement(doc, "SHORT-NAME", finalName));
			taskActivation.appendChild(XmlHelper.createTextElementWithDest(doc, "DEFINITION-REF",
					"ECUC-PARAM-CONF-CONTAINER-DEF",
					"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableExpiryPoint/OsScheduleTableTaskActivation"));

			Element referenceValues = doc.createElement("REFERENCE-VALUES");
			Element referenceValue = doc.createElement("ECUC-REFERENCE-VALUE");
			referenceValue.appendChild(XmlHelper.createTextElementWithDest(doc, "DEFINITION-REF", "ECUC-REFERENCE-DEF",
					"/AUTOSAR/EcucDefs/Os/OsScheduleTable/OsScheduleTableExpiryPoint/OsScheduleTableTaskActivation/OsScheduleTableActivateTaskRef"));
			referenceValue.appendChild(
					XmlHelper.createTextElementWithDest(doc, "VALUE-REF", "ECUC-CONTAINER-VALUE", "/Os/" + taskName));
			referenceValues.appendChild(referenceValue);
			taskActivation.appendChild(referenceValues);

			subContainers.appendChild(taskActivation);
		}
	}

	// Helper method to calculate LCM (Least Common Multiple)
	private static int calculateLCM(int a, int b) {
		return (a * b) / calculateGCD(a, b);
	}

	// Helper method to calculate GCD (Greatest Common Divisor)
	private static int calculateGCD(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
}
