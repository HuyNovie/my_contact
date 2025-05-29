package com.example.mycontact.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.mycontact.entities.Contact;

@Repository
public interface ContactRespository extends CrudRepository<Contact, Integer>{
	List<Contact> findByNameContaining(String term);
}

