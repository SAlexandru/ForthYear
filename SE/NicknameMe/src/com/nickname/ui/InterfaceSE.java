package com.nickname.ui;


import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfaceSE extends JFrame {

	private JPanel contentPane;
	private List<List<JRadioButton>> groupsOfButtons = new ArrayList<List<JRadioButton>>();
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane(contentPane);
		contentPane.setLayout(new GridLayout(70, 0, 0, 0));
		
		createTitle();
		
		createFirstQuestion();
		
		createSecondQuestion();
		
		createThirdQuestion();
		
		createFourthQuestion();
		
		createFifthQuestion();
		
		createSixthQuestion();
		
		createSeventhQuestion();	
		
		createEigthQuestion();
		
		createNinthQuestion();	
		
		createTenthQuestion();

		createSubmitButton();
		
		setContentPane(scrollPane);
	}
	
	private void createTitle() {
		JLabel lblNewLabel_9 = new JLabel("TODO: add title *with glitter*");
		contentPane.add(lblNewLabel_9);
	}
	
	private void createFirstQuestion() {
		JLabel lblNewLabel = new JLabel("1.What is your age?");
		Font font = lblNewLabel.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize() + 5);
		lblNewLabel.setFont(boldFont);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("under 15");
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("15-25");
		contentPane.add(rdbtnNewRadioButton_3);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("26-40");
		contentPane.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("41-60");
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("over 60	");
		contentPane.add(rdbtnNewRadioButton_4);
		
		ButtonGroup bg = new ButtonGroup();
		List<JRadioButton> firstQuestionGroup = new ArrayList<JRadioButton>();
		firstQuestionGroup.add(rdbtnNewRadioButton_1);
		firstQuestionGroup.add(rdbtnNewRadioButton_2);
		firstQuestionGroup.add(rdbtnNewRadioButton_3);
		firstQuestionGroup.add(rdbtnNewRadioButton_4);
		firstQuestionGroup.add(rdbtnNewRadioButton);
		
		groupsOfButtons.add(firstQuestionGroup);
		
		for (JRadioButton jRadioButton : firstQuestionGroup) {
			bg.add(jRadioButton);
		}
	}
	
	private void createSecondQuestion() {
		JLabel lblNewLabel_1 = new JLabel("2.Which group contains the first letter of your name?");
		Font font1 = lblNewLabel_1.getFont();
		Font boldFont1 = new Font(font1.getFontName(), Font.BOLD, font1.getSize() + 5);
		lblNewLabel_1.setFont(boldFont1);
		contentPane.add(lblNewLabel_1);
		
		JRadioButton rdbtnAh = new JRadioButton("A-H");
		contentPane.add(rdbtnAh);
		
		JRadioButton rdbtnIm = new JRadioButton("I-M");
		contentPane.add(rdbtnIm);
		
		JRadioButton rdbtnNr = new JRadioButton("N-R");
		contentPane.add(rdbtnNr);
		
		JRadioButton rdbtnSv = new JRadioButton("S-V");
		contentPane.add(rdbtnSv);
		
		JRadioButton rdbtnWz = new JRadioButton("W-Z");
		contentPane.add(rdbtnWz);
		
		ButtonGroup bg2 = new ButtonGroup();
		
		List<JRadioButton> secondQuestionGroup = new ArrayList<JRadioButton>();
		secondQuestionGroup.add(rdbtnAh);
		secondQuestionGroup.add(rdbtnIm);
		secondQuestionGroup.add(rdbtnNr);
		secondQuestionGroup.add(rdbtnSv);
		secondQuestionGroup.add(rdbtnWz);
		
		groupsOfButtons.add(secondQuestionGroup);
		
		for (JRadioButton jRadioButton : secondQuestionGroup) {
			bg2.add(jRadioButton);
		}
	}
	
	private void createThirdQuestion() {
		JLabel lblWhatIsYour = new JLabel("3.What is your drink of choice?");
		Font font2 = lblWhatIsYour.getFont();
		Font boldFont2 = new Font(font2.getFontName(), Font.BOLD, font2.getSize() + 5);
		lblWhatIsYour.setFont(boldFont2);
		contentPane.add(lblWhatIsYour);
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("water");
		contentPane.add(rdbtnNewRadioButton_5);
		
		JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("whiskey");
		contentPane.add(rdbtnNewRadioButton_6);
		
		JRadioButton rdbtnNewRadioButton_7 = new JRadioButton("soda");
		contentPane.add(rdbtnNewRadioButton_7);
		
		JRadioButton rdbtnNewRadioButton_8 = new JRadioButton("gin and tonic");
		contentPane.add(rdbtnNewRadioButton_8);
		
		JRadioButton rdbtnNewRadioButton_9 = new JRadioButton("cocktail");
		contentPane.add(rdbtnNewRadioButton_9);
		
		ButtonGroup bg3 = new ButtonGroup();
		
		List<JRadioButton> thirdQuestionGroup = new ArrayList<JRadioButton>();
		thirdQuestionGroup.add(rdbtnNewRadioButton_5);
		thirdQuestionGroup.add(rdbtnNewRadioButton_6);
		thirdQuestionGroup.add(rdbtnNewRadioButton_7);
		thirdQuestionGroup.add(rdbtnNewRadioButton_8);
		thirdQuestionGroup.add(rdbtnNewRadioButton_9);

		groupsOfButtons.add(thirdQuestionGroup);
		
		for (JRadioButton jRadioButton : thirdQuestionGroup) {
			bg3.add(jRadioButton);
		}
	}
	
	private void createFourthQuestion() {
		JLabel lblNewLabel_2 = new JLabel("4.What kind of movie do you most enjoy watching?");
		Font font3 = lblNewLabel_2.getFont();
		Font boldFont3 = new Font(font3.getFontName(), Font.BOLD, font3.getSize() + 5);
		lblNewLabel_2.setFont(boldFont3);
		contentPane.add(lblNewLabel_2);
		
		JRadioButton rdbtnNewRadioButton_10 = new JRadioButton("action");
		contentPane.add(rdbtnNewRadioButton_10);
		
		JRadioButton rdbtnNewRadioButton_11 = new JRadioButton("horror");
		contentPane.add(rdbtnNewRadioButton_11);
		
		JRadioButton rdbtnNewRadioButton_12 = new JRadioButton("comedy");
		contentPane.add(rdbtnNewRadioButton_12);
		
		JRadioButton rdbtnNewRadioButton_13 = new JRadioButton("animation");
		contentPane.add(rdbtnNewRadioButton_13);
		
		JRadioButton rdbtnNewRadioButton_14 = new JRadioButton("drama");
		contentPane.add(rdbtnNewRadioButton_14);
		
		ButtonGroup bg4 = new ButtonGroup();
		
		List<JRadioButton> fourthQuestionGroup = new ArrayList<JRadioButton>();
		fourthQuestionGroup.add(rdbtnNewRadioButton_10);
		fourthQuestionGroup.add(rdbtnNewRadioButton_11);
		fourthQuestionGroup.add(rdbtnNewRadioButton_12);
		fourthQuestionGroup.add(rdbtnNewRadioButton_13);
		fourthQuestionGroup.add(rdbtnNewRadioButton_14);

		groupsOfButtons.add(fourthQuestionGroup);
		
		for (JRadioButton jRadioButton : fourthQuestionGroup) {
			bg4.add(jRadioButton);
		}
	}

	private void createFifthQuestion() {
		JLabel lblNewLabel_3 = new JLabel("5.Which of these activities is your MOST favorite?");
		Font font4 = lblNewLabel_3.getFont();
		Font boldFont4 = new Font(font4.getFontName(), Font.BOLD, font4.getSize() + 5);
		lblNewLabel_3.setFont(boldFont4);
		contentPane.add(lblNewLabel_3);
		
		JRadioButton rdbtnNewRadioButton_15 = new JRadioButton("watch movies");
		contentPane.add(rdbtnNewRadioButton_15);
		
		JRadioButton rdbtnNewRadioButton_16 = new JRadioButton("shopping");
		contentPane.add(rdbtnNewRadioButton_16);
		
		JRadioButton rdbtnNewRadioButton_17 = new JRadioButton("dancing");
		contentPane.add(rdbtnNewRadioButton_17);
		
		JRadioButton rdbtnNewRadioButton_19 = new JRadioButton("sleeping");
		contentPane.add(rdbtnNewRadioButton_19);
		
		JRadioButton rdbtnNewRadioButton_18 = new JRadioButton("reading");
		contentPane.add(rdbtnNewRadioButton_18);
		
		ButtonGroup bg5 = new ButtonGroup();
		
		List<JRadioButton> fifthQuestionGroup = new ArrayList<JRadioButton>();
		fifthQuestionGroup.add(rdbtnNewRadioButton_15);
		fifthQuestionGroup.add(rdbtnNewRadioButton_16);
		fifthQuestionGroup.add(rdbtnNewRadioButton_17);
		fifthQuestionGroup.add(rdbtnNewRadioButton_19);
		fifthQuestionGroup.add(rdbtnNewRadioButton_18);

		groupsOfButtons.add(fifthQuestionGroup);
		
		for (JRadioButton jRadioButton : fifthQuestionGroup) {
			bg5.add(jRadioButton);
		}
	}
	
	private void createSixthQuestion() {
		JLabel lblNewLabel_4 = new JLabel("6.You believe the most important part of life is...");
		Font font5 = lblNewLabel_4.getFont();
		Font boldFont5 = new Font(font5.getFontName(), Font.BOLD, font5.getSize() + 5);
		lblNewLabel_4.setFont(boldFont5);
		contentPane.add(lblNewLabel_4);
		
		JRadioButton rdbtnNewRadioButton_20 = new JRadioButton("being happy");
		contentPane.add(rdbtnNewRadioButton_20);
		
		JRadioButton rdbtnNewRadioButton_21 = new JRadioButton("hanging out with friends");
		contentPane.add(rdbtnNewRadioButton_21);
		
		JRadioButton rdbtnNewRadioButton_22 = new JRadioButton("living well and getting rich");
		contentPane.add(rdbtnNewRadioButton_22);
		
		JRadioButton rdbtnNewRadioButton_23 = new JRadioButton("doing things your way");
		contentPane.add(rdbtnNewRadioButton_23);
		
		JRadioButton rdbtnNewRadioButton_24 = new JRadioButton("finding true love");
		contentPane.add(rdbtnNewRadioButton_24);
		
		ButtonGroup bg6 = new ButtonGroup();
		
		List<JRadioButton> sixthQuestionGroup = new ArrayList<JRadioButton>();
		sixthQuestionGroup.add(rdbtnNewRadioButton_20);
		sixthQuestionGroup.add(rdbtnNewRadioButton_21);
		sixthQuestionGroup.add(rdbtnNewRadioButton_22);
		sixthQuestionGroup.add(rdbtnNewRadioButton_23);
		sixthQuestionGroup.add(rdbtnNewRadioButton_24);

		groupsOfButtons.add(sixthQuestionGroup);
		
		for (JRadioButton jRadioButton : sixthQuestionGroup) {
			bg6.add(jRadioButton);
		}
		
	}
	
	private void createSeventhQuestion() {
		JLabel lblNewLabel_5 = new JLabel("7.On a windy day, a rooster lays an egg on a roof, which side of the roof does the egg roll down?");
		Font font6 = lblNewLabel_5.getFont();
		Font boldFont6 = new Font(font6.getFontName(), Font.BOLD, font6.getSize() + 5);
		lblNewLabel_5.setFont(boldFont6);
		contentPane.add(lblNewLabel_5);
		
		JRadioButton rdbtnNewRadioButton_25 = new JRadioButton("left");
		contentPane.add(rdbtnNewRadioButton_25);
		
		JRadioButton rdbtnNewRadioButton_26 = new JRadioButton("right");
		contentPane.add(rdbtnNewRadioButton_26);
		
		JRadioButton rdbtnNewRadioButton_27 = new JRadioButton("there is no egg");
		contentPane.add(rdbtnNewRadioButton_27);
		
		JRadioButton rdbtnNewRadioButton_28 = new JRadioButton("whichever direction the wind was blowing");
		contentPane.add(rdbtnNewRadioButton_28);
		
		JRadioButton rdbtnNewRadioButton_29 = new JRadioButton("none of the above");
		contentPane.add(rdbtnNewRadioButton_29);
		
		ButtonGroup bg7 = new ButtonGroup();
		
		List<JRadioButton> seventhQuestionGroup = new ArrayList<JRadioButton>();
		seventhQuestionGroup.add(rdbtnNewRadioButton_25);
		seventhQuestionGroup.add(rdbtnNewRadioButton_26);
		seventhQuestionGroup.add(rdbtnNewRadioButton_27);
		seventhQuestionGroup.add(rdbtnNewRadioButton_28);
		seventhQuestionGroup.add(rdbtnNewRadioButton_29);

		groupsOfButtons.add(seventhQuestionGroup);
		
		for (JRadioButton jRadioButton : seventhQuestionGroup) {
			bg7.add(jRadioButton);
		}
	}
	
	private void createEigthQuestion() {
		JLabel lblNewLabel_6 = new JLabel("8.If a leaf falls to the ground in a forest and no one hears it, does it make a sound?");
		Font font7= lblNewLabel_6.getFont();
		Font boldFont7 = new Font(font7.getFontName(), Font.BOLD, font7.getSize() + 5);
		lblNewLabel_6.setFont(boldFont7);
		contentPane.add(lblNewLabel_6);
		
		JRadioButton rdbtnNewRadioButton_30 = new JRadioButton("yes");
		contentPane.add(rdbtnNewRadioButton_30);
		
		JRadioButton rdbtnNewRadioButton_31 = new JRadioButton("no");
		contentPane.add(rdbtnNewRadioButton_31);
		
		JRadioButton rdbtnNewRadioButton_32 = new JRadioButton("depends on how heavy the leaf is");
		contentPane.add(rdbtnNewRadioButton_32);
		
		JRadioButton rdbtnNewRadioButton_33 = new JRadioButton("depends on where it landed");
		contentPane.add(rdbtnNewRadioButton_33);
		
		JRadioButton rdbtnNewRadioButton_34 = new JRadioButton("depends if there actually IS a leaf");
		contentPane.add(rdbtnNewRadioButton_34);
		
		ButtonGroup bg8 = new ButtonGroup();
		
		List<JRadioButton> eigthQuestionGroup = new ArrayList<JRadioButton>();
		eigthQuestionGroup.add(rdbtnNewRadioButton_30);
		eigthQuestionGroup.add(rdbtnNewRadioButton_31);
		eigthQuestionGroup.add(rdbtnNewRadioButton_32);
		eigthQuestionGroup.add(rdbtnNewRadioButton_33);
		eigthQuestionGroup.add(rdbtnNewRadioButton_34);

		groupsOfButtons.add(eigthQuestionGroup);
		
		for (JRadioButton jRadioButton : eigthQuestionGroup) {
			bg8.add(jRadioButton);
		}
	}
	
	private void createNinthQuestion() {
		JLabel lblNewLabel_7 = new JLabel("9.If your friends dared to do something completely crazy, you would:");
		Font font8 = lblNewLabel_7.getFont();
		Font boldFont8 = new Font(font8.getFontName(), Font.BOLD, font8.getSize() + 5);
		lblNewLabel_7.setFont(boldFont8);
		contentPane.add(lblNewLabel_7);
		
		JRadioButton rdbtnNewRadioButton_35 = new JRadioButton("Go for it! Yolo, right?");
		contentPane.add(rdbtnNewRadioButton_35);
		
		JRadioButton rdbtnNewRadioButton_36 = new JRadioButton("I don't really get dared to do stuff like that");
		contentPane.add(rdbtnNewRadioButton_36);
		
		JRadioButton rdbtnNewRadioButton_37 = new JRadioButton("shout out: \"I double dare you!\"");
		contentPane.add(rdbtnNewRadioButton_37);
		
		JRadioButton rdbtnNewRadioButton_38 = new JRadioButton("I make the dares in my group");
		contentPane.add(rdbtnNewRadioButton_38);
		
		JRadioButton rdbtnNewRadioButton_39 = new JRadioButton("run away, screaming");
		contentPane.add(rdbtnNewRadioButton_39);
		
		ButtonGroup bg9 = new ButtonGroup();
		
		List<JRadioButton> ninthQuestionGroup = new ArrayList<JRadioButton>();
		ninthQuestionGroup.add(rdbtnNewRadioButton_35);
		ninthQuestionGroup.add(rdbtnNewRadioButton_36);
		ninthQuestionGroup.add(rdbtnNewRadioButton_37);
		ninthQuestionGroup.add(rdbtnNewRadioButton_38);
		ninthQuestionGroup.add(rdbtnNewRadioButton_39);

		groupsOfButtons.add(ninthQuestionGroup);
		
		for (JRadioButton jRadioButton : ninthQuestionGroup) {
			bg9.add(jRadioButton);
		}
	}
	
	private void createTenthQuestion() {
		JLabel lblNewLabel_8 = new JLabel("10.What quote do you prefer:");
		Font font9 = lblNewLabel_8.getFont();
		Font boldFont9 = new Font(font9.getFontName(), Font.BOLD, font9.getSize() + 5);
		lblNewLabel_8.setFont(boldFont9);
		contentPane.add(lblNewLabel_8);
		
		JRadioButton rdbtnNewRadioButton_40 = new JRadioButton("Good things come in small packages");
		contentPane.add(rdbtnNewRadioButton_40);
		
		JRadioButton rdbtnNewRadioButton_41 = new JRadioButton("One smile can hide a million tears");
		contentPane.add(rdbtnNewRadioButton_41);
		
		JRadioButton rdbtnNewRadioButton_42 = new JRadioButton("I know crazy and I'm not afraid to use it!");
		contentPane.add(rdbtnNewRadioButton_42);
		
		JRadioButton rdbtnNewRadioButton_43 = new JRadioButton("Funny how we remember all these lyrics but nothing for exams");
		contentPane.add(rdbtnNewRadioButton_43);
		
		JRadioButton rdbtnNewRadioButton_44 = new JRadioButton("I'm sexy and I know it!");
		contentPane.add(rdbtnNewRadioButton_44);
		
		ButtonGroup bg10 = new ButtonGroup();
		
		List<JRadioButton> tenthQuestionGroup = new ArrayList<JRadioButton>();
		tenthQuestionGroup.add(rdbtnNewRadioButton_40);
		tenthQuestionGroup.add(rdbtnNewRadioButton_41);
		tenthQuestionGroup.add(rdbtnNewRadioButton_42);
		tenthQuestionGroup.add(rdbtnNewRadioButton_43);
		tenthQuestionGroup.add(rdbtnNewRadioButton_44);

		groupsOfButtons.add(tenthQuestionGroup);
		
		for (JRadioButton jRadioButton : tenthQuestionGroup) {
			bg10.add(jRadioButton);
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
                    System.out.println("No answers where chosen!");
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
		if(answerCounter == 10) {
			return true;
		}
		else {
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
