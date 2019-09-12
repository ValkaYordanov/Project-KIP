package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import controler.DeliveryCtr;
import controler.ProductCtr;
import controler.StoreCtr;
import controler.UserCtr;
import controler.WarehouseCtr;
import model.Delivery;
import model.Product;
import model.Store;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;


public class DeliveryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txfWeight;
	
	Object[][] data = null;
	static DefaultTableModel model;
	static DefaultTableModel modelDelivery;
	private JTable tblDelivery;
	private JTextField txfSearch;
	
	private JComboBox<String> cbbProducts;
	JComboBox<String> cbbDestination = new JComboBox<String>();

	
	static HashMap<String, String> products = new HashMap<>();

	String [] headerDelivery={"ID","Delivery ID","Store Name","Date","Employee Name"};

	/**
	 * Create the panel.
	 */
	public DeliveryPanel(int username) {
		setLayout(null);
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 593);
		setLayout(null);
		
		JLabel lblDelivery = new JLabel("Delivery");
		lblDelivery.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblDelivery.setBounds(10, 11, 272, 26);
		add(lblDelivery);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(204, 0, 51));
		separator.setBounds(10, 44, 359, 10);
		add(separator);
		
		JPanel deliveryPanel = new JPanel();
		deliveryPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deliveryPanel.setBackground(Color.WHITE);
		deliveryPanel.setBounds(10, 65, 536, 438);
		add(deliveryPanel);
		
		deliveryPanel.setLayout(null);
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDestination.setBounds(26, 40, 104, 19);
		deliveryPanel.add(lblDestination);
		
		
		
		cbbDestination.setBounds(26, 70, 194, 20);
		cbbDestination.setMaximumRowCount(900);
        cbbDestination.addItem("Select one destination");
		deliveryPanel.add(cbbDestination);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProducts.setBounds(26, 147, 82, 19);
		deliveryPanel.add(lblProducts);
		
		cbbProducts = new JComboBox<String>();
		cbbProducts.setBounds(26, 177, 194, 19);
		cbbProducts.addItem("Select one product");
		deliveryPanel.add(cbbProducts);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(304, 197, 194, 10);
		deliveryPanel.add(separator_2);
		
		JLabel lblWeightError = new JLabel("");
        lblWeightError.setBounds(289, 207, 209, 19);
        deliveryPanel.add(lblWeightError);
		
		txfWeight = new JTextField();
		txfWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!Main.isNumeric(txfWeight.getText())) {
					lblWeightError.setText("Invalid format, must be numbers");
				} else {
					lblWeightError.setText("");
				}
			}
		});
		txfWeight.setColumns(10);
		txfWeight.setBorder(null);
		txfWeight.setBounds(304, 177, 194, 20);
		deliveryPanel.add(txfWeight);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWeight.setBounds(304, 147, 133, 19);
		deliveryPanel.add(lblWeight);
		
		String [] header={"Product", "Weight"};
		
		model = new DefaultTableModel(data,header);
        
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        table.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        table.setBounds(40, 313, 363, 118);
        //table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);

        // Disable row editor
        table.setDefaultEditor(Object.class, null);

        // Disable header dragging
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane js=new JScrollPane(table);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        js.setVisible(true);
        js.setSize(472, 128);
        js.setLocation(26, 245);
        deliveryPanel.add(js);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		String productName = (String) cbbProducts.getSelectedItem();
        		String weight = txfWeight.getText();
        		if(cbbProducts.getSelectedIndex() != 0 && !weight.equals("")) {
        			products.put(productName, weight);
        			loadDeliveryProducts(products);
        			txfWeight.setText("");
            		cbbProducts.setSelectedIndex(0);
        		}
        	}
        });
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setBackground(new Color(204, 0, 51));
        btnAdd.setBounds(218, 384, 87, 25);
        deliveryPanel.add(btnAdd);
        
        
        
        JButton btnCreateDelivery = new JButton("Create delivery");
        btnCreateDelivery.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		if(products.isEmpty() || cbbDestination.getSelectedIndex() == 0) {
        			JOptionPane.showMessageDialog(null, "Please fill all the fields.", "Error", 1);
        		}  else {
        			String destination = (String) cbbDestination.getSelectedItem();
        			WarehouseCtr myWarehouse = new WarehouseCtr();
       
        			try {
						//myWarehouse.checkProductStock(products);
        				if(myWarehouse.checkProductStock(products)) {
        					// Get current date sql format
        					LocalDate date = LocalDate.now();
        					Date currentDate = Date.valueOf(date);
        					
        					// Get customer ID from the customer name
        					StoreCtr store = new StoreCtr();
        					Store myStore = store.getStoreIdByName(destination);
        					
        					int storeId = myStore.getId();
        					
        					// get last sale ID plus 1
        					DeliveryCtr myDelivery = new DeliveryCtr();
        					int deliveryId = myDelivery.getLastDeliveryId();
        					
        					myWarehouse.updateStockDelivery(products, storeId, currentDate, username,deliveryId);
    						getAllDeliveries();
    						cbbDestination.setSelectedIndex(0);
    						products.clear();
    						loadDeliveryProducts(products);
    						JOptionPane.showMessageDialog(null, "Delivery " + deliveryId + " created successefully", "Error", 1);
    						Main.checkForMinStock();	
        					

        					//JOptionPane.showMessageDialog(null, "There is stock available", "Success", 1);
        				} else {
        					JOptionPane.showMessageDialog(null, "There is no stock available", "Error", 1);
        				}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		
        		try {
					Main.checkForMinStock();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        btnCreateDelivery.setForeground(new Color(255, 255, 255));
        btnCreateDelivery.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCreateDelivery.setBackground(new Color(204, 0, 51));
        btnCreateDelivery.setBounds(196, 514, 152, 26);
        add(btnCreateDelivery);
     
       
        
        modelDelivery = new DefaultTableModel(data,headerDelivery);
        
		tblDelivery = new JTable(modelDelivery);
		tblDelivery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblDelivery.setColumnSelectionAllowed(true);
		tblDelivery.setCellSelectionEnabled(true);
		tblDelivery.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblDelivery.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblDelivery.setFillsViewportHeight(true);

        // Disable row editor
		tblDelivery.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblDelivery.getTableHeader().setReorderingAllowed(false);

		

		txfSearch = new JTextField();
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			getAllDeliveries();
        		} else {
        			String toSearch = txfSearch.getText();
            		searchDeliveryById(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			getAllDeliveries();
        			txfSearch.setText("Search by delivery ID...");
        		}
        	}
        });
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(0, 0, 0));      		
        		if(txfSearch.getText().equals("Search by delivery ID...")){
        			txfSearch.setText("");
        		}
        	}
        });
        
        txfSearch.setForeground(new Color(204, 204, 204));
        txfSearch.setText("Search by delivery ID...");
        txfSearch.setBounds(604, 44, 298, 20);
        add(txfSearch);
        txfSearch.setColumns(10);
        
		

        JScrollPane jsDelivery = new JScrollPane(tblDelivery);
        jsDelivery.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsDelivery.setVisible(true);
        jsDelivery.setSize(807, 438);
        jsDelivery.setLocation(604, 65);
        add(jsDelivery);
		
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		
        		int myRow = tblDelivery.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String deliveryId = tblDelivery.getValueAt(myRow, 1).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this delivery?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	DeliveryCtr myDelivery = new DeliveryCtr();
	                	int deleted = myDelivery.deleteDelivery(deliveryId);
	                	if(deleted == -1) {
	                		JOptionPane.showMessageDialog(null, "The delivery could not be deleted, there is still stock in the warehouse", "Error", 1);
	                	} else {
	                		JOptionPane.showMessageDialog(null, "Delivery deleted with success", "Success", 1);
	                		getAllDeliveries();
	                	}
	                	
	                }
				}
				
        	}
        });
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(new Color(204, 0, 51));
        btnDelete.setBounds(1322, 518, 89, 23);
        add(btnDelete);
        
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		int myRow = tblDelivery.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
				} else {
					String info[]=new String[4];
					String deliveryId = tblDelivery.getValueAt(myRow, 1).toString();
					String storeName = tblDelivery.getValueAt(myRow, 2).toString();
					String date = tblDelivery.getValueAt(myRow, 3).toString();
					String employeeName = tblDelivery.getValueAt(myRow, 4).toString();

					info[0] = deliveryId;
					info[1] = storeName;
					info[2] = date;
					info[3] = employeeName;

					DeliveryUpdate.main(info);

				}
        	}
        });
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(1221, 517, 89, 23);
        add(btnUpdate);
        
        JButton btnViewInfo = new JButton("View info");
        btnViewInfo.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = tblDelivery.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String deliveryId = tblDelivery.getValueAt(myRow, 1).toString();
					String storeName = tblDelivery.getValueAt(myRow, 2).toString();
					String date = tblDelivery.getValueAt(myRow, 3).toString();
					String employeeName = tblDelivery.getValueAt(myRow, 4).toString();
					
					String info[]=new String[4];
					info[0] = deliveryId;
					info[1] = storeName;
					info[2] = date;
					info[3] = employeeName;
					
					DeliveryInfoPanel.main(info);
				}
				
        	}
        });
        btnViewInfo.setForeground(new Color(255, 255, 255));
        btnViewInfo.setBackground(new Color(204, 0, 51));
        btnViewInfo.setBounds(1119, 518, 89, 23);
        add(btnViewInfo);
        
        
        getAllProductNames();
        getAllDestinations();
        getAllDeliveries();
	

	}

	
	public static void getAllDeliveries() {
		DeliveryCtr myDelivery = new DeliveryCtr();
		modelDelivery.setRowCount(0);
		
		StoreCtr myStore = new StoreCtr();
		
		UserCtr myUser = new UserCtr();
		
		try {
			ArrayList<Delivery> deliveries = myDelivery.getAll();
			
			for(Delivery delivery : deliveries) {
				String storeName = myStore.getStoreNameById(delivery.getStoreId());
				String employeeName = myUser.findUserNameById(delivery.getEmployeeId());
				modelDelivery.addRow(new Object[]{delivery.getId(), delivery.getDeliveryId(), storeName, delivery.getDeliveryDate(), employeeName});
			}
		} catch(Exception e) {
			System.out.println(e);
		}
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
	
	public void getAllDestinations() {
		StoreCtr myStore = new StoreCtr();
		try {
			ArrayList<Store> str = myStore.getAll();
			for (Store store : str) {
				cbbDestination.addItem(store.getStoreName());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void loadDeliveryProducts(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    model.setRowCount(0);
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        model.addRow(new Object[]{pair.getKey(), pair.getValue()});
	        
	        //System.out.println(pair.getValue() +" = " + pair.getKey());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }


	}
	
	
	public void searchDeliveryById(String deliveryId) {
		DeliveryCtr myDelivery = new DeliveryCtr();
		modelDelivery.setRowCount(0);
		
		StoreCtr myStore = new StoreCtr();
		
		UserCtr myUser = new UserCtr();
		
		Delivery returnedDelivery = new Delivery();

		
		try {
			if(Integer.valueOf(deliveryId) != null) {
				returnedDelivery = myDelivery.getSingleDeliveryById(deliveryId);
				if(returnedDelivery != null) {
					String storeName = myStore.getStoreNameById(returnedDelivery.getStoreId());
					String employeeName = myUser.findUserNameById(returnedDelivery.getEmployeeId());
					
					modelDelivery.addRow(new Object[]{returnedDelivery.getId(), deliveryId, storeName, returnedDelivery.getDeliveryDate(), employeeName});
				}
			}
			
			
				
		} catch(Exception e) {
			System.out.println(e);
		}

		
	}
}
