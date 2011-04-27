package org.sfnelson.sk.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

public abstract class ListPanel<T, E extends ListEntry<T>> extends Composite implements ClickHandler {

	interface Style extends CssResource {
		String hide();
	}

	@UiField Panel list;

	private final List<T> data = new ArrayList<T>();
	private final Map<T, E> rows = new HashMap<T, E>();
	private final List<E> free = new ArrayList<E>();

	private final ListEntry.EntryFactory<T, E> factory;

	protected ListPanel(ListEntry.EntryFactory<T, E> factory) {
		this.factory = factory;
	}

	public void setData(List<T> data) {
		for (int i = 0; i < data.size(); i++) {
			T updated = data.get(i);

			if (i < this.data.size()) {
				T current = this.data.get(i);
				if (updated.equals(current)) {
					rows.get(updated).clear();
					rows.get(updated).setData(updated);
					continue;
				}
				else {
					while (this.data.size() > i) {
						removeEntry(this.data.remove(this.data.size() - 1));
					}
				}
			}

			this.data.add(updated);
			this.list.add(addEntry(updated));
		}
	}

	public void showData(Set<T> data) {
		int i = 0;
		for (T d: this.data) {
			E e = rows.get(d);
			if (data.contains(d)) {
				e.removeStyleName("hide");
				if (i++ % 2 == 1) {
					e.removeStyleName("even");
					e.addStyleName("odd");
				}
				else {
					e.removeStyleName("odd");
					e.addStyleName("even");
				}
			}
			else {
				e.addStyleName("hide");
			}
		}
	}

	private E addEntry(T realm) {
		E entry;
		if (free.isEmpty()) {
			entry = factory.createEntry();
			entry.addClickHandler(this);
		}
		else {
			entry = free.remove(free.size() - 1);
		}
		entry.setData(realm);
		rows.put(realm, entry);
		entry.removeStyleName("hide");

		return entry;
	}

	private void removeEntry(T data) {
		E entry = rows.get(data);
		if (entry == null) return;

		entry.clear();
		entry.addStyleName("hide");
		entry.removeStyleName("odd");
		entry.removeStyleName("even");
		rows.remove(data);
		free.add(entry);
	}
}
