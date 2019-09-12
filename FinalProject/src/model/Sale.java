package model;

import java.util.Date;

public class Sale {
	
	private int id;
	private int saleId;
	private int customerId;
	private Date date;
	private int userId;
	private int productId;
	private double weight;
	private double price;
	
	public Sale() {
		
	}
	
	public Sale(int id, int saleId, int customerId, Date date , int userId, int productId, double weight, double price) {
		
		this.id = id;
		this.saleId = saleId;
		this.customerId = customerId; 
		this.date = date; 
		this.userId = userId; 
		this.productId = productId; 
		this.weight = weight;
		this.price = price;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Date getDate() {
		return date; 
	}
	
	public void setDate(Date date) {
		this.date = date; 
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
