package com.iGSE.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "image", unique = false, nullable = false, length = 100000)
	private byte[] image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Image(Long id, String name, String type, byte[] image) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.image = image;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", type=" + type + ", image=" + Arrays.toString(image) + "]";
	}
	
	
}
