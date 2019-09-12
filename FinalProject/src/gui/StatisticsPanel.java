package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import controler.DeliveryCtr;
import controler.ProductCtr;
import controler.SaleCtr;
import controler.UserCtr;
import model.Delivery;
import model.Product;
import model.User;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class StatisticsPanel extends JPanel {
	
	// Number of sales
	JComboBox<String> cbxMonths;
	JLabel lblProfit;
	JLabel lblNumberOfSales;
	
	
	// Sales from employee
	JComboBox<String> cbxMonthEmployee;
	JLabel lblNumberOfSalesEmployee;
	JLabel lblEmployeeNameL;
	
	
	// Delivery
	JComboBox<String> cbxMonthDelivery;
	JLabel lblEmployeeNameDeliveryL;
	JLabel lblProductNameL;
	JLabel lblWeightL;
	JLabel lblNumberOfDeliveries;
	
	int getYear = Calendar.getInstance().get(Calendar.YEAR);
	String year = String.valueOf(getYear);
	
	private String[] months = {"January","February","March","April","May","June","July","August","September","October","November","Dezember"};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public StatisticsPanel() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 1902, 641);
		setLayout(null);
		
		JLabel lblStatistics = new JLabel("Statistics");
		lblStatistics.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
		lblStatistics.setBounds(10, 11, 272, 26);
		add(lblStatistics);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(204, 0, 51));
		separator.setBounds(10, 44, 359, 10);
		add(separator);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 85, 371, 160);
		add(panel);
		panel.setLayout(null);
		
		cbxMonths = new JComboBox<String>();
		cbxMonths.setBounds(136, 11, 175, 20);
		
		cbxMonths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String month = (String) cbxMonths.getSelectedItem();
				Months monthEnum = Months.valueOf(month.toUpperCase());
				int monthNumber = monthEnum.getNumber();
				String monthNumberS = String.valueOf(monthNumber);
				
				if(monthNumberS.length() == 1) {
					monthNumberS = "0" + monthNumberS;
				}
				try {
					getProfitOfSales(monthNumberS);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		panel.add(cbxMonths);
		
		lblProfit = new JLabel("");
		lblProfit.setForeground(new Color(204, 0, 51));
		lblProfit.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblProfit.setBounds(136, 56, 160, 36);
		panel.add(lblProfit);
		
		JLabel lblSelectMonth = new JLabel("Select month");
		lblSelectMonth.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblSelectMonth.setBounds(10, 14, 100, 17);
		panel.add(lblSelectMonth);
		
		JLabel lblProfitThisMonth = new JLabel("Profit this month:");
		lblProfitThisMonth.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblProfitThisMonth.setBounds(10, 56, 119, 36);
		panel.add(lblProfitThisMonth);
		
		JLabel lblNOfSales = new JLabel("N\u00BA of sales: ");
		lblNOfSales.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblNOfSales.setBounds(211, 135, 84, 14);
		panel.add(lblNOfSales);
		
		lblNumberOfSales = new JLabel("");
		lblNumberOfSales.setForeground(new Color(204, 0, 51));
		lblNumberOfSales.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblNumberOfSales.setBounds(298, 135, 63, 14);
		panel.add(lblNumberOfSales);
		
		JLabel lblProfitFromSales = new JLabel("Profit from sales");
		lblProfitFromSales.setForeground(new Color(204, 0, 51));
		lblProfitFromSales.setBounds(10, 59, 111, 18);
		add(lblProfitFromSales);
		lblProfitFromSales.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		
		JLabel lblEmployeeSales = new JLabel("Employee Sales");
		lblEmployeeSales.setForeground(new Color(204, 0, 51));
		lblEmployeeSales.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblEmployeeSales.setBounds(10, 269, 111, 18);
		add(lblEmployeeSales);
		
		JPanel pnlEmployees = new JPanel();
		pnlEmployees.setLayout(null);
		pnlEmployees.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnlEmployees.setBackground(Color.WHITE);
		pnlEmployees.setBounds(10, 295, 371, 160);
		add(pnlEmployees);
		
		cbxMonthEmployee = new JComboBox<String>();
		cbxMonthEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String month = (String) cbxMonthEmployee.getSelectedItem();
				Months monthEnum = Months.valueOf(month.toUpperCase());
				int monthNumber = monthEnum.getNumber();
				String monthNumberS = String.valueOf(monthNumber);
				
				if(monthNumberS.length() == 1) {
					monthNumberS = "0" + monthNumberS;
				}
				try {
					getMostSalesFromEmployee(monthNumberS);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		cbxMonthEmployee.setBounds(136, 11, 175, 20);
		pnlEmployees.add(cbxMonthEmployee);
		
		lblEmployeeNameL = new JLabel("");
		lblEmployeeNameL.setForeground(new Color(204, 0, 51));
		lblEmployeeNameL.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblEmployeeNameL.setBounds(136, 56, 160, 36);
		pnlEmployees.add(lblEmployeeNameL);
		
		JLabel lblSelectMonthEmployee = new JLabel("Select month");
		lblSelectMonthEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblSelectMonthEmployee.setBounds(10, 14, 100, 17);
		pnlEmployees.add(lblSelectMonthEmployee);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblEmployeeName.setBounds(10, 56, 119, 36);
		pnlEmployees.add(lblEmployeeName);
		
		JLabel lblNSalesEmployee = new JLabel("N\u00BA of sales: ");
		lblNSalesEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblNSalesEmployee.setBounds(211, 135, 84, 14);
		pnlEmployees.add(lblNSalesEmployee);
		
		lblNumberOfSalesEmployee = new JLabel("");
		lblNumberOfSalesEmployee.setForeground(new Color(204, 0, 51));
		lblNumberOfSalesEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblNumberOfSalesEmployee.setBounds(298, 135, 63, 14);
		pnlEmployees.add(lblNumberOfSalesEmployee);
		
		JLabel lblProductSentOn = new JLabel("Product sent on delivery");
		lblProductSentOn.setForeground(new Color(204, 0, 51));
		lblProductSentOn.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblProductSentOn.setBounds(488, 59, 172, 18);
		add(lblProductSentOn);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(488, 85, 371, 249);
		add(panel_1);
		
		cbxMonthDelivery = new JComboBox<String>();
		cbxMonthDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String month = (String) cbxMonthDelivery.getSelectedItem();
				Months monthEnum = Months.valueOf(month.toUpperCase());
				int monthNumber = monthEnum.getNumber();
				String monthNumberS = String.valueOf(monthNumber);
				
				if(monthNumberS.length() == 1) {
					monthNumberS = "0" + monthNumberS;
				}
				try {
					getMostDeliveredProduct(monthNumberS);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		cbxMonthDelivery.setBounds(136, 11, 175, 20);
		panel_1.add(cbxMonthDelivery);
		
		lblEmployeeNameDeliveryL = new JLabel("");
		lblEmployeeNameDeliveryL.setForeground(new Color(204, 0, 51));
		lblEmployeeNameDeliveryL.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblEmployeeNameDeliveryL.setBounds(136, 56, 160, 36);
		panel_1.add(lblEmployeeNameDeliveryL);
		
		JLabel label_2 = new JLabel("Select month");
		label_2.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		label_2.setBounds(10, 14, 100, 17);
		panel_1.add(label_2);
		
		JLabel lblEmployeeNameDelivery = new JLabel("Employee Name");
		lblEmployeeNameDelivery.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblEmployeeNameDelivery.setBounds(10, 56, 119, 29);
		panel_1.add(lblEmployeeNameDelivery);
		
		JLabel lblNOfDeliveries = new JLabel("N\u00BA of deliveries");
		lblNOfDeliveries.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblNOfDeliveries.setBounds(179, 224, 117, 14);
		panel_1.add(lblNOfDeliveries);
		
		lblNumberOfDeliveries = new JLabel("");
		lblNumberOfDeliveries.setForeground(new Color(204, 0, 51));
		lblNumberOfDeliveries.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblNumberOfDeliveries.setBounds(298, 224, 63, 14);
		panel_1.add(lblNumberOfDeliveries);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblProductName.setBounds(10, 113, 119, 36);
		panel_1.add(lblProductName);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblWeight.setBounds(10, 160, 119, 36);
		panel_1.add(lblWeight);
		
		lblProductNameL = new JLabel("");
		lblProductNameL.setForeground(new Color(204, 0, 51));
		lblProductNameL.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblProductNameL.setBounds(136, 113, 160, 36);
		panel_1.add(lblProductNameL);
		
		lblWeightL = new JLabel("");
		lblWeightL.setForeground(new Color(204, 0, 51));
		lblWeightL.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblWeightL.setBounds(136, 160, 160, 36);
		panel_1.add(lblWeightL);
		
		populateMonths();
		
	}
	
	public void populateMonths() {
		for(String month : months) {
			cbxMonths.addItem(month);
			cbxMonthEmployee.addItem(month);
			cbxMonthDelivery.addItem(month);
		}
	}
	

	public void getProfitOfSales(String month) throws Exception {
		
		SaleCtr mySale = new SaleCtr();
		double profit = mySale.getProfitFromSale(year, month);
		int count = mySale.getNumberOfSales(year, month);
		lblNumberOfSales.setText(String.valueOf(count));
		lblProfit.setText(String.valueOf(profit) + " DKK");
	}
	
	public void getMostSalesFromEmployee(String month) throws Exception {
		
		UserCtr myUser = new UserCtr();
		ArrayList<User> users = myUser.getAllUsers();
		
		SaleCtr mySale = new SaleCtr();
		
		int mostSales = 0;
		int employeeId = 0;
		
		for(User user : users) {
			int nSales = mySale.getSalesFromEmplyees(user.getId(), year,month);
			if(nSales > mostSales) {
				mostSales = nSales;
				employeeId = user.getId();
			}
		}
		
		lblEmployeeNameL.setText(myUser.findUserNameById(employeeId));
		lblNumberOfSalesEmployee.setText(String.valueOf(mostSales));
		
	}
	
	public void getMostDeliveredProduct(String month) throws Exception {
		
		ProductCtr myProduct = new ProductCtr();
		
		UserCtr myEmployee = new UserCtr();
		
		ArrayList<Product> products = myProduct.getAll();
		
		DeliveryCtr myDelivery = new DeliveryCtr();
		
		double weight = 0;
		String employeeName = null;
		String productName = null;
		int numberOfDeliveries = 0;
		
		
		for(Product product : products) {
			Delivery singleDelivery = myDelivery.getDeliveryByUser(product.getId(), year, month);
			if(singleDelivery.getWeight() > weight){
				weight = singleDelivery.getWeight();
				employeeName = myEmployee.findUserNameById(singleDelivery.getEmployeeId());
				productName = myProduct.getProductName(String.valueOf(product.getId()));
				numberOfDeliveries = singleDelivery.getId(); // SAVED THE COUNT VALUE ON DELIVERY ID
			}
		}
		
		lblEmployeeNameDeliveryL.setText(employeeName);
		lblProductNameL.setText(productName);
		lblWeightL.setText(String.valueOf(weight));
		lblNumberOfDeliveries.setText(String.valueOf(numberOfDeliveries));
		
		
	}
}
