package com.example.mycontact.service;

import java.util.List;

import com.example.mycontact.entities.Contact;

public interface ContactService {
	
	Iterable<Contact> findAll();
	
	List<Contact> search(String name);
	
	Contact findById(Integer id);
	
	void save(Contact contact);
	
	void delete(Integer id);
	
}
