package com.garraio.cordoba.application.ui.components.tables.providers;

import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.COMPONENT;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.EVENT_NAME;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.PERIOD;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.RUNNABLE;
import static com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnLabelConstants.TYPE;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.garraio.cordoba.application.core.adapter.events.AbstractEventAdapter;
import com.garraio.cordoba.application.core.adapter.events.InitEventAdapter;
import com.garraio.cordoba.application.core.adapter.events.OperationInvokedEventAdapter;
import com.garraio.cordoba.application.core.adapter.events.PeriodicEventAdapter;

import autosar40.swcomponent.swcinternalbehavior.rteevents.InitEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.OperationInvokedEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;
import autosar40.swcomponent.swcinternalbehavior.rteevents.TimingEvent;

public class EventTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	private final Map<RTEEvent, AbstractEventAdapter> adapterCache = new WeakHashMap<>();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (!(element instanceof RTEEvent)) {
			return "";
		}

		RTEEvent event = (RTEEvent) element;
		final String[] COLUMNS = EventTableColumnProvider.getColumns();
		String columnName = COLUMNS[columnIndex];

		AbstractEventAdapter eventAdapter = getAdapter(event);
		if (eventAdapter == null) {
			return "";
		}

		return getValueForColumn(eventAdapter, columnName);
	}

	private AbstractEventAdapter getAdapter(RTEEvent event) {
		return adapterCache.computeIfAbsent(event, e -> {
			if (e instanceof InitEvent) {
				return new InitEventAdapter((InitEvent) e);
			} else if (e instanceof TimingEvent) {
				return new PeriodicEventAdapter((TimingEvent) e);
			} else if (e instanceof OperationInvokedEvent) {
				return new OperationInvokedEventAdapter((OperationInvokedEvent) e);
			}
			return null;
		});
	}

	private String getValueForColumn(AbstractEventAdapter adapter, String columnName) {
		switch (columnName) {
		case EVENT_NAME:
			return adapter.getShortName();
		case TYPE:
			return adapter.getTypeName();
		case RUNNABLE:
			return nullSafe(() -> adapter.getRunnable().getShortName());
		case COMPONENT:
			return nullSafe(() -> adapter.getParentComponent().getShortName());
		case PERIOD:
			if (adapter instanceof PeriodicEventAdapter) {
				return ((PeriodicEventAdapter) adapter).getPeriod();
			}
			return "";
		default:
			return "";
		}
	}

	private String nullSafe(Supplier<String> supplier) {
		try {
			return supplier.get();
		} catch (NullPointerException e) {
			return "";
		}
	}

	@Override
	public void dispose() {
		adapterCache.clear();
		super.dispose();
	}

}
