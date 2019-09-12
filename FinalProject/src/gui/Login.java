package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.howtodoinjava.hashing.password.demo.bcrypt.BCrypt;

import controler.UserCtr;

import model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Login extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txfEmployee;
	private JPasswordField passwordField;
	private String userInfo[]= new String[3];
	
	private static Login frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread myThread = new Thread(new Login());
				    myThread.start();
				    myThread.join();
				    
					//frame = new Login();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlLogo = new JPanel();
		pnlLogo.setBackground(new Color(204, 0, 0));
		pnlLogo.setBounds(0, 0, 384, 254);
		contentPane.add(pnlLogo);
		pnlLogo.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(120, 49, 161, 154);
		pnlLogo.add(lblLogo);

		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/img/logo.png")));

		
		JPanel pnlFields = new JPanel();
		pnlFields.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlFields.setBackground(new Color(255, 255, 255));
		pnlFields.setBounds(0, 185, 384, 316);
		contentPane.add(pnlFields);
		pnlFields.setLayout(null);
		
		JLabel lblEmployee = new JLabel("Login ID");
		lblEmployee.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployee.setBounds(61, 91, 77, 22);
		pnlFields.add(lblEmployee);
		
		txfEmployee = new JTextField();
		txfEmployee.setBorder(null);
		txfEmployee.setBounds(148, 94, 147, 20);
		pnlFields.add(txfEmployee);
		txfEmployee.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(59, 144, 77, 19);
		pnlFields.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				String usernameS = txfEmployee.getText();
				int usernameI = Integer.parseInt(usernameS);
				
				char[] originalPassword = passwordField.getPassword();
				String mypassword = String.valueOf(originalPassword);
				
		         try{
		        	UserCtr myUser = new UserCtr();
		        	User usr = myUser.checkLogin(usernameI, mypassword);
		     	 	if(usr != null) {
		     	 		// Get the user unique ID from the database and save it to send as an argument
		     	 		int userId = usr.getId();
		     	 		String userIdS = String.valueOf(userId);
		     	 		userInfo[0] = userIdS;
		     	 		
		     	 		// Username to send as an argument
		     	 		userInfo[1] = usernameS;
		     	 		
		     	 		// Get the type of user from the database and save it to send as an argument
		     	 		int typeOfUser = usr.getTypeOfUser();
		     	 		String typeOfUserS = String.valueOf(typeOfUser);
		     	 		userInfo[2] = typeOfUserS;
		     	 		
		     	 		frame.dispose();
		     	 		Main.main(userInfo);
		     	 	}
		     	 	else {
		     	 		JOptionPane.showMessageDialog(null, "Username or password are wrong.", "Error", 1);
		     	 		passwordField.setText("");
		     	 	}
		         }
		         catch(Exception e)
		         {
		        	 System.out.println(e);
		             try {
		            	 JOptionPane.showMessageDialog(null, "Username or password are wrong.", "Error", 1);
						throw new Exception("The username or the password are wrong!");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		         }
			}
		});
		
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLogin.setBounds(112, 236, 157, 33);
		pnlFields.add(btnLogin);
		
		JSeparator sptE = new JSeparator();
		sptE.setForeground(new Color(255, 255, 255));
		sptE.setBackground(new Color(255, 0, 0));
		sptE.setBounds(147, 114, 148, 11);
		pnlFields.add(sptE);
		
		JSeparator sptP = new JSeparator();
		sptP.setForeground(new Color(255, 255, 255));
		sptP.setBackground(new Color(255, 0, 0));
		sptP.setBounds(148, 169, 147, 14);
		pnlFields.add(sptP);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setEchoChar('*');
		passwordField.setBounds(148, 145, 147, 20);
		pnlFields.add(passwordField);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		frame = new Login();
		frame.setVisible(true);
	}
}
