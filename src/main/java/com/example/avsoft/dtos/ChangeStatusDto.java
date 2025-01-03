package com.example.avsoft.dtos;

public class ChangeStatusDto {
 private String status;
 public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getStatusSpecial() {
	return statusSpecial;
}
public void setStatusSpecial(String statusSpecial) {
	this.statusSpecial = statusSpecial;
}
private String statusSpecial;
private int amount;

public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
}
