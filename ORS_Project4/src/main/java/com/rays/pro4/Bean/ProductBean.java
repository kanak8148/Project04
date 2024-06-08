package com.rays.pro4.Bean;

import java.util.Date;

// Create table st_product query
//CREATE TABLE st_product (id INT PRIMARY KEY, productName VARCHAR(255), productAmmount VARCHAR(255), purchaseDate DATE);

public class ProductBean extends BaseBean {

	private String productName;
	private String productAmmount;
	private Date purchaseDate;
	private String description ;
	private String category ;
	
	

	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductAmmount() {
		return productAmmount;
	}

	public void setProductAmmount(String productAmmount) {
		this.productAmmount = productAmmount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return category;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return category;
	}

}