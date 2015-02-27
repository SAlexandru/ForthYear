package listeners;

import java.util.List;

import models.OuterMethodModel;

import org.eclipse.swt.widgets.Event;

import returnnullanalysis.Pile;
import returnnullanalysis.ReturnNullView;
import utils.ListViewHelper;

public class MetTableListener extends TableListener {
	@Override
	public void handleEvent(Event event) {
		ReturnNullView.listViewer.refresh();
		ListViewHelper.getInstance().clear();
		List<OuterMethodModel> methods;
		int i;
		methods = Pile.getInstance().getMethods()
				.get(table.getSelectionIndex()).getOuterMethods();
		for (i = 0; i < methods.size(); i++) {
			ReturnNullView.listViewer.add(methods.get(i).getCustomName());
			ListViewHelper.getInstance().getOuterMethods().add(methods.get(i));
		}
	}

}
