package com.example.avsoft.dtos;

public class VideoRequestDto {

	private String batch;
	private String title;

	public String getBatch() {
		return batch;
	}

	public VideoRequestDto(String batch, String title) {
		super();
		this.batch = batch;
		this.title = title;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "VideoRequestDto [batch=" + batch + ", title=" + title + "]";
	}

	public VideoRequestDto() {

	}

}
