package controler;

import java.util.ArrayList;
import java.sql.Date;

import dbConnect.ConnectDB;
import dbConnect.DBCustomer;
import dbConnect.DBStore;
import dbConnect.IFDBCustomer;
import dbConnect.IFDBStore;
import model.Customer;
import model.Store;


public class StoreCtr {
	
	public ArrayList<Store> getAll() {
		
      IFDBStore dbStore = new DBStore();
      ArrayList<Store> allStores = new ArrayList<Store>();
      allStores = dbStore.getAllStores(false);
      return allStores;
      
    }
	
	public ArrayList<Store> searchByName(String name){
		IFDBStore dbStore = new DBStore();
	    ArrayList<Store> searchedStore = new ArrayList<Store>();
	    searchedStore = dbStore.getAllStoreName(name, false);
	    return searchedStore;
		
	}
	
	public Store getStoreIdByName(String storeName) throws Exception {
		IFDBStore dbStore = new DBStore();
		Store myStore = new Store();
		myStore = dbStore.findStoreId(storeName);
		return myStore;
	}
	
	public String getStoreNameById(int storeId) throws Exception {
		IFDBStore dbStore = new DBStore();
		String storeName = dbStore.findStoreName(storeId);
		return storeName;
	}

	public void insertStore(String name, String address) throws Exception {    
		Store storeObj = new Store();
         storeObj.setStoreName(name);
         storeObj.setAddress(address);
         
         try{
        	 
        	ConnectDB.startTransaction();
          	IFDBStore dbStore = new DBStore();
     	 	dbStore.createStore(storeObj);
     	 	ConnectDB.commitTransaction();
         }
         catch(Exception e)
         {
        	 ConnectDB.rollbackTransaction();
             throw new Exception("Store not inserted");
         }
    }
	
	public void updateStore(int storeId, String name, String address) throws Exception {
		
		Store storeObj = new Store();
	   	storeObj.setId(storeId);
		 storeObj.setStoreName(name);
         storeObj.setAddress(address);
         
	    try{
	   	 
	   	ConnectDB.startTransaction();
	     	IFDBStore dbStore = new DBStore();
		 	dbStore.updateStore(storeObj);
		 	ConnectDB.commitTransaction();
	    }
	    catch(Exception e)
	    {
	   	 ConnectDB.rollbackTransaction();
	        throw new Exception("Couldn't update the store");
	    }
		
	}
			
	public int deleteStore(String id) {
		IFDBStore dbStore = new DBStore();
    	int rc = dbStore.deleteStore(id);
    	return rc;
    }	

	public  ArrayList<Store> getAllStoreNames() {
		
		IFDBStore dbStore = new DBStore();
	    ArrayList<Store> allStore = new ArrayList<Store>();
	    allStore = dbStore.getAllStores(false);
	    return allStore;
	      
	}
}
