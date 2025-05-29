package com.example.mycontact.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name="contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" , nullable = false)
	private Long id;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(name = "email")
	private String email;

	@Size(max=50)
	@NotBlank(message = "Name is required")
	@Column(name = "name", nullable = false )
	private String name;

	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^[0-9]{10,11}$", message = "Phone must be 10 or 11 digits")
	@Column(name = "phone" , nullable = false)
	private String phone;
	
	  public Contact() {
	        // constructor mặc định để Hibernate sử dụng
	    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
