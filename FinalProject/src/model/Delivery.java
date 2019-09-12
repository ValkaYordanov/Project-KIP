package model;

import java.sql.Date;

public class Delivery {
	
private int id;
private int deliveryId;
private int storeId;
private Date deliveryDate;
private double weight;
private int employeeId;
private int productId;


public Delivery () {
	
}

public Delivery(int id, int deliveryId, int storeId, Date deliveryDate, double weight, int employeeId, int productId){
	this.id = id;
	this.deliveryId=deliveryId;
	this.deliveryDate = deliveryDate;
	this.weight = weight;
	this.employeeId = employeeId;
	this.storeId = storeId;
	this.productId=productId;
	
}

public int getId() {
	return id;
}

public void setDeliveryId(int deliveryId) {
	this.deliveryId = deliveryId;
}

public int getDeliveryId() {
	return deliveryId;
}

public void setId(int id) {
	this.id = id;
}

public Date getDeliveryDate() {
	return deliveryDate;
}

public void setDeliveryDate(Date deliveryDate) {
	this.deliveryDate = deliveryDate;
}

public double getWeight() {
	return weight;
}

public void setWeight(double weight) {
	this.weight = weight;
}



public int getEmployeeId() {
	return employeeId;
}

public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
}

public int getStoreId() {
	return storeId;
}

public void setStoreId(int storeId) {
	this.storeId = storeId;
}

public int getProductId() {
	return productId;
}

public void setProductId(int productId) {
	this.productId = productId;
}
 
 
}
