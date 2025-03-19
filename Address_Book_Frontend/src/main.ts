import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appRouting } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; 

bootstrapApplication(AppComponent, {
  providers: [
    appRouting,
    provideHttpClient()
  ]
}).catch(err => console.error(err));
