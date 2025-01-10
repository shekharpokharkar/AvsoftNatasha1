package com.example.avsoft.dtos;

public class UserPaymentsRequestDTO {

	
	private Long batchId;
	private int userId;
	private int amount;
	private String status;

	public  Long getBatchId() {
		return batchId;
	}

	public void setBatchId( Long batchId) {
		this.batchId = batchId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "UserPaymentsRequestDTO [batchId=" + batchId + ", userId=" + userId + ", amount=" + amount + ", status="
				+ status + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
