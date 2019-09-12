package dbConnect;

import java.util.ArrayList;


import model.Store;

public interface IFDBStore {
	
	 public ArrayList<Store> getAllStores(boolean retriveAssociation);
	 
	    public int createStore(Store store) throws Exception;
	  
	    public int updateStore(Store store);
	   
	    public ArrayList<Store> getAllStoreName(String name, boolean retriveAssociation);

	    public int deleteStore(String id);
	    
	    public Store findStoreId(String name) throws Exception;
	    
	    public String findStoreName(int id) throws Exception;

}
