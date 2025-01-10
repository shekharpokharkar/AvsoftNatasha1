package com.example.avsoft.dtos;

import java.math.BigDecimal;

public class InstallmentDTO {

	private int insatllmentId;
	private int installmentNo;
	private String date;
	private BigDecimal amount;
	private Long batch;
	private int totalPaidAmount;
	
	
	
	public int getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(int totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public int getInsatllmentId() {
		return insatllmentId;
	}

	public void setInsatllmentId(int insatllmentId) {
		this.insatllmentId = insatllmentId;
	}

	public int getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	public InstallmentDTO(int insatllmentId, int installmentNo, String date, BigDecimal amount, Long batch) {

		this.insatllmentId = insatllmentId;
		this.installmentNo = installmentNo;
		this.date = date;
		this.amount = amount;
		this.batch = batch;
	}

	public InstallmentDTO() {

	}

}
