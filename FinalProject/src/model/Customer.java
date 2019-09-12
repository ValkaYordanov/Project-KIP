package model;

public class Customer {

	private int id;
	private String name; 
	private String street; 
	private String numberOnStreet; 
	private String city; 
	private String phoneNo; 
	
	public Customer(int id, String name, String street, String numberOnStreet, String city, String phoneNo) {
		this.id=id;
		this.name = name; 
		this.street = street; 
		this.numberOnStreet = numberOnStreet; 
		this.city = city; 
		this.phoneNo = phoneNo; 
	}
	
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() { 
		return id; 
	}
	
	public void setId(int id) { 
		this.id=id; 
	}


	public String getName() {
		return name; 
	}
	
	public void setName(String name) {
		this.name = name; 
	}
	
	public String getStreet() {
		return street; 
	}
	
	public void setStreet(String street) {
		this.street = street; 
	}
	
	public String getNumberOnStreet() {
		return numberOnStreet; 
	}
	
	public void setNumberOnStreet(String numberOnStreet) {
		this.numberOnStreet = numberOnStreet; 
	}
	
	
	public String getCity() {
		return city; 
	}

	public void setCity(String city) {
		this.city = city; 
	}
	
	public String getPhoneNo() {
		return phoneNo; 
	}
	
	public void setPhoneNo(String phoneNo) { 
		this.phoneNo = phoneNo; 
	}
	
	
}
