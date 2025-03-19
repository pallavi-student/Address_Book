package com.bridgelabz.address_book.service;

import com.bridgelabz.address_book.dto.ContactDTO;
import com.bridgelabz.address_book.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final List<Contact> contactList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getName(), contact.getEmail(), contact.getPhone());
    }

    public List<ContactDTO> getAllContacts() {
        return contactList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactList.stream()
                .filter(contact -> contact.getId().equals(id))
                .map(this::convertToDTO)
                .findFirst();
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = new Contact(idCounter.getAndIncrement(), contactDTO.getName(), contactDTO.getEmail(), contactDTO.getPhone());
        contactList.add(contact);
        return convertToDTO(contact);
    }

    public Optional<ContactDTO> updateContact(Long id, ContactDTO contactDTO) {
        for (Contact contact : contactList) {
            if (contact.getId().equals(id)) {
                contact.setName(contactDTO.getName());
                contact.setEmail(contactDTO.getEmail());
                contact.setPhone(contactDTO.getPhone());
                return Optional.of(convertToDTO(contact));
            }
        }
        return Optional.empty();
    }

    public boolean deleteContact(Long id) {
        return contactList.removeIf(contact -> contact.getId().equals(id));
    }
}