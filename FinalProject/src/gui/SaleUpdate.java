package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controler.CustomerCtr;
import controler.SaleCtr;
import model.Customer;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class SaleUpdate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private static SaleUpdate frame;
	
	String errorMessage;
	
	private static String saleId;
	private static String customerName;
	private static String date;
	private static String employeeName;
	
	JComboBox cbxCustomerName;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					saleId = args[0];
					customerName = args[1];
					date = args[2];
					employeeName = args[3];
					frame = new SaleUpdate();
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
	public SaleUpdate() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 597, 352);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateSale = new JLabel("Update sale");
		lblUpdateSale.setForeground(Color.WHITE);
		lblUpdateSale.setFont(new Font("Microsoft YaHei", Font.BOLD, 21));
		lblUpdateSale.setBounds(12, 13, 349, 31);
		contentPane.add(lblUpdateSale);
		
		lblUpdateSale.setText("Update sale - " + saleId);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 58, 349, 15);
		contentPane.add(separator);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(!cbxCustomerName.getSelectedItem().equals(customerName)){
					String newCustomerName = (String) cbxCustomerName.getSelectedItem();
					SaleCtr mySale = new SaleCtr();
					try {
						CustomerCtr myCustomer = new CustomerCtr();
						Customer customer = myCustomer.getCustomerIdByName(newCustomerName);
						if(mySale.updateSale(saleId,customer.getId()) != -1) {
							JOptionPane.showMessageDialog(null, "Sale updated with success", "Success", 1);
							SalePanel.getAllSales();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "It was not possible to update the sale", "Error", 1);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "There is no changes in the customer name, closing without update.", "Error", 1);
					frame.dispose();
				}
				
			}
		});
		btnUpdate.setBackground(new Color(255, 255, 255));
		btnUpdate.setBounds(233, 255, 97, 25);
		contentPane.add(btnUpdate);
		
		JLabel lblEmployeeName = new JLabel("Employee Name:");
		lblEmployeeName.setForeground(Color.WHITE);
		lblEmployeeName.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblEmployeeName.setBounds(12, 90, 133, 16);
		contentPane.add(lblEmployeeName);
		
		JLabel lblCustomerName = new JLabel("Customer Name:");
		lblCustomerName.setForeground(Color.WHITE);
		lblCustomerName.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblCustomerName.setBounds(12, 159, 133, 16);
		contentPane.add(lblCustomerName);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblDate.setBounds(461, 13, 106, 29);
		contentPane.add(lblDate);
		
		lblDate.setText(date);
		
		cbxCustomerName = new JComboBox();
		cbxCustomerName.setBounds(149, 156, 167, 25);
		contentPane.add(cbxCustomerName);
		
		JLabel lblEmployee = new JLabel("");
		lblEmployee.setForeground(Color.WHITE);
		lblEmployee.setFont(new Font("Microsoft YaHei", Font.ITALIC, 12));
		lblEmployee.setBounds(144, 86, 167, 20);
		contentPane.add(lblEmployee);
		
		lblEmployee.setText(employeeName);
		
		getAllCustomers();
		
		cbxCustomerName.setSelectedItem(customerName);
		
	}
	public void getAllCustomers() {
		CustomerCtr myCustomer = new CustomerCtr();
		try {
			ArrayList<Customer> cust = myCustomer.getAllCustomerNames();
			for(Customer customer : cust) {
				cbxCustomerName.addItem(customer.getName());
			} 
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
