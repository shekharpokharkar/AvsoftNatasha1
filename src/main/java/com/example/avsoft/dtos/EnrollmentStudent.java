package com.example.avsoft.dtos;

import java.util.Date;

public class EnrollmentStudent {
  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
  public int getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

private int enrollmentId;	
	
  private String name;
  private String email;
  private String image;
  private Date enrollmentDate;
  private String status;
  private int amount;
  
  public String getStatusSpecial() {
	return statusSpecial;
}
public void setStatusSpecial(String statusSpecial) {
	this.statusSpecial = statusSpecial;
}
private String statusSpecial;
  
}
