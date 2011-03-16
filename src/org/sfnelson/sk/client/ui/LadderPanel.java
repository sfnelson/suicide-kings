package org.sfnelson.sk.client.ui;

import java.util.List;

import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.view.LadderView;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LadderPanel extends Composite implements LadderView {

    private static LadderPanelUiBinder uiBinder = GWT.create(LadderPanelUiBinder.class);

    interface LadderPanelUiBinder extends UiBinder<Widget, LadderPanel> {}

    interface Style extends CssResource {
        String editMode();
        String viewMode();
        String edit();
        String view();
    }

    @UiField Style style;
    @UiField CellList<CharacterProxy> container;
    @UiField TextBox name;
    @UiField Button edit;
    @UiField Button add;
    @UiField Button done;

    private LadderPresenter presenter;

    public LadderPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        addStyleName(style.viewMode());
    }

    public void setPresenter(LadderPresenter presenter) {
        this.presenter = presenter;
    }

    public void setSize(int size) {
        container.setRowCount(size);
    }

    public void setValues(List<CharacterProxy> values) {
        container.setRowData(values);
    }

    @UiHandler("add")
    void add(ClickEvent e) {
        if (name.getValue() != null) {
            presenter.registerCharacter(name.getValue());
        }
    }

    @UiHandler("edit")
    void edit(ClickEvent e) {
        removeStyleName(style.viewMode());
        addStyleName(style.editMode());
    }

    @UiHandler("done")
    void done(ClickEvent e) {
        removeStyleName(style.editMode());
        addStyleName(style.viewMode());
    }

    @UiFactory
    CellList<CharacterProxy> createContainer() {
        return new CellList<CharacterProxy>(new CharacterCell());
    }

    class CharacterCell extends AbstractCell<CharacterProxy> {

        @Override
        public void render(Context context, CharacterProxy c, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant("<div>");
            sb.appendHtmlConstant("<span>");
            sb.appendEscaped(c.getName());
            sb.appendHtmlConstant("</span>");
            sb.appendHtmlConstant("<button class=" + style.edit() + ">Remove</button>");
            sb.appendHtmlConstant("</div>");
        }

    }
}
