package com.garraio.cordoba.application.core.generator.xml.eventmapping;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class OsTaskParser {

	public static class ParsedTask {
		String taskName;
		int priority;
		int activation;
		String scheduleType;
		boolean autostart;

		@Override
		public String toString() {
			return "ParsedTask{" + "taskName='" + taskName + '\'' + ", priority=" + priority + ", activation="
					+ activation + ", scheduleType='" + scheduleType + '\'' + ", autostart=" + autostart + '}';
		}
	}

	public static List<ParsedTask> parseTasksFromFile(String filePath) throws Exception {
		// Step 1: Parse the XML file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(filePath));
		doc.getDocumentElement().normalize();

		// Step 2: Find all OsTask containers
		Map<String, ParsedTask> taskMap = new HashMap<>();
		NodeList containerNodes = doc.getElementsByTagName("ECUC-PARAM-CONF-CONTAINER");

		for (int i = 0; i < containerNodes.getLength(); i++) {
			Element container = (Element) containerNodes.item(i);
			String shortName = getChildText(container, "SHORT-NAME");
			if (shortName != null && shortName.startsWith("OsTask_")) {
				ParsedTask task = new ParsedTask();
				task.taskName = shortName;
				task.priority = parseIntegerParameter(container, "OsTaskPriority");
				task.activation = parseIntegerParameter(container, "OsTaskActivation");
				task.scheduleType = parseEnumParameter(container, "OsTaskSchedule");
				task.autostart = false; // initially assume not autostart
				taskMap.put(shortName, task);
			}
		}

		// Step 3: Find Alarms that autostart and activate tasks
		NodeList alarmContainers = doc.getElementsByTagName("ECUC-PARAM-CONF-CONTAINER");
		for (int i = 0; i < alarmContainers.getLength(); i++) {
			Element container = (Element) alarmContainers.item(i);
			String shortName = getChildText(container, "SHORT-NAME");
			if (shortName != null && shortName.startsWith("OsAlarm_")) {
				if (hasSubContainer(container, "OsAlarmAutostart")) {
					// Autostart exists, check which task it activates
					List<String> activatedTasks = findActivatedTasks(container);
					for (String taskName : activatedTasks) {
						ParsedTask task = taskMap.get(taskName);
						if (task != null) {
							task.autostart = true;
						}
					}
				}
			}
		}

		return new ArrayList<>(taskMap.values());
	}

	private static String getChildText(Element parent, String tagName) {
		NodeList children = parent.getElementsByTagName(tagName);
		if (children.getLength() > 0) {
			return children.item(0).getTextContent();
		}
		return null;
	}

	private static int parseIntegerParameter(Element container, String paramName) {
		NodeList paramValues = container.getElementsByTagName("ECUC-INTEGER-PARAM-VALUE");
		for (int i = 0; i < paramValues.getLength(); i++) {
			Element param = (Element) paramValues.item(i);
			if (paramName.equals(getChildText(param, "SHORT-NAME"))) {
				String value = getChildText(param, "VALUE");
				if (value != null) {
					return Integer.parseInt(value);
				}
			}
		}
		return 0; // default if not found
	}

	private static String parseEnumParameter(Element container, String paramName) {
		NodeList paramValues = container.getElementsByTagName("ECUC-ENUMERATION-PARAM-VALUE");
		for (int i = 0; i < paramValues.getLength(); i++) {
			Element param = (Element) paramValues.item(i);
			if (paramName.equals(getChildText(param, "SHORT-NAME"))) {
				return getChildText(param, "VALUE");
			}
		}
		return ""; // default if not found
	}

	private static boolean hasSubContainer(Element container, String subContainerName) {
		NodeList subContainers = container.getElementsByTagName("SUB-CONTAINERS");
		for (int i = 0; i < subContainers.getLength(); i++) {
			NodeList subList = ((Element) subContainers.item(i)).getElementsByTagName("ECUC-PARAM-CONF-CONTAINER");
			for (int j = 0; j < subList.getLength(); j++) {
				Element sub = (Element) subList.item(j);
				if (subContainerName.equals(getChildText(sub, "SHORT-NAME"))) {
					return true;
				}
			}
		}
		return false;
	}

	private static List<String> findActivatedTasks(Element alarmContainer) {
		List<String> tasks = new ArrayList<>();
		NodeList subContainers = alarmContainer.getElementsByTagName("SUB-CONTAINERS");
		for (int i = 0; i < subContainers.getLength(); i++) {
			NodeList subList = ((Element) subContainers.item(i)).getElementsByTagName("ECUC-PARAM-CONF-CONTAINER");
			for (int j = 0; j < subList.getLength(); j++) {
				Element sub = (Element) subList.item(j);
				if ("OsAlarmAction".equals(getChildText(sub, "SHORT-NAME"))) {
					NodeList innerSub = sub.getElementsByTagName("ECUC-PARAM-CONF-CONTAINER");
					for (int k = 0; k < innerSub.getLength(); k++) {
						Element innerAction = (Element) innerSub.item(k);
						if ("OsAlarmSetEvent".equals(getChildText(innerAction, "SHORT-NAME"))
								|| "OsAlarmActivateTask".equals(getChildText(innerAction, "SHORT-NAME"))) {
							NodeList refs = innerAction.getElementsByTagName("ECUC-REFERENCE");
							for (int m = 0; m < refs.getLength(); m++) {
								Element ref = (Element) refs.item(m);
								String dest = getChildText(ref, "DESTINATION-REF");
								if (dest != null && dest.contains("OsTask_")) {
									String taskName = dest.substring(dest.lastIndexOf("/") + 1);
									tasks.add(taskName);
								}
							}
						}
					}
				}
			}
		}
		return tasks;
	}
}
