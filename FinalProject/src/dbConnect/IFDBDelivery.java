package dbConnect;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;

public interface IFDBDelivery {
	public abstract ArrayList<Delivery> getAllDelivery(boolean retriveAssociation);
	
    public int insertDelivery(Delivery deli) throws Exception;
    public int getLastDeliveryId() throws SQLException;
    public Delivery getSingleDeliveryById(String deliveryId);
    public int deleteDelivery(String deliveryId);
	public ArrayList<Delivery> getDeliveryById(String deliveryId);
	public int updateDelivery(String deliveryId, int storeId) throws Exception;
	
	// Queries for statistics
	public abstract Delivery getDeliveryByUser(int userId, String year, String month) throws SQLException;
}
