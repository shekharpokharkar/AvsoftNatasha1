package com.example.avsoft.dtos;

public class BatchInstallmentDetailsDTO {

	public int installmentNo;
	public String InstallmentFee;
	public String InstallmentDate;

	public int getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}

	public String getInstallmentFee() {
		return InstallmentFee;
	}

	public void setInstallmentFee(String installmentFee) {
		InstallmentFee = installmentFee;
	}

	public String getInstallmentDate() {
		return InstallmentDate;
	}

	public void setInstallmentDate(String installmentDate) {
		InstallmentDate = installmentDate;
	}

	@Override
	public String toString() {
		return "BatchInstallmentDetailsDTO [installmentNo=" + installmentNo + ", InstallmentFee=" + InstallmentFee
				+ ", InstallmentDate=" + InstallmentDate + "]";
	}

}