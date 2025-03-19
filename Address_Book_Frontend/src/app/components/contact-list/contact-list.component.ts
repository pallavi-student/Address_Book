import { Component, OnInit } from '@angular/core';
import { ContactService } from '../../services/contact.service';
import { CommonModule } from '@angular/common';
import { ContactFormComponent } from '../contact-form/contact-form.component';
import { Router } from 'express';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-contact-list',
  standalone  : true,
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css'],
  imports :[CommonModule, ContactFormComponent, RouterLink] 
})
export class ContactListComponent implements OnInit {
  contacts: any[] = [];  
  showForm = false ; 

  toggleForm() {
    this.showForm = !this.showForm;
  }

  constructor(private contactService: ContactService) {}

  ngOnInit(): void {
    this.getContacts();
  }

  getContacts(): void {
    this.contactService.getContacts().subscribe((data) => {
      this.contacts = data;
    });
  }
}
