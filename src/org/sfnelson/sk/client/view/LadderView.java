package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.view.LadderView.LadderPresenter;

public interface LadderView extends View<LadderPresenter> {

    public void setSize(int available);
    public void setValues(List<CharacterProxy> values);

    public interface LadderPresenter extends View.ViewPresenter {
        void registerCharacter(String name);
        void joinParty(CharacterProxy character);
        void leaveParty(CharacterProxy character);
    }
}
