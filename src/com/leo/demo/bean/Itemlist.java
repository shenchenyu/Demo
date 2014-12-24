package com.leo.demo.bean;

class Itemlist{
	private String billId;
	private String id;
	private String name;
	private int pricePerUnit;
	private int quantity;
	private int total;
	
	public Itemlist(String billId, String id, String name, int pricePerUnit,
			int quantity, int total) {
		super();
		this.billId = billId;
		this.id = id;
		this.name = name;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
		this.total = total;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(int pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
