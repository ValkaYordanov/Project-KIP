package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBStore implements IFDBStore{
	private  Connection con;
	
	public DBStore() {
		con = ConnectDB.getInstance().getDBcon();
	}

	//Implements the methods from the interface
	// get all products
	public ArrayList<Store> getAllStores(boolean retriveAssociation)
	{
		return miscWhere("", retriveAssociation);
	}
	
	public ArrayList<Store> getAllStoreName(String name, boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Store> list = new ArrayList<Store>();	
		
	    String query="SELECT id, storeName, address FROM store WHERE storeName LIKE ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name + "%");
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
				Store storeObj = new Store();
				storeObj = buildStore(results);	
	             list.add(storeObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	
	public Store findStoreId(String name) throws Exception {
		
		ResultSet results;
		Store storeObj = new Store();
				
		String query="SELECT id FROM store where storeName=?";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name);
      		
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if( results.next() ){
				storeObj = buildStore(results);
                stmt.close();
                          
			} else{
				storeObj = null;
                stmt.close();
            }

		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		
		return storeObj;
		
	}
	
	public String findStoreName(int id) throws Exception {
		
		ResultSet results;
		String storeName = null;
				
		String query="SELECT storeName FROM store where id=?";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, id);
      		
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if( results.next() ){
				storeName = results.getString("storeName");
			}
			stmt.close();             

		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		
		return storeName;
		
	}
	
	//insert a new product
	@Override
	public int createStore(Store store) throws Exception
	{  //call to get the next id number

		int rc = -1;
		String query="INSERT INTO store(storeName, address)  VALUES(?,?)";

		try{ // insert new customer 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, store.getStoreName());
      		stmt.setString(2, store.getAddress());
      		
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception ("Store is not inserted correct");
		}
		return(rc);
	}
	
	
	@Override
	public int updateStore(Store store)
	{
		
		int rc=-1;

		String query="UPDATE store SET storeName = ?, address = ? WHERE id = ?";
			
		
		try{ // update store

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, store.getStoreName());
      		stmt.setString(2, store.getAddress());
      		stmt.setInt(3, store.getId());
      		rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Update exception in store db: " +ex);
		}
		return(rc);
	}

	public int deleteStore(String id)
	{
		int rc=-1;

		String query="DELETE FROM store WHERE id = ?";
		try{ // delete from product
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in store db: "+ex);
		}
		return(rc);
	}

	private ArrayList<Store> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Store> list = new ArrayList<Store>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				Store storeObj = new Store();
				storeObj = buildStore(results);	
	             list.add(storeObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	private String buildQuery(String wClause)
	{
	    String query="SELECT id, storeName, address FROM store";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private Store buildStore(ResultSet results)
      {   
		Store storeObj = new Store();
          try{
        	  	storeObj.setId(results.getInt("id"));
                storeObj.setStoreName(results.getString("storeName"));
                storeObj.setAddress(results.getString("address"));
              
          }
         catch(Exception e)
         {
             System.out.println("error in building the store object");
             System.out.println("store");
         }
         return storeObj;
      }


}  


