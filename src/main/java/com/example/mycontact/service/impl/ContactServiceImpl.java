package com.example.mycontact.service.impl;

import java.util.List;

import com.example.mycontact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mycontact.repository.ContactRespository;
import com.example.mycontact.entities.Contact;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRespository contactResponsitory;
	
	//search all
	@Override
	public Iterable<Contact> findAll() {
		return contactResponsitory.findAll();
	}
	// Display item
	@Override
	public List<Contact> search(String term) {
		return contactResponsitory.findByNameContaining(term);
	}
	
	//search to id
	@Override
	public Contact findById(Integer id) {
		return contactResponsitory.findById(id).get();
	}
	
	
	//search contact
	@Override
	public void save(Contact contact) {
		contactResponsitory.save(contact);
		
	}

	//delete contact
	@Override
	public void delete(Integer id) {
		Contact contact = contactResponsitory.findById(id).get();
		contactResponsitory.delete(contact);
	}

	
}