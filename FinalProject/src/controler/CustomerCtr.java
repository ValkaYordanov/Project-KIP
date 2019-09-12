package controler;

import java.util.ArrayList;
import java.sql.Date;

import dbConnect.ConnectDB;
import dbConnect.DBCustomer;
import dbConnect.IFDBCustomer;
import model.Customer;


public class CustomerCtr {
	
	public ArrayList<Customer> getAll() {
		
      IFDBCustomer dbCust = new DBCustomer();
      ArrayList<Customer> allCust = new ArrayList<Customer>();
      allCust = dbCust.getAllCustomer(false);
      return allCust;
    }
	
	public ArrayList<Customer> searchByName(String name){
		IFDBCustomer dbCust = new DBCustomer();
	    ArrayList<Customer> searchedCust = new ArrayList<Customer>();
	    searchedCust = dbCust.getAllCustomerName(name, false);
	    return searchedCust;
		
	}
	

	public Customer getCustomerIdByName(String customerName) throws Exception {
		IFDBCustomer dbCust = new DBCustomer();
		Customer myCustomer = new Customer();
		myCustomer = dbCust.findCustomerId(customerName);
		return myCustomer;
	}
	
	public String getCustomerNameById(int customerId) throws Exception {
		IFDBCustomer dbCust = new DBCustomer();
		String customerName = dbCust.findCustomerName(customerId);
		return customerName;
	}
	
	public void insertCustomer(String name, String street, String numberOnStreet, String city, String phoneNo) throws Exception {    
		Customer custObj = new Customer();
         custObj.setName(name);
         custObj.setStreet(street);
         custObj.setNumberOnStreet(numberOnStreet);
         custObj.setCity(city);
         custObj.setPhoneNo(phoneNo);
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBCustomer dbCus = new DBCustomer();
     	 	dbCus.createCustomer(custObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception("Customer not inserted");
         }
    }
	
	public void updateCustomer(int customerId, String name, String street, String numberOnStreet, String city, String phoneNo) throws Exception {
		
		Customer custObj = new Customer();
	   	custObj.setId(customerId);
		 custObj.setName(name);
         custObj.setStreet(street);
         custObj.setNumberOnStreet(numberOnStreet);
         custObj.setCity(city);
         custObj.setPhoneNo(phoneNo);
	    
	    try{
	   	 
	   	ConnectDB.startTransaction();
	     	IFDBCustomer dbCus = new DBCustomer();
		 	dbCus.updateCustomer(custObj);
		 	ConnectDB.commitTransaction();
	    }
	    catch(Exception e)
	    {
	   	 ConnectDB.rollbackTransaction();
	        throw new Exception("Couldn't update the product");
	    }
		
	}
			
	public int deleteCustomer(String id) {
		IFDBCustomer dbCust = new DBCustomer();
    	int rc = dbCust.deleteCustomer(id);
    	return rc;
    }	

	public  ArrayList<Customer> getAllCustomerNames() {
		
		IFDBCustomer dbCust = new DBCustomer();
	    ArrayList<Customer> allCust = new ArrayList<Customer>();
	    allCust = dbCust.getAllCustomer(false);
	    return allCust;
	      
	}
}
