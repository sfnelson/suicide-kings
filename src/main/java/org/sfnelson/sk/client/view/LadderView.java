package org.sfnelson.sk.client.view;

import java.util.List;
import java.util.Set;

import org.sfnelson.sk.client.Character;
import org.sfnelson.sk.client.view.LadderView.LadderPresenter;

public interface LadderView extends View<LadderPresenter> {

	public void setData(List<Character> values);
	public void showData(Set<Character> values);

	public interface LadderPresenter extends View.ViewPresenter {
		void setShowAll(boolean showAll);
		void registerCharacter(String name);
	}
}
