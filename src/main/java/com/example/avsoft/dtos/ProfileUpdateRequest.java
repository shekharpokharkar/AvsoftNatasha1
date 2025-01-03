package com.example.avsoft.dtos;

public class ProfileUpdateRequest {
	   public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	private String address;
	    private String contact;
}
