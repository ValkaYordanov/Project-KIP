package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DBSale implements IFDBSale{
	private  Connection con;
	
	public DBSale() {
		con = ConnectDB.getInstance().getDBcon();
	}
	
	public ArrayList<Sale> getAllSales(boolean retriveAssociation){
		return miscWhere("", retriveAssociation);
	}
	
	public ArrayList<Sale> getSaleById(String saleId) {
		
		ResultSet results;
	    ArrayList<Sale> list = new ArrayList<Sale>();	
		
	    String query =  "SELECT id, customerId, date, userId, productId, weight, price FROM sale WHERE saleId = ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, saleId);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
				Sale saleObj = new Sale();
				saleObj.setId(results.getInt("id"));
				saleObj.setCustomerId(results.getInt("customerId"));
				saleObj.setDate(results.getDate("date"));
				saleObj.setUserId(results.getInt("userId"));
				saleObj.setProductId(results.getInt("productId"));
				saleObj.setWeight(results.getDouble("weight"));
				saleObj.setPrice(results.getDouble("price"));
	            list.add(saleObj);
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
		
	}
	
	public Sale getSingleSaleById(String saleId) {
		
		ResultSet results;
	    Sale mySale = null;
		
	    String query =  "SELECT id, customerId, date, userId FROM sale WHERE saleId = ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, saleId);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			if( results.next() ){
				mySale = new Sale();
				mySale.setId(results.getInt("id"));
				mySale.setCustomerId(results.getInt("customerId"));
				mySale.setDate(results.getDate("date"));
				mySale.setUserId(results.getInt("userId"));
	      
			}			
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return mySale;
		
	}

	public int getLastSaleId() throws SQLException {
		
		int saleId = 0;
		ResultSet results;
		
		String query="SELECT TOP 1 MAX(saleId) as saleId FROM sale";
		
		PreparedStatement stmt = con.prepareStatement(query);
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	
    	if( results.next() ){
    		saleId = results.getInt("saleId");
    		saleId += 1;
		} else {
			saleId = 1;
		}
		
		return saleId;
	}
	
	public double getProfitFromSale(String year, String month) throws SQLException {
		
		double profit = 0;
		ResultSet results;
		
		String query="SELECT SUM(price) FROM sale WHERE date LIKE ?";
		
		PreparedStatement stmt = con.prepareStatement(query);
		String concat = year+"-"+month+"%";
		stmt.setString(1, concat);
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	if( results.next() ){
    		profit += results.getDouble(1);
    
		}
		
		return profit;
	}
	
	
	public int getSalesFromEmplyees(int employeeId, String year, String month) throws SQLException {
		
		int count = 0;
		ResultSet results;
		
		String concat = year+"-"+month+"%";
		
		String query="SELECT COUNT(id) FROM sale WHERE userId = ? AND date LIKE ?";
		
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, employeeId);
		stmt.setString(2, concat);
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	if( results.next() ){
    		count = results.getInt(1);
		}
		
		return count;
	}
	
	public int getNumberOfSales(String year, String month) throws SQLException {
		
		int count = 0;
		ResultSet results;
		
		String query="SELECT COUNT(id) FROM sale WHERE date LIKE ?";
		
		PreparedStatement stmt = con.prepareStatement(query);
		String concat = year+"-"+month+"%";
		stmt.setString(1, concat);
    	stmt.setQueryTimeout(5);
    	results = stmt.executeQuery();
    	if( results.next() ){
    		count = results.getInt(1);
		}
		
		return count;
	}
	
	@Override
	public int insertSale(Sale sale) throws Exception{  

		int rc = -1;
		String query="INSERT INTO sale(saleId, customerId, date, userId, productId, weight, price)  VALUES(?,?,?,?,?,?,?)";

		try{
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, sale.getSaleId());
      		stmt.setInt(2, sale.getCustomerId());
      		stmt.setDate(3, (java.sql.Date) sale.getDate());
      		stmt.setInt(4, sale.getUserId());
      		stmt.setInt(5, sale.getProductId());
      		stmt.setDouble(6, sale.getWeight());
      		stmt.setDouble(7, sale.getPrice());
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException ex){
			throw new Exception (ex);
		}
		return(rc);
	}
	
	@Override
	public int updateSale(String saleId, int customerId) throws Exception{  

		int rc = -1;
		String query="UPDATE sale SET customerId=? WHERE saleId=?";

		try{
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, customerId);
      		stmt.setString(2, saleId);
      		
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException ex){
			throw new Exception (ex);
		}
		return(rc);
	}
	
	public int deleteSale(String saleId)
	{
		int rc=-1;

		String query="DELETE FROM sale WHERE saleId = ?";
		try{ // delete from sale
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, saleId);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Delete exception in sale db: "+ex);
		}
		return(rc);
	}
	
	private ArrayList<Sale> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<Sale> list = new ArrayList<Sale>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				Sale saleObj = new Sale();
				saleObj = buildSale(results);	
	            list.add(saleObj);
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
	    String query="SELECT DISTINCT (saleId) id, saleId, customerId, date, userId  FROM sale ORDER BY saleId";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	
	private Sale buildSale(ResultSet results){   
		
		Sale saleObj = new Sale();
        try{
        	saleObj.setId(results.getInt("id"));
        	saleObj.setSaleId(results.getInt("saleId"));
        	saleObj.setCustomerId(results.getInt("customerId"));
        	saleObj.setDate(results.getDate("date"));
        	saleObj.setUserId(results.getInt("userId"));
        	//saleObj.setProductId(results.getInt("productId"));
        	//saleObj.setWeight(results.getDouble("weight"));
        	//saleObj.setPrice(results.getDouble("price"));
        	  
        } catch(Exception e) {
             System.out.println("error in building the Sale object");
        }
         return saleObj;
      }

	
	// QUERIES FOR STATISTICS ---------------------------
	
	public ArrayList<Integer> getAllSalesIds(boolean retriveAssociation){
		
		ResultSet results;
	    ArrayList<Integer> ids = new ArrayList<Integer>();	
		
	    String query =  "SELECT DISTINCT (saleId) saleId FROM sale ORDER BY saleId";
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				ids.add(results.getInt("saleId"));
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return ids;
		
	}
	
	
	public int getNumberOfSales(int saleId) {
		ResultSet results;
		
		int numberOfSales = 0;
		
		String query =  "SELECT COUNT(*) FROM sale WHERE saleId = ?";
		  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, saleId);
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			if( results.next() ){
				numberOfSales = results.getInt(1);
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		
		return numberOfSales;
	}

}  


