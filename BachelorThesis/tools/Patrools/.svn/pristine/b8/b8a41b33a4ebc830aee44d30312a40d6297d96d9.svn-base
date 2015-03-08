package lrg.patrools.wala.extensions;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ssa.SSAGotoInstruction;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;

import plugins.Wrapper;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.insider.visualizations.MonitorCanceledException;
import lrg.jMondrian.commands.AbstractNumericalCommand;
import lrg.patrools.wala.extensions.listeners.WalaRepresentationBuilder;

public class ColorCommandsComputer {

	private static Set<TypeReference> toWalaTypes(ClassHierarchy ch, Set<IType> setOfEclipseTypes) {
		Set<TypeReference> res = new HashSet<TypeReference>();
		for(IType t : setOfEclipseTypes) {
			//System.err.println(t);
			res.add(RepresentationsConversionUtilities.eclipse2wala(ch, t).getReference());
		}
		return res;
	}
	
	private static Set<MethodReference> toWalaMethods(ClassHierarchy ch, Set<IMethod> setOfEclipseMethods) {
		Set<MethodReference> res = new HashSet<MethodReference>();
		for(IMethod t  : setOfEclipseMethods) {
			//try {
			//	if(t.getDeclaringType().isAnonymous()) {
			//		System.err.println(t);					
			//	}
			//} catch (JavaModelException e) {
			//	e.printStackTrace();
			//}
			res.add(RepresentationsConversionUtilities.eclipse2wala(t, ch).getReference());
		}
		return res;
	}
		
	private static double computeLAMetric(ClassHierarchy ch, IJavaProject proj, SCAVariable in, Set<TypeReference> allConcreteTypes, InputContext context, int instrPos) {
		Map<Integer,TypesSet> var2types = in.getVar2Types();
		double aggregatedValue = -1;
		for(Integer use : var2types.keySet()) {			
			if(!context.isInScope(use,instrPos)) continue;
			Set<TypeReference> initial = var2types.get(use).toSet(); 
			double value;
			if(initial.equals(allConcreteTypes)) {
				if(allConcreteTypes.size() == 1) {
					value = 0;
				} else {
					value = 1;
				}
			} else {
				Set<TypeReference> varTypes = new HashSet<TypeReference>();
				varTypes.addAll(initial);
				varTypes.retainAll(allConcreteTypes);
				if(varTypes.size() == 0) {
					value = -1;
				} else if(varTypes.size() == 1) {
					value = 0;
				} else {
					value = ((double)(varTypes.size()  - 1)) / (allConcreteTypes.size() - 1);
				}
			}
			if(aggregatedValue == -1) {
				aggregatedValue = value;
			} else if(value == -1) {
				continue;
			} else if(aggregatedValue > value){
				aggregatedValue = value;
			}
		}
		return aggregatedValue;
	}
	
	private static final Color level1 = new Color(255,200,200);
	private static final Color level2 = new Color(255,130,130);	
	
	private static List<IBasicBlock> depthFirstOrder(IBasicBlock bb, AstMethod theMethod, Set<Integer> visited, List<IBasicBlock> res) {
		if(!visited.contains(bb.getNumber())) {
			visited.add(bb.getNumber());
			Collection<IBasicBlock> col = new ArrayList<IBasicBlock>();
			Iterator<IBasicBlock> tmpIt = theMethod.cfg().getSuccNodes(bb);
			while(tmpIt.hasNext()) col.add(tmpIt.next());
			if(col.size() <= 1) {
				res.add(bb);
				Iterator<IBasicBlock> it = col.iterator();
				while(it.hasNext())
					depthFirstOrder(it.next(),theMethod,visited,res);
			} else {
				Iterator<IBasicBlock> it = col.iterator(); 
				while(it.hasNext())
					depthFirstOrder(it.next(),theMethod,visited,res);
				res.add(bb);
			}
		}
		return res;
	}
	
	public static HashMap<AbstractEntityInterface,AbstractNumericalCommand> computeLAColorCommands(AbstractEntityInterface theBaseClass, List<AbstractEntityInterface> clients, List<AbstractEntityInterface> allConcreteTypes, IProgressMonitor monitor) throws MonitorCanceledException { 
		
		try {
	
			//Prepare cone
			Set<IType> cone = new HashSet<IType>();
			for(AbstractEntityInterface t : allConcreteTypes) {
				cone.add(((IType)((Wrapper)t).getElement()));
			}
			
			//Wala set-up
			monitor.subTask("Setting-Up Wala");
			ClassHierarchy ch = WalaRepresentationBuilder.getInstance().parseProject(((IType)((Wrapper)theBaseClass).getElement()).getJavaProject());
			
			//Analyze clients
			monitor.subTask("Analyzing clients");			
			HashMap<AbstractEntityInterface,AbstractNumericalCommand> client2command = new HashMap<AbstractEntityInterface,AbstractNumericalCommand>();
			Set<TypeReference> theWalaCone = toWalaTypes(ch,cone);
			TypesSetFactory.getInstance().init(ch);
			
			//Find calee in order to filter variables
			GroupEntity callee = theBaseClass.getGroup("method chain");
			Set<IMethod> calleeEclipse = new HashSet<IMethod>();
			for(AbstractEntityInterface cpm : (List<AbstractEntityInterface>)callee.getElements()) {
				calleeEclipse.add((IMethod)((Wrapper)cpm).getElement());
			}
			Set<MethodReference> theWalaCallee = toWalaMethods(ch,calleeEclipse);
			
			for(int index = 0; index < clients.size(); index++) {
				
				if(monitor.isCanceled()) throw new MonitorCanceledException();
				monitor.worked(1);
				AbstractEntityInterface client = clients.get(index);
				
				final HashMap<Integer,Double> char2metric = new HashMap<Integer,Double>();

				//Find client Eclipse representation
				Wrapper wrappedClient =  (Wrapper)client;
				IMethod theClient = (IMethod)wrappedClient.getElement();

				//Find client Wala representation
				AstMethod theClientWala = RepresentationsConversionUtilities.eclipse2wala(theClient,ch);

				//Execute SCA
				InputContext context = new InputContext(client,theClientWala,theWalaCallee);
				Map<Integer,SCAVariable> beforeBasicBlock = IntraProceduralSCA.getInstance().execute(context,theClientWala);
				
				Object[] instrTab = theClientWala.cfg().getInstructions();
				Iterator<IBasicBlock> bbIt = depthFirstOrder(theClientWala.cfg().entry(),theClientWala,new HashSet<Integer>(),new ArrayList<IBasicBlock>()).iterator();
				while(bbIt.hasNext()) {
					IBasicBlock bb = (IBasicBlock)bbIt.next();
					if(bb.getFirstInstructionIndex() < 0) continue;
					List<SCAVariable> beforeInstruction = IntraProceduralSCA.getInstance().beforeEachInstructionFrom(beforeBasicBlock.get(bb.getNumber()),bb,theClientWala);
					for(int i = bb.getFirstInstructionIndex(); i <= bb.getLastInstructionIndex(); i++) {
						if(theClientWala.getSourcePosition(i) == null) continue;
						if(instrTab[i] instanceof SSAGotoInstruction) continue;
						int offsetStart = theClientWala.getSourcePosition(i).getFirstOffset();
						int offsetEnd = theClientWala.getSourcePosition(i).getLastOffset();
						double metric = computeLAMetric(ch, ((IType)((Wrapper)theBaseClass).getElement()).getJavaProject(), beforeInstruction.get(i-bb.getFirstInstructionIndex()),theWalaCone,context,i);
						for(int j = offsetStart; j <= offsetEnd; j++) {
							Double lastMetric = char2metric.get(j);
							if(lastMetric == null) {
								char2metric.put(j, metric);
							} else if(lastMetric == -1) {
								char2metric.put(j, metric);								
							}
						}
						if(context.getJoinedChars((SSAInstruction)instrTab[i]) != null) {
							for(Integer j : context.getJoinedChars((SSAInstruction)instrTab[i])) {
								Double lastMetric = char2metric.get(j);								
								if(lastMetric == null) {
									char2metric.put(j, metric);
								} else if(lastMetric == -1) {
									char2metric.put(j, metric);								
								}
							}
						}
					}
				}
				//TODO: catch blocks are draw in one single color
				//TODO: try characters are not included in any instruction
				client2command.put(client, new AbstractNumericalCommand() {
					public double execute() {
						Double metric = char2metric.get(receiver);
						if(metric == null) return Color.LIGHT_GRAY.getRGB();
						double color;
						if(metric == -1) {
							color = Color.LIGHT_GRAY.getRGB();
						} else if(metric == 0) {
							color = Color.WHITE.getRGB();
						} else if(metric == 1) {
							color = Color.RED.getRGB();
						} else if(metric >= 0.5) {
							color = level2.getRGB();
						} else {
							color = level1.getRGB();
						}
						return color;
					}
				});
			}
			return client2command;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		} catch (ClassHierarchyException e) {
			e.printStackTrace();
			return null;
		} catch (CancelException e) {
			e.printStackTrace();
			return null;
		} catch(MonitorCanceledException e) {
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
    private static Color[] colorList = {
    /**green**/ Color.green,
    /**blue**/  Color.blue,
    /**cyan**/  Color.cyan,
    /**orange**/Color.orange,
    /**purple**/new Color(160,0,160),
    /**pink**/  new Color(255,127,170),
    /**brown**/ new Color(163,34,18),
    /**yellow**/Color.yellow};
    
	public static Map[] computeGDColorCommands(AbstractEntityInterface theBaseClass, List<AbstractEntityInterface> clients, List<AbstractEntityInterface> allConcreteTypes, IProgressMonitor monitor, int filter_type) throws MonitorCanceledException { 
		try {
			
			//Prepare cone
			Set<IType> cone = new HashSet<IType>();
			for(AbstractEntityInterface t : allConcreteTypes) {
				cone.add(((IType)((Wrapper)t).getElement()));
			}
			
			//Wala set-up
			monitor.subTask("Setting-Up Wala");
			ClassHierarchy ch = WalaRepresentationBuilder.getInstance().parseProject(((IType)((Wrapper)theBaseClass).getElement()).getJavaProject());

			//Analyze clients
			monitor.subTask("Analyzing clients");			
			Set<TypeReference> theWalaCone = toWalaTypes(ch,cone);
			TypesSetFactory.getInstance().init(ch);
			
			//Find callee in order to filter variables
			GroupEntity callee = theBaseClass.getGroup("method chain");
			Set<IMethod> calleeEclipse = new HashSet<IMethod>();
			for(AbstractEntityInterface cpm : (List<AbstractEntityInterface>)callee.getElements()) {
				calleeEclipse.add((IMethod)((Wrapper)cpm).getElement());
			}
			Set<MethodReference> theWalaCallee = toWalaMethods(ch,calleeEclipse);

			//Cache results
			Map<AbstractEntityInterface, InputContext> client2context = new HashMap<AbstractEntityInterface,InputContext>();
			Map<SSAInstruction,SCAVariable> instr2sca = new HashMap<SSAInstruction,SCAVariable>();
			Map<AbstractEntityInterface, Set<Integer>> client2discriminatingInstr = new HashMap<AbstractEntityInterface, Set<Integer>>();
			Set<Set<TypeReference>> groups = new HashSet<Set<TypeReference>>();
			
			//For filters & zone aggregation
			final Map<Set<TypeReference>, Integer> groupsNoClients = new HashMap<Set<TypeReference>, Integer>();
			Set<Set<TypeReference>> countedGroups = new HashSet<Set<TypeReference>>();
			final Map<AbstractEntityInterface,Map<Set<TypeReference>,Integer>> client2set2zone = new HashMap<AbstractEntityInterface,Map<Set<TypeReference>,Integer>>();
			
			//Execute SCA and find the groups
			for(int index = 0; index < clients.size(); index++) {
				
				if(monitor.isCanceled()) throw new MonitorCanceledException();
				monitor.worked(1);
				AbstractEntityInterface client = clients.get(index);
				
				//Find client Eclipse representation
				Wrapper wrappedClient =  (Wrapper)client;
				IMethod theClient = (IMethod)wrappedClient.getElement();

				//Find client Wala representation
				AstMethod theClientWala = RepresentationsConversionUtilities.eclipse2wala(theClient,ch);

				//Execute SCA
				InputContext context = new InputContext(client,theClientWala, theWalaCallee);
				Map<Integer,SCAVariable> beforeBasicBlock = IntraProceduralSCA.getInstance().execute(context,theClientWala);
				client2context.put(client, context);
				client2discriminatingInstr.put(client,IntraProceduralSCA.getInstance().discriminatingInstructions());
								
				//Process
				countedGroups.clear();
				Object[] instrTab = theClientWala.cfg().getInstructions();
				Iterator<IBasicBlock> bbIt = theClientWala.cfg().iterator();
				while(bbIt.hasNext()) {
					IBasicBlock bb = (IBasicBlock)bbIt.next();
					if(bb.getFirstInstructionIndex() < 0) continue;
					List<SCAVariable> beforeInstruction = IntraProceduralSCA.getInstance().beforeEachInstructionFrom(beforeBasicBlock.get(bb.getNumber()),bb,theClientWala);
					for(int i = bb.getFirstInstructionIndex(); i <= bb.getLastInstructionIndex(); i++) {
						if(theClientWala.getSourcePosition(i) == null) continue;
						if(instrTab[i] instanceof SSAGotoInstruction) continue;
						instr2sca.put((SSAInstruction)instrTab[i], beforeInstruction.get(i-bb.getFirstInstructionIndex()));
						if(client2discriminatingInstr.get(client).contains(i)) continue;
						Map<Integer,TypesSet> var2types = beforeInstruction.get(i-bb.getFirstInstructionIndex()).getVar2Types();
						for(Integer use : var2types.keySet()) {			
							if(!context.isInScope(use,i)) continue;
							Set<TypeReference> initial = var2types.get(use).toSet();
							boolean added = false;
							if(initial.equals(theWalaCone)) {
								groups.add(initial);
								added = true;
							} else {
								Set<TypeReference> varTypes = new HashSet<TypeReference>();
								varTypes.addAll(initial);
								varTypes.retainAll(theWalaCone);
								if(varTypes.size() != 0) {
									added = true;
									groups.add(varTypes);
									initial = varTypes;
								}
							}
							if(added) {
								//Count prevalence
								if(!countedGroups.contains(initial)) {
									Integer count = groupsNoClients.get(initial);
									if(count == null) {
										groupsNoClients.put(initial, 1);
									} else {
										groupsNoClients.put(initial, count++);										
									}
									countedGroups.add(initial);
								}
								//Count zone dimension - heuristics : number of chars with the same potential color
								Map<Set<TypeReference>,Integer> set2zone = client2set2zone.get(client);
								if(set2zone == null) {
									set2zone = new HashMap<Set<TypeReference>,Integer>();
									client2set2zone.put(client,set2zone);
								}
								int offsetStart = theClientWala.getSourcePosition(i).getFirstOffset();
								int offsetEnd = theClientWala.getSourcePosition(i).getLastOffset();
								int dim = offsetEnd - offsetStart + 1;
								if(context.getJoinedChars((SSAInstruction)instrTab[i]) != null) {
									dim += context.getJoinedChars((SSAInstruction)instrTab[i]).size();
								}
								Integer lastDim = set2zone.get(initial) == null ? 0 : set2zone.get(initial);
								dim+=lastDim;
								set2zone.put(initial, dim);
							}
						}
					}
				}
			}
			
			//Filter the groups
			List<Set<TypeReference>> renderedGroups = new ArrayList<Set<TypeReference>>();
			renderedGroups.addAll(groups);
			if(groups.size() > 8) {
				Comparator<Set<TypeReference>> cmp = null;
				if(filter_type == 0) {
					//Top 8 Prevalent 
					cmp = new Comparator<Set<TypeReference>>() {
						public int compare(Set<TypeReference> o1, Set<TypeReference> o2) {
							int no1 = groupsNoClients.get(o1);
							int no2 = groupsNoClients.get(o2);
							if(no1 == no2) {
								return o2.size() - o1.size();
							} else {
								return no2 - no1;
							}
						}
					};
				} else if(filter_type == 1) {
					//Top 8 Largest
					cmp = new Comparator<Set<TypeReference>>() {
						public int compare(Set<TypeReference> o1, Set<TypeReference> o2) {
							return o2.size() - o1.size();
						}
					};
				}
				Collections.sort(renderedGroups,cmp);
				int index = 7;
				while(index > 0 && cmp.compare(renderedGroups.get(index),renderedGroups.get(index + 1)) == 0) {
					index--;
				}
				renderedGroups = renderedGroups.subList(0, index + 1);
				//Ensures that group order is similar from a run to another run; this preserves the color assignment
				//For potential performance issues, this sorting is separated from the previous one 
				//TODO: Group should be filtered first according to the name in order to ensure consistent colors between type of filters
				final Comparator<Set<TypeReference>> compar = cmp; 
				Collections.sort(renderedGroups,new Comparator<Set<TypeReference>>() {
					public int compare(Set<TypeReference> o1, Set<TypeReference> o2) {
						int rez = compar.compare(o1, o2);
						if(rez == 0) {
							Comparator<TypeReference> secondCmp = new Comparator<TypeReference>() {
								public int compare(TypeReference o1, TypeReference o2) {
									return o1.getName().toString().compareTo(o2.getName().toString());
								}
							};
							List<TypeReference> l1 = new ArrayList<TypeReference>();
							l1.addAll(o1);
							List<TypeReference> l2 = new ArrayList<TypeReference>();
							l2.addAll(o2);
							Collections.sort(l1,secondCmp);
							Collections.sort(l2,secondCmp);
							String r1 = "", r2 = "";
							for(TypeReference tr : l1) {
								r1+=tr.getName().toString();
							}
							for(TypeReference tr : l2) {
								r2+=tr.getName().toString();
							}
							return r1.compareTo(r2);
						} else {
							return rez;
						}
					}					
				});
			}
			
			//Assign colors to groups
			//There should be maximum 8 groups here
			Map<Set<TypeReference>,Color> groups2Color = new HashMap<Set<TypeReference>,Color>();
			Map<Integer,Set<TypeReference>> color2group = new HashMap<Integer,Set<TypeReference>>();
			int cColor = 0;
			for(Set<TypeReference> group : renderedGroups) {
				if(group.equals(theWalaCone) && group.size() != 1) {
					groups2Color.put(group,Color.RED);
					color2group.put(Color.RED.getRGB(), group);
				} else {
					groups2Color.put(group,colorList[cColor++]);
					color2group.put(colorList[cColor-1].getRGB(), group);				
				}
			}
			
			//Draw the clients
			HashMap<AbstractEntityInterface,AbstractNumericalCommand> client2command = new HashMap<AbstractEntityInterface,AbstractNumericalCommand>();
			for(int index = 0; index < clients.size(); index++) {
				
				if(monitor.isCanceled()) throw new MonitorCanceledException();
				monitor.worked(1);
				AbstractEntityInterface client = clients.get(index);
				
				final HashMap<Integer,Integer> char2metric = new HashMap<Integer,Integer>();

				//Find client Eclipse representation
				Wrapper wrappedClient =  (Wrapper)client;
				IMethod theClient = (IMethod)wrappedClient.getElement();

				//Find the Wala representation
				AstMethod theClientWala = RepresentationsConversionUtilities.eclipse2wala(theClient,ch);

				//Locate context
				InputContext context = client2context.get(client);
				
				Object[] instrTab = theClientWala.cfg().getInstructions();
				Iterator<IBasicBlock> bbIt = depthFirstOrder(theClientWala.cfg().entry(),theClientWala,new HashSet<Integer>(),new ArrayList<IBasicBlock>()).iterator();
				while(bbIt.hasNext()) {
					IBasicBlock bb = (IBasicBlock)bbIt.next();
					if(bb.getFirstInstructionIndex() < 0) continue;
					for(int i = bb.getFirstInstructionIndex(); i <= bb.getLastInstructionIndex(); i++) {
						if(theClientWala.getSourcePosition(i) == null) continue;
						if(instrTab[i] instanceof SSAGotoInstruction) continue;
						if(client2discriminatingInstr.get(client).contains(i)) {
							//Color the discriminating code in dark gray
							int offsetStart = theClientWala.getSourcePosition(i).getFirstOffset();
							int offsetEnd = theClientWala.getSourcePosition(i).getLastOffset();
							for(int j = offsetStart; j <= offsetEnd; j++) {
								Integer lastColor = char2metric.get(j);
								if(lastColor == null)								
									char2metric.put(j,Color.GRAY.getRGB());
							}
							if(context.getJoinedChars((SSAInstruction)instrTab[i]) != null) {
								for(Integer j : context.getJoinedChars((SSAInstruction)instrTab[i])) {
									Integer lastColor = char2metric.get(j);
									if(lastColor == null)								
										char2metric.put(j,Color.GRAY.getRGB());
								}
							}
							continue;
						}
						Map<Integer,TypesSet> var2types = instr2sca.get((SSAInstruction)instrTab[i]).getVar2Types();
						for(Integer use : var2types.keySet()) {			
							if(!context.isInScope(use,i)) continue;
							Set<TypeReference> initial = var2types.get(use).toSet();
							if(!initial.equals(theWalaCone)) {
								Set<TypeReference> varTypes = new HashSet<TypeReference>();
								varTypes.addAll(initial);
								varTypes.retainAll(theWalaCone);
								initial = varTypes;
							}
							Color color = groups2Color.get(initial);
							if(color == null) continue;
							int offsetStart = theClientWala.getSourcePosition(i).getFirstOffset();
							int offsetEnd = theClientWala.getSourcePosition(i).getLastOffset();
							for(int j = offsetStart; j <= offsetEnd; j++) {
								Integer lastColor = char2metric.get(j);
								if(lastColor == null) {
									char2metric.put(j, color.getRGB());
								} else if(lastColor == Color.GRAY.getRGB()) {
									continue;
								} else {
									//Use the color of the smallest zone
									Map<Set<TypeReference>,Integer> set2zone = client2set2zone.get(client);
									if(set2zone != null) {
										Set<TypeReference> zs1 = color2group.get(color.getRGB());
										Set<TypeReference> zs2 = color2group.get(lastColor);
										Integer z1 = set2zone.get(zs1);
										Integer z2 = set2zone.get(zs2);
										if(z2 > z1) {
											char2metric.put(j, color.getRGB());											
										}
									}
								}
							}
							if(context.getJoinedChars((SSAInstruction)instrTab[i]) != null) {
								for(Integer j : context.getJoinedChars((SSAInstruction)instrTab[i])) {
									Integer lastColor = char2metric.get(j);								
									if(lastColor == null) {
										char2metric.put(j, color.getRGB());
									} else if(lastColor == Color.GRAY.getRGB()) {
										continue;
									} else {
										//Use the color of the smallest zone
										Map<Set<TypeReference>,Integer> set2zone = client2set2zone.get(client);
										if(set2zone != null) {
											Set<TypeReference> zs1 = color2group.get(color.getRGB());
											Set<TypeReference> zs2 = color2group.get(lastColor);
											Integer z1 = set2zone.get(zs1);
											Integer z2 = set2zone.get(zs2);
											if(z2 > z1) {
												char2metric.put(j, color.getRGB());											
											}
										}
									}
								}
							}
						}
					}
				}
				//TODO: catch blocks are draw in one single color
				//TODO: try characters are not included in any instruction
				client2command.put(client, new AbstractNumericalCommand() {
					public double execute() {
						Integer color = char2metric.get(receiver);
						if(color == null) return Color.LIGHT_GRAY.getRGB();
						return color;
					}
				});
			}
			
			Map<Set<AbstractEntityInterface>,Color> groups2colorCodePro = new HashMap<Set<AbstractEntityInterface>,Color>();
			for(Set<TypeReference> wT: groups2Color.keySet()) {
				HashSet<AbstractEntityInterface> cpSet = new HashSet<AbstractEntityInterface>();
				for(TypeReference tr : wT) {
					Wrapper we = Wrapper.createInstance(RepresentationsConversionUtilities.wala2eclipse(tr, ch, ((IType)((Wrapper)theBaseClass).getElement()).getJavaProject()));
					we.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
					cpSet.add(we);
				}
				groups2colorCodePro.put(cpSet,groups2Color.get(wT));
			}

			return new Map[] {client2command,groups2colorCodePro};
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		} catch (ClassHierarchyException e) {
			e.printStackTrace();
			return null;
		} catch (CancelException e) {
			e.printStackTrace();
			return null;
		} catch(MonitorCanceledException e) {
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
