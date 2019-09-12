package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static int usernameI;
	
	JPanel bottomPanel = new JPanel();

	String errorMessage;
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					
					SettingsPanel frame = new SettingsPanel();
					frame.setVisible(true);
					//usernameI = Integer.valueOf(username);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsPanel() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 150, 1550, 720);
		getContentPane().setBackground(new Color(204, 0, 51));
		getContentPane().setLayout(null);
		

		
		bottomPanel.setBackground(new Color(255, 255, 255));
		bottomPanel.setBounds(224, 52, 1179, 608);
		getContentPane().add(bottomPanel);
		bottomPanel.setLayout(null);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBounds(10, 52, 187, 354);
		getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		JLabel lblUser = new JLabel("Users");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUser.setBounds(84, 49, 79, 26);
		optionsPanel.add(lblUser);
		
		JLabel lblStores = new JLabel("Stores");
		lblStores.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStores.setBounds(88, 209, 75, 26);
		optionsPanel.add(lblStores);
		
		JLabel lblCustomers = new JLabel("Customers");
		lblCustomers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCustomers.setBounds(84, 134, 79, 26);
		optionsPanel.add(lblCustomers);
		

		JLabel lblUserSettingsImage = new JLabel("");
		lblUserSettingsImage.setIcon(new ImageIcon(SettingsPanel.class.getResource("/img/userSettings.png")));
		lblUserSettingsImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel userSettingsPanel = new UserSettingsPanel(usernameI);		
				bottomPanel.removeAll();
				bottomPanel.repaint();

				bottomPanel.add(userSettingsPanel);
				
			}
		});
		lblUserSettingsImage.setBounds(10, 11, 64, 64);
		optionsPanel.add(lblUserSettingsImage);
		
		
	
		
		
		
		JLabel lblCustomerSettingsImage = new JLabel("");
		lblCustomerSettingsImage.setIcon(new ImageIcon(SettingsPanel.class.getResource("/img/customerSettings.png")));
		lblCustomerSettingsImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel customerSettingsPanel = new CustomerSettingsPanel(usernameI);		
				bottomPanel.removeAll();
				bottomPanel.repaint();

				bottomPanel.add(customerSettingsPanel);
				
			}
		});
		lblCustomerSettingsImage.setBounds(10, 89, 75, 71);
		optionsPanel.add(lblCustomerSettingsImage);
		
		JLabel lblStoreSettingsIMage = new JLabel("");
		lblStoreSettingsIMage.setIcon(new ImageIcon(SettingsPanel.class.getResource("/img/storeSettings.png")));
		lblStoreSettingsIMage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel storeSettingsPanel = new StoreSettingsPanel(usernameI);		
				bottomPanel.removeAll();
				bottomPanel.repaint();

				bottomPanel.add(storeSettingsPanel);
				
			}
		});
		lblStoreSettingsIMage.setBounds(22, 171, 56, 64);
		optionsPanel.add(lblStoreSettingsIMage);
		
		
		
	}
	
	public void returnUserPanel() {
		JPanel userSettingsPanel = new UserSettingsPanel(usernameI);		
		bottomPanel.removeAll();
		bottomPanel.repaint();

		bottomPanel.add(userSettingsPanel);
		
	}
	
}
