package com.rays.pro4.Bean;

import java.util.Date;

public class OrderBean  extends BaseBean{
	
	private String orderName;
	private String orderPrice;
	private Date orderConfirm;
	
	

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Date getOrderConfirm() {
		return orderConfirm;
	}

	public void setOrderConfirm(Date orderConfirm) {
		this.orderConfirm = orderConfirm;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return orderConfirm+"";
	}

}
