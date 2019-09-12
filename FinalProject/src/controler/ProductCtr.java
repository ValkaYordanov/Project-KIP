package controler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import dbConnect.ConnectDB;
import dbConnect.DBProduct;
import dbConnect.IFDBProduct;
import model.Product;

public class ProductCtr {
	
	public ArrayList<Product> getAll() {
		
      IFDBProduct dbProd = new DBProduct();
      ArrayList<Product> allProd = new ArrayList<Product>();
      allProd = dbProd.getAllProduct(false);
      return allProd;
    }
	
	public ArrayList<Product> searchByName(String name){
		IFDBProduct dbProd = new DBProduct();
	    ArrayList<Product> searchedProd = new ArrayList<Product>();
	    searchedProd = dbProd.getAllProductName(name, false);
	    return searchedProd;
		
	}
	
	public String getProductName(String id) throws Exception {
		IFDBProduct dbProd = new DBProduct();
		String productName = dbProd.getProductName(id);
		return productName;
	}

	public void insertProduct(String name, double price, String typeOfMeat, int minStock, String nameOfSupplier) throws Exception {    
         Product prodObj = new Product();
         prodObj.setName(name);
         prodObj.setPrice(price);
         prodObj.setTypeOfMeat(typeOfMeat);
         prodObj.setMinStock(minStock);
         prodObj.setNameOfSupplier(nameOfSupplier);
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBProduct dbCus = new DBProduct();
     	 	dbCus.insertProduct(prodObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception("Product not inserted");
         }
    }
	
	public void updateProduct(int productId, String name, double price, String typeOfMeat, int minStock, String nameOfSupplier) throws Exception {
		
		Product prodObj = new Product();
		prodObj.setId(productId);
	    prodObj.setName(name);
	    prodObj.setPrice(price);
	    prodObj.setTypeOfMeat(typeOfMeat);
	    prodObj.setMinStock(minStock);
	    prodObj.setNameOfSupplier(nameOfSupplier);
	    
	    try{
	   	 
	   	ConnectDB.startTransaction();
	     	IFDBProduct dbCus = new DBProduct();
		 	dbCus.updateProduct(prodObj);
		 	ConnectDB.commitTransaction();
	    }
	    catch(Exception e)
	    {
	   	 ConnectDB.rollbackTransaction();
	        throw new Exception("Couldn't update the product");
	    }
		
	}
			
	public int deleteProduct(String id) {
    	IFDBProduct dbProd = new DBProduct();
    	int rc = dbProd.deleteProduct(id);
    	return rc;
    }	

	public  ArrayList<Product> getAllProductsNames() {
		
		IFDBProduct dbProd = new DBProduct();
	    ArrayList<Product> allProd = new ArrayList<Product>();
	    allProd = dbProd.getAllProduct(false);
	    return allProd;
	      
	}

	public double getPriceFromProduct(int id) throws SQLException {
		
		double price;
		
		IFDBProduct dbProd = new DBProduct();
		price = dbProd.getPriceFromProduct(id);
	    
		return price;
	}
	
	
	public double getStockFromProduct(int id) throws SQLException {
		
		double stock;
		
		IFDBProduct dbProd = new DBProduct();
		stock = dbProd.getStockFromProduct(id);
	    
		return stock;
	}

}
