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

import controler.StoreCtr;
import model.Store;


import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StoreSettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfNameOfStore;
	
	
	Object[][] data = null;
	static DefaultTableModel model;
	
	
	
	String [] header={"Store ID", "Name of the store", "Address"};
	
	private JTextField txfSearch;
	private JTextField txfAddress;
	
	String errorMessage;
	

	String[] fieldsArray = {"Name of the store", "Address"};
	int[] sizesArray = {50, 70};


	/**
	 * Create the panel.
	 */
	public StoreSettingsPanel(int username) {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 641);
		setLayout(null);			
		setLayout(null);
		
		JLabel lblStore = new JLabel("Store");
		lblStore.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblStore.setBounds(10, 11, 272, 26);
		add(lblStore);
		
		JSeparator storeSeparator = new JSeparator();
		storeSeparator.setForeground(new Color(204, 0, 51));
		storeSeparator.setBounds(10, 44, 359, 10);
		add(storeSeparator);
		
		
		JPanel storePanel = new JPanel();
		storePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		storePanel.setBackground(Color.WHITE);
		storePanel.setBounds(10, 65, 336, 252);
		add(storePanel);
		
		storePanel.setLayout(null);
		
		JLabel lblNameOfStore = new JLabel("Name of store");
		lblNameOfStore.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNameOfStore.setBounds(26, 23, 181, 19);
		storePanel.add(lblNameOfStore);
		
		txfNameOfStore = new JTextField();
		txfNameOfStore.setBorder(null);
		txfNameOfStore.setBounds(26, 48, 194, 20);
		storePanel.add(txfNameOfStore);
		txfNameOfStore.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 72, 194, 10);
		storePanel.add(separator_1);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddress.setBounds(26, 87, 152, 14);
		storePanel.add(lblAddress);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(26, 136, 193, 2);
		storePanel.add(separator_2);
		

		JTextPane errorPane = new JTextPane();
        errorPane.setFont(new Font("Tahoma", Font.PLAIN, 17));
        errorPane.setForeground(Color.RED);
        errorPane.setBounds(623, 596, 399, 155);
        add(errorPane);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				String nameOfStore = txfNameOfStore.getText();
				String address = txfAddress.getText();
			
        		
	            errorMessage = "";
				
				String[] allfields = {nameOfStore, address};
				
				String[] fields = {nameOfStore, address};
				
				
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
						errorMessage += "\n - Please fill all the fields before creating the store";
						break;
					}
				
				}
				
				if(errorMessage.equals("")) {
					errorPane.setVisible(false);
					
				
					
					StoreCtr store = new StoreCtr();
					try {
						store.insertStore(nameOfStore, address);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					JOptionPane.showMessageDialog(null, "Store added successefully", "Success", 1);
					getAllStores();
					txfNameOfStore.setText("");
					txfAddress.setText("");
					
					
				} else {
					errorPane.setText(errorMessage);
					
				}
	
			}
		});
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(204, 0, 51));
		btnCreate.setBounds(10, 218, 89, 23);
		storePanel.add(btnCreate);
		
		txfAddress = new JTextField();
		txfAddress.setColumns(10);
		txfAddress.setBorder(null);
		txfAddress.setBounds(26, 112, 194, 20);
		storePanel.add(txfAddress);
		
		
		
		
		model = new DefaultTableModel(data,header);
        
		
		JTable tblStores = new JTable(model);
		tblStores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblStores.setColumnSelectionAllowed(true);
		tblStores.setCellSelectionEnabled(true);
		tblStores.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblStores.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblStores.setFillsViewportHeight(true);

        // Disable row editor
		tblStores.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblStores.getTableHeader().setReorderingAllowed(false);


        JScrollPane jsStores = new JScrollPane(tblStores);
        jsStores.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsStores.setVisible(true);
        jsStores.setSize(574, 394);
        jsStores.setLocation(448, 65);
        add(jsStores);
        
        txfSearch = new JTextField();
        
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			getAllStores();
        		} else {
        			String toSearch = txfSearch.getText();
            		searchStoreByName(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			getAllStores();
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
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(830, 470, 89, 23);
        add(btnUpdate);
        
        JButton btnCancel = new JButton("Cancel");
        JButton btnUpdateSet = new JButton("Update");
        
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
			        		
        		int myRow = tblStores.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
				} else {
					
					
					String id = tblStores.getValueAt(myRow, 0).toString();
				    String name = tblStores.getValueAt(myRow, 1).toString();
					String address = tblStores.getValueAt(myRow, 2).toString();
					
        		
	        		txfNameOfStore.setText(name);
	        		txfAddress.setText(address);
	        		
					
					errorMessage = "";
					
					String[] fields = {name,address};
					
					
					btnCreate.setVisible(false);
					btnUpdate.setVisible(false);
					btnDelete.setVisible(false);
					btnUpdateSet.setVisible(true);
					btnCancel.setVisible(true);
					
	
					btnCancel.setBounds(10, 218, 89, 23);
					storePanel.add(btnCancel);
					btnCancel.setForeground(new Color(255, 255, 255));
					btnCancel.setBackground(new Color(204, 0, 51));
					 
					btnCancel.addMouseListener(new MouseAdapter() {
						 public void mousePressed(MouseEvent arg0) {
							 
							    btnCreate.setVisible(true);
								btnUpdate.setVisible(true);
								btnDelete.setVisible(true);
								btnUpdateSet.setVisible(false);
								btnCancel.setVisible(false);
								
								txfNameOfStore.setText("");
								txfAddress.setText("");
								getAllStores();
							 
							 
						 }
					 });
				
				
					btnUpdateSet.setBounds(237, 217, 89, 23);
					storePanel.add(btnUpdateSet);
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
					
							String name = txfNameOfStore.getText();
							String address = txfAddress.getText();
						
				
							if(!name.equals("") && !address.equals("")) {
							
					
								int storeId = Integer.valueOf(id);
								
								StoreCtr store = new StoreCtr();
								try {
								
						
									store.updateStore(storeId, name, address);
									JOptionPane.showMessageDialog(null, "Store updated successefully", "Success", 1);
				
									
									getAllStores();
				            		
				            		btnCreate.setVisible(true);
									btnUpdate.setVisible(true);
									btnDelete.setVisible(true);
									btnUpdateSet.setVisible(false);
									btnCancel.setVisible(false);
									txfNameOfStore.setText("");
									txfAddress.setText("");
							
							
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, errorMessage, "Error updating the store", 1);
								}
								
							} else {
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
        		
        		int myRow = tblStores.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String id = tblStores.getValueAt(myRow, 0).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the store " +id+" ?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	StoreCtr myStore=new StoreCtr();
	                	myStore.deleteStore(id);
	                    
	                		JOptionPane.showMessageDialog(null, "Store deleted with success", "Success", 1);
	                		getAllStores();
	                	
	                	
	                }
				}
        	}
        });
		
        
        getAllStores();
        
	}
	
	
	public void getAllStores() {
		model.setNumRows(0);
		StoreCtr myStores = new StoreCtr();
		try {
			ArrayList<Store> stores = myStores.getAll();
			for (Store store : stores) {
				model.addRow(new Object[]{store.getId(), store.getStoreName() , store.getAddress()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void searchStoreByName(String name) {
		model.setNumRows(0);
		StoreCtr myStores = new StoreCtr();
		try {
			ArrayList<Store> stores = myStores.searchByName(name);
			for (Store store : stores) {
	          model.addRow(new Object[]{store.getId(), store.getStoreName() , store.getAddress()});
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
