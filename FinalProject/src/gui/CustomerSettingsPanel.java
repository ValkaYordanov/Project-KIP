package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controler.CustomerCtr;
import model.Customer;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CustomerSettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfNameOfCustomer;
	
	
	Object[][] data = null;
	static DefaultTableModel model;
	
	
	
	String [] header={"Customer ID","Name Of customer","Street name","Number on street","City","Phone Number"};
	
	private JTextField txfSearch;
	private JTextField txfStreet;
	private JTextField txfNumberOfStreet;
	private JTextField txfCity;
	private JTextField txfPhoneNumber;
	
	String errorMessage;
	

	String[] fieldsArray = {"Customer name", "Street" ,"Number on street", "City", "Phone Number"};
	int[] sizesArray = {100, 150, 15,30,15};


	/**
	 * Create the panel.
	 */
	public CustomerSettingsPanel(int username) {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 641);
		setLayout(null);			
		setLayout(null);
		
		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblCustomer.setBounds(10, 11, 272, 26);
		add(lblCustomer);
		
		JSeparator customerSeparator = new JSeparator();
		customerSeparator.setForeground(new Color(204, 0, 51));
		customerSeparator.setBounds(10, 44, 359, 10);
		add(customerSeparator);
		
		
		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		customerPanel.setBackground(Color.WHITE);
		customerPanel.setBounds(10, 65, 398, 394);
		add(customerPanel);
		
		customerPanel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 200, 194, 10);
		customerPanel.add(separator);
		
		JLabel lblNumberOnStreet = new JLabel("Number on street");
		lblNumberOnStreet.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumberOnStreet.setBounds(26, 151, 133, 19);
		customerPanel.add(lblNumberOnStreet);
		
		JLabel lblNameOfCustomer = new JLabel("Name");
		lblNameOfCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNameOfCustomer.setBounds(26, 23, 181, 19);
		customerPanel.add(lblNameOfCustomer);
		
		txfNameOfCustomer = new JTextField();
		txfNameOfCustomer.setBorder(null);
		txfNameOfCustomer.setBounds(26, 48, 194, 20);
		customerPanel.add(txfNameOfCustomer);
		txfNameOfCustomer.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 72, 194, 10);
		customerPanel.add(separator_1);
		
		JLabel lblStreet = new JLabel("Street");
		lblStreet.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStreet.setBounds(26, 87, 152, 14);
		customerPanel.add(lblStreet);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(26, 136, 193, 2);
		customerPanel.add(separator_2);
		

		JTextPane errorPane = new JTextPane();
        errorPane.setFont(new Font("Tahoma", Font.PLAIN, 17));
        errorPane.setForeground(Color.RED);
        errorPane.setBounds(623, 596, 399, 155);
        add(errorPane);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				String nameOfCustomer = txfNameOfCustomer.getText();
				String nameOfStreet = txfStreet.getText();
				String numberOnStreet = txfNumberOfStreet.getText();
				String city = txfCity.getText();
				String phoneNo = txfPhoneNumber.getText();
        		String strNumOnStreet=numberOnStreet+"";
        		
	            errorMessage = "";
				
				String[] allfields = {nameOfCustomer, nameOfStreet, strNumOnStreet, city, phoneNo};
				
				String[] fields = {nameOfCustomer, nameOfStreet, city};
				
				
				// Compare length of input fields to see if it's bigger than allowed on the database
				for(int i = 0; i < fields.length; i++) {
					if(fields[i].length() > sizesArray[i]) {
						errorMessage += "\n - Invalid number of characters in " + fieldsArray[i] + ". \n" +
								"Maximum allowed is: " + sizesArray[i] + "\n";
					}
				
				}
				
				// Check for empty fields
				for(int i = 0; i < allfields.length; i++) {
					if(allfields[i].length() < 1) {
						errorMessage += "\n - Please fill all the fields before creating the customer";
						break;
					}
				
				}
				
				if(errorMessage.equals("")) {
					errorPane.setVisible(false);
					
				
					
					CustomerCtr customer = new CustomerCtr();
					try {
						customer.insertCustomer(nameOfCustomer, nameOfStreet, numberOnStreet, city, phoneNo);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					JOptionPane.showMessageDialog(null, "Customer added successefully", "Success", 1);
					getAllCustomers();
					txfNameOfCustomer.setText("");
					txfStreet.setText("");
					txfNumberOfStreet.setText("");
					txfCity.setText("");
					txfPhoneNumber.setText("");
					
				} else {
					errorPane.setText(errorMessage);
					errorPane.setVisible(true);
					
				}
	
			}
		});
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(204, 0, 51));
		btnCreate.setBounds(26, 360, 89, 23);
		customerPanel.add(btnCreate);
		
		txfStreet = new JTextField();
		txfStreet.setColumns(10);
		txfStreet.setBorder(null);
		txfStreet.setBounds(26, 112, 194, 20);
		customerPanel.add(txfStreet);
		
		txfNumberOfStreet = new JTextField();
		txfNumberOfStreet.setColumns(10);
		txfNumberOfStreet.setBorder(null);
		txfNumberOfStreet.setBounds(26, 176, 194, 20);
		customerPanel.add(txfNumberOfStreet);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(26, 210, 133, 19);
		customerPanel.add(lblCity);
		
		txfCity = new JTextField();
		txfCity.setColumns(10);
		txfCity.setBorder(null);
		txfCity.setBounds(26, 240, 194, 20);
		customerPanel.add(txfCity);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(26, 264, 194, 10);
		customerPanel.add(separator_4);
		
		JLabel lblPhonenumber = new JLabel("Phone number");
		lblPhonenumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPhonenumber.setBounds(26, 279, 133, 19);
		customerPanel.add(lblPhonenumber);
		
		txfPhoneNumber = new JTextField();
		txfPhoneNumber.setColumns(10);
		txfPhoneNumber.setBorder(null);
		txfPhoneNumber.setBounds(26, 304, 194, 20);
		customerPanel.add(txfPhoneNumber);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(26, 329, 194, 10);
		customerPanel.add(separator_3);
		
		
		
		
		model = new DefaultTableModel(data,header);
        
	
		JTable tblCustomers = new JTable(model);
		tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblCustomers.setColumnSelectionAllowed(true);
		tblCustomers.setCellSelectionEnabled(true);
		tblCustomers.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblCustomers.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblCustomers.setFillsViewportHeight(true);

        // Disable row editor
		tblCustomers.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblCustomers.getTableHeader().setReorderingAllowed(false);


        JScrollPane jsCustomers = new JScrollPane(tblCustomers);
        jsCustomers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsCustomers.setVisible(true);
        jsCustomers.setSize(574, 394);
        jsCustomers.setLocation(448, 65);
        add(jsCustomers);
        
        txfSearch = new JTextField();
        
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			getAllCustomers();
        		} else {
        			String toSearch = txfSearch.getText();
            		searchCustomerByName(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			getAllCustomers();
        			txfSearch.setText("Search...");
        		}
        	}
        });
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(0, 0, 0));      		
        		if(txfSearch.getText().equals("Search...")){
        			txfSearch.setText("");
        		}
        	}
        });
        
        txfSearch.setForeground(Color.LIGHT_GRAY);
        txfSearch.setText("Search...");
        txfSearch.setBounds(448, 44, 343, 20);
        add(txfSearch);
        txfSearch.setColumns(10);
        
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(new Color(204, 0, 51));
        btnDelete.setBounds(933, 470, 89, 23);
        add(btnDelete);
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setForeground(new Color(255, 255, 255));
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(834, 470, 89, 23);
        add(btnUpdate);
        
        JButton btnCancel = new JButton("Cancel");
        JButton btnUpdateSet = new JButton("Update");
        
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
			        		
			        		int myRow = tblCustomers.getSelectedRow();
							if(myRow == -1) {
								JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
							} else {
								
								
								String id = tblCustomers.getValueAt(myRow, 0).toString();
							    String name = tblCustomers.getValueAt(myRow, 1).toString();
								String street = tblCustomers.getValueAt(myRow, 2).toString();
								String numberOnStreet = tblCustomers.getValueAt(myRow, 3).toString();
								String city = tblCustomers.getValueAt(myRow, 4).toString();
								String phone = tblCustomers.getValueAt(myRow, 5).toString();
			        		
			        		txfNameOfCustomer.setText(name);
			        		txfStreet.setText(street);
			        		txfNumberOfStreet.setText(numberOnStreet);
			        		txfCity.setText(city);
			        		txfPhoneNumber.setText(phone);
								
			        		
							
							errorMessage = "";
							
							String[] fields = {name, street, numberOnStreet, city, phone};
							
							
							
							

							btnCreate.setVisible(false);
							btnUpdate.setVisible(false);
							btnDelete.setVisible(false);
							btnUpdateSet.setVisible(true);
							btnCancel.setVisible(true);
							

						     btnCancel.setBounds(26, 360, 89, 23);
							 customerPanel.add(btnCancel);
							 btnCancel.setForeground(new Color(255, 255, 255));
							 btnCancel.setBackground(new Color(204, 0, 51));
							 
							 btnCancel.addMouseListener(new MouseAdapter() {
								 public void mousePressed(MouseEvent arg0) {
									 
									    btnCreate.setVisible(true);
										btnUpdate.setVisible(true);
										btnDelete.setVisible(true);
										btnUpdateSet.setVisible(false);
										btnCancel.setVisible(false);
										
										txfNameOfCustomer.setText("");
										txfStreet.setText("");
										txfNumberOfStreet.setText("");
										txfCity.setText("");
										txfPhoneNumber.setText("");
									 
									 
								 }
							 });
							
							
							 customerPanel.add(btnUpdateSet);
							 btnUpdateSet.setBounds(287, 360, 89, 23);
							 btnUpdateSet.setForeground(new Color(255, 255, 255));
							 btnUpdateSet.setBackground(new Color(204, 0, 51));
							 
						        btnUpdateSet.addMouseListener(new MouseAdapter() {
						        	@Override
						        	public void mousePressed(MouseEvent arg0) {
						        		

										// Compare length of input fields to see if it's bigger than allowed on the database
										for(int i = 0; i < fields.length; i++) {
											if(fields[i].length() > sizesArray[i]) {
												JOptionPane.showMessageDialog(null, "Invalid number of characters in " + fieldsArray[i], "Error", 1);
												//errorMessage += "Invalid number of characters in " + fieldsArray[i];
												break;
											}
										
										}
										
										
										
										String name = txfNameOfCustomer.getText();
										String street = txfStreet.getText();
										String numberOnStreet = txfNumberOfStreet.getText();
										String city = txfCity.getText();
										String phone = txfPhoneNumber.getText();
								
						        		
							
							if(!name.equals("") && !street.equals("") &&  !numberOnStreet.equals("") && !city.equals("") && !phone.equals("")) {
								
								
								int customerId = Integer.valueOf(id);
								
								CustomerCtr customer = new CustomerCtr();
								try {
									customer.updateCustomer(customerId,name, street, numberOnStreet, city, phone);
									JOptionPane.showMessageDialog(null, "Customer edited successefully", "Success", 1);
									
									txfNameOfCustomer.setText("");
									txfStreet.setText("");
									txfNumberOfStreet.setText("");
									txfCity.setText("");
									txfPhoneNumber.setText("");
									getAllCustomers();
									
									   btnCreate.setVisible(true);
										btnUpdate.setVisible(true);
										btnDelete.setVisible(true);
										btnUpdateSet.setVisible(false);
										btnCancel.setVisible(false);
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									JOptionPane.showMessageDialog(null, errorMessage, "Error updating the customer", 1);
								}
								
								
								
							}   else {
			        			JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
			        		}
						        	}
		            		
		        	
						        }
		        
						        		);
						       		
						
							}
        	}});
        
    
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = tblCustomers.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String id = tblCustomers.getValueAt(myRow, 0).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the customer " +id+" ?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	CustomerCtr myCustomer = new CustomerCtr();
	                    myCustomer.deleteCustomer(id);
	                    
	                		JOptionPane.showMessageDialog(null, "Customer deleted with success", "Success", 1);
	                		getAllCustomers();
	                	
	                	
	                }
				}
        	}
        });
		
        
        getAllCustomers();
	}
	

	
	
	public void getAllCustomers() {
		model.setNumRows(0);
		CustomerCtr myCustomers = new CustomerCtr();
		try {
			ArrayList<Customer> customer = myCustomers.getAll();
			for (Customer cust : customer) {
				model.addRow(new Object[]{cust.getId(), cust.getName() , cust.getStreet() , cust.getNumberOnStreet(), cust.getCity(), cust.getPhoneNo()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void searchCustomerByName(String name) {
		model.setNumRows(0);
		CustomerCtr myCustomers = new CustomerCtr();
		try {
			ArrayList<Customer> customer = myCustomers.searchByName(name);
			for (Customer cust : customer) {
	          model.addRow(new Object[]{cust.getId(), cust.getName() , cust.getStreet() , cust.getNumberOnStreet(), cust.getCity(), cust.getPhoneNo()});
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
