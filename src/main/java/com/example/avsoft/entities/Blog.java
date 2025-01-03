package com.example.avsoft.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Table(name="blog")
@Entity
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	public String getAuthorFullName() {
		return authorFullName;
	}

	@Column(name = "author_full_name")
    private String authorFullName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUseId() {
		return useId;
	}

	public void setUseId(int useId) {
		this.useId = useId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Column(name="userId",nullable = true)
	private int useId;
	
	@Column(name="title",nullable = true)
	private String title;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Column(name="category",nullable =  true)
	private String category;
	
	@Column(name="thumbnail",nullable=false)
	private String thumbnail;
	
	@Column(name="flag")
	private Boolean flag =false;
	
	@Column(name="content",nullable = true, length = 4500)
	private String content;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;
	
	@Column(name = "updated_at", updatable = true)
	private Date updatedAt;

	public void setAuthorFullName(String fullName) {
		// TODO Auto-generated method stub
		
		this.authorFullName = fullName;
		
	}
	
	
	
	

}
