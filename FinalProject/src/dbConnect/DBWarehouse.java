package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBWarehouse implements IFDBWarehouse{
	private  Connection con;
	
	public DBWarehouse() {
		con = ConnectDB.getInstance().getDBcon();
	}

	public ArrayList<Warehouse> getAllProducts(boolean retriveAssociation)
	{
		return miscWhere("", retriveAssociation);
	}
	
	
	public ArrayList<Warehouse> getAllProductName(String name, boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Warehouse> list = new ArrayList<Warehouse>();	
		
	    String query="SELECT serialNumber,productId,productName,stock,expireDate,dateOfProduce FROM warehouse WHERE productName LIKE ? ORDER BY expireDate";
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name + "%");
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
		     	 Warehouse prodObj = new Warehouse();
		     	 prodObj = buildProduct(results);	
	             list.add(prodObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	public ArrayList<Warehouse> checkProductStocks(boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Warehouse> list = new ArrayList<Warehouse>();	
		
	    String query="SELECT productId,productName,stock FROM warehouse";
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
		     	 Warehouse prodObj = new Warehouse();
		     	 prodObj.setProductId(results.getInt("productId"));
		     	 prodObj.setProductName(results.getString("productName"));
		     	 prodObj.setStock(results.getDouble("stock"));
	             list.add(prodObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	
	public double getProductStock(String productName) {
	
		ResultSet results;
		double stock = 0;
		String query =  "SELECT SUM(stock) as stock FROM warehouse WHERE productName = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, productName);
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery();
	 		
	 		if( results.next() ){
                stock = results.getDouble("stock");
                stmt.close();
                          
			} else{
				stock = 0;
            }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return stock;
		
  		
	}
	
	public Warehouse getFirstExpirationDate(String productName) {
		ResultSet results;
		Warehouse wareObj = new Warehouse();
                
        String query =  "SELECT TOP 1 serialNumber,stock,productId, expireDate FROM warehouse WHERE productName = ? ORDER BY expireDate ASC";
        
		try{
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, productName);
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery();
	 		
	 		if( results.next() ){
	 			wareObj.setSerialNumber(results.getInt("serialNumber"));
	 			wareObj.setStock(results.getDouble("stock"));
	 			wareObj.setProductId(results.getInt("productId"));
                stmt.close();
                          
			} else{
				wareObj = null;
            }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return wareObj;
	}
	
	public int updateStock(int serialNumber, double newStock) throws Exception {
		
		int rc = -1;
		String query="UPDATE warehouse SET stock=? WHERE serialNumber=?";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setDouble(1, newStock);
      		stmt.setInt(2, serialNumber);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception ("Product is not inserted correct");
		}
		return(rc);
	}
	
	
	@Override
	public int insertProductWarehouse(Warehouse prod) throws Exception
	{  //call to get the next id number

		int rc = -1;
		String query="INSERT INTO warehouse(productId, productName, stock, expireDate, dateOfProduce)  VALUES(?,?,?,?,?)";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, prod.getProductId());
      		stmt.setString(2, prod.getProductName());
      		stmt.setDouble(3, prod.getStock());
      		stmt.setDate(4, prod.getExpirationDate());
      		stmt.setDate(5, prod.getProductionDate());
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception ("Product is not inserted correct");
		}
		return(rc);
	}
	
	
	@Override
	public int updateProductWarehouse(Warehouse warehouse)
	{
		Warehouse wareObj = warehouse;
		int rc=-1;

		String query="UPDATE warehouse SET stock=?, expireDate=?, dateOfProduce=? WHERE serialNumber=?";
		
		try{ // update product
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setDouble(1, wareObj.getStock());
			stmt.setDate(2, wareObj.getExpirationDate());
			stmt.setDate(3, wareObj.getProductionDate());
			stmt.setInt(4, wareObj.getSerialNumber());
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();

			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Update exception in product db: " +ex);
		}
		return(rc);
	}
	
	public int deleteProduct(int serialNumber)
	{
		int rc=-1;

		String query="DELETE FROM warehouse WHERE serialNumber = ?";
		try{ // delete from product
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, serialNumber);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in product db: "+ex);
		}
		return(rc);
	}

	private ArrayList<Warehouse> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Warehouse> list = new ArrayList<Warehouse>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				Warehouse prodObj = new Warehouse();
		     	prodObj = buildProduct(results);	
	            list.add(prodObj);	
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
	    String query="SELECT serialNumber,productId,productName,stock,expireDate,dateOfProduce FROM warehouse ORDER BY productId";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private Warehouse buildProduct(ResultSet results)
      {   
		Warehouse wareObj = new Warehouse();
          try{
        	  wareObj.setSerialNumber(results.getInt("serialNumber"));
        	  wareObj.setProductId(results.getInt("productId"));
        	  wareObj.setProductName(results.getString("productName"));
        	  wareObj.setStock(results.getDouble("stock"));
        	  wareObj.setExpirationDate(results.getDate("expireDate"));
        	  wareObj.setProductionDate(results.getDate("dateOfProduce"));
          }
         catch(Exception e)
         {
             System.out.println(e);
         }
         return wareObj;
      }
	


}  


