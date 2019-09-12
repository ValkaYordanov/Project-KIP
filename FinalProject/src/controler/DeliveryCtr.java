package controler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import dbConnect.ConnectDB;
import dbConnect.DBDelivery;
import dbConnect.IFDBDelivery;
import model.Delivery;

public class DeliveryCtr {
	
	public ArrayList<Delivery> getAll() {
		
      IFDBDelivery dbDelivery = new DBDelivery();
      ArrayList<Delivery> allDelivery = new ArrayList<Delivery>();
      allDelivery = dbDelivery.getAllDelivery(false);
      return allDelivery;
    }
	
	public ArrayList<Delivery> getDeliveryById(String deliveryId){
		IFDBDelivery dbDelivery = new DBDelivery();
	    ArrayList<Delivery> allDeliveries = new ArrayList<Delivery>();
	    allDeliveries = dbDelivery.getDeliveryById(deliveryId);
	    return allDeliveries;
	}
	
	public Delivery getSingleDeliveryById(String deliveryId){
		IFDBDelivery dbDelivery = new DBDelivery();
		Delivery myDelivery = new Delivery();
	    myDelivery = dbDelivery.getSingleDeliveryById(deliveryId);
	    return myDelivery;
	}
	
	public Delivery getDeliveryByUser(int userId, String year, String month) throws SQLException{
		IFDBDelivery dbDelivery = new DBDelivery();
		Delivery myDelivery = new Delivery();
	    myDelivery = dbDelivery.getDeliveryByUser(userId, year, month);
	    return myDelivery;
	}
	
	public int updateDelivery(String deliveryId, int storeId) throws Exception {
		ConnectDB.startTransaction();
      	IFDBDelivery dbDelivery = new DBDelivery();
 	 	int rc = dbDelivery.updateDelivery(deliveryId, storeId);
 	 	ConnectDB.commitTransaction();
 	 	return rc;
	}
	
	public void insertDelivery(int deliveryId, int storeId, Date deliveryDate, int userId, int productId, double weight) throws Exception {    
		 Delivery deliObj = new Delivery();
		 deliObj.setDeliveryId(deliveryId);
		 deliObj.setStoreId(storeId);
		 deliObj.setDeliveryDate(deliveryDate);
		 deliObj.setWeight(weight);
		 deliObj.setProductId(productId);
		 deliObj.setEmployeeId(userId);
		
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBDelivery dbDelivery = new DBDelivery();
          	dbDelivery.insertDelivery(deliObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
        	 System.out.println(e);
             throw new Exception("Delivery not created");
         }
    }
	
	public int deleteDelivery(String deliveryId) {
    	IFDBDelivery dbDelivery = new DBDelivery();
    	int rc = dbDelivery.deleteDelivery(deliveryId);
    	return rc;
    }
	
    public int getLastDeliveryId() throws SQLException {
 		
		IFDBDelivery  dbDelivery = new DBDelivery();
	    int deliveryId = dbDelivery.getLastDeliveryId();
	    
	    return deliveryId;
	    
	}
}
