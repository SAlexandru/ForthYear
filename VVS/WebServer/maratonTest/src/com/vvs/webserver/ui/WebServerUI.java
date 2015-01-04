package com.vvs.webserver.ui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.vvs.webserver.WebServer;
import com.vvs.webserver.WebServerState;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebServerUI {

	private JFrame frame;
	private JTextField txtAddPort;
	private JTextField txtAddRootDir;
	private JTextField txtAddMtnDir;
	private JLabel lblNewServerStatus;
	private JLabel lblNewAddr;
	private JLabel lblPort;
	private JCheckBox chckbxMode;
	private JButton btnToggleButton;
	private JPanel infoPanel;
	private JPanel togglePanel;
	private JPanel configPanel;
	private JLabel lblRootcheck;
	private JLabel lblMtncheck;
	
	private WebServer srv;
	private ServerSocket skt;
	private Path root = Paths.get("./TestSite");
	private Path maintenance = Paths.get("./TestSite/maintenance");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebServerUI window = new WebServerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WebServerUI() {
		try {
			skt = new ServerSocket(10008,100);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		srv = new WebServer(skt, root, maintenance, 10);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		infoPanel = new JPanel();
		frame.getContentPane().add(infoPanel, BorderLayout.WEST);
		
		JLabel lblInfo = new JLabel("WebServer info:");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblServerStatus_1 = new JLabel("Server status:");
		
		JLabel lblNewLabel = new JLabel("Server address:");
		
		JLabel lblServerListeningPort = new JLabel("Server listening port:");
		
		lblNewServerStatus = new JLabel("Stopped");
		lblNewServerStatus.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblNewAddr = new JLabel("127.0.0.1");
		lblNewAddr.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblPort = new JLabel("10008");
		GroupLayout gl_panel = new GroupLayout(infoPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblServerListeningPort)
								.addComponent(lblNewLabel)
								.addComponent(lblServerStatus_1))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblNewServerStatus, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
									.addComponent(lblNewAddr, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
								.addComponent(lblPort, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerStatus_1)
						.addComponent(lblNewServerStatus))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewAddr))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerListeningPort)
						.addComponent(lblPort))
					.addGap(163))
		);
		infoPanel.setLayout(gl_panel);
		
		togglePanel = new JPanel();
		frame.getContentPane().add(togglePanel, BorderLayout.EAST);
		
		btnToggleButton = new JButton("Start server");
		btnToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// start server, change text of button
				if(btnToggleButton == e.getSource())
				{
					if(lblNewServerStatus.getText().equals("Stopped"))
					{
						System.out.println("stopped, now starting");
						// server was turned on. Clicking the button again would stop the server.
						btnToggleButton.setText("Stop server");
						if(chckbxMode.isSelected())
							lblNewServerStatus.setText("Maintenance");
						else
							lblNewServerStatus.setText("Running");
						togglePanel.repaint();
						infoPanel.repaint();
						try {
							srv.setState(WebServerState.RUNNING);
							chckbxMode.setEnabled(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					if(lblNewServerStatus.getText().equals("Running") || lblNewServerStatus.getText().equals("Maintenance"))
					{
						System.out.println("running, now stopping");
						// server is turned off. Clicking again should start the server
						btnToggleButton.setText("Start server");
						lblNewServerStatus.setText("Stopped");
						togglePanel.repaint();
						infoPanel.repaint();
						try {
							srv.setState(WebServerState.STOPPED);
							chckbxMode.setEnabled(false);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					lblRootcheck.setVisible(false);
					lblMtncheck.setVisible(false);
				}
			}
		});
		
		JLabel lblWebserverControl = new JLabel("WebServer control:");
		lblWebserverControl.setHorizontalAlignment(SwingConstants.CENTER);
		
		chckbxMode = new JCheckBox("Switch to maintenance mode");
		chckbxMode.setEnabled(false);
		chckbxMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// if ItemEvent.DESELECTED;
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					// server is now entering maintenance mode
					lblNewServerStatus.setText("Maintenance");
					chckbxMode.setSelected(true);
					try {
						srv.setState(WebServerState.MAINTENANCE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				if(e.getStateChange() == ItemEvent.DESELECTED)
				{
					// server is now entering maintenance mode
					lblNewServerStatus.setText("Running");
					chckbxMode.setSelected(false);
					try {
						srv.setState(WebServerState.RUNNING);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(togglePanel);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(10)
									.addComponent(btnToggleButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblWebserverControl, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGap(14)
							.addComponent(chckbxMode, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(lblWebserverControl)
					.addGap(12)
					.addComponent(btnToggleButton)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(chckbxMode)
					.addContainerGap())
		);
		togglePanel.setLayout(gl_panel_1);
		
		configPanel = new JPanel();
		frame.getContentPane().add(configPanel, BorderLayout.SOUTH);
		
		JLabel lblSrvConfig = new JLabel("WebServer configuration:");
		lblSrvConfig.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Server listening on port:");
		
		JLabel lblNewLabel_2 = new JLabel("Web root directory:");
		
		JLabel lblMaintenanceDirectory = new JLabel("Maintenance directory:");
		
		txtAddPort = new JTextField();
		txtAddPort.setColumns(10);
		txtAddPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// action is pressing enter
				 String t = txtAddPort.getText();
				 try {
					InetSocketAddress addr = new InetSocketAddress(Integer.parseInt(t));
					srv.bind(addr, 100);
					lblPort.setText(t);
					infoPanel.repaint();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame,
						    "Invalid port number, please introduce a number!",
						    "Port",
						    JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame,
						    "Cannot bind to specified port, please try another!",
						    "Port",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		txtAddRootDir = new JTextField();
		txtAddRootDir.setColumns(10);
		txtAddRootDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// action is pressing enter
				 String t = txtAddRootDir.getText();
				 File newRoot = new File(t);
				 Boolean isDir = false;
				 Boolean doesExist = newRoot.exists();
				 // show validator label
				 if (doesExist)
				 {
					 System.out.println("Exists");
					 isDir = newRoot.isDirectory();
					 if (isDir)
					 {
						 try {
							root = Paths.get(t);
							srv.setBaseDirectory(root);
							
							ImageIcon icon = new ImageIcon("ok.jpg");
							lblRootcheck.setIcon(icon);
							lblRootcheck.setVisible(true);
						} catch (IOException e1) {
							ImageIcon icon = new ImageIcon("no.jpg");
							lblRootcheck.setIcon(icon);
							lblRootcheck.setVisible(true);
						}
					 }
				 }
				 
				 if(!isDir)
				 {
						ImageIcon icon = new ImageIcon("no.jpg");
						lblRootcheck.setIcon(icon);
						lblRootcheck.setVisible(true);
				 }
			}
		});
		
		txtAddMtnDir = new JTextField();
		txtAddMtnDir.setColumns(10);
		txtAddMtnDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// action is pressing enter
				 String t = txtAddMtnDir.getText();
				 
				 File newMtn = new File(t);
				 Boolean isDir = false;
				 Boolean doesExist = newMtn.exists();
				 // show validator label
				 if (doesExist)
				 {
					 System.out.println("Exists");
					 isDir = newMtn.isDirectory();
					 if (isDir)
					 {
						maintenance = Paths.get(t);
						try {
							srv.setMaintenancePath(maintenance);
							ImageIcon icon = new ImageIcon("ok.jpg");
							lblMtncheck.setIcon(icon);
							lblMtncheck.setVisible(true);
						} catch (IOException e1) {
							ImageIcon icon = new ImageIcon("no.jpg");
							lblMtncheck.setIcon(icon);
							lblMtncheck.setVisible(true);
						}
						
					 }
				 }
				 
				 if(!isDir)
				 {
						ImageIcon icon = new ImageIcon("no.jpg");
						lblMtncheck.setIcon(icon);
						lblMtncheck.setVisible(true);
				 }
			}
		});
		
		JButton btnBrowseRoot = new JButton("...");
		btnBrowseRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser();
				openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                openFile.showOpenDialog(null);
                String t = openFile.getSelectedFile().getPath();
                txtAddRootDir.setText(t);
			}
		});
		
		JButton btnBrowseMtn = new JButton("...");
		btnBrowseMtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser();
				openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                openFile.showOpenDialog(null);
                String t = openFile.getSelectedFile().getPath();
                txtAddMtnDir.setText(t);
			}
		});
		
		lblRootcheck = new JLabel("");
		lblRootcheck.setVisible(false);
		
		lblMtncheck = new JLabel("");
		lblMtncheck.setVisible(false);
		GroupLayout gl_panel_2 = new GroupLayout(configPanel);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(lblNewLabel_1)
											.addGap(18))
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(lblNewLabel_2)
											.addGap(39)))
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtAddPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtAddRootDir, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
										.addComponent(txtAddMtnDir)))
								.addComponent(lblMaintenanceDirectory))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnBrowseMtn, 0, 0, Short.MAX_VALUE)
								.addComponent(btnBrowseRoot, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMtncheck)
								.addComponent(lblRootcheck)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(65)
							.addComponent(lblSrvConfig)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(lblSrvConfig)
					.addGap(11)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtAddPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtAddRootDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowseRoot, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRootcheck, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaintenanceDirectory)
						.addComponent(txtAddMtnDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowseMtn)
						.addComponent(lblMtncheck, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(22))
		);
		configPanel.setLayout(gl_panel_2);
	}
}
