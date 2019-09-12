package controler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import dbConnect.ConnectDB;
import dbConnect.DBSale;
import dbConnect.IFDBSale;
import model.Sale;


public class SaleCtr {
	
	public ArrayList<Sale> getAllSales() {
		 IFDBSale dbSale = new DBSale();
	     ArrayList<Sale> allSale = new ArrayList<Sale>();
	     allSale = dbSale.getAllSales(false);
	     return allSale;
		
	}
	
	public Sale getSingleSaleById(String saleId){
		IFDBSale dbSale = new DBSale();
	    Sale mySale = new Sale();
	    mySale = dbSale.getSingleSaleById(saleId);
	    return mySale;
	}
	
	public ArrayList<Sale> getSaleById(String saleId){
		IFDBSale dbSale = new DBSale();
	    ArrayList<Sale> allSale = new ArrayList<Sale>();
	    allSale = dbSale.getSaleById(saleId);
	    return allSale;
	}
	
	public int getLastSaleId() throws SQLException {
		
		IFDBSale  dbSale = new DBSale();
	    int saleId = dbSale.getLastSaleId();
	    
	    return saleId;
	    
	}
	
	public int updateSale(String saleId, int customerId) throws Exception {
		ConnectDB.startTransaction();
      	IFDBSale dbSale = new DBSale();
 	 	int rc = dbSale.updateSale(saleId, customerId );
 	 	ConnectDB.commitTransaction();
 	 	return rc;
	}

	public void insertSale(int saleId, int customerId, Date date, int userId, int productId, double weight, double price) throws Exception {    
		Sale saleObj = new Sale();
		
		saleObj.setSaleId(saleId);
		saleObj.setCustomerId(customerId);
		saleObj.setDate(date);
		saleObj.setUserId(userId);
		saleObj.setProductId(productId);
		saleObj.setWeight(weight);
		saleObj.setPrice(price);
		
		try{
        	 
        	ConnectDB.startTransaction();
          	IFDBSale dbCus = new DBSale();
     	 	dbCus.insertSale(saleObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception(e);
         }
    }
	
	public int deleteSale(String saleId) {
    	IFDBSale dbSale = new DBSale();
    	int rc = dbSale.deleteSale(saleId);
    	return rc;
    }
	
	
	// QUERIES FOR STATISTICS ---------------------------
	
	public int getSalesFromEmplyees(int employeeId, String year, String month) throws Exception {
		IFDBSale dbSale = new DBSale();
		int numberOfSales = dbSale.getSalesFromEmplyees(employeeId, year, month);
		return numberOfSales;
	}
	
	public double getProfitFromSale(String year, String month) throws Exception {
		IFDBSale dbSale = new DBSale();
		double profit = dbSale.getProfitFromSale(year,month);
	    return profit;
	}
	
	public int getNumberOfSales(String year, String month) throws Exception {
		IFDBSale dbSale = new DBSale();
		int profit = dbSale.getNumberOfSales(year,month);
	    return profit;
	}
}
