package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controler.DeliveryCtr;
import controler.ProductCtr;
import model.Delivery;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;


public class DeliveryInfoPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private static String deliveryId;
	private static String storeName;
	private static String date;
	private static String employeeName;
	
	JLabel lblLDate;
	JLabel lblLEmployee;
	JLabel lblLStore;
	JTextPane txpProducts;
	
	private String products = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deliveryId = args[0];
					storeName = args[1];
					date = args[2];
					employeeName = args[3];
					
					DeliveryInfoPanel frame = new DeliveryInfoPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public DeliveryInfoPanel() throws Exception {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 626);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Invoice.class.getResource("/img/logo.png")));
		lblLogo.setBounds(12, 13, 164, 137);
		contentPane.add(lblLogo);
		
		JTextArea txtrAddress = new JTextArea();
		txtrAddress.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		txtrAddress.setForeground(new Color(153, 153, 153));
		txtrAddress.setRows(2);
		txtrAddress.setText("KIP-3\r\nBulgaria, Varna, ul\r\nSredec N\u00BA 51");
		txtrAddress.setBounds(177, 44, 164, 77);
		contentPane.add(txtrAddress);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		lblDate.setBounds(28, 246, 46, 14);
		contentPane.add(lblDate);
		
		lblLDate = new JLabel("");
		lblLDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblLDate.setBounds(116, 246, 100, 14);
		contentPane.add(lblLDate);
		
		JLabel lblStore = new JLabel("Store:");
		lblStore.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
		lblStore.setBounds(28, 183, 79, 19);
		contentPane.add(lblStore);
		
		JLabel lblEmployee = new JLabel("Employee:");
		lblEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
		lblEmployee.setBounds(28, 213, 93, 22);
		contentPane.add(lblEmployee);
		
		lblLEmployee = new JLabel("");
		lblLEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblLEmployee.setBounds(117, 217, 164, 19);
		contentPane.add(lblLEmployee);
		
		lblLStore = new JLabel("");
		lblLStore.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblLStore.setBounds(117, 185, 79, 19);
		contentPane.add(lblLStore);
		
		JLabel lblProductId = new JLabel("Product Id");
		lblProductId.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblProductId.setForeground(new Color(153, 153, 153));
		lblProductId.setBounds(28, 286, 106, 32);
		contentPane.add(lblProductId);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblWeight.setForeground(new Color(153, 153, 153));
		lblWeight.setBounds(197, 286, 116, 32);
		contentPane.add(lblWeight);
		
		txpProducts = new JTextPane();
		txpProducts.setContentType("text/html");
		txpProducts.setEditable(false);
		txpProducts.setBounds(28, 321, 269, 242);
		contentPane.add(txpProducts);
		
		loadInfo();
	}
	
	public void loadInfo() throws Exception {
		
		DeliveryCtr myDelivery = new DeliveryCtr();
		
		ProductCtr myProduct = new ProductCtr();
				
		ArrayList<Delivery> deliveries = myDelivery.getDeliveryById(deliveryId);
		
		lblLDate.setText(date);
		lblLEmployee.setText(employeeName);
		lblLStore.setText(storeName);
		
		for (Delivery delivery : deliveries) {
			
			products += "<b style='font-size:14px; color:grey;'>" + myProduct.getProductName(String.valueOf(delivery.getProductId())) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + delivery.getWeight();
			products += "<br><hr><br></b>";

			
		}
		
		txpProducts.setText(products);
	}
}
