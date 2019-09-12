package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBDelivery implements IFDBDelivery{
	private  Connection con;
	
	public DBDelivery() {
		con = ConnectDB.getInstance().getDBcon();
	}
	
	public ArrayList<Delivery> getAllDelivery(boolean retriveAssociation){
		return miscWhere("", retriveAssociation);
	}
	
    public int getLastDeliveryId() throws SQLException {
		
		int deliveryId = 0;
		ResultSet results;
		
		String query="SELECT TOP 1 MAX(deliveryId) as deliveryId FROM delivery";
		
		Statement stmt = con.createStatement();
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery(query);
    	
    	if( results.next() ){
    		deliveryId = results.getInt("deliveryId");
    		deliveryId += 1;
		} else {
			deliveryId = 1;
		}
		
		return deliveryId;
	}
    
	public Delivery getDeliveryByUser(int productId,String year, String month) throws SQLException {
    	
    	Delivery delObj = new Delivery();
		ResultSet results;
		
		String concat = year+"-"+month+"%";
		
		String query="SELECT SUM(weight),COUNT(id) as numberOfDelivery, userId FROM delivery WHERE productId=? AND deliveryDate LIKE ? GROUP BY userId";
		
		try{
			PreparedStatement stmt = con.prepareStatement(query);
	  		stmt.setInt(1, productId);
	  		stmt.setString(2, concat);
	  		stmt.setQueryTimeout(5);
	    	results = stmt.executeQuery();
	    	
	    	if( results.next() ){
	    		delObj.setWeight(results.getDouble(1));
	    		delObj.setId(results.getInt("numberOfDelivery"));
	    		delObj.setEmployeeId(results.getInt("userId"));
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}

		return delObj;
    	
    }
    
    public ArrayList<Delivery> getDeliveryById(String deliveryId) {
		
		ResultSet results;
	    ArrayList<Delivery> list = new ArrayList<Delivery>();	
		
	    String query = "SELECT deliveryId,storeId, deliveryDate, weight, productId , userId FROM delivery WHERE deliveryId = ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, deliveryId);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
				Delivery deliveryObj = new Delivery();
				deliveryObj.setDeliveryId(results.getInt("deliveryId"));
				deliveryObj.setStoreId(results.getInt("storeId"));
				deliveryObj.setDeliveryDate(results.getDate("deliveryDate"));
				deliveryObj.setWeight(results.getDouble("weight"));
				deliveryObj.setProductId(results.getInt("productId"));
	            list.add(deliveryObj);
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
		
	}
    
    
    public Delivery getSingleDeliveryById(String deliveryId) {
		
		ResultSet results;
	    Delivery myDelivery = null;
		
	    String query =  "SELECT id, storeId, deliveryDate, userId FROM delivery WHERE deliveryId = ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, deliveryId);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			if( results.next() ){
				myDelivery = new Delivery();
				myDelivery.setId(results.getInt("id"));
				myDelivery.setStoreId(results.getInt("storeId"));
				myDelivery.setDeliveryDate(results.getDate("deliveryDate"));
				myDelivery.setEmployeeId(results.getInt("userId"));
	      
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return myDelivery;
		
	}
	
	@Override
	public int insertDelivery(Delivery deli) throws Exception
	{  //call to get the next id number

		int rc = -1;
		String query="INSERT INTO delivery(deliveryId,storeId,deliveryDate,weight,productId,userId)  VALUES(?,?,?,?,?,?)";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, deli.getDeliveryId());
			stmt.setInt(2, deli.getStoreId());
      		stmt.setDate(3, deli.getDeliveryDate());
      		stmt.setDouble(4, deli.getWeight());
      		stmt.setInt(5, deli.getProductId());
      		stmt.setInt(6, deli.getEmployeeId());
      	
      		
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		return(rc);
	}
	
	public int updateDelivery(String deliveryId, int storeId) throws Exception{  

		int rc = -1;
		String query="UPDATE delivery SET storeId=? WHERE deliveryId=?";

		try{
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, storeId);
      		stmt.setString(2, deliveryId);
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException ex){
			throw new Exception (ex);
		}
		return(rc);
	}
	
	private ArrayList<Delivery> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Delivery> list = new ArrayList<Delivery>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				Delivery deliObj = new Delivery();
		     	deliObj = buildDelivery(results);	
	            list.add(deliObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	
	public int deleteDelivery(String deliveryId)
	{
		int rc=-1;

		String query="DELETE FROM delivery WHERE id = ?";
		try{ // delete from delivery
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, deliveryId);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in delivery db: "+ex);
		}
		return(rc);
	}
	
	private String buildQuery(String wClause)
	{
	    String query="SELECT DISTINCT (deliveryId) id, deliveryId, storeId, deliveryDate, userId  FROM delivery ORDER BY deliveryId";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private Delivery buildDelivery(ResultSet results){   
		
		Delivery deliveryObj = new Delivery();
        try{
        	deliveryObj.setId(results.getInt("id"));
        	deliveryObj.setDeliveryId(results.getInt("deliveryId"));
        	deliveryObj.setStoreId(results.getInt("storeId"));
        	deliveryObj.setDeliveryDate(results.getDate("deliveryDate"));
        	deliveryObj.setEmployeeId(results.getInt("userId"));
        	
        	  
        } catch(Exception e) {
             System.out.println("error in building the Delivery object");
        }
        
         return deliveryObj;
      }


}  


