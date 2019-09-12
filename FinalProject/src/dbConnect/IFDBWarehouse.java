package dbConnect;

import java.util.ArrayList;

import model.Product;
import model.Warehouse;

public interface IFDBWarehouse {

	public ArrayList<Warehouse> getAllProducts(boolean retriveAssociation);
	
	public ArrayList<Warehouse> getAllProductName(String name, boolean retriveAssociation);
	
	public int insertProductWarehouse(Warehouse prod) throws Exception;

	public int deleteProduct(int serialNumber);

	public int updateProductWarehouse(Warehouse prod);

	public double getProductStock(String productName);

	public Warehouse getFirstExpirationDate(String productName);

	public int updateStock(int serialNumber, double newStock) throws Exception;

	public ArrayList<Warehouse> checkProductStocks(boolean retriveAssociation);
	

}
