import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-form',
  imports: [HttpClientModule,FormsModule],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent {
  http=inject(HttpClient);
  router=inject(Router)

  userData:any={
    "name": "",
    "profilePic": "",
    "salary": "",
    "gender": "",
    "startDate": "",
    "department": [],
    "note": ""
  }

  days: number[] = [];
  years: number[] = [];
  months: string[] = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];

  isEditMode=false;

  constructor() {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { userData?: any };
    if (state?.userData) {
      this.userData = { ...state.userData };
      this.isEditMode = true;

    }
    this.days = Array.from({ length: 31 }, (_, i) => i + 1);
    

    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: currentYear - 1999 }, (_, i) => currentYear - i);
  } 
  updateDepartment(event: any) {
    const value = event.target.value;
    if (event.target.checked) {
      this.userData.department.push(value);
    } else {
      this.userData.department = this.userData.department.filter((dept: string) => dept !== value);
    }
  }
  saveUserData(){
    if(this.isEditMode){
      this.http.put("http://localhost:8080/api/employees/update/" + this.userData.id, this.userData).subscribe(() => {
        alert("User updated successfully!");
        this.router.navigateByUrl('/employees');
      });
    }else{
      this.http.post("http://localhost:8080/api/employees/add",this.userData).subscribe((result:any)=>{
        alert("Data added successfully!")
        this.router.navigateByUrl('/employees');
      });
    }
  }
}