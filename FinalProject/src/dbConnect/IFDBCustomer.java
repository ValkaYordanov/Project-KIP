package dbConnect;

import java.util.ArrayList;

import model.Customer;


public interface IFDBCustomer {
	
	    public ArrayList<Customer> getAllCustomer(boolean retriveAssociation);
	 
	    public int createCustomer(Customer cust) throws Exception;
	  
	    public int updateCustomer(Customer cust);
	   
	    public ArrayList<Customer> getAllCustomerName(String name, boolean retriveAssociation);

	    public int deleteCustomer(String id);

		public Customer findCustomerId(String customerName) throws Exception;

		public String findCustomerName(int customerId) throws Exception;

}
