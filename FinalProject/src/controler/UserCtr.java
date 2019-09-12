package controler;

import java.util.ArrayList;

import dbConnect.ConnectDB;
import dbConnect.DBStore;
import dbConnect.DBUser;
import dbConnect.IFDBStore;
import dbConnect.IFDBUser;
import model.Store;
import model.User;

public class UserCtr {

	public User checkLogin(int username, String password) throws Exception
    {
      	IFDBUser dbUser = new DBUser();
    	User myObj = dbUser.checkLogin(username, password, false);
 	 	return myObj;
    }
	
	public User findById(int id) throws Exception
    {
        IFDBUser dbUser = new DBUser();
        return dbUser.searchUserByEmployeeId(id, true);
    }
	
	public ArrayList<User> searchUserByName(String name){
		IFDBUser dbUser = new DBUser();
	    ArrayList<User> searchedUser = new ArrayList<User>();
	    searchedUser = dbUser.getAllUserName(name, false);
	    return searchedUser;
		
	}
	
	public ArrayList<User> getAllUsers() {
		
	      IFDBUser dbUser = new DBUser();
	      ArrayList<User> allUser = new ArrayList<User>();
	      allUser = dbUser.getAllUsers(false);
	      return allUser;
	    }
	
	public User checkEmployeeID(int employeeId) throws Exception {
		ConnectDB.startTransaction();
      	IFDBUser dbUser = new DBUser();
    	User myObj = dbUser.searchUserByEmployeeId(employeeId, false);
 	 	ConnectDB.commitTransaction();;
 	 	return myObj;
		
	}
	
	public String findUserNameById(int id) {
		
		IFDBUser dbUser = new DBUser();
    	String userName = dbUser.searchUserNameByEmployeeId(id, false);
    	return userName;
		
	}
	
	public String getUserNameByLogin(String login) {
		IFDBUser dbUser = new DBUser();
    	String userName = dbUser.searchUserNameByEmployeeLogin(login, false);
    	return userName;
		
	}
	
	public void insertUser(int userName,String name ,String password, int typeOfUser) throws Exception {    
        User userObj = new User();
        userObj.setUserName(userName);
        userObj.setName(name);
        userObj.setPassword(password);
        userObj.setTypeOfUser(typeOfUser);
        
        try{
       	 
       	ConnectDB.startTransaction();
         	IFDBUser dbUser = new DBUser();
         	dbUser.createUser(userObj);
    	 	ConnectDB.commitTransaction();
        }
        catch(Exception e)
        {
       	 ConnectDB.rollbackTransaction();
            throw new Exception("User not inserted");
        }
   }
	
   public void updateUser(int id, int userName,String name ,String password, int typeOfUser) throws Exception {
		
		User userObj = new User();
		userObj.setId(id);
		    userObj.setUserName(userName);
	        userObj.setName(name);
	        userObj.setPassword(password);
	        userObj.setTypeOfUser(typeOfUser);
	    
	    try{
	   	 
	   	ConnectDB.startTransaction();
	     	IFDBUser dbUser = new DBUser();
	     	dbUser.updateUser(userObj);
		 	ConnectDB.commitTransaction();
	    }
	    catch(Exception e)
	    {
	   	 ConnectDB.rollbackTransaction();
	        throw new Exception("Couldn't update the user");
	    }
		
	}
			
	public int deleteUser(String id) {
    	IFDBUser dbUser = new DBUser();
    	int rc = dbUser.deleteUser(id);
    	return rc;
    }	
	
	
}
