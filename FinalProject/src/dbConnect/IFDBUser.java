package dbConnect;
import model.*;

import java.util.ArrayList;

public interface IFDBUser {

    public ArrayList<User> getAllUsers(boolean retriveAssociation);

    public ArrayList<User> getAllUserName(String name, boolean retriveAssociation);
    
    public int createUser(User usr) throws Exception;
  
    public int updateUser(User usr);
    
    public User checkLogin(int username, String password, boolean retriveAssociation) throws Exception;

    public int deleteUser(String id);

	public User searchUserByEmployeeId(int employeeId, boolean b) throws Exception;

	public String searchUserNameByEmployeeId(int id, boolean b);
	
	 public String searchUserNameByEmployeeLogin(String login, boolean retrieveAssociation);
    
}
