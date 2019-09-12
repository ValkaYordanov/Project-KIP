package model;

public class User {
	
	// Defining initial variables
	
	private int id;
	private String name;
	private int username;
	private String password;
	private int typeOfUser;
	
	public User() {
		
	}
	
	public User(int id,String name, int username, String password, int typeOfUser ) { 
		this.id = id;
		this.name=name;
		this.username = username; 
		this.password = password; 
		this.typeOfUser = typeOfUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public int getUserName() {
		return username;
	}

	public void setUserName(int username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(int typeOfUser) {
		this.typeOfUser = typeOfUser;
	}
	
	
	


}
