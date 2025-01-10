package com.example.avsoft.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Installment_Structure")
public class InstallmentStructure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Installment_ID")
	private int insatllmentId;

	@Column(name = "Installment_No")
	private int installmentNo;

	@Column(name = "Due_Date")
	private LocalDate date;

	@Column(name = "Installment_Amount")
	private BigDecimal amount;

	@Column(name = "Batch_Id")
	private Long batch;
	
	

	public int getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public void createInstallaionDetails() {

	}

	public int getInsatllmentId() {
		return insatllmentId;
	}

	public void setInsatllmentId(int insatllmentId) {
		this.insatllmentId = insatllmentId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

	

}
