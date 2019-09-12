package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controler.DeliveryCtr;
import controler.StoreCtr;
import model.Store;

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

public class DeliveryUpdate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private static DeliveryUpdate frame;
	
	String errorMessage;
	
	private static String deliveryId;
	private static String storeName;
	private static String date;
	private static String employeeName;
	
	JComboBox cbxStoreName;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					deliveryId = args[0];
					storeName = args[1];
					date = args[2];
					employeeName = args[3];
					frame = new DeliveryUpdate();
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
	public DeliveryUpdate() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 597, 352);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateDelivery = new JLabel("Update delivery");
		lblUpdateDelivery.setForeground(Color.WHITE);
		lblUpdateDelivery.setFont(new Font("Microsoft YaHei", Font.BOLD, 21));
		lblUpdateDelivery.setBounds(12, 13, 349, 31);
		contentPane.add(lblUpdateDelivery);
		
		lblUpdateDelivery.setText("Update delivery - " + deliveryId);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 58, 349, 15);
		contentPane.add(separator);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(!cbxStoreName.getSelectedItem().equals(storeName)){
					String newStoreName = (String) cbxStoreName.getSelectedItem();
					DeliveryCtr myDelivery = new DeliveryCtr();
					try {
						StoreCtr myStore = new StoreCtr();
						Store store = myStore.getStoreIdByName(newStoreName);
						if(myDelivery.updateDelivery(deliveryId,store.getId()) != -1) {
							JOptionPane.showMessageDialog(null, "Delivery updated with success", "Success", 1);
							DeliveryPanel.getAllDeliveries();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "It was not possible to update the delivery", "Error", 1);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "There is no changes in the store name, closing without update.", "Error", 1);
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
		
		JLabel lblStoreName = new JLabel("Store Name:");
		lblStoreName.setForeground(Color.WHITE);
		lblStoreName.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblStoreName.setBounds(12, 159, 133, 16);
		contentPane.add(lblStoreName);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblDate.setBounds(461, 13, 106, 29);
		contentPane.add(lblDate);
		
		lblDate.setText(date);
		
		cbxStoreName = new JComboBox();
		cbxStoreName.setBounds(149, 156, 167, 25);
		contentPane.add(cbxStoreName);
		
		JLabel lblEmployee = new JLabel("");
		lblEmployee.setForeground(Color.WHITE);
		lblEmployee.setFont(new Font("Microsoft YaHei", Font.ITALIC, 12));
		lblEmployee.setBounds(144, 86, 167, 20);
		contentPane.add(lblEmployee);
		
		lblEmployee.setText(employeeName);
		
		getAllStores();
		
		cbxStoreName.setSelectedItem(storeName);
		
	}
	public void getAllStores() {
		StoreCtr myStore = new StoreCtr();
		try {
			ArrayList<Store> store = myStore.getAllStoreNames();
			for(Store str : store) {
				cbxStoreName.addItem(str.getStoreName());
			} 
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
