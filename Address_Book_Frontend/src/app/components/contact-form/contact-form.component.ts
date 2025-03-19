import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ContactService } from '../../services/contact.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css'],
  imports : [ReactiveFormsModule, RouterLink],
  standalone: true
})
export class ContactFormComponent {
  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private contactService: ContactService) {
    this.contactForm = this.fb.group({
      fullName: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.pattern('[0-9]{10}')]],
      address: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zipCode: ['', [Validators.required, Validators.pattern('[0-9]{6}')]]
    });
  }
  router = inject(Router)
  submitForm() {
    console.log("Submit button clicked!"); // Debug log
  
    if (this.contactForm.valid) {
      console.log("Form is valid. Sending data..."); // Debug log
  
      const contactData = {
        name: this.contactForm.value.fullName, 
        phone: this.contactForm.value.phoneNumber, 
        address: this.contactForm.value.address,
        city: this.contactForm.value.city,
        state: this.contactForm.value.state,
        zipCode: this.contactForm.value.zipCode
      };
  
      this.contactService.createContact(contactData).subscribe({ 

        next: () => {
          console.log("Contact added successfully!"); 
          alert('Contact added successfully!');
          this.contactForm.reset();
          this.router.navigate(['/contacts']);
        },


        error: (err) => {
          console.error("Error adding contact:", err); // Debug log
          alert('Failed to add contact. Check console for details.');
        } 
      });
  
    } else {
      console.warn("Form is invalid:", this.contactForm.value); // Debug log
    }
  }
}  