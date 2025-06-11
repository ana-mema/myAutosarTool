package com.garraio.cordoba.application.ui.components.tables.providers;

import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.COMPONENT;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.EVENT_NAME;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.PERIOD;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.RUNNABLE;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.TYPE;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

public class EventTableColumnProvider {
	private static final String[] COLUMNS = { EVENT_NAME, TYPE, RUNNABLE, COMPONENT, PERIOD };

	public static String[] getColumns() {
		return COLUMNS;
	}

	public static void createColumns(TableViewer viewer) {
		for (String columnName : COLUMNS) {
			addColumn(viewer, columnName);
		}

		viewer.setLabelProvider(new EventTableLabelProvider());
	}

	private static void addColumn(TableViewer viewer, String columnName) {
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(columnName);
		column.getColumn().setWidth(150);
	}

}
