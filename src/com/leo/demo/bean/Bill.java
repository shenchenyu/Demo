package com.leo.demo.bean;

public class Bill {
	private int id ;
	private String companyName;
	private String invoiceData;
	private String total;
	private String dueDate;
	private boolean Recurring;
	private String category;
	private Bill_item item;
	public Bill(int id,String companyName, String invoiceData, String total,
			String dueDate, boolean recurring, String category, Bill_item item) {
		super();
		this.id = id; 
		this.companyName = companyName;
		this.invoiceData = invoiceData;
		this.total = total;
		this.dueDate = dueDate;
		Recurring = recurring;
		this.category = category;
		this.item = item;
	}
	public Bill(String companyName, String invoiceData, String total,
			String dueDate, boolean recurring, String category, Bill_item item) {
		super(); 
		this.companyName = companyName;
		this.invoiceData = invoiceData;
		this.total = total;
		this.dueDate = dueDate;
		Recurring = recurring;
		this.category = category;
		this.item = item;
	}
	
	public Bill(){}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getInvoiceData() {
		return invoiceData;
	}
	public void setInvoiceData(String invoiceData) {
		this.invoiceData = invoiceData;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isRecurring() {
		return Recurring;
	}
	public void setRecurring(boolean recurring) {
		Recurring = recurring;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Bill_item getItem() {
		return item;
	}
	public void setItem(Bill_item item) {
		this.item = item;
	};
	
	
	
	
	
}
