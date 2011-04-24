package org.sfnelson.sk.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface ListEntry<T> extends IsWidget, HasClickHandlers {

	public interface EntryFactory<T, E extends ListEntry<T>> {
		E createEntry();
	}

	public T getData();
	public void setData(T data);
	public void clear();

	public void addStyleName(String style);
	public void removeStyleName(String style);

}
