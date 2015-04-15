package com.nickname.ui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.nickname.xml.XMLParsers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterfaceSE extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private List<List<JRadioButton>> groupsOfButtons = new ArrayList<List<JRadioButton>>();
	private static final int NUMBER_OF_ANSWERS = 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceSE frame = new InterfaceSE();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceSE() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 950, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane(contentPane);
		contentPane.setLayout(new GridLayout(70, 0, 0, 0));
		
		XMLParsers xmlParsers = null;
		
		try {
			xmlParsers = new XMLParsers("src\\com\\nickname\\xml\\ToTest.xml");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		createTitle();
		
		if(xmlParsers != null) {
			Map<String,List<String>> interfaceInput = xmlParsers.getQuestions();

			for (Map.Entry<String, List<String>> entry : interfaceInput.entrySet())
			{
				createQuestion(entry.getKey(), entry.getValue());
			}
		}

		createSubmitButton();
		
		setContentPane(scrollPane);
	}
	
	private void createTitle() {
		JLabel label = new JLabel("TODO: add title *with glitter*");
		contentPane.add(label);
	}
	
	private void createQuestion(String question, List<String> answers) {
		JLabel questionLabel = new JLabel(question);
		Font previousQuestionFont = questionLabel.getFont();
		Font questionFont = new Font(previousQuestionFont.getFontName(), Font.BOLD, previousQuestionFont.getSize() + 5);
		questionLabel.setFont(questionFont);
		contentPane.add(questionLabel);
		
		List<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		List<JRadioButton> questionGroup = new ArrayList<JRadioButton>();
		
		for(int i = 0; i<answers.size(); i++) {
			JRadioButton radioButton = new JRadioButton(answers.get(i));
			radioButtons.add(radioButton);
			contentPane.add(radioButton);
			questionGroup.add(radioButton);
		}

		groupsOfButtons.add(questionGroup);
		
		for (JRadioButton jRadioButton : questionGroup) {
			buttonGroup.add(jRadioButton);
		}
	}
	
	private JButton createSubmitButton() {
		JButton btnNewButton = new JButton("Submit Answers!");
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> answers = returnAllAnswers();
                if (null == answers) {
                    JOptionPane.showMessageDialog(null, "Please answer to all questions!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

				for (String string : answers) {
					System.out.println(string);
				}
			}
		});
		
		contentPane.add(btnNewButton);
		return btnNewButton;
	}
	
	private boolean checkIfAllAnswers() {
		int answerCounter = 0;
		for (List<JRadioButton> group : groupsOfButtons) {
			for (JRadioButton jRadioButton : group) {
				if(jRadioButton.isSelected()) {
					answerCounter++;
				}
			}
		}
		if(answerCounter == NUMBER_OF_ANSWERS) {
			return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Please select an answer to every question!", "NickName", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private List<String> returnAllAnswers() {
        if (!checkIfAllAnswers()) {
            return null;
        }
		List<String> answers = new ArrayList<String>();
		for (List<JRadioButton> group : groupsOfButtons) {
			for (JRadioButton jRadioButton : group) {
				if(jRadioButton.isSelected()) {
					answers.add(jRadioButton.getText());
				}
			}
		}
		return answers;
	}
}
