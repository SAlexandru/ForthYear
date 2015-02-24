package gui;

import models.OuterMethodModel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTextArea;


public class ComparisonResultWindow
	{
	private JFrame window;
	
	public ComparisonResultWindow(Stack<Object> result)
		{
		window=new JFrame("Comparison result");
		window.setSize(new Dimension(500,300));
		window.setLocationRelativeTo(null);
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setSize(new Dimension(450,250));
		textArea.setLineWrap(true);
	    window.add(textArea);
	    window.setVisible(true);
	    String text;
	    
   	    if ((boolean)result.pop()==false)
   		   {
   		   textArea.setText("The 'THEN' block from this method has no equals in other methods");
   		   }
   	    else
   		   {
   		   text="The 'THEN' block from the method "+((OuterMethodModel)result.pop()).getCustomName()+
   				   " is the same with the 'THEN' block of the following methods: ";
   		   while (!result.isEmpty())
   			   text=text+"\n - "+((OuterMethodModel)result.pop()).getCustomName();
   		   textArea.setText(text);
   		   }
		}
	}
