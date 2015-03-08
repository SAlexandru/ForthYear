package lrg.patrools.visualizations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.insider.visualizations.AbstractVisualizationOperations;
import lrg.insider.visualizations.MonitorCanceledException;
import lrg.jMondrian.access.Command;
import lrg.jMondrian.commands.AbstractNumericalCommand;
import lrg.jMondrian.commands.AbstractStringCommand;
import lrg.jMondrian.figures.Figure;
import lrg.jMondrian.layouts.ScatterPlotLayout;
import lrg.jMondrian.painters.RectangleNodePainter;
import lrg.jMondrian.util.CommandColor;
import lrg.patrools.util.Placeholder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import plugins.Wrapper;

public class MicroprintBuilder extends AbstractVisualizationOperations {

	private Figure lastBuiltFigure = null;
	private AbstractNumericalCommand cColor = new AbstractNumericalCommand() {
		public double execute() {
			return Color.LIGHT_GRAY.getRGB();
		}		
	};
	private final static int CHAR_WIDTH = 4;
	private final static int CHAR_HEIGHT = 4;
	private final static int NO_CHARS_PER_TAB = 4;
	
	public void setCharCommand(AbstractNumericalCommand cmd) {
		cColor = cmd;
	}
	
	@Override
	public void buildFigure(AbstractEntityInterface theEntity, IProgressMonitor monitor) throws MonitorCanceledException {
		try {			
			IMethod theMethod = (IMethod)((Wrapper)theEntity).getElement();
			String sourceCode = theMethod.getSource();
			int sourceOffset = theMethod.getSourceRange().getOffset();

			//Initial corrections
			int i = 0;
			String addedSpaces = "";
			while(sourceCode.charAt(i) != '{') {
				if(sourceCode.charAt(i) == '/' && sourceCode.charAt(i+1) == '/') {
					while(sourceCode.charAt(i)!='\n') i++;
					i++;
					addedSpaces="";
				} else if(sourceCode.charAt(i) == '/' && sourceCode.charAt(i+1) == '*') {
					while(sourceCode.charAt(i)!='*' || sourceCode.charAt(i+1)!='/') i++;
					i+=2;
					addedSpaces="";
				} else if(sourceCode.charAt(i) == '\n') {
					addedSpaces = "";
					i++;
				} else if(sourceCode.charAt(i) == '\t') {
					for(int j = 0; j < NO_CHARS_PER_TAB; j++)
					addedSpaces += " ";
					i++;
				} else {
					addedSpaces += " ";
					i++;
				}				
			}
			
			sourceOffset += i;
			sourceCode = addedSpaces + sourceCode.substring(i);
			
			final ArrayList<Placeholder> elements = new ArrayList<Placeholder>();
			final HashMap<Placeholder,Integer> xPos = new HashMap<Placeholder,Integer>();
			final HashMap<Placeholder,Integer> yPos = new HashMap<Placeholder,Integer>();
			final HashMap<Placeholder,Integer> lenPos = new HashMap<Placeholder,Integer>();
			final HashMap<Placeholder,Double> colorPos = new HashMap<Placeholder,Double>();
			String codeLines[] = sourceCode.split("\n");
			int eliminatedLines = 0;
			boolean start = false;
			
			for(int line = 0; line < codeLines.length; line++) {
				//Remove white spaces from the beginning
				int firstNonWhiteSpaceIndex = 0;
				int columnStartsAt = 0;
				while(firstNonWhiteSpaceIndex < codeLines[line].length()) {
					char currentChar = codeLines[line].charAt(firstNonWhiteSpaceIndex);
					if(currentChar == '{') {
						start = true;
					}
					if(start) {
						sourceOffset++;
					}
					if(currentChar == ' ' || currentChar == '\r') {
						columnStartsAt++;
						firstNonWhiteSpaceIndex++;
					} else if(currentChar == '\t') {
						columnStartsAt+=NO_CHARS_PER_TAB;
						firstNonWhiteSpaceIndex++;						
					}
					else break;
				}
				//If the line is empty eliminate it
				if(firstNonWhiteSpaceIndex == codeLines[line].length()) {
					eliminatedLines++;
					sourceOffset++; //for new line
					continue;
				}
				//Remove white spaces from tail
				int lastNonWhiteSpaceIndex = codeLines[line].length() - 1;
				while(codeLines[line].charAt(lastNonWhiteSpaceIndex) == ' ' || codeLines[line].charAt(lastNonWhiteSpaceIndex) == '\t' || codeLines[line].charAt(lastNonWhiteSpaceIndex) == '\r') {
					lastNonWhiteSpaceIndex--;
				}
				//Remove line comments
				for(int ws = firstNonWhiteSpaceIndex; ws < lastNonWhiteSpaceIndex; ws++) {
					if(codeLines[line].charAt(ws) == '/' && codeLines[line].charAt(ws) == '/') {
						lastNonWhiteSpaceIndex = ws - 1;
						if(lastNonWhiteSpaceIndex > firstNonWhiteSpaceIndex)
						while(codeLines[line].charAt(lastNonWhiteSpaceIndex) == ' ' || codeLines[line].charAt(lastNonWhiteSpaceIndex) == '\t' || codeLines[line].charAt(lastNonWhiteSpaceIndex) == '\r') {
							lastNonWhiteSpaceIndex--;
						}
						break;
					}
				}
				if(firstNonWhiteSpaceIndex > lastNonWhiteSpaceIndex) {
					eliminatedLines++;
					sourceOffset+=codeLines[line].length() - lastNonWhiteSpaceIndex - 1;
					continue;
				}
				//Processing the line
				String str = "";
				double lastColor = 0;
				for(int column = firstNonWhiteSpaceIndex; column <= lastNonWhiteSpaceIndex; column++) {
					char currentChar = codeLines[line].charAt(column);
					cColor.setReceiver(sourceOffset);
					double currentColor = cColor.execute();
					if(column == firstNonWhiteSpaceIndex) {
						str+=currentChar;						
						lastColor = currentColor;
					} else if(currentChar == ';') {
						str+=currentChar;												
					} else if(currentColor == lastColor) {
						str+=currentChar;						
					} else {
						Placeholder obj = new Placeholder(str);
						elements.add(obj);
						xPos.put(obj, columnStartsAt * CHAR_WIDTH);
						yPos.put(obj, line * CHAR_HEIGHT);
						lenPos.put(obj, str.length() * CHAR_WIDTH);
						colorPos.put(obj, lastColor);
						columnStartsAt+=str.length();
						str=""+currentChar;
						lastColor=currentColor;
					}
					if(column == lastNonWhiteSpaceIndex) {
						Placeholder obj = new Placeholder(str);
						elements.add(obj);
						xPos.put(obj, columnStartsAt * CHAR_WIDTH);
						yPos.put(obj, line * CHAR_HEIGHT);
						lenPos.put(obj, str.length() * CHAR_WIDTH);
						colorPos.put(obj, lastColor);						
					}
					sourceOffset++; //Offset of the next char
				}
				sourceOffset+= codeLines[line].length() - lastNonWhiteSpaceIndex - 1;
			}
			lastBuiltFigure = new Figure();
			lastBuiltFigure.nodesUsing(elements, new RectangleNodePainter(0,CHAR_HEIGHT,false,true)
				.x(new AbstractNumericalCommand() {
					public double execute() {
						return xPos.get(receiver);
					}
				})
				.y(new AbstractNumericalCommand() {
					public double execute() {
						return yPos.get(receiver);
					}
				})
				.width(new AbstractNumericalCommand() {
					public double execute() {
						return lenPos.get(receiver);
					}
				})
				.name(new AbstractStringCommand("Name") {
					public String execute() {
						return "'"+receiver.toString()+"'";
					}
				}).color(new AbstractNumericalCommand() {
					public double execute() {
						return colorPos.get(receiver);
					}					
				}));
			lastBuiltFigure.layout(new ScatterPlotLayout());
		} catch (JavaModelException e) {
				e.printStackTrace();
		}
	}

	@Override
	public Figure getFigure() {
		return lastBuiltFigure;
	}

}
