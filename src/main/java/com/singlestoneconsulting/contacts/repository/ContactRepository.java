package com.singlestoneconsulting.contacts.repository;

import com.singlestoneconsulting.contacts.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactRepository extends MongoRepository<Contact, String> {

}
