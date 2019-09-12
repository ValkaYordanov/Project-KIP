package dbConnect;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IFDBProduct {
    // get all product
    public ArrayList<Product> getAllProduct(boolean retriveAssociation);
    public ArrayList<Product> getAllProductName(String name, boolean retriveAssociation);
    
    public ArrayList<Product> getAllProductsNames(boolean retriveAssociation);
    //get one product having the rentPrice
    public Product findProduct(int serialNumber, boolean retriveAssociation);
    
    public Product findProductId(String name) throws Exception;
    //insert a new product
    public int insertProduct(Product prod) throws Exception;
    //update information about an product
    public int updateProduct(Product prod);
	//delete product
    public int deleteProduct(String serialNumber);
   
    public double getPriceFromProduct(int id) throws SQLException;
    
    public double getStockFromProduct(int id) throws SQLException;
    
    public String getProductName(String id) throws Exception;
    
}
