package com.example.avsoft.dtos;

import java.util.List;

public class BatchInstallmentDTO {

	private int insatllmentNo;
	private String noOfDaysInbetweenInstallment;
	private List<BatchInstallmentDetailsDTO> batchInstallmentdto;

	public int getInsatllmentNo() {
		return insatllmentNo;
	}

	public void setInsatllmentNo(int insatllmentNo) {
		this.insatllmentNo = insatllmentNo;
	}

	public String getNoOfDaysInbetweenInstallment() {
		return noOfDaysInbetweenInstallment;
	}

	public void setNoOfDaysInbetweenInstallment(String noOfDaysInbetweenInstallment) {
		this.noOfDaysInbetweenInstallment = noOfDaysInbetweenInstallment;
	}

	public List<BatchInstallmentDetailsDTO> getBatchInstallmentdto() {
		return batchInstallmentdto;
	}

	public void setBatchInstallmentdto(List<BatchInstallmentDetailsDTO> batchInstallmentdto) {
		this.batchInstallmentdto = batchInstallmentdto;
	}

	@Override
	public String toString() {
		return "BatchInstallmentDTO [insatllmentNo=" + insatllmentNo + ", noOfDaysInbetweenInstallment="
				+ noOfDaysInbetweenInstallment + ", batchInstallmentdto=" + batchInstallmentdto + "]";
	}

}
