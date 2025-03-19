import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { ContactListComponent } from './components/contact-list/contact-list.component';
import { ContactFormComponent } from './components/contact-form/contact-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'contacts', pathMatch: 'full' },
  { path: 'contacts', component: ContactListComponent },
  {path: 'add-user', component: ContactFormComponent}
];

export const appRouting = provideRouter(routes);
