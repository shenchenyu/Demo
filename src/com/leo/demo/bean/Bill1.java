package com.leo.demo.bean;

import java.util.List;

public class Bill1 {
	private String id ;
	private	String billDate;
	private Category category;
	private String createOn;
	private String description;
	private String dueDate;
	private boolean isRecurring;
	private List<Itemlist> items;
	private String lastUpdatedOn;
	private int status;
	private Vendor vendor;
	private String amount;
	private RecurringSetting recurringSetting;
	
	
	public Bill1(){};
	
	
	public Bill1(String id, String billDate, Category category,
			String createOn, String description, String dueDate,
			boolean isRecurring, List<Itemlist> items, String lastUpdatedOn,
			int status, Vendor vendor,String amount) {
		super();
		this.id = id;
		this.billDate = billDate;
		this.category = category;
		this.createOn = createOn;
		this.description = description;
		this.dueDate = dueDate;
		this.isRecurring = isRecurring;
		this.items = items;
		this.lastUpdatedOn = lastUpdatedOn;
		this.status = status;
		this.vendor = vendor;
		this.amount = amount;
	}
	public Bill1(String billDate, Category category,
			String createOn, String description, String dueDate,
			boolean isRecurring, List<Itemlist> items, String lastUpdatedOn,
			int status, Vendor vendor,String amount) {
		super();
		this.billDate = billDate;
		this.category = category;
		this.createOn = createOn;
		this.description = description;
		this.dueDate = dueDate;
		this.isRecurring = isRecurring;
		this.items = items;
		this.lastUpdatedOn = lastUpdatedOn;
		this.status = status;
		this.vendor = vendor;
		this.amount = amount;
	}
	public Bill1(String billDate, String amount,String dueDate) {
		super();
		this.billDate = billDate;
		this.dueDate = dueDate;
		this.amount = amount;
	}
	
	
	
	
	
	public RecurringSetting getRecurringSetting() {
		return recurringSetting;
	}


	public void setRecurringSetting(RecurringSetting recurringSetting) {
		this.recurringSetting = recurringSetting;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getCreateOn() {
		return createOn;
	}
	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isRecurring() {
		return isRecurring;
	}
	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}
	
	public List<Itemlist> getItems() {
		return items;
	}
	public void setItems(List<Itemlist> items) {
		this.items = items;
	}
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	@Override
	public String toString() {
		return "Bill1 [id=" + id + ", billDate=" + billDate + ", category="
				+ category + ", createOn=" + createOn + ", description="
				+ description + ", dueDate=" + dueDate + ", isRecurring="
				+ isRecurring + ", items=" + items + ", lastUpdatedOn="
				+ lastUpdatedOn + ", status=" + status + ", vendor=" + vendor
				+ "]";
	}
	
}
