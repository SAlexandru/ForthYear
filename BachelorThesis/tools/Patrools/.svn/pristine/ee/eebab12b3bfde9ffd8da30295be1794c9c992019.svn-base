package lrg.patrools.visualizations;

import java.util.Comparator;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.insider.visualizations.AbstractVisualizationOperations;
import lrg.insider.visualizations.MonitorCanceledException;
import lrg.jMondrian.commands.AbstractFigureDescriptionCommand;
import lrg.jMondrian.commands.AbstractNumericalCommand;
import lrg.jMondrian.figures.Figure;
import lrg.jMondrian.layouts.Checker;
import lrg.jMondrian.painters.RectangleNodePainter;
import lrg.jMondrian.util.CommandColor;
import lrg.patrools.util.UselessOperation;
import lrg.patrools.wala.extensions.ColorCommandsComputer;
import lrg.patrools.wala.extensions.IntraProceduralSCA;

public class LevelOfAbstractionBuilder extends AbstractVisualizationOperations {

	private Figure lastBuiltFigure = null;
	private final MicroprintBuilder microprintFactory = new MicroprintBuilder();
	private int maxWidth = 1200;
	
	@SuppressWarnings("unchecked")
	@Override
	public void buildFigure(AbstractEntityInterface theEntity, final IProgressMonitor monitor) throws MonitorCanceledException {
		GroupEntity clients = theEntity.getGroup("base class external clients").distinct();
		GroupEntity allConcreteCone = theEntity.getGroup("concrete cone");

		if(clients.size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " class does not have external clients. View building stopped.");
		}
		
		if(theEntity.getGroup("all descendants").size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " class does not have descendants. View building stopped.");
		}
		
		if(allConcreteCone.size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " hierarchy does not contain concrete classes. View building stopped.");
		}
		
		monitor.beginTask("Build Level of Abstraction View", clients.size());
		final HashMap<AbstractEntityInterface, AbstractNumericalCommand> client2command = ColorCommandsComputer.computeLAColorCommands(theEntity, clients.getElements(), allConcreteCone.getElements(), monitor);
		Figure f = new Figure();
		f.nodesUsingForEach(clients.getElements(), new RectangleNodePainter(true).frameColor(CommandColor.LIGHT_GRAY), new AbstractFigureDescriptionCommand() {
			public Figure<Object> describe() {
				try {
					microprintFactory.setCharCommand(client2command.get(receiver));
					microprintFactory.buildFigure((AbstractEntityInterface)receiver, monitor);
				} catch (MonitorCanceledException e) {}
				return microprintFactory.getFigure();
			}
		});
		f.layout(new Checker(new Comparator() {
    		public int compare(Object a, Object b) {
    			int ma = (int)((Double)((AbstractEntityInterface)a).getProperty("LOC").getValue()).doubleValue();
    			int mb = (int)((Double)((AbstractEntityInterface)b).getProperty("LOC").getValue()).doubleValue();
    			if(ma!=mb) {
    				return ma - mb;
    			} else {
        			String na = ((String)((AbstractEntityInterface)a).getProperty("Name").getValue());
        			String nb = ((String)((AbstractEntityInterface)b).getProperty("Name").getValue());
    				return na.compareTo(nb);
    			}
    		}
		},maxWidth));
		lastBuiltFigure = f;
	}

	@Override
	public Figure getFigure() {
		return lastBuiltFigure;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
}
