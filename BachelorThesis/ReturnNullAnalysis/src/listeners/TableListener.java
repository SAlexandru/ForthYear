package listeners;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

public abstract class TableListener implements Listener
	{
	protected Table table;
	
	public void setTable(Table table)
		{
		this.table=table;
		}
	}
