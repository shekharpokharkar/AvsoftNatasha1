package com.example.avsoft.dtos;

import java.math.BigDecimal;

public class InstallmentDTO {

	private int insatllmentId;
	private int installmentNo;
	private String date;
	private BigDecimal amount;
	private int batch;

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

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public InstallmentDTO(int insatllmentId, int installmentNo, String date, BigDecimal amount, int batch) {

		this.insatllmentId = insatllmentId;
		this.installmentNo = installmentNo;
		this.date = date;
		this.amount = amount;
		this.batch = batch;
	}

	public InstallmentDTO() {

	}

}
