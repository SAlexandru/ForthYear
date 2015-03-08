package lrg.patrools.visualizations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.insider.plugins.groups.memoria.InheritanceRelation;
import lrg.insider.visualizations.AbstractVisualizationOperations;
import lrg.insider.visualizations.MonitorCanceledException;
import lrg.jMondrian.commands.AbstractEntityCommand;
import lrg.jMondrian.commands.AbstractFigureDescriptionCommand;
import lrg.jMondrian.commands.AbstractNumericalCommand;
import lrg.jMondrian.figures.Figure;
import lrg.jMondrian.layouts.Checker;
import lrg.jMondrian.layouts.CrossReductionTreeLayout;
import lrg.jMondrian.layouts.FlowLayout;
import lrg.jMondrian.painters.EllipseNodePainter;
import lrg.jMondrian.painters.LineEdgePainter;
import lrg.jMondrian.painters.RectangleNodePainter;
import lrg.jMondrian.util.CommandColor;
import lrg.patrools.util.Placeholder;
import lrg.patrools.util.UselessOperation;
import lrg.patrools.wala.extensions.ColorCommandsComputer;

public class GroupDiscriminationBuilder extends AbstractVisualizationOperations {

	public static final int TOP_8_PREVALENT = 0, TOP_8_LARGEST = 1;
	
	private Figure lastBuiltFigure = null;
	private final MicroprintBuilder microprintFactory = new MicroprintBuilder();
	private int filter_type = TOP_8_PREVALENT;
	private int maxWidth = 1200;

	public GroupDiscriminationBuilder(int filter_type) {
		this.filter_type = filter_type;
	}
	
	public void buildFigure(AbstractEntityInterface theEntity, final IProgressMonitor monitor) throws MonitorCanceledException {

		final GroupEntity clients = theEntity.getGroup("base class external clients").distinct();
		final GroupEntity allConcreteCone = theEntity.getGroup("concrete cone");
		final GroupEntity classesInHierarchy = theEntity.getGroup("all descendants").union((AbstractEntity)theEntity).distinct();
		final List<AbstractEntityInterface> inheritanceInHierarchy = new ArrayList<AbstractEntityInterface>();
        
		if(clients.size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " class does not have external clients. View building stopped.");
		}
		
		if(theEntity.getGroup("all descendants").size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " class does not have descendants. View building stopped.");
		}
		
		if(allConcreteCone.size() == 0) {
			throw new UselessOperation("The " + theEntity.getProperty("Name") + " hierarchy does not contain concrete classes. View building stopped.");
		}

		GroupEntity allEdges = theEntity.belongsTo("system").getGroup("all inheritance relations");
        Iterator<InheritanceRelation> it = allEdges.iterator();
        while(it.hasNext()) {
            InheritanceRelation rel = it.next();
            if(classesInHierarchy.isInGroup(rel.getSubClass()) && classesInHierarchy.isInGroup(rel.getSuperClass())) {
            	inheritanceInHierarchy.add(rel);
            }
        }
		
		monitor.beginTask("Build Group Discrimination View", 2 * clients.size());

		Map[] res = ColorCommandsComputer.computeGDColorCommands(theEntity, clients.getElements(), allConcreteCone.getElements(), monitor, filter_type);
		final Map<AbstractEntityInterface, AbstractNumericalCommand> client2command = res[0];
		final Map<Set<AbstractEntityInterface>,Color> set2Color = res[1];
		List<Object> members = new ArrayList<Object>();
		members.add(clients);
		members.add(classesInHierarchy);
		Figure f = new Figure();
		f.nodesUsingForEach(members, new RectangleNodePainter(true).frameColor(CommandColor.LIGHT_GRAY),
			new AbstractFigureDescriptionCommand() {
				public Figure<Object> describe() {
					Figure f = new Figure();
					if(receiver == clients) {
						f.nodesUsingForEach(clients.getElements(), new RectangleNodePainter(true).frameColor(CommandColor.LIGHT_GRAY), new AbstractFigureDescriptionCommand() {
							public Figure<Object> describe() {
								try {
									microprintFactory.setCharCommand(client2command.get((AbstractEntityInterface)receiver));
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
					} else {
						f.nodesUsingForEach(classesInHierarchy.getElements(), new RectangleNodePainter(15,15,true).
								color(new AbstractNumericalCommand() {
						                 public double execute() {
						                        if(((Boolean)((AbstractEntityInterface)receiver).getProperty("is interface").getValue())) return Color.lightGray.getRGB();
						                        else if(((Boolean)((AbstractEntityInterface)receiver).getProperty("is abstract").getValue())) return Color.gray.getRGB();
						                        else return Color.white.getRGB();
						                 }
								}), new AbstractFigureDescriptionCommand() {
									public Figure<Object> describe() {
										Figure f = new Figure();
										List<Placeholder> l = new ArrayList<Placeholder>();
										if(((Boolean)((AbstractEntityInterface)receiver).getProperty("is abstract").getValue()) || ((Boolean)((AbstractEntityInterface)receiver).getProperty("is interface").getValue())) {
											GroupEntity concreteCone = ((AbstractEntityInterface)receiver).getGroup("concrete cone");
											Set<AbstractEntityInterface> setCone = new HashSet<AbstractEntityInterface>();
											setCone.addAll(concreteCone.getElements());
											for(Set<AbstractEntityInterface> setOftypes : set2Color.keySet()) {
												if(set2Color.get(setOftypes) != Color.red) {
													if(setOftypes.equals(setCone)) {
														Placeholder ph = new Placeholder(set2Color.get(setOftypes));
														l.add(ph);														
													}
												}
											}
										} else {
											for(Set<AbstractEntityInterface> setOftypes : set2Color.keySet()) {
												if(setOftypes.contains(receiver) && set2Color.get(setOftypes) != Color.red) {
													Placeholder ph = new Placeholder(set2Color.get(setOftypes));
													l.add(ph);
												}
											}
										}
										f.nodesUsing(l, new EllipseNodePainter(7,7,false).color(
											new AbstractNumericalCommand() {
												public double execute() {
													return ((Color)((Placeholder)receiver).getEnclosed()).getRGB();
												}
											}
										));
										f.layout(new FlowLayout());
										return f;
									}							
						});
						f.edgesUsing(inheritanceInHierarchy, new LineEdgePainter(createAbstractEntityCommand("getSubClass"), createAbstractEntityCommand("getSuperClass")));
						f.layout(new CrossReductionTreeLayout(20,50,false));
					}
					return f;
				}
				
				private AbstractEntityCommand createAbstractEntityCommand(final String prop) {
					return new AbstractEntityCommand(prop) {
			            public AbstractEntityInterface execute() {
			                try {
			                    return (AbstractEntityInterface)receiver.getClass().getMethod(prop,new Class[] {}).invoke(receiver);
			                } catch(Exception e) {
			                    System.err.println(e);
			                    throw new RuntimeException(prop + " method does not exist or it is improperly invoked");
			                }
			            }
			        };
				}
		});
		f.layout(new FlowLayout());
		lastBuiltFigure = f;
	}

	@Override
	public Figure getFigure() {
		return lastBuiltFigure;
	}
}
