package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controler.ProductCtr;
import model.Product;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;

public class ProductPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfName;
	private JTextField txfMinStock;
	private JTextField txfPrice;
	private JTextField txfTypeOfMeat;
	private JTextField txfSupplier;
	JLabel lblWeightError;
	
	Object[][] data = null;
	static DefaultTableModel model;
	private JTextField txfSearch;
	
	String [] header={"Serial Number","Name","Minimal Stock","Price","Type of meat","Name of Supplier"};
	String [] fieldValues={"id","name","purchasePrice","salesPrice","rentPrice","countryOfOrigin","minStock", "nameOfSupplier"};
	
	
	
	/* Fields for saving the new products values */
	private String name;
	private String minStock;
	private String supplier;
	private String typeOfMeat;
	private String price;

	String errorMessage;
	
	JLabel lblPriceError;	
	
	String[] fieldsArray = {"Product name", "Supplier" , "Type of meat"};
	int[] sizesArray = {100, 50, 50};
	
	
	
	public boolean checkData(String myString) {
		if(myString.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	

	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		setLayout(null);
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 812);
		setLayout(null);				
		
		
		JLabel lblProductManagement = new JLabel("Product Management");
		lblProductManagement.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblProductManagement.setBounds(11, 11, 272, 26);
		add(lblProductManagement);
		
		JSeparator productManagementSeparator = new JSeparator();
		productManagementSeparator.setForeground(new Color(204, 0, 51));
		productManagementSeparator.setBounds(11, 44, 359, 10);
		add(productManagementSeparator);
		
		JPanel newProductPanel = new JPanel();
		newProductPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		newProductPanel.setBackground(new Color(255, 255, 255));
		newProductPanel.setBounds(11, 76, 554, 508);
		add(newProductPanel);
		newProductPanel.setLayout(null);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(24, 44, 133, 19);
		newProductPanel.add(lblProductName);
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txfName = new JTextField();
		txfName.setBounds(24, 74, 194, 20);
		newProductPanel.add(txfName);
		txfName.setBorder(null);
		txfName.setColumns(10);
		
		JSeparator NameSeparator = new JSeparator();
		NameSeparator.setBounds(24, 94, 194, 10);
		newProductPanel.add(NameSeparator);
		
		JLabel lblMinStock = new JLabel("Minimal Stock");
		lblMinStock.setBounds(24, 143, 133, 19);
		newProductPanel.add(lblMinStock);
		lblMinStock.setFont(new Font("Tahoma", Font.BOLD, 16));
	

        txfMinStock = new JTextField();
        txfMinStock.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyReleased(KeyEvent e) {
				if(!Main.isNumeric(txfMinStock.getText())) {
					lblWeightError.setText("Invalid format, must be numbers");
				} else {
					lblWeightError.setText("");
				}
			}
        
        });
        
		txfMinStock.setBounds(24, 173, 194, 20);
		newProductPanel.add(txfMinStock);
		txfMinStock.setColumns(10);
		txfMinStock.setBorder(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(24, 193, 194, 10);
		newProductPanel.add(separator);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(315, 44, 133, 19);
		newProductPanel.add(lblPrice);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		
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
		txfPrice.setBounds(315, 74, 194, 20);
		newProductPanel.add(txfPrice);
		txfPrice.setColumns(10);
		txfPrice.setBorder(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(315, 94, 194, 10);
		newProductPanel.add(separator_1);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setBounds(24, 226, 133, 19);
		newProductPanel.add(lblSupplier);
		lblSupplier.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txfSupplier = new JTextField();
		txfSupplier.setBounds(24, 256, 194, 20);
		newProductPanel.add(txfSupplier);
		txfSupplier.setColumns(10);
		txfSupplier.setBorder(null);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(24, 276, 194, 10);
		newProductPanel.add(separator_4);
		
		JLabel lblTypeOfMeat = new JLabel("Type of meat");
		lblTypeOfMeat.setBounds(315, 226, 133, 19);
		newProductPanel.add(lblTypeOfMeat);
		lblTypeOfMeat.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		txfTypeOfMeat = new JTextField();
		txfTypeOfMeat.setBounds(315, 256, 194, 20);
		newProductPanel.add(txfTypeOfMeat);
		txfTypeOfMeat.setColumns(10);
		txfTypeOfMeat.setBorder(null);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(314, 276, 195, 10);
		newProductPanel.add(separator_3);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(216, 414, 99, 32);
		newProductPanel.add(btnCreate);
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCreate.setBackground(new Color(204, 0, 51));
		btnCreate.setForeground(new Color(255, 255, 255));
		
		lblPriceError = new JLabel("");
		lblPriceError.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblPriceError.setForeground(Color.RED);
		lblPriceError.setBounds(315, 104, 194, 14);
		newProductPanel.add(lblPriceError);
		
		lblWeightError = new JLabel("");
		lblWeightError.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblWeightError.setForeground(new Color(204, 0, 51));
		lblWeightError.setBounds(24, 206, 226, 18);
		newProductPanel.add(lblWeightError);
		
		JTextPane errorPane = new JTextPane();
        errorPane.setFont(new Font("Tahoma", Font.PLAIN, 17));
        errorPane.setForeground(Color.RED);
        errorPane.setBounds(623, 585, 594, 87);
        add(errorPane);
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				name = txfName.getText();
				supplier = txfSupplier.getText();
				typeOfMeat = txfTypeOfMeat.getText();
				price = txfPrice.getText();
				minStock = txfMinStock.getText();
				
				errorMessage = "";
				
				String[] fields = {name, supplier, typeOfMeat};
				
				String[] allfields = {name, supplier, typeOfMeat, price, minStock};
				
				
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
						errorMessage += "\n - Please fill all the fields before creating the product";
						break;
					}
				
				}
				
				if(errorMessage.equals("") && lblPriceError.getText().equals("")) {
					errorPane.setVisible(false);
					
					Double dPrice = Double.valueOf(price);
					int iMinstock = Integer.valueOf(minStock);
					
					
					ProductCtr product = new ProductCtr();
					try {
						product.insertProduct(name, dPrice, typeOfMeat, iMinstock, supplier);	
						JOptionPane.showMessageDialog(null, "Product added successefully", "Success", 1);
						printAllProducts();
						txfName.setText("");
						txfSupplier.setText("");
						txfTypeOfMeat.setText("");
						txfPrice.setText("");
						txfMinStock.setText("");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "The product name already exists", "Error", 1);
						//e.printStackTrace();
					}
					
					
					
					
				} else {
					errorPane.setText(errorMessage);
					
				}
	
			}
		});
		 
        model = new DefaultTableModel(data,header);
        
		JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        table.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
        table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);

        // Disable row editor
        table.setDefaultEditor(Object.class, null);

        // Disable header dragging
        table.getTableHeader().setReorderingAllowed(false);


        JScrollPane js=new JScrollPane(table);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        js.setVisible(true);
        js.setSize(807, 465);
        js.setLocation(623, 119);
        add(js);
        
        txfSearch = new JTextField();
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			printAllProducts();
        		} else {
        			String toSearch = txfSearch.getText();
            		searchProductByName(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			printAllProducts();
        			txfSearch.setText("Search by product name...");
        		}
        	}
        });
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(0, 0, 0));      		
        		if(txfSearch.getText().equals("Search by product name...")){
        			txfSearch.setText("");
        		}
        	}
        });
        
        txfSearch.setForeground(new Color(204, 204, 204));
        txfSearch.setText("Search by product name...");
        txfSearch.setBounds(623, 76, 298, 20);
        add(txfSearch);
        txfSearch.setColumns(10);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(new Color(204, 0, 51));
        btnDelete.setBounds(1341, 596, 89, 23);
        add(btnDelete);
        
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = table.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String id = table.getValueAt(myRow, 0).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the product" +id+" ?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	ProductCtr myProduct = new ProductCtr();
	                	int deleted = myProduct.deleteProduct(id);
	                	if(deleted == -1) {
	                		JOptionPane.showMessageDialog(null, "The product could not be deleted, there is still stock in the warehouse", "Error", 1);
	                	} else {
	                		JOptionPane.showMessageDialog(null, "Product deleted with success", "Success", 1);
	                		printAllProducts();
	                	}
	                	
	                }
				}
        	}
        });
        
        
        
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setForeground(new Color(255, 255, 255));
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(1242, 596, 89, 23);
        add(btnUpdate);
        
        
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {

				int myRow = table.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
				} else {
					String info[]=new String[6];
					String id = table.getValueAt(myRow, 0).toString();
					String name = table.getValueAt(myRow, 1).toString();
					String minimalStock = table.getValueAt(myRow, 2).toString();
					String price = table.getValueAt(myRow, 3).toString();
					String typeOfMeat = table.getValueAt(myRow, 4).toString();
					String supplier = table.getValueAt(myRow, 5).toString();
					
					info[0] = name;
					info[1] = minimalStock;
					info[2] = price;
					info[3] = typeOfMeat;
					info[4] = supplier;
					info[5] = id;

					ProductUpdate.main(info);

				}
            		
        	}
        });
      
        
        
        printAllProducts();
        
	}
	
	public static void printAllProducts() {
		model.setNumRows(0);
		ProductCtr myProduct = new ProductCtr();
		try {
			ArrayList<Product> prod = myProduct.getAll();
			for (Product product : prod) {
	          model.addRow(new Object[]{product.getId(), product.getName(), product.getMinStock(), product.getPrice(), product.getTypeOfMeat(), product.getNameOfSupplier()});
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void searchProductByName(String name) {
		model.setNumRows(0);
		ProductCtr myProduct = new ProductCtr();
		try {
			ArrayList<Product> prod = myProduct.searchByName(name);
			for (Product product : prod) {
	          model.addRow(new Object[]{product.getId(), product.getName(), product.getMinStock(), product.getPrice(), product.getTypeOfMeat(), product.getNameOfSupplier()});
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
