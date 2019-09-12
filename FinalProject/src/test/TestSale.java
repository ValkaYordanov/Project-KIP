
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import dbConnect.ConnectDB;
import dbConnect.DBSale;
import model.Sale;


class TestSale {

	ConnectDB con = null;
	static Sale tempSale;
	public HashMap<String, String> products = new HashMap<>();
	public int saleId = 10;
	public int customerId;
	
	public String date;
	public int userId;
	public double price;

	@Before
	public void setUp() {
		con = ConnectDB.getInstance();
	}
	
	
	@Test
	public void wasSaleUpdated() {
		tempSale = new Sale();
		
		int numUpdate = 0;
		customerId = 2;
		
		DBSale mySale = new DBSale();
		
		
		try {
			numUpdate = mySale.updateSale(String.valueOf(saleId), customerId);
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		}
    
		assertTrue( numUpdate > 0 );
	}
	
	@Test
	public void wasSaleCreated() {
		
		tempSale = new Sale();
		products.put("1", "100");
		products.put("2", "200");
		
		customerId = 1;
		date = "2019-05-31";
		userId = 1;
		price = 100.0;
		
		int numInsert = 0;
		
		DBSale mySale = new DBSale();
		
		tempSale.setSaleId(saleId);
		tempSale.setCustomerId(customerId);
		tempSale.setDate(Date.valueOf(date));
		tempSale.setUserId(userId);
		tempSale.setPrice(price);
		
		Iterator it = products.entrySet().iterator();
		
	    while (it.hasNext()) {
	    	
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        tempSale.setProductId(Integer.parseInt((String) pair.getKey()));
	        tempSale.setWeight(Integer.parseInt((String) pair.getValue())); 
	        
	        try {
				numInsert = mySale.insertSale(tempSale);
			} catch(Exception ex) { 
				System.out.println("Error: " + ex.getMessage());
			}
	    }
		
		assertEquals("One row inserted", 1, numInsert );
		
	}

}
