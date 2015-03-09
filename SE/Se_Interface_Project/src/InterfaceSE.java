import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;


public class InterfaceSE extends JFrame {

	private JPanel contentPane;

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
		
		JLabel lblNewLabel_9 = new JLabel("TODO: add title *with glitter*");
		contentPane.add(lblNewLabel_9);
		
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
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);
		bg.add(rdbtnNewRadioButton_3);
		bg.add(rdbtnNewRadioButton_4);
		bg.add(rdbtnNewRadioButton);
		
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
		bg2.add(rdbtnAh);
		bg2.add(rdbtnIm);
		bg2.add(rdbtnNr);
		bg2.add(rdbtnSv);
		bg2.add(rdbtnWz);
		
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
		bg3.add(rdbtnNewRadioButton_5);
		bg3.add(rdbtnNewRadioButton_6);
		bg3.add(rdbtnNewRadioButton_7);
		bg3.add(rdbtnNewRadioButton_8);
		bg3.add(rdbtnNewRadioButton_9);
		
		
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
		bg4.add(rdbtnNewRadioButton_10);
		bg4.add(rdbtnNewRadioButton_11);
		bg4.add(rdbtnNewRadioButton_12);
		bg4.add(rdbtnNewRadioButton_13);
		bg4.add(rdbtnNewRadioButton_14);
		
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
		bg5.add(rdbtnNewRadioButton_15);
		bg5.add(rdbtnNewRadioButton_16);
		bg5.add(rdbtnNewRadioButton_17);
		bg5.add(rdbtnNewRadioButton_19);
		bg5.add(rdbtnNewRadioButton_18);
		
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
		bg6.add(rdbtnNewRadioButton_20);
		bg6.add(rdbtnNewRadioButton_21);
		bg6.add(rdbtnNewRadioButton_22);
		bg6.add(rdbtnNewRadioButton_23);
		bg6.add(rdbtnNewRadioButton_24);
		
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
		bg7.add(rdbtnNewRadioButton_25);
		bg7.add(rdbtnNewRadioButton_26);
		bg7.add(rdbtnNewRadioButton_27);
		bg7.add(rdbtnNewRadioButton_28);
		bg7.add(rdbtnNewRadioButton_29);
		
		
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
		bg8.add(rdbtnNewRadioButton_30);
		bg8.add(rdbtnNewRadioButton_31);
		bg8.add(rdbtnNewRadioButton_32);
		bg8.add(rdbtnNewRadioButton_33);
		bg8.add(rdbtnNewRadioButton_34);
		
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
		bg9.add(rdbtnNewRadioButton_35);
		bg9.add(rdbtnNewRadioButton_36);
		bg9.add(rdbtnNewRadioButton_37);
		bg9.add(rdbtnNewRadioButton_38);
		bg9.add(rdbtnNewRadioButton_39);
		
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
		bg10.add(rdbtnNewRadioButton_40);
		bg10.add(rdbtnNewRadioButton_41);
		bg10.add(rdbtnNewRadioButton_42);
		bg10.add(rdbtnNewRadioButton_43);
		bg10.add(rdbtnNewRadioButton_44);
		
		JButton btnNewButton = new JButton("Submit Answers!");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> valuesSelected = new ArrayList<String>();
				//valuesSelected.add(arg0)
			}
		});
		contentPane.add(btnNewButton);
		
		setContentPane(scrollPane);
		
		bg10.getSelection();
	}

}
