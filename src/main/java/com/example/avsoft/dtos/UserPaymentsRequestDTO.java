package com.example.avsoft.dtos;

public class UserPaymentsRequestDTO {

	private int batchId;
	private int userId;
	private int amount;
	private String status;

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
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
