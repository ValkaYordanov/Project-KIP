package controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.sql.Date;

import dbConnect.ConnectDB;
import dbConnect.DBProduct;
import dbConnect.DBWarehouse;
import dbConnect.IFDBProduct;
import dbConnect.IFDBWarehouse;
import model.Product;
import model.Sale;
import model.Warehouse;


public class WarehouseCtr {
	
	public ArrayList<Warehouse> getAllProducts() {
		 IFDBWarehouse dbProd = new DBWarehouse();
	     ArrayList<Warehouse> allProd = new ArrayList<Warehouse>();
	     allProd = dbProd.getAllProducts(false);
	     return allProd;
		
	}
	
	public ArrayList<Warehouse> searchByName(String name){
		IFDBWarehouse dbProd = new DBWarehouse();
	    ArrayList<Warehouse> searchedProd = new ArrayList<Warehouse>();
	    searchedProd = dbProd.getAllProductName(name, false);
	    return searchedProd;
		
	}
	
	public ArrayList<Warehouse> checkProductStocks(){
		IFDBWarehouse dbProd = new DBWarehouse();
	    ArrayList<Warehouse> prodList = new ArrayList<Warehouse>();
	    prodList = dbProd.checkProductStocks(false);
	    return prodList;
		
	}
	

	public void insertProductWarehouse(int productId, String productName, double stock, Date expirationDate, Date productionDate) throws Exception {    
		Warehouse wareObj = new Warehouse();
		wareObj.setProductId(productId);
		wareObj.setProductName(productName);
		wareObj.setStock(stock);
		wareObj.setExpirationDate(expirationDate);
		wareObj.setProductionDate(productionDate);
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBWarehouse dbCus = new DBWarehouse();
     	 	dbCus.insertProductWarehouse(wareObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception(e);
         }
    }
	
	public void updateProductWarehouse(int serialNumber, double stock, Date expirationDate, Date productionDate) throws Exception {    
		Warehouse wareObj = new Warehouse();
		wareObj.setSerialNumber(serialNumber);
		wareObj.setStock(stock);
		wareObj.setExpirationDate(expirationDate);
		wareObj.setProductionDate(productionDate);
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBWarehouse dbCus = new DBWarehouse();
     	 	dbCus.updateProductWarehouse(wareObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception("Product not inserted in the warehouse");
         }
    }
		
	public int deleteProduct(int id) {
    	IFDBWarehouse dbWarehouse = new DBWarehouse();
    	int rc = dbWarehouse.deleteProduct(id);
    	return rc;
    }
	
	public int getIdFromProductName(String name) throws Exception {
		IFDBProduct dbProd = new DBProduct();
	    Product myProduct = dbProd.findProductId(name);
	    int productId = myProduct.getId();
	    
	    return productId;
	}

	
	// For checking if all the stock on the warehouse table is enough for the request on sale.
	public double getProductStock(String productName) {
		IFDBWarehouse dbWarehouse = new DBWarehouse();
		double stock = dbWarehouse.getProductStock(productName);
		return stock;
	}

	
	// Iterate through the hashmap products and compare the stocks on [getProductStock]
	public boolean checkProductStock(HashMap products) {
		Iterator it = products.entrySet().iterator();
		
	    while (it.hasNext()) {
	    	
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        String productName = (String) pair.getKey();
	        
	        double weight = Integer.parseInt((String) pair.getValue());
	        if(weight > getProductStock(productName)) {
	        	return false;
	        }
	    }
		return true;
		
	}
	
	
	
	public Warehouse firstExpiringDateAndSaveSale(String productName, double weight, int customerId, Date currentDate, int username, int saleId) throws Exception {
		IFDBWarehouse dbWarehouse = new DBWarehouse();
		
		Warehouse myWarehouse = new Warehouse();
		
		myWarehouse = dbWarehouse.getFirstExpirationDate(productName);
		
		double stock = myWarehouse.getStock();

		if(weight > 0) {
			int serialNumber = myWarehouse.getSerialNumber();
			
			SaleCtr mySale = new SaleCtr();
			
			int productId = myWarehouse.getProductId();
			
			// get price from product
			ProductCtr myproduct = new ProductCtr();
			double price = myproduct.getPriceFromProduct(productId);
			
			// Total price of the product price multiply by the weight
			double totalPrice = price * weight;
			
			if(stock >= weight) {
				double newStock = stock - weight;
				
				if(newStock == 0) {
					dbWarehouse.deleteProduct(serialNumber);
				} else {
					int wasUpdated = dbWarehouse.updateStock(serialNumber,newStock);
					
					if(wasUpdated != -1) {			
						mySale.insertSale(saleId, customerId, currentDate, username, productId, weight, totalPrice );
					} else {
						System.out.println("Error updating the product");
						return myWarehouse;
						
					}
				}
				
			} else {
				
				totalPrice = price * stock;
				double newWeight = weight - stock;
				if(dbWarehouse.deleteProduct(serialNumber) != -1) {
					mySale.insertSale(saleId, customerId, currentDate, username, productId, stock, totalPrice );
					
					firstExpiringDateAndSaveSale(productName,newWeight, customerId, currentDate, username, saleId);
				
				}
			}
		}
		
		
		return myWarehouse;
	    
	}
	
	public boolean updateStock(HashMap products, int customerId, Date currentDate, int username , int saleId) throws Exception {
		Iterator it = products.entrySet().iterator();
		
	    while (it.hasNext()) {
	    	
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        String productName = (String) pair.getKey();
	        
	        double weight = Integer.parseInt((String) pair.getValue());
	        
	        firstExpiringDateAndSaveSale(productName,weight, customerId, currentDate, username, saleId);
	    }
		return false;
		
	}
	
	
	
	public Warehouse firstExpiringDateAndSaveDelivery(String productName, double weight, int storeId, Date currentDate, int username, int deliveryId) throws Exception {
		IFDBWarehouse dbWarehouse = new DBWarehouse();
		
		Warehouse myWarehouse = new Warehouse();
		
		myWarehouse = dbWarehouse.getFirstExpirationDate(productName);
		
		double stock = myWarehouse.getStock();

		if(weight > 0) {
			int serialNumber = myWarehouse.getSerialNumber();
			
			DeliveryCtr myDelivery = new DeliveryCtr();
			
			int productId = myWarehouse.getProductId();
			
			
			if(stock >= weight) {
				double newStock = stock - weight;
				
				if(newStock == 0) {
					dbWarehouse.deleteProduct(serialNumber);
				} else {
					int wasUpdated = dbWarehouse.updateStock(serialNumber,newStock);
					
					if(wasUpdated != -1) {			
						myDelivery.insertDelivery(deliveryId, storeId, currentDate, username, productId, weight);
					} else {
						System.out.println("Error updating the product");
						return myWarehouse;
						
					}
				}
				
			} else {
				
				double newWeight = weight - stock;
				if(dbWarehouse.deleteProduct(serialNumber) != -1) {
					myDelivery.insertDelivery(deliveryId, storeId, currentDate, username, productId, stock);
					
					firstExpiringDateAndSaveDelivery(productName,newWeight, storeId, currentDate, username, deliveryId);
				
				}
			}
		}
		
		
		return myWarehouse;
	    
	}
	
	
	public boolean updateStockDelivery(HashMap products, int storeId, Date currentDate, int username , int deliveryId) throws Exception {
		Iterator it = products.entrySet().iterator();
		
	    while (it.hasNext()) {
	    	
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        String productName = (String) pair.getKey();
	        
	        double weight = Integer.parseInt((String) pair.getValue());
	        
	        firstExpiringDateAndSaveDelivery(productName,weight, storeId, currentDate, username, deliveryId);
	    }
		return false;
		
	}
	
	
	
	
}

