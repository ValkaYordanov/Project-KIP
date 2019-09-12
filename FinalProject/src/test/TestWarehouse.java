package test;

import static org.junit.Assert.assertEquals;
import java.sql.Date;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import dbConnect.ConnectDB;
import dbConnect.DBWarehouse;
import model.Warehouse;

class TestWarehouse {

	ConnectDB con = null;
	static Warehouse tempWarehouse;

	@Before
	public void setUp() {
		con = ConnectDB.getInstance();
	}
	
	@Test
	public void wasInsertedWarehouse() {
		
		tempWarehouse = new Warehouse();
		int numInsert = 0;
		
		tempWarehouse.setProductId(1);
		tempWarehouse.setProductName("Sausage");
		tempWarehouse.setStock(100);
		tempWarehouse.setExpirationDate(Date.valueOf("2019-06-10"));
		tempWarehouse.setProductionDate(Date.valueOf("2019-05-20"));
		
		DBWarehouse myWarehouse = new DBWarehouse();
		
		// Act
		try {
			numInsert = myWarehouse.insertProductWarehouse(tempWarehouse);
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		}
	
		// Assert
		assertEquals("One row inserted", 1, numInsert );
		
	}
	
	@Test
	public void wasUpdatedWarehouse() {
		
		tempWarehouse = new Warehouse();
		int numUpdated = 0;
		
		tempWarehouse.setSerialNumber(20);
		tempWarehouse.setStock(200);
		tempWarehouse.setExpirationDate(Date.valueOf("2019-07-10"));
		tempWarehouse.setProductionDate(Date.valueOf("2019-06-20"));
		
		DBWarehouse myWarehouse = new DBWarehouse();
		
		// Act
		try {
			numUpdated = myWarehouse.updateProductWarehouse(tempWarehouse);
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		}
	
		// Assert
		assertEquals("One row updated", 1, numUpdated );
		
	}
	

}
