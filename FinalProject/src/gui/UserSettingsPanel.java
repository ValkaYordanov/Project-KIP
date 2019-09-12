package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
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

import controler.UserCtr;
import model.User;


import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPasswordField;

public class UserSettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfNameOfUser;
	
	
	Object[][] data = null;
	DefaultTableModel model;
	String errorMessage;
	
	String [] header={"User ID","Login ID","Name","Type of user"};
	
	String[] fieldsArray = {"User name", "Password"};
	int[] sizesArray = {75, 400};
	
	private JComboBox<String> cbbTypeOfUser;
	private JTextField txfSearch;
	private JPasswordField password;
	private JPasswordField passwordConfirm;
	private JTextField txfLoginId;

	/**
	 * Create the panel.
	 */
	public UserSettingsPanel(int username) {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 641);
		setLayout(null);			
		setLayout(null);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblUser.setBounds(10, 11, 272, 26);
		add(lblUser);
		
		JSeparator usersSeparator = new JSeparator();
		usersSeparator.setForeground(new Color(204, 0, 51));
		usersSeparator.setBounds(10, 44, 359, 10);
		add(usersSeparator);
		
		
		JPanel userPanel = new JPanel();
		userPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		userPanel.setBackground(Color.WHITE);
		userPanel.setBounds(10, 65, 398, 460);
		add(userPanel);
		
		userPanel.setLayout(null);
		JLabel lblTypeOfUser = new JLabel("Type of user");
		lblTypeOfUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTypeOfUser.setBounds(26, 23, 133, 19);
		userPanel.add(lblTypeOfUser);
		
		
		cbbTypeOfUser = new JComboBox<String>();
		cbbTypeOfUser.setBounds(26, 53, 112, 20);
		cbbTypeOfUser.setMaximumRowCount(900);
        cbbTypeOfUser.addItem("Select one type of user");
        cbbTypeOfUser.addItem("Manager");
        cbbTypeOfUser.addItem("Employee");
		userPanel.add(cbbTypeOfUser);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 403, 194, 10);
		userPanel.add(separator);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConfirmPassword.setBounds(26, 349, 133, 19);
		userPanel.add(lblConfirmPassword);
		
		JLabel lblNameOfUser = new JLabel("Name");
		lblNameOfUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNameOfUser.setBounds(26, 181, 181, 19);
		userPanel.add(lblNameOfUser);
		
		txfNameOfUser = new JTextField();
		txfNameOfUser.setBorder(null);
		txfNameOfUser.setBounds(26, 211, 194, 20);
		userPanel.add(txfNameOfUser);
		txfNameOfUser.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 235, 194, 10);
		userPanel.add(separator_1);
		
		JLabel lblProductionDate = new JLabel("Password");
		lblProductionDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProductionDate.setBounds(26, 269, 152, 14);
		userPanel.add(lblProductionDate);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(26, 318, 193, 2);
		userPanel.add(separator_2);
		
		JTextPane errorPane = new JTextPane();
        errorPane.setFont(new Font("Tahoma", Font.PLAIN, 17));
        errorPane.setForeground(Color.RED);
        errorPane.setBounds(448, 583, 375, 152);
        add(errorPane);
		
        
        JButton btnCreate = new JButton("Create");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				
				String login = txfLoginId.getText();
				String nameOfUser = txfNameOfUser.getText();
				String pass = new String(password.getPassword());
				String passConfirm = new String(passwordConfirm.getPassword());
				int typeOfUser = cbbTypeOfUser.getSelectedIndex();
				
				
	            errorMessage = "";
				
                String[] fields = {nameOfUser, pass};
				
				// Compare length of input fields to see if it's bigger than allowed on the database
				for(int i = 0; i < fields.length; i++) {
					if(fields[i].length() > sizesArray[i]) {
						errorMessage += "\n - Invalid number of characters in " + fieldsArray[i] + ". \n" +
								"Maximum allowed is: " + sizesArray[i] + "\n";
					}
				
				}
				
				if(errorMessage.equals("")) {
					errorPane.setVisible(false);
				
				
				
				if(cbbTypeOfUser.getSelectedIndex() != 0 && !login.equals("") &&  !nameOfUser.equals("") && !pass.equals("") && !passConfirm.equals("")) {
					
					
						
						if(!pass.equals(passConfirm)) {
							JOptionPane.showMessageDialog(null, "The passwords do not match", "Error", 1);
						} else {
							int loginId = Integer.valueOf(login);
							UserCtr myUser = new UserCtr();
							try {
								if(myUser.checkEmployeeID(loginId) == null) {
									
									try {
										myUser.insertUser(loginId, nameOfUser, pass,typeOfUser);
										JOptionPane.showMessageDialog(null, "User added successefully", "Success", 1);

					            		cbbTypeOfUser.setSelectedIndex(0);
					            		txfNameOfUser.setText("");
					            		txfLoginId.setText("");
					            		password.setText("");
					            		passwordConfirm.setText("");
					            		printAllUsers();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									
								} else {
									JOptionPane.showMessageDialog(null, "There is already a employee with that ID", "Error", 1);
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							
							
						}

					
				
					
					
					
				}  else {
        			JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
        		}
					
				}else {
					errorPane.setText(errorMessage);
				}
	
			}
		});
        
		
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(204, 0, 51));
		btnCreate.setBounds(26, 424, 89, 23);
		userPanel.add(btnCreate);
		
		password = new JPasswordField();
		password.setBorder(null);
		password.setBounds(26, 379, 194, 20);
		userPanel.add(password);
		
		passwordConfirm = new JPasswordField();
		passwordConfirm.setBorder(null);
		passwordConfirm.setBounds(26, 294, 194, 20);
		userPanel.add(passwordConfirm);
		
		JLabel lblEmployeeId = new JLabel("Login ID");
		lblEmployeeId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeId.setBounds(26, 114, 181, 19);
		userPanel.add(lblEmployeeId);
		
		JLabel lblLoginError = new JLabel("");
		lblLoginError.setBounds(24, 166, 196, 19);
		userPanel.add(lblLoginError);
		  
		
		txfLoginId = new JTextField();
		txfLoginId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isNumber(txfLoginId.getText())) {
					lblLoginError.setText("Invalid format, must be numbers");
				} else {
					lblLoginError.setText("");
				}
			}
		});
		txfLoginId.setColumns(10);
		txfLoginId.setBorder(null);
		txfLoginId.setBounds(26, 135, 194, 20);
		userPanel.add(txfLoginId);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(26, 158, 194, 10);
		userPanel.add(separator_3);
		
		
	
	        
	  
	        
		
        model = new DefaultTableModel(data,header);
        
		
		JTable tblUsers = new JTable(model);
		tblUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblUsers.setColumnSelectionAllowed(true);
		tblUsers.setCellSelectionEnabled(true);
		tblUsers.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblUsers.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblUsers.setFillsViewportHeight(true);

        // Disable row editor
		tblUsers.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblUsers.getTableHeader().setReorderingAllowed(false);


        JScrollPane jsUsers = new JScrollPane(tblUsers);
        jsUsers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsUsers.setVisible(true);
        jsUsers.setSize(492, 460);
        jsUsers.setLocation(448, 65);
        add(jsUsers);
        
        txfSearch = new JTextField();
        
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			printAllUsers();
        		} else {
        			String toSearch = txfSearch.getText();
        			searchUsersByName(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			printAllUsers();
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
        btnDelete.setBounds(851, 537, 89, 23);
        add(btnDelete);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(204, 0, 51));
        
          JButton btnUpdate = new JButton("Update");
          btnUpdate.setBounds(742, 538, 89, 23);
          add(btnUpdate);
          btnUpdate.setForeground(Color.WHITE);
          btnUpdate.setBackground(new Color(204, 0, 51));
        
          JButton btnUpdateSet = new JButton("Update");
          JButton btnCancel = new JButton("Cancel");
          
  	  btnUpdate.addMouseListener(new MouseAdapter() {
		  	@Override
		  	public void mousePressed(MouseEvent arg0) {
			        		
			        		int myRow = tblUsers.getSelectedRow();
							if(myRow == -1) {
								JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
							} else {
								
								UserCtr user= new UserCtr();
								User us = new User();
								
								String id = tblUsers.getValueAt(myRow, 0).toString();
							    String loginId = tblUsers.getValueAt(myRow, 1).toString();
								String name= tblUsers.getValueAt(myRow, 2).toString();
								
								
								int userId=Integer.parseInt(id);
								try {
									us = user.findById(userId);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							
								
								
						    //cbbTypeOfUser.addItem(typeOfUser);
			        		txfLoginId.setText(loginId);
			        		txfNameOfUser.setText(name);
			        	
			        		
								
			        		
							
							errorMessage = "";
							
							 String[] field = {name};
							
						     int[] size = {75};
							
							
					
							btnCreate.setVisible(false);
							btnUpdate.setVisible(false);
							btnDelete.setVisible(false);
							btnUpdateSet.setVisible(true);
							btnCancel.setVisible(true);
							
							 btnUpdateSet.setBounds(285, 424, 89, 23);
							 userPanel.add(btnUpdateSet);
							 btnUpdateSet.setForeground(new Color(255, 255, 255));
							 btnUpdateSet.setBackground(new Color(204, 0, 51));
							
							 
							 
							 
							 btnCancel.setBounds(26, 424, 89, 23);
								 userPanel.add(btnCancel);
								 btnCancel.setForeground(new Color(255, 255, 255));
								 btnCancel.setBackground(new Color(204, 0, 51));
								 
								 btnCancel.addMouseListener(new MouseAdapter() {
									 public void mousePressed(MouseEvent arg0) {
										 
										    btnCreate.setVisible(true);
											btnUpdate.setVisible(true);
											btnDelete.setVisible(true);
											btnUpdateSet.setVisible(false);
											btnCancel.setVisible(false);
											
											cbbTypeOfUser.setSelectedIndex(0);
						            		txfNameOfUser.setText("");
						            		txfLoginId.setText("");
						            		password.setText("");
						            		passwordConfirm.setText("");
						            		printAllUsers();
										 
										 
									 }
								 });
								 
								 
							        
							 
						        btnUpdateSet.addMouseListener(new MouseAdapter() {
						        	@Override
						        	public void mousePressed(MouseEvent arg0) {
						        		
						        		
						        		// Compare length of input fields to see if it's bigger than allowed on the database
										for(int i = 0; i < field.length; i++) {
											if(field[i].length() > size[i]) {
												JOptionPane.showMessageDialog(null, "Invalid number of characters in " + fieldsArray[i], "Error", 1);
												//errorMessage += "Invalid number of characters in " + fieldsArray[i];
												break;
											}
										
										}
										

										String loginId = txfLoginId.getText();
										int login=Integer.parseInt(loginId);
										String nameOfUser = txfNameOfUser.getText();
										String pass = new String(password.getPassword());
										String passConfirm = new String(passwordConfirm.getPassword());
										int typeOfUser = cbbTypeOfUser.getSelectedIndex();
										
								
										if(cbbTypeOfUser.getSelectedIndex() != 0 && !loginId.equals("") &&  !nameOfUser.equals("") && !pass.equals("") && !passConfirm.equals("")) {
										
											if(!pass.equals(passConfirm)) {
												JOptionPane.showMessageDialog(null, "The passwords do not match", "Error", 1);
											} else {
												int userId = Integer.valueOf(id);
												UserCtr myUser = new UserCtr();
												try {
													myUser.updateUser(userId, login, nameOfUser, pass,typeOfUser);
													JOptionPane.showMessageDialog(null, "User updated successefully", "Success", 1);
	
								            		cbbTypeOfUser.setSelectedIndex(0);
								            		txfNameOfUser.setText("");
								            		txfLoginId.setText("");
								            		password.setText("");
								            		passwordConfirm.setText("");
								            		printAllUsers();
								            		
								            		btnCreate.setVisible(true);
													btnUpdate.setVisible(true);
													btnDelete.setVisible(true);
													btnUpdateSet.setVisible(false);
													btnCancel.setVisible(false);
	
												} catch (Exception e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
													JOptionPane.showMessageDialog(null, errorMessage, "Error updating the user", 1);
												}
											}
										}  
										else {
											JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
										}
								}
						      
						        });
							} 
						} 	
		  	});

  

  	
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = tblUsers.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String id = tblUsers.getValueAt(myRow, 0).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the user " +id+" ?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	UserCtr myUser=new UserCtr();
	                	myUser.deleteUser(id);
	                    
	                		JOptionPane.showMessageDialog(null, "User deleted with success", "Success", 1);
	                		printAllUsers();
	                	
	                	
	                }
				}
        	}
        });
		
        printAllUsers();
        
	}
	
	public void getAllUsersNames() {
		UserCtr myUser = new UserCtr();
		try {
			ArrayList<User> user = myUser.getAllUsers();
			for (User usr : user) {
				model.addRow(new Object[]{ usr.getName()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void printAllUsers() {
		model.setNumRows(0);
		UserCtr myUser = new UserCtr();
		try {
			ArrayList<User> user = myUser.getAllUsers();
			for (User usr : user) {
				model.addRow(new Object[]{usr.getId() , usr.getUserName() , usr.getName(), usr.getTypeOfUser()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void searchUsersByName(String name) {
		model.setNumRows(0);
		UserCtr myUser = new UserCtr();
		try {
			ArrayList<User> user = myUser.searchUserByName(name);
			for (User usr : user) {
				model.addRow(new Object[]{usr.getId() , usr.getUserName() , usr.getName(), usr.getTypeOfUser()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static boolean isNumber(String strNum) {
	    return strNum.matches("-?\\d+(\\d+)?");
	}
}
