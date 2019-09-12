package model;

import java.sql.Date;

public class Warehouse {

	private int serialNumber;
	private double stock;
	private Date expirationDate;
	private Date productionDate; 
	private int productId;
	private String productName;
	


	public Warehouse() {
		
	}
	
	public Warehouse(int serialNumber, double stock , Date expirationDate, Date productionDate, int productId, String productName){
		
		this.serialNumber = serialNumber;
		this.stock=stock;
		this.expirationDate = expirationDate;
		this.productionDate = productionDate;
		this.productId = productId;
		this.productName = productName;
	}


	public int getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	public double getStock() {
		return stock;
	}


	public void setStock(double stock) {
		this.stock = stock;
	}


	public Date getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(Date exprireDate) {
		this.expirationDate = exprireDate;
	}


	public Date getProductionDate() {
		return productionDate;
	}


	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
