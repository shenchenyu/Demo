package com.leo.demo.bean;

public class Category{
	private String id;
	private String name;
	private String parentId;
		
	public Category(String id, String name, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parentId="
				+ parentId + "]";
	}
	
}
