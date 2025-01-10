package com.example.avsoft.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Table(name = "video")
@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "myEntityGen")
	@TableGenerator(name = "myEntityGen", initialValue = 1000, allocationSize = 1)
	@Column(nullable = false)
	private int id;

	@Column(name = "batch", nullable = false)
	private String batch;

	@Column(name = "title", nullable = false)
	private String title;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "Video_Status")
	private boolean videostatus;

	public boolean isVideostatus() {
		return videostatus;
	}

	public void setVideostatus(boolean videostatus) {
		this.videostatus = videostatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Video() {

	}

	public Video(int id, String batch, String title, Date createdAt, Date updatedAt) {

		this.id = id;
		this.batch = batch;
		this.title = title;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getBatch() {
		return batch;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
