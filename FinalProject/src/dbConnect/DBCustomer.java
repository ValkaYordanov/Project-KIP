package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBCustomer implements IFDBCustomer{
	private  Connection con;
	
	public DBCustomer() {
		con = ConnectDB.getInstance().getDBcon();
	}

	//Implements the methods from the interface
	// get all products
	public ArrayList<Customer> getAllCustomer(boolean retriveAssociation)
	{
		return miscWhere("", retriveAssociation);
	}
	
	public ArrayList<Customer> getAllCustomerName(String name, boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Customer> list = new ArrayList<Customer>();	
		
	    String query="SELECT id, name, street, numberOnStreet, city, phoneno FROM customer WHERE name LIKE ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name + "%");
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
				Customer custObj = new Customer();
				custObj = buildCustomer(results);	
	             list.add(custObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}

	
	
	
	public Customer findCustomer(String  name, boolean retriveAssociation)
	{   String wClause = "  name = '" + name + "'";
	return singleWhere(wClause, retriveAssociation);
	}
	
	
	public Customer findCustomerId(String name) throws Exception {
		
		ResultSet results;
		Customer custObj = new Customer();
				
		String query="SELECT id FROM customer where name=?";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name);
      		
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if( results.next() ){
				custObj.setId(results.getInt("id"));
                stmt.close();
                          
			} else{
				custObj = null;
                stmt.close();
            }

		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		
		return custObj;
		
	}


	public String findCustomerName(int id) throws Exception {
			
			ResultSet results;
			String customerName = null;
					
			String query="SELECT name FROM customer where id=?";
	
			try{ // insert new product 
				PreparedStatement stmt = con.prepareStatement(query);
	      		stmt.setInt(1, id);
	      		
				stmt.setQueryTimeout(5);
				results = stmt.executeQuery();
				if( results.next() ){
					customerName = results.getString("name");
				}
				stmt.close();             
	
			}//end try
			catch(SQLException ex){
				throw new Exception (ex);
			}
			
			return customerName;
			
		}



	@Override
	public int createCustomer(Customer cust) throws Exception
	{  //call to get the next id number

		int rc = -1;
		String query="INSERT INTO customer(name, street, numberOnStreet, city, phoneno)  VALUES(?,?,?,?,?)";

		try{ // insert new customer 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, cust.getName());
      		stmt.setString(2, cust.getStreet());
      		stmt.setString(3, cust.getNumberOnStreet());
      		stmt.setString(4, cust.getCity());
      		stmt.setString(5, cust.getPhoneNo());
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception ("Customer is not inserted correct");
		}
		return(rc);
	}
	
	
	@Override
	public int updateCustomer(Customer cust)
	{
		Customer custObj  = cust;
		int rc=-1;

		String query="UPDATE customer SET name = ?, street = ?, numberOnStreet = ?, city = ?, phoneno = ? WHERE id = ?";
			
		
		try{ // update product

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, custObj.getName());
      		stmt.setString(2, custObj.getStreet());
      		stmt.setString(3, custObj.getNumberOnStreet());
      		stmt.setString(4, custObj.getCity());
      		stmt.setString(5, custObj.getPhoneNo());
      		stmt.setInt(6, custObj.getId());
      		rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Update exception in customer db: " +ex);
		}
		return(rc);
	}

	public int deleteCustomer(String id)
	{
		int rc=-1;

		String query="DELETE FROM customer WHERE id = ?";
		try{ // delete from product
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in customer db: "+ex);
		}
		return(rc);
	}

	private ArrayList<Customer> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Customer> list = new ArrayList<Customer>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				Customer custObj = new Customer();
				custObj = buildCustomer(results);	
	             list.add(custObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	private Customer singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Customer custObj = new Customer();
                
        String query =  buildQuery(wClause);
        
		try{
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
                custObj = buildCustomer(results);
                stmt.close();
                          
			} else{
				custObj = null;
            }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return custObj;
	}

	private String buildQuery(String wClause)
	{
	    String query="SELECT id, name, street, numberOnStreet, city, phoneno FROM Customer";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private Customer buildCustomer(ResultSet results)
      {   
		Customer custObj = new Customer();
          try{
        	  	custObj.setId(results.getInt("id"));
                custObj.setName(results.getString("name"));
                custObj.setStreet(results.getString("street"));
                custObj.setNumberOnStreet(results.getString("numberOnStreet"));
                custObj.setCity(results.getString("city"));
                custObj.setPhoneNo(results.getString("phoneno"));
          }
         catch(Exception e)
         {
             System.out.println(e);
         }
         return custObj;
      }


}  


