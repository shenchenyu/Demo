package com.leo.demo.bean;

public class Bill_item {
	private int id;
	private String item_name;
	private float money;


	public Bill_item(){};

	public Bill_item(int id, String item_name, float money) {
		super();
		this.id = id;
		this.item_name = item_name;
		this.money = money;
	}
	
	public Bill_item(String item_name, float money) {
		super();
		this.item_name = item_name;
		this.money = money;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}



}
