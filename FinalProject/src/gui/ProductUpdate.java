package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controler.ProductCtr;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductUpdate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txfProductName;
	private JTextField txfMinimalStock;
	private JTextField txfSupplier;
	private JTextField txfPrice;
	private JTextField txfTypeOfMeet;
	
	private static String productName;
	private static String minimalStock;
	private static String price;
	private static String typeOfMeat;
	private static String supplier;
	private static String id;
	
	private static ProductUpdate frame;
	


	String errorMessage;
	
	
	String[] fieldsArray = {"Product name", "Supplier" , "Type of meat"};
	int[] sizesArray = {100, 50, 50};


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					productName = args[0];
					minimalStock = args[1];
					price = args[2];
					typeOfMeat = args[3];
					supplier = args[4];
					id = args[5];
					frame = new ProductUpdate();
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
	public ProductUpdate() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 597, 552);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateProduct = new JLabel("Update product - " + productName);
		lblUpdateProduct.setForeground(new Color(255, 255, 255));
		lblUpdateProduct.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		lblUpdateProduct.setBounds(12, 13, 349, 31);
		contentPane.add(lblUpdateProduct);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 58, 349, 15);
		contentPane.add(separator);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblProductName.setForeground(new Color(255, 255, 255));
		lblProductName.setBounds(22, 101, 123, 39);
		contentPane.add(lblProductName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblPrice.setForeground(new Color(255, 255, 255));
		lblPrice.setBounds(348, 112, 56, 16);
		contentPane.add(lblPrice);
		
		JLabel lblMinimalStock = new JLabel("Minimal Stock");
		lblMinimalStock.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblMinimalStock.setForeground(new Color(255, 255, 255));
		lblMinimalStock.setBounds(22, 223, 123, 15);
		contentPane.add(lblMinimalStock);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblSupplier.setForeground(new Color(255, 255, 255));
		lblSupplier.setBounds(22, 320, 123, 16);
		contentPane.add(lblSupplier);
		
		JLabel lblTypeOfMeat = new JLabel("Type of Meat");
		lblTypeOfMeat.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblTypeOfMeat.setForeground(new Color(255, 255, 255));
		lblTypeOfMeat.setBounds(350, 301, 116, 32);
		contentPane.add(lblTypeOfMeat);
		
		txfProductName = new JTextField();
		txfProductName.setEditable(false);
		txfProductName.setForeground(new Color(255, 255, 255));
		txfProductName.setCaretColor(new Color(255, 255, 255));
		txfProductName.setBackground(new Color(204, 0, 51));
		txfProductName.setBorder(null);
		txfProductName.setBounds(22, 141, 179, 22);
		contentPane.add(txfProductName);
		txfProductName.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(22, 171, 179, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 271, 179, 2);
		contentPane.add(separator_2);
		
		txfMinimalStock = new JTextField();
		txfMinimalStock.setForeground(new Color(255, 255, 255));
		txfMinimalStock.setColumns(10);
		txfMinimalStock.setBorder(null);
		txfMinimalStock.setBackground(new Color(204, 0, 51));
		txfMinimalStock.setBounds(22, 241, 179, 22);
		contentPane.add(txfMinimalStock);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(22, 368, 179, 2);
		contentPane.add(separator_3);
		
		txfSupplier = new JTextField();
		txfSupplier.setForeground(new Color(255, 255, 255));
		txfSupplier.setColumns(10);
		txfSupplier.setBorder(null);
		txfSupplier.setBackground(new Color(204, 0, 51));
		txfSupplier.setBounds(22, 338, 179, 22);
		contentPane.add(txfSupplier);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(350, 171, 179, 2);
		contentPane.add(separator_4);
		
		JLabel lblPriceError = new JLabel("");
		lblPriceError.setBounds(348, 180, 223, 22);
		contentPane.add(lblPriceError);
		
		txfPrice = new JTextField();
		txfPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!Main.isNumeric(txfPrice.getText())) {
					lblPriceError.setText("Invalid format, must be numbers");
				} else {
					lblPriceError.setText("");
				}
			}
		});
		txfPrice.setForeground(new Color(255, 255, 255));
		txfPrice.setColumns(10);
		txfPrice.setBorder(null);
		txfPrice.setBackground(new Color(204, 0, 51));
		txfPrice.setBounds(350, 141, 179, 22);
		contentPane.add(txfPrice);
		
		
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(350, 368, 179, 2);
		contentPane.add(separator_5);
		
		txfTypeOfMeet = new JTextField();
		txfTypeOfMeet.setForeground(new Color(255, 255, 255));
		txfTypeOfMeet.setColumns(10);
		txfTypeOfMeet.setBorder(null);
		txfTypeOfMeet.setBackground(new Color(204, 0, 51));
		txfTypeOfMeet.setBounds(350, 338, 179, 22);
		contentPane.add(txfTypeOfMeet);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				productName = txfProductName.getText();
				supplier = txfSupplier.getText();
				typeOfMeat = txfTypeOfMeet.getText();
				price = txfPrice.getText();
				minimalStock = txfMinimalStock.getText();
				
				errorMessage = "";
				
				String[] fields = {productName, supplier, typeOfMeat};
				
				String[] allfields = {productName, supplier, typeOfMeat, price, minimalStock};
				
				
				// Compare length of input fields to see if it's bigger than allowed on the database
				for(int i = 0; i < fields.length; i++) {
					if(fields[i].length() > sizesArray[i]) {
						//JOptionPane.showMessageDialog(null, "Invalid number of characters in " + fieldsArray[i], "Error", 1);
						errorMessage += "Invalid number of characters in " + fieldsArray[i];
						break;
					}
				
				}
				
				// Check for empty fields
				for(int i = 0; i < allfields.length; i++) {
					if(allfields[i].length() < 1) {
						//JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
						errorMessage += "Please fill all the fields";
						break;
					}
				
				}
				
				if(errorMessage.equals("")) {
					
					Double dPrice = Double.valueOf(price);
					int iMinstock = Integer.valueOf(minimalStock);
					int productId = Integer.valueOf(id);
					
					ProductCtr product = new ProductCtr();
					try {
						product.updateProduct(productId,productName, dPrice, typeOfMeat, iMinstock, supplier);
						JOptionPane.showMessageDialog(null, "Product edited successefully", "Success", 1);
						ProductPanel.printAllProducts();
						frame.dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, errorMessage, "Error updating the product", 1);
					}
					
					
					
				} else {
					JOptionPane.showMessageDialog(null, errorMessage, "Error", 1);
					
				}
				
				
			}
		});
		btnUpdate.setBackground(new Color(255, 255, 255));
		btnUpdate.setBounds(221, 444, 97, 25);
		contentPane.add(btnUpdate);
		
		txfProductName.setText(productName);
		txfMinimalStock.setText(minimalStock);
		txfSupplier.setText(supplier);
		txfPrice.setText(price);
		txfTypeOfMeet.setText(typeOfMeat);
		
		
	}
}
