package model;

public class Store {

	private int id;
	private String storeName;
	private String address;
	
	public Store(int id, String storeName,String address)
	{
		this.id=id;
		this.storeName=storeName;
		this.address=address;
	}
	
	public Store() {
		// TODO Auto-generated constructor stub
	}

	public int getId() { 
		return id; 
	}
	
	public void setId(int id) { 
		this.id=id; 
	}
	
	public String getStoreName()
	{
		return storeName;
	}
	
	public void setStoreName(String storeName)
	{
		this.storeName=storeName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address=address;
	}
}
