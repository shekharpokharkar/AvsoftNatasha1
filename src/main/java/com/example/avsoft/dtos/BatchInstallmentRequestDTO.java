package com.example.avsoft.dtos;

import java.util.List;

import com.example.avsoft.entities.Courses;

public class BatchInstallmentRequestDTO {

	private String title;

	private int fee;

	private int specialfee;

	private String description;

	private String duration;

	private List<Courses> courses;

	private BatchInstallmentDTO installmentDTO;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getSpecialfee() {
		return specialfee;
	}

	public void setSpecialfee(int specialfee) {
		this.specialfee = specialfee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}

	public BatchInstallmentDTO getInstallmentDTO() {
		return installmentDTO;
	}

	public void setInstallmentDTO(BatchInstallmentDTO installmentDTO) {
		this.installmentDTO = installmentDTO;
	}

	@Override
	public String toString() {
		return "BatchInstallmentRequestDTO [title=" + title + ", fee=" + fee + ", specialfee=" + specialfee
				+ ", description=" + description + ", duration=" + duration + ", courses=" + courses
				+ ", installmentDTO=" + installmentDTO + "]";
	}

}
