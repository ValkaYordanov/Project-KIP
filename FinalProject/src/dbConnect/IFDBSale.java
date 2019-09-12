package dbConnect;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Sale;

public interface IFDBSale {

	public int insertSale(Sale sale) throws Exception;

	public int getLastSaleId() throws SQLException;

	public ArrayList<Sale> getAllSales(boolean b);
	
	public ArrayList<Sale> getSaleById(String saleId);

	public Sale getSingleSaleById(String saleId);

	public int deleteSale(String saleId);
	
	
	
	
	
	public ArrayList<Integer> getAllSalesIds(boolean retriveAssociation);
	
	public int getNumberOfSales(int saleId);

	public int updateSale(String saleId, int customerId) throws Exception;

	
	// QUERIES FOR STATISTICS ---------------------------
	
	public double getProfitFromSale(String year, String month) throws Exception;

	public int getNumberOfSales(String year, String month) throws Exception;

	public int getSalesFromEmplyees(int employeeId, String year, String month) throws Exception;
	
}
