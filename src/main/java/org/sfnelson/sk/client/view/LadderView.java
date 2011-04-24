package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.Character;
import org.sfnelson.sk.client.view.LadderView.LadderPresenter;

public interface LadderView extends View<LadderPresenter> {

	public void setData(List<Character> values);
	public void showData(List<Character> values);

	public interface LadderPresenter extends View.ViewPresenter {
		void setShowAll(boolean showAll);
		void registerCharacter(String name);
		void joinParty(Character character);
		void leaveParty(Character character);
	}
}
