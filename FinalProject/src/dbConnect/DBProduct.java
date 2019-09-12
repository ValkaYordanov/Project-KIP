package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBProduct implements IFDBProduct{
	private  Connection con;
	
	public DBProduct() {
		con = ConnectDB.getInstance().getDBcon();
	}

	//Implements the methods from the interface
	// get all products
	public ArrayList<Product> getAllProduct(boolean retriveAssociation)
	{
		return miscWhere("", retriveAssociation);
	}
	
	public ArrayList<Product> getAllProductName(String name, boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Product> list = new ArrayList<Product>();	
		
	    String query="SELECT id, name, price, typeOfMeat, minStock, nameOfSupplier FROM Product WHERE name LIKE ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name + "%");
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
		     	 Product prodObj = new Product();
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

	public ArrayList<Product> getAllProductsNames(boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<Product> list = new ArrayList<Product>();	
		
	    String query="SELECT name FROM Product";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
		     	 Product prodObj = new Product();
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
	
	public double getPriceFromProduct(int id) throws SQLException {
		
		ResultSet results;
		double price = 0.0;
		String query="SELECT price FROM product WHERE id=?";
		
		PreparedStatement stmt = con.prepareStatement(query);
  		stmt.setInt(1, id); 	
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	
    	if( results.next() ){
    		price = results.getDouble("price");
		}
		
		return price;
		
	}
	
	public double getStockFromProduct(int id) throws SQLException {
		
		ResultSet results;
		double stock = 0.0;
		String query="SELECT minStock FROM product WHERE id=?";
		
		PreparedStatement stmt = con.prepareStatement(query);
  		stmt.setInt(1, id); 	
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	
    	if( results.next() ){
    		stock = results.getDouble("minStock");
		}
		
		return stock;
		
	}
	
	public Product findProduct(int  serialNumber, boolean retriveAssociation){   
		String wClause = "  serialNumber = '" + serialNumber + "'";
		return singleWhere(wClause, retriveAssociation);
	}
	
	
	public String getProductName(String id) throws Exception {
		
		ResultSet results;
		String productName = null;
		
		String query="SELECT name FROM product where id=?";
		
		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, id);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if( results.next() ){
                productName = results.getString("name");           
			} 
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		
		return productName;
	}
	
	public Product findProductId(String name) throws Exception {
		
		ResultSet results;
		Product prodObj = new Product();
				
		String query="SELECT id FROM product where name=?";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name);
      		
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if( results.next() ){
				prodObj.setId(results.getInt("id"));
                stmt.close();
                          
			} else{
                prodObj = null;
                stmt.close();
            }

		}//end try
		catch(SQLException ex){
			throw new Exception (ex);
		}
		
		return prodObj;
		
	}
	//insert a new product
	@Override
	public int insertProduct(Product prod) throws Exception {  

		int rc = -1;
		String query="INSERT INTO product(name, price, typeOfMeat, minStock, nameOfSupplier)  VALUES(?,?,?,?,?)";

		try{ // insert new product 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, prod.getName());
      		stmt.setDouble(2, prod.getPrice());
      		stmt.setString(3, prod.getTypeOfMeat());
      		stmt.setInt(4, prod.getMinStock());
      		stmt.setString(5, prod.getNameOfSupplier());
      		
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
	public int updateProduct(Product prod)
	{
		Product prodObj  = prod;
		int rc=-1;

		String query="UPDATE product SET name = ?, price = ?, typeOfMeat = ?, minStock = ?, nameOfSupplier = ? WHERE id = ?";
			
		
		try{ // update product

			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, prodObj.getName());
      		stmt.setDouble(2, prodObj.getPrice());
      		stmt.setString(3, prodObj.getTypeOfMeat());
      		stmt.setInt(4, prodObj.getMinStock());
      		stmt.setString(5, prodObj.getNameOfSupplier());
      		stmt.setInt(6, prodObj.getId());
      		rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Update exception in product db: " +ex);
		}
		return(rc);
	}

	public int deleteProduct(String serialNumber)
	{
		int rc=-1;

		String query="DELETE FROM product WHERE id = ?";
		try{ // delete from product
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, serialNumber);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in product db: "+ex);
		}
		return(rc);
	}

	private ArrayList<Product> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Product> list = new ArrayList<Product>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
		     	 Product prodObj = new Product();
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
	
	private Product singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Product prodObj = new Product();
                
        String query =  buildQuery(wClause);
        
		try{
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
                prodObj = buildProduct(results);
                stmt.close();
                          
			} else{
                prodObj = null;
            }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return prodObj;
	}

	private String buildQuery(String wClause)
	{
	    String query="SELECT id, name, price, typeOfMeat, minStock, nameOfSupplier FROM Product";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private Product buildProduct(ResultSet results) {   
		Product prodObj = new Product();
          try{
        	  	prodObj.setId(results.getInt("id"));
                prodObj.setName(results.getString("name"));
                prodObj.setPrice(results.getDouble("price"));
                prodObj.setTypeOfMeat(results.getString("typeOfMeat"));
                prodObj.setMinStock(results.getInt("minStock"));
                prodObj.setNameOfSupplier(results.getString("nameOfSupplier"));
          }
         catch(Exception e)
         {
             System.out.println(e);
         }
         return prodObj;
      }


}  


