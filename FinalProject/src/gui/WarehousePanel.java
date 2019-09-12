package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Robot;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controler.ProductCtr;
import controler.WarehouseCtr;
import model.Product;
import model.Warehouse;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class WarehousePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfWeight;
	private JTextField txfDaysForComsuption;
	private JTextField txfProductionDate;
	
	
	Object[][] data = null;
	static DefaultTableModel model;
	
	private JTable tblWarehouse;
	
	String [] header={"Serial Number","Product Name","Stock","Expire Date","Production Date"};
	
	
	private JComboBox<String> cbbProducts;
	private JTextField txfSearch;

	/**
	 * Create the panel.
	 */
	public WarehousePanel(int username) {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 641);
		setLayout(null);			
		
		JLabel lblWarehouse = new JLabel("Warehouse");
		lblWarehouse.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblWarehouse.setBounds(10, 11, 272, 26);
		add(lblWarehouse);
		
		JSeparator warehouseSeparator = new JSeparator();
		warehouseSeparator.setForeground(new Color(204, 0, 51));
		warehouseSeparator.setBounds(10, 44, 359, 10);
		add(warehouseSeparator);
		
		
		JPanel deliveryPanel = new JPanel();
		deliveryPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deliveryPanel.setBackground(Color.WHITE);
		deliveryPanel.setBounds(10, 65, 536, 438);
		add(deliveryPanel);
		
		deliveryPanel.setLayout(null);
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProductName.setBounds(26, 40, 133, 19);
		deliveryPanel.add(lblProductName);
		
		
		cbbProducts = new JComboBox<String>();
		cbbProducts.setBounds(26, 70, 194, 20);
		cbbProducts.setMaximumRowCount(900);
        cbbProducts.addItem("Select one product");
		deliveryPanel.add(cbbProducts);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(301, 90, 194, 10);
		deliveryPanel.add(separator);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWeight.setBounds(301, 40, 133, 19);
		deliveryPanel.add(lblWeight);
		

		JLabel lblWeightError = new JLabel("");
		lblWeightError.setBounds(288, 101, 210, 19);
		deliveryPanel.add(lblWeightError);
		
		
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
		
		txfWeight.setBorder(null);
		txfWeight.setBounds(301, 70, 202, 20);
		deliveryPanel.add(txfWeight);
		txfWeight.setColumns(10);
		
		JLabel lblDaysForConsumption = new JLabel("Days for consumption");
		lblDaysForConsumption.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDaysForConsumption.setBounds(26, 149, 181, 19);
		deliveryPanel.add(lblDaysForConsumption);
		
		JLabel lblDayError = new JLabel("");
		lblDayError.setBounds(21, 214, 199, 23);
		deliveryPanel.add(lblDayError);
		
		txfDaysForComsuption = new JTextField();
		txfDaysForComsuption.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!isNumberDay(txfDaysForComsuption.getText())) {
					lblDayError.setText("Invalid format, must be numbers");
				} else {
					lblDayError.setText("");
				}
			}
		});
		txfDaysForComsuption.setBorder(null);
		txfDaysForComsuption.setBounds(26, 185, 194, 20);
		deliveryPanel.add(txfDaysForComsuption);
		txfDaysForComsuption.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 205, 194, 10);
		deliveryPanel.add(separator_1);
		
		JLabel lblProductionDate = new JLabel("Production date");
		lblProductionDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProductionDate.setBounds(301, 153, 152, 14);
		deliveryPanel.add(lblProductionDate);
		
		
		
		JLabel lblDateError = new JLabel("");
		lblDateError.setBounds(109, 280, 344, 55);
		deliveryPanel.add(lblDateError);
		
		
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
		txfProductionDate.setBorder(null);
		txfProductionDate.setBounds(301, 185, 194, 19);
		deliveryPanel.add(txfProductionDate);
		txfProductionDate.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(302, 205, 193, 2);
		deliveryPanel.add(separator_2);
		
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				String productName = (String) cbbProducts.getSelectedItem();
        		String weight = txfWeight.getText();
        		String daysForComsuption = txfDaysForComsuption.getText();
        		String productionDate = txfProductionDate.getText();
        		
        		if(cbbProducts.getSelectedIndex() != 0 && !weight.equals("") && !daysForComsuption.equals("") && !productionDate.equals("")) {
        			
        			WarehouseCtr myWarehouse = new WarehouseCtr();
        			
        			double weightD = Double.valueOf(weight);
        			
        			Date productionDateD = Date.valueOf(productionDate);
        			
        			
        			// Convert the days for integer
        			int daysForComsuptionI = Integer.valueOf(daysForComsuption);
        			
        			LocalDate comsuptionDate = LocalDate.parse(productionDate);
        			comsuptionDate = comsuptionDate.plusDays(daysForComsuptionI);
        			

        			// Convert to sql date format
        			Date daysForComsuptionDate = Date.valueOf(comsuptionDate);
        					
        			
					try {
						int productId = myWarehouse.getIdFromProductName(productName);
						myWarehouse.insertProductWarehouse(productId,productName, weightD, daysForComsuptionDate, productionDateD);
	        			
	        			txfWeight.setText("");
	            		cbbProducts.setSelectedIndex(0);
	            		txfProductionDate.setText("");
	            		txfDaysForComsuption.setText("");
	            		
	            		JOptionPane.showMessageDialog(null, "Product added to warehouse", "Success", 1);
	            		Main.checkForMinStock();
	            		getAllProducts();
	            		
	            		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Couldn't find a product with that name", "Error", 1);
					}
      
        			
        			
        		} else {
        			JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", 1);
        		}
				
				
				//LocalDate.now().plusDays(nrOfDays);
			}
		});
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(204, 0, 51));
		btnCreate.setBounds(214, 377, 89, 23);
		deliveryPanel.add(btnCreate);
		
		
		
		model = new DefaultTableModel(data,header);
        
		tblWarehouse = new JTable(model);
		tblWarehouse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblWarehouse.setColumnSelectionAllowed(true);
		tblWarehouse.setCellSelectionEnabled(true);
		tblWarehouse.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblWarehouse.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblWarehouse.setFillsViewportHeight(true);

        // Disable row editor
		tblWarehouse.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblWarehouse.getTableHeader().setReorderingAllowed(false);


        JScrollPane jsDelivery = new JScrollPane(tblWarehouse);
        jsDelivery.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsDelivery.setVisible(true);
        jsDelivery.setSize(807, 412);
        jsDelivery.setLocation(604, 91);
        add(jsDelivery);
        
        txfSearch = new JTextField();
        
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			getAllProducts();
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
        			getAllProducts();
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
        
        txfSearch.setForeground(Color.LIGHT_GRAY);
        txfSearch.setText("Search by product name...");
        txfSearch.setBounds(604, 65, 343, 20);
        add(txfSearch);
        txfSearch.setColumns(10);
        
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.setForeground(new Color(255, 255, 255));
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(1208, 514, 89, 23);
        add(btnUpdate);
        
        
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {

				int myRow = tblWarehouse.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
				} else {
					String info[]=new String[6];
					String serialNumber = tblWarehouse.getValueAt(myRow, 0).toString();
					String name = tblWarehouse.getValueAt(myRow, 1).toString();
					String stock = tblWarehouse.getValueAt(myRow, 2).toString();
					String expireDate = tblWarehouse.getValueAt(myRow, 3).toString();
					String productionDate = tblWarehouse.getValueAt(myRow, 4).toString();
					
					info[0] = serialNumber;
					info[1] = name;
					info[2] = stock;
					info[3] = expireDate;
					info[4] = productionDate;
					
					
					WarehouseUpdate.main(info);
					try {
						Main.checkForMinStock();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
            		
        	}
        });
        
        
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(new Color(204, 0, 51));
        btnDelete.setBounds(1322, 514, 89, 23);
        add(btnDelete);
        
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = tblWarehouse.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String id = tblWarehouse.getValueAt(myRow, 0).toString();
					int idS = Integer.valueOf(id);
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the product" +id+" ?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	WarehouseCtr myProduct = new WarehouseCtr();
	                	int deleted = myProduct.deleteProduct(idS);
	                	if(deleted == -1) {
	                		JOptionPane.showMessageDialog(null, "The product could not be deleted", "Error", 1);
	                	} else {
	                		JOptionPane.showMessageDialog(null, "Product deleted from the warehouse with success", "Success", 1);
	                		getAllProducts();
	                	}
	                	
	                }
				}
        	}
        });
        
        
        
        
		
        getAllProductNames();
        getAllProducts();
	}
	
	public void getAllProductNames() {
		ProductCtr myProduct = new ProductCtr();
		try {
			ArrayList<Product> prod = myProduct.getAll();
			for (Product product : prod) {
				cbbProducts.addItem(product.getName());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public static void getAllProducts() {
		model.setNumRows(0);
		WarehouseCtr myWarehouse = new WarehouseCtr();
		try {
			ArrayList<Warehouse> warehouse = myWarehouse.getAllProducts();
			for (Warehouse prod : warehouse) {
				model.addRow(new Object[]{prod.getSerialNumber() , prod.getProductName(), prod.getStock(), prod.getExpirationDate(), prod.getProductionDate()});
			
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void searchProductByName(String name) {
		model.setNumRows(0);
		WarehouseCtr myWarehouse = new WarehouseCtr();
		try {
			ArrayList<Warehouse> warehouse = myWarehouse.searchByName(name);
			for (Warehouse prod : warehouse) {
	          model.addRow(new Object[]{prod.getSerialNumber(), prod.getProductName(), prod.getStock(), prod.getExpirationDate(), prod.getProductionDate()});
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static boolean isNumberDay(String strNum) {
	    return strNum.matches("-?\\d+(\\d+)?");
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
