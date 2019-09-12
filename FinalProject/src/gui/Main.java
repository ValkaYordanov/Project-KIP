package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controler.ProductCtr;
import controler.UserCtr;
import controler.WarehouseCtr;
import model.Warehouse;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private static String username;
	private static String typeOfUser;
	private static int usernameI;
	
	private static int typeOfUserI;
	
	private static String notifications = "";
	
	private JScrollPane scrollBar;
	private static JTextPane txpNotification;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					username = args[1];
					typeOfUser = args[2];
					typeOfUserI = Integer.valueOf(typeOfUser);
					Main frame = new Main();
					frame.setVisible(true);
					usernameI = Integer.valueOf(username);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Main() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(20, 863, 164, 146);
		contentPane.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Main.class.getResource("/img/logo.png")));
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(204, 0, 51));
		headerPanel.setForeground(new Color(255, 255, 255));
		headerPanel.setBounds(0, 0, 1902, 200);
		contentPane.add(headerPanel);
		headerPanel.setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 255, 255));

		bottomPanel.setBounds(10, 211, 1560, 650);
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		JLabel lblProduct = new JLabel("Product Management");
		lblProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblProduct.setForeground(new Color(255, 255, 255));
		lblProduct.setBounds(142, 135, 202, 27);
		headerPanel.add(lblProduct);
		
		JLabel lblWarehouse = new JLabel("Warehouse");
		lblWarehouse.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblWarehouse.setForeground(new Color(255, 255, 255));
		lblWarehouse.setBounds(356, 140, 104, 16);
		headerPanel.add(lblWarehouse);
		
		JLabel lblSales = new JLabel("Sales");
		lblSales.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSales.setForeground(new Color(255, 255, 255));
		lblSales.setBounds(501, 140, 56, 16);
		headerPanel.add(lblSales);
		
		JLabel lblDelivery = new JLabel("Delivery");
		lblDelivery.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDelivery.setForeground(new Color(255, 255, 255));
		lblDelivery.setBounds(594, 137, 82, 22);
		headerPanel.add(lblDelivery);
		
		JLabel lblStatistics = new JLabel("Statistics");
		lblStatistics.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblStatistics.setForeground(new Color(255, 255, 255));
		lblStatistics.setBounds(730, 137, 118, 22);
		headerPanel.add(lblStatistics);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSettings.setBounds(1775, 137, 82, 22);
		headerPanel.add(lblSettings);
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(new Color(255, 255, 255));
		userPanel.setBorder(null);
		userPanel.setBounds(1225, 160, 519, 40);
		headerPanel.add(userPanel);
		userPanel.setLayout(null);
		
		JLabel lblCurrentUser = new JLabel("");
		lblCurrentUser.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblCurrentUser.setBounds(0, 0, 499, 40);
		
		
		UserCtr currentUser=new UserCtr();
		
	String name = currentUser.getUserNameByLogin(username);
		
		if(!username.equals(null)) {
			lblCurrentUser.setText("Current logged employee: " + name );
		}
		userPanel.add(lblCurrentUser);
		
		JLabel lblDeliveryImage = new JLabel("");
		lblDeliveryImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDeliveryImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel deliveryPanel = new DeliveryPanel(usernameI);
				bottomPanel.removeAll();
				bottomPanel.revalidate();
				bottomPanel.repaint();
				bottomPanel.add(deliveryPanel);
			}
		});
		lblDeliveryImage.setIcon(new ImageIcon(Main.class.getResource("/img/truck.png")));
		lblDeliveryImage.setBounds(594, 52, 72, 86);
		headerPanel.add(lblDeliveryImage);
		
		JLabel lblStatisticsImage = new JLabel("");
		lblStatisticsImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblStatisticsImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
				if(checkUserPermissions()) {
					JPanel statisticsPanel = new StatisticsPanel();
					bottomPanel.removeAll();
					bottomPanel.revalidate();
					bottomPanel.repaint();
					bottomPanel.add(statisticsPanel);
					
				} else {
					JOptionPane.showMessageDialog(null, "You don't have permissions to access", "Error", 1);
				}
			}
		});
		lblStatisticsImage.setIcon(new ImageIcon(Main.class.getResource("/img/statistics.png")));
		lblStatisticsImage.setBounds(734, 46, 129, 108);
		headerPanel.add(lblStatisticsImage);
		
		JLabel lblSalesImage = new JLabel("");
		lblSalesImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSalesImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
				if(checkUserPermissions()) {
					JPanel salePanel = new SalePanel(usernameI);
					bottomPanel.removeAll();
					bottomPanel.revalidate();
					bottomPanel.repaint();
					bottomPanel.add(salePanel);
					
				} else {
					JOptionPane.showMessageDialog(null, "You don't have permissions to access", "Error", 1);
				}
			}
		});
		lblSalesImage.setIcon(new ImageIcon(Main.class.getResource("/img/sales.png")));
		lblSalesImage.setBounds(491, 63, 83, 70);
		headerPanel.add(lblSalesImage);
		
		JLabel lblWarehouseImage = new JLabel("");
		lblWarehouseImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblWarehouseImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JPanel warehousePanel = new WarehousePanel(usernameI);
				bottomPanel.removeAll();
				bottomPanel.revalidate();
				bottomPanel.repaint();

				bottomPanel.add(warehousePanel);
			}
		});
		lblWarehouseImage.setIcon(new ImageIcon(Main.class.getResource("/img/warehouse.png")));
		lblWarehouseImage.setBounds(371, 68, 72, 70);
		headerPanel.add(lblWarehouseImage);
		
		
		
		JLabel lblProdManagement = new JLabel("");
		lblProdManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblProdManagement.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				if(checkUserPermissions()) {
					JPanel productPanel = new ProductPanel();		
					bottomPanel.removeAll();
					bottomPanel.revalidate();
					bottomPanel.repaint();

					bottomPanel.add(productPanel);
					
				} else {
					JOptionPane.showMessageDialog(null, "You don't have permissions to access", "Error", 1);
				}
				
			}
		});
		
		lblProdManagement.setIcon(new ImageIcon(Main.class.getResource("/img/prodManagement.png")));
		lblProdManagement.setBounds(195, 63, 72, 65);
		headerPanel.add(lblProdManagement);
		
	
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
			if(checkUserPermissions()) {
				SettingsPanel.main(null);
			} else {
				JOptionPane.showMessageDialog(null, "You don't have permissions to access", "Error", 1);
			}
			
		}
		});
		btnSettings.setForeground(new Color(204, 0, 51));
		btnSettings.setBackground(new Color(204, 0, 51));
		btnSettings.setIcon(new ImageIcon(Main.class.getResource("/img/settings.png")));
		btnSettings.setBounds(1768, 68, 89, 70);
		headerPanel.add(btnSettings);
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(Color.WHITE);
		notificationPanel.setBounds(1580, 211, 314, 628);
		notificationPanel.setLayout(null);
		contentPane.add(notificationPanel);
        
        JLabel lblNotifications = new JLabel("Notifications");
        lblNotifications.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        lblNotifications.setBounds(10, 11, 97, 26);
        notificationPanel.add(lblNotifications);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(204, 0, 51));
        separator.setBounds(10, 37, 155, 2);
        notificationPanel.add(separator);
        
        txpNotification = new JTextPane();
        txpNotification.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        txpNotification.setEditable(false);
        txpNotification.setBounds(10, 50, 275, 565);
        
        
         scrollBar = new JScrollPane(txpNotification,
                 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         scrollBar.setBounds(10, 63, 294, 565);
         txpNotification.setContentType("text/html");
         notificationPanel.add(scrollBar);
         
         JSeparator separator_1 = new JSeparator();
         separator_1.setForeground(new Color(255, 255, 255));
         separator_1.setBackground(new Color(204, 0, 51));
         separator_1.setOrientation(SwingConstants.VERTICAL);
         separator_1.setBounds(1575, 211, 13, 628);
         contentPane.add(separator_1);
         
         
         checkForMinStock();
		
	}
	
	public static boolean isNumeric(String strNum) {
	    return strNum.matches("-?\\d+(\\.\\d+)?");
	}
	
	public boolean checkUserPermissions() {

		
		Users manager = Users.MANAGER;
		if(typeOfUserI == manager.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void checkForMinStock() throws SQLException {
		
		txpNotification.setText(notifications);
		
		WarehouseCtr myWarehouse = new WarehouseCtr();
		ArrayList<Warehouse> myProducts = new ArrayList<Warehouse>();
		
		ProductCtr product = new ProductCtr();
		
		myProducts = myWarehouse.checkProductStocks();
		
		for(Warehouse prod : myProducts) {
			double availableStock = myWarehouse.getProductStock(prod.getProductName());
			double minStock = product.getStockFromProduct(prod.getProductId());
			
			if(minStock > availableStock) {
				buildString(prod.getProductName(), minStock,availableStock);
			}
			
		}
		
		if(!notifications.equals("")) {
			txpNotification.setText(notifications);
		}
		
		notifications = "";
		
	}
	
	
	
	public static void buildString(String productName, double minStock, double stock) {
		notifications += "The stock of the product <b>" + productName + "</b> has reached it minimum.<br> - Current stock: <b style='color:red'>" + stock + "</b><br> - Minimum stock: <b>" + minStock +"</b> <br>";
	}
}
