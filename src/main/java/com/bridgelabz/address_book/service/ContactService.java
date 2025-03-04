package com.bridgelabz.address_book.service;

import com.bridgelabz.address_book.dto.ContactDTO;
import com.bridgelabz.address_book.model.Contact;
import com.bridgelabz.address_book.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;



    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contact -> new ContactDTO(contact.getName(), contact.getEmail(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contact -> new ContactDTO(contact.getName(), contact.getEmail(), contact.getPhone()));
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = new Contact(null, contactDTO.getName(), contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactRepository.save(contact);
        return new ContactDTO(savedContact.getName(), savedContact.getEmail(), savedContact.getPhone());
    }

    public Optional<ContactDTO> updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(contactDTO.getName());
                    contact.setEmail(contactDTO.getEmail());
                    contact.setPhone(contactDTO.getPhone());
                    Contact updatedContact = contactRepository.save(contact);
                    return new ContactDTO(updatedContact.getName(), updatedContact.getEmail(), updatedContact.getPhone());
                });
    }

    public boolean deleteContact(Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return true;
                }).orElse(false);
    }
}