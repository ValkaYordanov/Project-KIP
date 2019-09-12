package dbConnect;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import com.howtodoinjava.hashing.password.demo.bcrypt.BCrypt;

public class DBUser implements IFDBUser{
    private  Connection con;
   
    public DBUser() {
      con = ConnectDB.getInstance().getDBcon();
    }
    
	@Override
	public ArrayList<User> getAllUsers(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	
	}
	
	public ArrayList<User> getAllUserName(String name, boolean retriveAssociation)
	{
		ResultSet results;
	    ArrayList<User> list = new ArrayList<User>();	
		
	    String query="SELECT id,username,name,typeOfUser FROM users WHERE name LIKE ?";
  
        try{
        	PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setString(1, name + "%");
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery();

			while( results.next() ){
				User UserObj = new User();
				UserObj = buildUser(results);	
	             list.add(UserObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	@Override
	public int createUser(User usr) throws Exception {
		int rc = -1;
		
		int username = usr.getUserName();
		String name = usr.getName();
		String password = usr.getPassword();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(16));
		int typeOfUser = usr.getTypeOfUser();
		
		String query="INSERT INTO users(username, name, password, typeOfUser)  VALUES(?,?,?,?)";
		
		try{ // insert new user 
			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, username);
      		stmt.setString(2, name);
      		stmt.setString(3, hashedPassword);
      		stmt.setInt(4, typeOfUser);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
		}//end try
		catch(SQLException ex){
			throw new Exception ("User is not inserted");
		}
		return(rc);
	}
	@Override
	public int updateUser(User usr) {
		User userObj  = usr;
		int rc=-1;
		
		String hashedPassword = BCrypt.hashpw(userObj.getPassword(), BCrypt.gensalt(16));

		String query="UPDATE users SET userName = ?, name = ?, password = ?, typeOfUser = ? WHERE id = ?";
			
		
		try{ // update product

			PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, userObj.getUserName());
      		stmt.setString(2, userObj.getName());
      		stmt.setString(3, hashedPassword);
      		stmt.setInt(4, userObj.getTypeOfUser());
      		stmt.setInt(5, userObj.getId());
      		rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Update exception in user db: " +ex);
		}
		return(rc);
	}
	
	   public int deleteUser(String id) {
		   int rc=-1;

			String query="DELETE FROM users WHERE id = ?";
			try{ // delete from user
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, id);
				stmt.setQueryTimeout(5);
				rc = stmt.executeUpdate();
				stmt.close();
			}
			catch(Exception ex){
				System.out.println("Delete exception in user db: "+ex);
			}
			return(rc);
	   }
	   
	   public String searchUserNameByEmployeeId(int id, boolean retrieveAssociation) {
		   ResultSet results;
		   String userName = null;
		   
		   String query = "SELECT name from users where id=?";
		   try{ // update product

				PreparedStatement stmt = con.prepareStatement(query);
	      		stmt.setInt(1, id);
	      		stmt.setQueryTimeout(5);
				results = stmt.executeQuery();
	      		if( results.next() ){
					userName = results.getString("name");	                          
				} 
				stmt.close();
			}
			catch(Exception ex){
				System.out.println("Update exception in user db: " +ex);
			}
		   
		   
		   return userName;
	   }
	
	   
	   
	   public String searchUserNameByEmployeeLogin(String login, boolean retrieveAssociation) {
		   ResultSet results;
		   String userName = null;
		   
		   String query = "SELECT name from users where username=?";
		   try{ // update product

				PreparedStatement stmt = con.prepareStatement(query);
	      		stmt.setString(1, login);
	      		stmt.setQueryTimeout(5);
				results = stmt.executeQuery();
	      		if( results.next() ){
					userName = results.getString("name");	                          
				} 
				stmt.close();
			}
			catch(Exception ex){
				System.out.println("Update exception in user db: " +ex);
			}
		   
		   
		   return userName;
	   }
	   
	   
	
	   public User searchUserByEmployeeId(int employeeId, boolean retrieveAssociation) throws Exception {
		   	ResultSet results;
			
			User usrObj = new User();
			
			String query="SELECT id FROM users WHERE username = ?";     
	      	try{
	      		PreparedStatement stmt = con.prepareStatement(query);
	      		stmt.setInt(1, employeeId);
	      		stmt.setQueryTimeout(5);
	      		results = stmt.executeQuery();
	      		if( results.next() ){
    				usrObj.setId(results.getInt("id"));
				} else{
					usrObj = null;
	            }
	          stmt.close();
	       }
	       catch(SQLException ex){
	          throw new Exception ("exception" + ex);
	       }
	       return(usrObj);
		}
	   

	   public User checkLogin(int username, String password, boolean retriveAssociation) throws Exception {
		
		ResultSet results;
		
		User usrObj = new User();
		
		String query="SELECT password FROM users WHERE username = ?";     
      	try{
      		PreparedStatement stmt = con.prepareStatement(query);
      		stmt.setInt(1, username);
      		stmt.setQueryTimeout(5);
      		results = stmt.executeQuery();
      		if( results.next() ){
      			String databasePassword = results.getString("password");
      			
		        boolean matched = BCrypt.checkpw(password, databasePassword);
		        
	 			if(matched) {
	 				String wClause = "username = ?";
	 		        return singleWhere(wClause, username, retriveAssociation);
	 			}
	 			else {
	 				usrObj = null;
	 			}
	 			
			} else{
				usrObj = null;
            }
          stmt.close();
      }
       catch(SQLException ex){
          throw new Exception ("exception" + ex);
       }
       return(usrObj);
	}
	

	
	private ArrayList<User> miscWhere(String wClause, boolean retrieveAssociation)
	{
        ResultSet results;
	    ArrayList<User> list = new ArrayList<User>();	
		
	    String query =  buildQuery(wClause);
  
        try{
        	Statement stmt = con.createStatement();
        	stmt.setQueryTimeout(5);
        	results = stmt.executeQuery(query);

			while( results.next() ){
				User usrObj = new User();
				usrObj = buildUser(results);	
	            list.add(usrObj);	
			}
            stmt.close();     
          	
		}
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	private User singleWhere(String wClause, int username, boolean retrieveAssociation)
	{
		ResultSet results;
		User usrObj = new User();
                
        String query =  buildQuery(wClause);
        
		try{
	 		PreparedStatement stmt = con.prepareStatement(query);
	 		stmt.setInt(1, username);
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery();
	 		
	 		if( results.next() ){
	 			usrObj = buildUser(results);
                stmt.close();
                          
			} else{
				usrObj = null;
            }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return usrObj;
	}

	private String buildQuery(String wClause)
	{
	    String query="SELECT id,username,name,typeOfUser FROM users";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	
	private User buildUser(ResultSet results)
      {   
		User usrObj = new User();
          try{
        	  usrObj.setId(results.getInt("id"));
        	  usrObj.setUserName(results.getInt("username"));
        	  usrObj.setName(results.getString("name"));
        	  usrObj.setTypeOfUser(results.getInt("typeOfUser"));
          }
         catch(Exception e)
         {
             System.out.println("error in building the user object");
         }
         return usrObj;
      }

}  
    

