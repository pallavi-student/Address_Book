package com.bridgelabz.address_book.controller;

import com.bridgelabz.address_book.dto.ContactDTO;
import org.springframework.web.bind.annotation.RequestMapping;


import com.bridgelabz.address_book.model.Contact;
import com.bridgelabz.address_book.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getName(), contact.getEmail(), contact.getPhone());
    }
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok(contactRepository.save(contact));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(updatedContact.getName());
                    contact.setEmail(updatedContact.getEmail());
                    contact.setPhone(updatedContact.getPhone());
                    return ResponseEntity.ok(contactRepository.save(contact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dto")
    public ResponseEntity<List<ContactDTO>> getAllContactsDto() {
        List<ContactDTO> contacts = contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contacts);
    }


    @GetMapping("/dto/{id}")
    public ResponseEntity<ContactDTO> getContactByIdDto(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/dto")
    public ResponseEntity<ContactDTO> createContactDto(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact(null, contactDTO.getName(), contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(convertToDTO(savedContact));
    }


    @PutMapping("/dto/{id}")
    public ResponseEntity<ContactDTO> updateContactDto(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(contactDTO.getName());
                    contact.setEmail(contactDTO.getEmail());
                    contact.setPhone(contactDTO.getPhone());
                    Contact updatedContact = contactRepository.save(contact);
                    return ResponseEntity.ok(convertToDTO(updatedContact));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/dto/{id}")
    public ResponseEntity<Object> deleteContactDto(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }



}