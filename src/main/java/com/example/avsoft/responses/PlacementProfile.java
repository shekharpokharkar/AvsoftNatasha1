package com.example.avsoft.responses;

public class PlacementProfile {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	private String name;
	public PlacementProfile(String name, String company, String salary, String image) {
		super();
		this.name = name;
		this.company = company;
		this.salary = salary;
		this.image = image;
	}
	private String company;
	private String salary;
	private String image;
	

}
