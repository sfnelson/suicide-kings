package org.sfnelson.sk.client.ui;

import java.util.Date;
import java.util.List;

import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.view.EventsView;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EventsPanel extends Composite implements EventsView {

    private static EventsPanelUiBinder uiBinder = GWT.create(EventsPanelUiBinder.class);

    interface EventsPanelUiBinder extends UiBinder<Widget, EventsPanel> {
    }

    public EventsPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField CellTable<EventProxy> events;

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setEvents(List<EventProxy> response) {
        events.setRowData(response);
    }

    @UiFactory
    CellTable<EventProxy> createEventList() {
        CellTable<EventProxy> table = new CellTable<EventProxy>();

        table.addColumn(new Column<EventProxy, Date>(new DateCell()) {
            @Override
            public Date getValue(EventProxy event) {
                return event.getDate();
            }
        });

        table.addColumn(new Column<EventProxy, String>(new TextCell()) {
            @Override
            public String getValue(EventProxy event) {
                return event.getCharacter().getName();
            }
        });

        table.addColumn(new Column<EventProxy, String>(new TextCell()) {
            @Override
            public String getValue(EventProxy event) {
                switch (event.getType()) {
                case CREATED:
                    return "added to the group";
                case  JOINED:
                    return "joined the raid";
                case LEFT:
                    return "left the raid";
                case LOOT:
                    return  "received";
                default:
                    return null;
                }
            }
        });

        return table;
    }
}
