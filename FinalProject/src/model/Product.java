package model;

import java.sql.Date;

public class Product {
	
	private int id; 
	private String name;
	private double price;
	private String typeOfMeat;
	private int minStock;
	private String nameOfSupplier;
	
	public Product()
	{}
	
	public Product(int id, String name,double price, String typeOfMeat, int minStock, String nameOfSupplier) {
		
		this.id=id; 
		this.name=name;
		this.price=price;
		this.typeOfMeat=typeOfMeat;
		this.minStock=minStock;
		this.nameOfSupplier=nameOfSupplier;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price=price;
	}

	public String getTypeOfMeat() {
		return typeOfMeat;
	}

	public void setTypeOfMeat(String typeOfMeat) {
		this.typeOfMeat = typeOfMeat;
	}
	
	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	
	public String getNameOfSupplier() {
		return nameOfSupplier;
	}

	public void setNameOfSupplier(String nameOfSupplier) {
		this.nameOfSupplier = nameOfSupplier;
	}
	
	
	

}
