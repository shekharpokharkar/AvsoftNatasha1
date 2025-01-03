package com.example.avsoft.dtos;

import java.math.BigDecimal;

public class UserPaymentDTO {

	private int userId;
	private Long batchid;
	private int amount;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Long getBatchid() {
		return batchid;
	}

	public void setBatchid(Long batchid) {
		this.batchid = batchid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
