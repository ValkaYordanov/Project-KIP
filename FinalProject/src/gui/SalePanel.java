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
import controler.CustomerCtr;
import controler.ProductCtr;
import controler.SaleCtr;
import controler.UserCtr;
import controler.WarehouseCtr;
import model.Customer;
import model.Product;
import model.Sale;

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
import javax.swing.SwingConstants;


public class SalePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Object[][] data = null;
	static DefaultTableModel model;
	static DefaultTableModel modelSale;
	private JTable tblSale;
	private JTextField txfSearch;
	
	private JComboBox<String> cbbProducts;
	private JComboBox<String> cbbCustomerName;
	
	static HashMap<String, String> products = new HashMap<>();

	String [] headerSale={"ID","Sale ID","Customer Name","Date","Employee Name"};
	private JTextField txfWeight;

	/**
	 * Create the panel.
	 */
	public SalePanel(int username) {
		setLayout(null);
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 593);
		setLayout(null);
		
		JLabel lblSale = new JLabel("Sale");
		lblSale.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblSale.setBounds(10, 11, 272, 26);
		add(lblSale);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(204, 0, 51));
		separator.setBounds(10, 44, 359, 10);
		add(separator);
		
		JPanel salePanel = new JPanel();
		salePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		salePanel.setBackground(Color.WHITE);
		salePanel.setBounds(10, 65, 536, 438);
		add(salePanel);
		
		salePanel.setLayout(null);
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setHorizontalAlignment(SwingConstants.LEFT);
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCustomerName.setBounds(26, 17, 133, 19);
		salePanel.add(lblCustomerName);
		
		
		cbbCustomerName = new JComboBox<String>();
		cbbCustomerName.setBounds(26, 48, 194, 20);
		cbbCustomerName.setMaximumRowCount(900);
        cbbCustomerName.addItem("Select one customer");
		salePanel.add(cbbCustomerName);
		
		JLabel lblDeliveryDate = new JLabel("Weight");
		lblDeliveryDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDeliveryDate.setBounds(304, 102, 133, 19);
		salePanel.add(lblDeliveryDate);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(304, 158, 194, 10);
		salePanel.add(separator_1);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProducts.setBounds(26, 118, 82, 19);
		salePanel.add(lblProducts);
		
		cbbProducts = new JComboBox<String>();
		cbbProducts.setBounds(26, 144, 194, 19);
		cbbProducts.addItem("Select one product");
		salePanel.add(cbbProducts);
		
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
        js.setLocation(26, 190);
        salePanel.add(js);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		String productName = (String) cbbProducts.getSelectedItem();
        		String weight = txfWeight.getText();
        		if(cbbProducts.getSelectedIndex() != 0 && !weight.equals("")) {
        			products.put(productName, weight);
        			loadSaleProducts(products);
        			txfWeight.setText("");
            		cbbProducts.setSelectedIndex(0);
        		} else {
        			JOptionPane.showMessageDialog(null, "Please select a product and a weight", "Error", 1);
        		}
        	}
        });
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setBackground(new Color(204, 0, 51));
        btnAdd.setBounds(217, 341, 87, 25);
        salePanel.add(btnAdd);
        
        
        JLabel lblWeightError = new JLabel("");
        lblWeightError.setBounds(292, 165, 206, 19);
        salePanel.add(lblWeightError);
        
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
        txfWeight.setBounds(304, 132, 194, 22);
        salePanel.add(txfWeight);
        txfWeight.setColumns(10);
    
        
        JButton btnCreateSale = new JButton("Create sale");
        btnCreateSale.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		if(products.isEmpty() || cbbCustomerName.getSelectedIndex() == 0) {
        			JOptionPane.showMessageDialog(null, "Please fill all the fields.", "Error", 1);
        		}  else {
        			String customerName = (String) cbbCustomerName.getSelectedItem();
        			WarehouseCtr myWarehouse = new WarehouseCtr();
       
        			try {
						//myWarehouse.checkProductStock(products);
        				if(myWarehouse.checkProductStock(products)) {
        					// Get current date sql format
        					LocalDate date = LocalDate.now();
        					Date currentDate = Date.valueOf(date);
        					
        					// Get customer ID from the customer name
        					CustomerCtr customerCtr = new CustomerCtr();
        					Customer myCustomer = customerCtr.getCustomerIdByName(customerName);
        					
        					int customerId = myCustomer.getId();
        					
        					// get last sale ID plus 1
        					SaleCtr mySale = new SaleCtr();
        					int saleId = mySale.getLastSaleId();
        					
        					myWarehouse.updateStock(products, customerId, currentDate, username,saleId);
    						getAllSales();
    						cbbCustomerName.setSelectedIndex(0);
    						products.clear();
    						loadSaleProducts(products);
    						JOptionPane.showMessageDialog(null, "Sale " + saleId + " created successefully", "Error", 1);
    						Main.checkForMinStock();	
        					

        					//JOptionPane.showMessageDialog(null, "There is stock available", "Success", 1);
        				} else {
        					JOptionPane.showMessageDialog(null, "There is no stock available", "Error", 1);
        					products.clear();
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
        btnCreateSale.setForeground(new Color(255, 255, 255));
        btnCreateSale.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCreateSale.setBackground(new Color(204, 0, 51));
        btnCreateSale.setBounds(196, 514, 152, 26);
        add(btnCreateSale);
        
        modelSale = new DefaultTableModel(data,headerSale);
        
		tblSale = new JTable(modelSale);
		tblSale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSale.setColumnSelectionAllowed(true);
		tblSale.setCellSelectionEnabled(true);
		tblSale.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        
		tblSale.setPreferredScrollableViewportSize(new Dimension(450,63));
		tblSale.setFillsViewportHeight(true);

        // Disable row editor
		tblSale.setDefaultEditor(Object.class, null);

        // Disable header dragging
		tblSale.getTableHeader().setReorderingAllowed(false);


		txfSearch = new JTextField();
        txfSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		if(txfSearch.getText().equals("")){
        			getAllSales();
        		} else {
        			String toSearch = txfSearch.getText();
            		searchSaleById(toSearch);
        		}
        		
        	}
        });
        
        // Just a nice detail [Change the color when focus]
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(204, 204, 204));
        		if(txfSearch.getText().equals("")){
        			getAllSales();
        			txfSearch.setText("Search by sale ID...");
        		}
        	}
        });
        txfSearch.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent arg0) {
        		txfSearch.setForeground(new Color(0, 0, 0));      		
        		if(txfSearch.getText().equals("Search by sale ID...")){
        			txfSearch.setText("");
        		}
        	}
        });
        
        txfSearch.setForeground(new Color(204, 204, 204));
        txfSearch.setText("Search by sale ID...");
        txfSearch.setBounds(604, 65, 298, 20);
        add(txfSearch);
        txfSearch.setColumns(10);
        
        
        JScrollPane jsSale = new JScrollPane(tblSale);
        jsSale.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsSale.setVisible(true);
        jsSale.setSize(807, 412);
        jsSale.setLocation(604, 91);
        add(jsSale);
        
        JButton btnInvoice = new JButton("Invoice");
        btnInvoice.setForeground(new Color(255, 255, 255));
        btnInvoice.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		
        		int myRow = tblSale.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row", "No row selected", 1);
				} else {
					
					String id = tblSale.getValueAt(myRow, 1).toString();
					String info[]=new String[1];
					info[0] = id;
					Invoice.main(info);
				}
        	}
        });
        btnInvoice.setBackground(new Color(204, 0, 51));
        btnInvoice.setBounds(1112, 516, 97, 25);
        add(btnInvoice);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		
        		int myRow = tblSale.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to delete it", "No row selected", 1);
				} else {
					String saleId = tblSale.getValueAt(myRow, 1).toString();
					String ObjButtons[] = {"Yes","No"};
	        		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this sale?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	                if(PromptResult==JOptionPane.YES_OPTION)
	                {
	                	SaleCtr mySale = new SaleCtr();
	                	int deleted = mySale.deleteSale(saleId);
	                	if(deleted == -1) {
	                		JOptionPane.showMessageDialog(null, "The product could not be deleted, there is still stock in the warehouse", "Error", 1);
	                	} else {
	                		JOptionPane.showMessageDialog(null, "Sale deleted with success", "Success", 1);
	                		getAllSales();
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
        		int myRow = tblSale.getSelectedRow();
				if(myRow == -1) {
					JOptionPane.showMessageDialog(null, "You need to select one row to be able to edit", "No row selected", 1);
				} else {
					String info[]=new String[4];
					String saleId = tblSale.getValueAt(myRow, 1).toString();
					String customerName = tblSale.getValueAt(myRow, 2).toString();
					String date = tblSale.getValueAt(myRow, 3).toString();
					String employeeName = tblSale.getValueAt(myRow, 4).toString();

					info[0] = saleId;
					info[1] = customerName;
					info[2] = date;
					info[3] = employeeName;

					SaleUpdate.main(info);

				}
        	}
        });
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(204, 0, 51));
        btnUpdate.setBounds(1221, 517, 89, 23);
        add(btnUpdate);
		
        
        getAllSales();
        getAllProductNames();
        getAllCustomers();
        products.clear();

	}
	
	
	public void searchSaleById(String saleId) {
		SaleCtr mySale = new SaleCtr();
		modelSale.setRowCount(0);
		
		CustomerCtr myCustomer = new CustomerCtr();
		
		UserCtr myUser = new UserCtr();
		
		Sale returnedSale = new Sale();
		
		try {
			if(Integer.valueOf(saleId) != null) {
				returnedSale = mySale.getSingleSaleById(saleId);
				if(returnedSale != null) {
					String customerName = myCustomer.getCustomerNameById(returnedSale.getCustomerId());
					String employeeName = myUser.findUserNameById(returnedSale.getUserId());
					
					modelSale.addRow(new Object[]{returnedSale.getId(), saleId, customerName, returnedSale.getDate(), employeeName});

				}
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
	
	public void getAllCustomers() {
		CustomerCtr myCustomer = new CustomerCtr();
		try {
			ArrayList<Customer> cust = myCustomer.getAllCustomerNames();
			for(Customer customer : cust) {
				cbbCustomerName.addItem(customer.getName());
			
			} 
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void getAllSales() {
		SaleCtr mySale = new SaleCtr();
		modelSale.setRowCount(0);
		
		CustomerCtr myCustomer = new CustomerCtr();
		
		UserCtr myUser = new UserCtr();
		
		try {
			ArrayList<Sale> sales = mySale.getAllSales();
			for(Sale sale : sales) {
				String customerName = myCustomer.getCustomerNameById(sale.getCustomerId());
				String employeeName = myUser.findUserNameById(sale.getUserId());
				
				modelSale.addRow(new Object[]{sale.getId(), sale.getSaleId(), customerName, sale.getDate(), employeeName});
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public static void loadSaleProducts(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    model.setRowCount(0);
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        model.addRow(new Object[]{pair.getKey(), pair.getValue()});
	        
	    }


	}
}
