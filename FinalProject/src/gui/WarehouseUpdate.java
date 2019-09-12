package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Robot;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controler.WarehouseCtr;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WarehouseUpdate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txfWeight;
	private JTextField txfDaysForComsuption;
	private JTextField txfProductionDate;
	
	
	private static String serialNumber;
	private static String productName;
	private static String stock;
	private static String expireDate;
	private static String productionDate;
	
	JLabel lblDateError;
	static WarehouseUpdate frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serialNumber = args[0];
					productName = args[1];
					stock = args[2];
					expireDate = args[3];
					productionDate = args[4];
					
					frame = new WarehouseUpdate();
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
	public WarehouseUpdate() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 597, 426);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateProduct = new JLabel("Update product - " + serialNumber);
		lblUpdateProduct.setBounds(12, 13, 349, 31);
		lblUpdateProduct.setForeground(new Color(255, 255, 255));
		lblUpdateProduct.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		contentPane.add(lblUpdateProduct);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 58, 349, 15);
		contentPane.add(separator);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setBounds(350, 101, 123, 39);
		lblWeight.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblWeight.setForeground(new Color(255, 255, 255));
		contentPane.add(lblWeight);
		
		JLabel lblDaysOfComsuption = new JLabel("Days for comsuption");
		lblDaysOfComsuption.setBounds(22, 244, 123, 16);
		lblDaysOfComsuption.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblDaysOfComsuption.setForeground(new Color(255, 255, 255));
		contentPane.add(lblDaysOfComsuption);
		
		JLabel lblProductionDate = new JLabel("Production date");
		lblProductionDate.setBounds(350, 225, 116, 32);
		lblProductionDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblProductionDate.setForeground(new Color(255, 255, 255));
		contentPane.add(lblProductionDate);
		
		
		JLabel lblWeightError = new JLabel("");
		lblWeightError.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		lblWeightError.setForeground(Color.WHITE);
		lblWeightError.setBounds(350, 184, 221, 15);
		contentPane.add(lblWeightError);
		
		
		txfWeight = new JTextField();
		txfWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!Main.isNumeric(txfWeight.getText())) {
					lblWeightError.setText("Invalid format, must be numbers");
				} else {
					lblWeightError.setText("");
				}
			}
		});
		txfWeight.setBounds(350, 141, 179, 22);
		txfWeight.setForeground(new Color(255, 255, 255));
		txfWeight.setCaretColor(new Color(255, 255, 255));
		txfWeight.setBackground(new Color(204, 0, 51));
		txfWeight.setBorder(null);
		contentPane.add(txfWeight);
		txfWeight.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(350, 171, 179, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(22, 292, 179, 2);
		contentPane.add(separator_3);
		
		txfDaysForComsuption = new JTextField();
		txfDaysForComsuption.setBounds(22, 262, 179, 22);
		txfDaysForComsuption.setForeground(new Color(255, 255, 255));
		txfDaysForComsuption.setColumns(10);
		txfDaysForComsuption.setBorder(null);
		txfDaysForComsuption.setBackground(new Color(204, 0, 51));
		contentPane.add(txfDaysForComsuption);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(350, 292, 179, 2);
		contentPane.add(separator_5);
		
		txfProductionDate = new JTextField();
		txfProductionDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dataFormatter(txfProductionDate, e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isNumberDate(txfProductionDate.getText())) {
					lblDateError.setText("Invalid format, must be number in format YYYY-MM-DD");
				} else {
					lblDateError.setText("");
				}
			}
		});
		txfProductionDate.setBounds(350, 262, 179, 22);
		txfProductionDate.setForeground(new Color(255, 255, 255));
		txfProductionDate.setColumns(10);
		txfProductionDate.setBorder(null);
		txfProductionDate.setBackground(new Color(204, 0, 51));
		contentPane.add(txfProductionDate);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setForeground(Color.WHITE);
		lblProductName.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblProductName.setBounds(22, 108, 123, 25);
		contentPane.add(lblProductName);
		
		JLabel lblSetProductName = new JLabel(productName);
		lblSetProductName.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblSetProductName.setForeground(Color.WHITE);
		lblSetProductName.setBounds(22, 145, 179, 22);
		contentPane.add(lblSetProductName);
		
		txfWeight.setText(stock);
		
		
		// get the current date
		LocalDate dateNow = LocalDate.now();
		
		// create local date format of string date
		LocalDate oldExpireDate = LocalDate.parse(expireDate);
		
		// get the difference of days between the two dates
		int days = (int) ChronoUnit.DAYS.between(dateNow, oldExpireDate);
		
		
		String numberDays = String.valueOf(days);
		txfDaysForComsuption.setText(numberDays);
		txfProductionDate.setText(productionDate);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 171, 179, 2);
		contentPane.add(separator_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
        		String weight = txfWeight.getText();
        		String daysForComsuption = txfDaysForComsuption.getText();
        		String productionDate = txfProductionDate.getText();
        		
        		if(!weight.equals("") && !daysForComsuption.equals("") && !productionDate.equals("")) {
        			
        			
        			WarehouseCtr myWarehouse = new WarehouseCtr();
        			
        			double weightD = Double.valueOf(weight);
        			
        			Date productionDateD = Date.valueOf(productionDate);
        			
        			
        			// Convert the days for integer
        			int daysForComsuptionI = Integer.valueOf(daysForComsuption);
        			
        			LocalDate comsuptionDate = LocalDate.parse(productionDate);
        			comsuptionDate = comsuptionDate.plusDays(daysForComsuptionI);
        			

        			// Convert to sql date format
        			Date daysForComsuptionDate = Date.valueOf(comsuptionDate);
        			
        			int serialNumberS = Integer.valueOf(serialNumber);
        			
        			
					try {
						
						myWarehouse.updateProductWarehouse(serialNumberS, weightD, daysForComsuptionDate, productionDateD);
	            		
	            		JOptionPane.showMessageDialog(null, "Product updated on the warehouse", "Success", 1);
	            	
	            		WarehousePanel.getAllProducts();
	            		frame.dispose();
	            		
	            		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Couldn't find a product with that name", "Error", 1);
					}
      
        			
        			
        		} else {
        			JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
        		}
        		
        		try {
					Main.checkForMinStock();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(227, 330, 89, 23);
		contentPane.add(btnUpdate);
		
		lblDateError = new JLabel("");
		lblDateError.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		lblDateError.setForeground(Color.WHITE);
		lblDateError.setBounds(350, 299, 221, 15);
		contentPane.add(lblDateError);
	
		
	}
	public static boolean isNumberDate(String strNum) {
	    return strNum.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	}
	
	public void dataFormatter(JTextField inputField, KeyEvent e) {
		if(e.getKeyCode() != e.VK_BACK_SPACE) {
			String text = inputField.getText();
			if(text.length() == 4 || text.length() == 7) {
				inputField.setText(text + "-");
			}
			if(text.length() > 9) {
				Robot robot;
				try {
					robot = new Robot();
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
