package com.axeiya.gwtckeditor.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface SaveHandler<T> extends EventHandler {
	void onTitreChange(SaveEvent<T> event);
}
