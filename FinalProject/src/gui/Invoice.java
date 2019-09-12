package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controler.CustomerCtr;
import controler.ProductCtr;
import controler.SaleCtr;
import controler.UserCtr;
import model.Sale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

public class Invoice extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private static String id;
	private String products = "";
	private double finalPrice = 0;
	
	JLabel lblEmployee;
	JLabel lblCustomer;
	JLabel lblEmployeeName;
	JLabel lblCustomerName;
	JLabel lblDate;
	JLabel lblInvoiceNumber;
	
	ProductCtr myProduct;
	
	JLabel lblPrice; // Total price
	
	JTextPane txtpnProducts; // Product Panel

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					id = args[0];
					Invoice frame = new Invoice();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Invoice() throws Exception {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 845);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTotalPrice = new JLabel("PAID");
		lblTotalPrice.setOpaque(true);
		lblTotalPrice.setHorizontalAlignment(JLabel.CENTER);
		lblTotalPrice.setVerticalAlignment(JLabel.CENTER);
		lblTotalPrice.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPrice.setBackground(new Color(51, 51, 51));
		lblTotalPrice.setForeground(new Color(255, 255, 255));
		lblTotalPrice.setFont(new Font("Microsoft YaHei", Font.BOLD, 17));
		lblTotalPrice.setBounds(382, 108, 117, 40);
		contentPane.add(lblTotalPrice);
		
		lblEmployee = new JLabel("Employee ");
		lblEmployee.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblEmployee.setBounds(22, 198, 85, 30);
		contentPane.add(lblEmployee);
		
		JLabel lblInvoice = new JLabel("Invoice Number ");
		lblInvoice.setFont(new Font("Microsoft YaHei", Font.BOLD, 21));
		lblInvoice.setBounds(388, 38, 185, 24);
		contentPane.add(lblInvoice);
		
		lblCustomer = new JLabel("Customer ");
		lblCustomer.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblCustomer.setBounds(22, 241, 85, 30);
		contentPane.add(lblCustomer);
		
		lblEmployeeName = new JLabel("");
		lblEmployeeName.setForeground(new Color(102, 102, 102));
		lblEmployeeName.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblEmployeeName.setBounds(119, 194, 323, 34);
		contentPane.add(lblEmployeeName);
		
		lblCustomerName = new JLabel("");
		lblCustomerName.setForeground(new Color(102, 102, 102));
		lblCustomerName.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblCustomerName.setBounds(119, 241, 317, 30);
		contentPane.add(lblCustomerName);
		
		lblPrice = new JLabel("");
		lblPrice.setBorder(new LineBorder(new Color(102, 102, 102)));
		lblPrice.setHorizontalAlignment(JLabel.RIGHT);
		lblPrice.setVerticalAlignment(JLabel.CENTER);
		lblPrice.setForeground(new Color(153, 153, 153));
		lblPrice.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
		lblPrice.setBounds(487, 110, 185, 38);
		contentPane.add(lblPrice);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Invoice.class.getResource("/img/logo.png")));
		lblLogo.setBounds(12, 13, 164, 135);
		contentPane.add(lblLogo);
		
		JTextArea txtrAddress = new JTextArea();
		txtrAddress.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		txtrAddress.setForeground(new Color(153, 153, 153));
		txtrAddress.setRows(2);
		txtrAddress.setText("KIP-3\r\nBulgaria, Varna, ul\r\nSredec N\u00BA 51");
		txtrAddress.setBounds(177, 44, 164, 77);
		contentPane.add(txtrAddress);
		
		JLabel lblInvoiceNumberDefault = new JLabel("#KIP-3");
		lblInvoiceNumberDefault.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblInvoiceNumberDefault.setForeground(new Color(153, 153, 153));
		lblInvoiceNumberDefault.setBounds(575, 41, 51, 24);
		contentPane.add(lblInvoiceNumberDefault);
		
		lblInvoiceNumber = new JLabel("");
		lblInvoiceNumber.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
		lblInvoiceNumber.setForeground(new Color(153, 153, 153));
		lblInvoiceNumber.setBounds(628, 45, 37, 16);
		contentPane.add(lblInvoiceNumber);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(638, 265, 106, 22);
		contentPane.add(editorPane);
		
		JLabel lblIssueDate = new JLabel("Issue Date");
		lblIssueDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
		lblIssueDate.setBounds(388, 75, 111, 33);
		contentPane.add(lblIssueDate);
		
		lblDate = new JLabel("");
		lblDate.setForeground(new Color(153, 153, 153));
		lblDate.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblDate.setBounds(574, 78, 117, 24);
		contentPane.add(lblDate);
		
		JLabel lblProduct = new JLabel("Product");
		lblProduct.setForeground(new Color(153, 153, 153));
		lblProduct.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblProduct.setBounds(28, 348, 71, 16);
		contentPane.add(lblProduct);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setForeground(new Color(153, 153, 153));
		lblQuantity.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblQuantity.setBounds(163, 348, 78, 16);
		contentPane.add(lblQuantity);
		
		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setForeground(new Color(153, 153, 153));
		lblPrice_1.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblPrice_1.setBounds(498, 348, 56, 16);
		contentPane.add(lblPrice_1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 153, 153));
		separator.setBounds(22, 372, 543, 16);
		contentPane.add(separator);
		
		txtpnProducts = new JTextPane();
		txtpnProducts.setEditable(false);
		txtpnProducts.setForeground(new Color(102, 102, 102));
		txtpnProducts.setFont(new Font("Microsoft YaHei", Font.PLAIN, 19));
		txtpnProducts.setContentType("text/html");
		txtpnProducts.setBounds(22, 386, 543, 325);
		contentPane.add(txtpnProducts);
		
		JButton btnSaveInvoice = new JButton("Save Invoice");
		btnSaveInvoice.setForeground(new Color(255, 255, 255));
		btnSaveInvoice.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				 try {
					getSaveSnapShot(contentPane, "Invoice-" + lblInvoiceNumber.getText() + ".png");
					JOptionPane.showMessageDialog(null, "Invoice saved to main project folder", "Invoice-" + lblInvoiceNumber.getText() + " saved", 1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			}
		});
		btnSaveInvoice.setBackground(new Color(204, 0, 51));
		btnSaveInvoice.setBounds(459, 742, 114, 23);
		contentPane.add(btnSaveInvoice);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				PrinterJob job = PrinterJob.getPrinterJob();
			}
		});
		btnPrint.setForeground(new Color(255, 255, 255));
		btnPrint.setBackground(new Color(204, 0, 51));
		btnPrint.setBounds(353, 742, 89, 23);
		btnPrint.addActionListener(new btnPrintAction());
		contentPane.add(btnPrint);
		
		JLabel lblPricePerProduct = new JLabel("Price per product");
		lblPricePerProduct.setForeground(new Color(153, 153, 153));
		lblPricePerProduct.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblPricePerProduct.setBounds(310, 348, 132, 16);
		contentPane.add(lblPricePerProduct);
		
		getSalesInfo(id);
	}
	
	
	public static class btnPrintAction implements ActionListener, Printable{
	
		        public int print(Graphics gx, PageFormat pf, int page) throws PrinterException {
		
		            if (page>0){return NO_SUCH_PAGE;} //Only one page
		
		            Graphics2D g = (Graphics2D)gx; //Cast to Graphics2D object
		
		            g.translate(pf.getImageableX(), pf.getImageableY()); //Match origins to imageable area
		
		            g.drawString ("Hello world", 100, 100); //Print Hello World at offset (100, 100)
		
		            return PAGE_EXISTS; //Page exists (offsets start at zero!)
		
		        }
		
		        public void actionPerformed(ActionEvent e) {
		
		            PrinterJob job = PrinterJob.getPrinterJob(); //Get the printer's job list
		
		            job.setPrintable(this); //We print with this class (btnPrintAction, which implements Printable)
		
		            if (job.printDialog() == true) { //If we clicked OK in the print dialog
		
		                try {job.print();} catch (PrinterException ex){
		
		                    //It did not work (PrinterException thrown), so add any error handling routines.
		
		                }
		
		            }
		
		        }
		
		    }

	
	
	
	
	
	
	public static BufferedImage getScreenShot(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        component.paint(image.getGraphics());
        return image;
    }

    public static void getSaveSnapShot(Component component, String fileName) throws Exception {
        BufferedImage img = getScreenShot(component);
        // write the captured image as a PNG
        ImageIO.write(img, "png", new File(fileName));
    }
    
    
    

	public void getSalesInfo(String saleId) throws Exception {
		
		SaleCtr mySale = new SaleCtr();
		
		CustomerCtr myCustomer = new CustomerCtr();
		
		UserCtr myUser = new UserCtr();
		
		myProduct = new ProductCtr();
		
		
		
		
		ArrayList<Sale> sales = mySale.getSaleById(saleId);
		
		Sale singleSale = sales.get(0);
		
		String customerName = myCustomer.getCustomerNameById(singleSale.getCustomerId());
		String employeeName = myUser.findUserNameById(singleSale.getUserId());
		
		java.util.Date date = singleSale.getDate();
		String dateS = String.valueOf(date);
		
		lblCustomerName.setText(customerName);
		lblEmployeeName.setText(employeeName);
		lblDate.setText(dateS);
		lblInvoiceNumber.setText(saleId);
		
		
		// Simple lambda expression for printing products
		sales.forEach(n -> {
			try {
				buildProductList(n);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}); 
		
		lblPrice.setText(finalPrice + " DKK ");
		txtpnProducts.setText(products);
		
	}
	
	public void buildProductList(Sale sale) throws Exception {
		
		products += "<b style='font-size:14px; color:grey;'>" + myProduct.getProductName(String.valueOf(sale.getProductId())) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sale.getWeight() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sale.getPrice()/sale.getWeight() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sale.getPrice();
		products += "<br><hr><br></b>";

		finalPrice += sale.getPrice();
		
	}
}
