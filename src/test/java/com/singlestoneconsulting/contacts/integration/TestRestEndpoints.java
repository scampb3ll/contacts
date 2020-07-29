package com.singlestoneconsulting.contacts.integration;

import com.singlestoneconsulting.contacts.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRestEndpoints {

    public static final String BASE_MAPPING_CONTACT = "/contacts";
    public static final String LOCATION = "Location";

    private static TestRestTemplate testRestTemplate;

    private static Contact contact;

    @BeforeEach
    public void initializeTestContact(){
        contact = new Contact();

        contact.setAddress(new ContactAddress("111 Some St","SomeTown","VA","22222"));
        contact.setName(new ContactName("John","Q","Test"));
        contact.addPhoneNumber(new ContactPhoneNumber("213-555-1212", PHONE_TYPE.home));
        contact.setEmail("johnQ@yahoo.com");
    }

    @BeforeAll
    public static void initializeTestTemplate(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:8080");
        testRestTemplate  =  new TestRestTemplate(restTemplateBuilder);

    }

    @Test
    public void testCreateContact_Success() throws Exception {
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.CREATED, c.getStatusCode());
    }
    @Test
    public void testCreateContact_InvalidZip() throws Exception {
        contact.getAddress().setZip("123456");
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.BAD_REQUEST, c.getStatusCode());

        contact.getAddress().setZip("1234X");
        c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.BAD_REQUEST, c.getStatusCode());

    }
    @Test
    public void testCreateContact_InvalidPhone() throws Exception {
        contact.getPhone().add(new ContactPhoneNumber("notAphone",PHONE_TYPE.home));
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.BAD_REQUEST, c.getStatusCode());
    }
    @Test
    public void testCreateContact_MissingLastName() throws Exception {
        contact.getName().setLast("");
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.BAD_REQUEST, c.getStatusCode());
    }
    @Test
    public void testCreateContact_MissingStreet() throws Exception {
        contact.getAddress().setStreet("");
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.BAD_REQUEST, c.getStatusCode());
    }

    @Test
    public void testUpdate() throws Exception{
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals( HttpStatus.CREATED, c.getStatusCode());

        Contact updatedContact = c.getBody();
        updatedContact.setName(new ContactName("changed","","name"));
        HttpEntity<Contact> requestUpdate = new HttpEntity<>(updatedContact);
        ResponseEntity<Contact> updatedResult = testRestTemplate.exchange(
                c.getHeaders().getFirst(LOCATION),
                HttpMethod.PUT,
                requestUpdate, Contact.class);

        assertEquals("changed",updatedResult.getBody().getName().getFirst());

    }

    @Test
    public void testReadAndDelete() throws Exception {
        ResponseEntity<Contact> c = testRestTemplate.postForEntity(BASE_MAPPING_CONTACT, contact, Contact.class);
        assertEquals(HttpStatus.CREATED,c.getStatusCode());

        ResponseEntity<Contact> fetched = testRestTemplate.getForEntity(c.getHeaders().getFirst(LOCATION),Contact.class);
        assertEquals(HttpStatus.OK,fetched.getStatusCode());

        testRestTemplate.delete(c.getHeaders().getFirst(LOCATION));

        ResponseEntity<Contact> fetchDeleted = testRestTemplate.getForEntity(c.getHeaders().getFirst(LOCATION),Contact.class);
        assertEquals(HttpStatus.NOT_FOUND,fetchDeleted.getStatusCode());

    }
}
