import { Component, OnInit } from '@angular/core';
import { DataUserService } from 'src/app/services/data-user.service';
import { CustomerService } from 'src/app/services/customer.service';
import { Router } from '@angular/router';
import { PasswordBeans } from 'src/app/models/passwordBeans';

@Component({
  selector: 'app-update-customer-details',
  templateUrl: './update-customer-details.component.html',
  styleUrls: ['./update-customer-details.component.scss']
})
export class UpdateCustomerDetailsComponent implements OnInit {
  passwordBeans : PasswordBeans = new PasswordBeans();
  constructor(public dataUserService: DataUserService, public route: Router, public customerService: CustomerService) { }

  ngOnInit() {

  }


  customerUpdate() {
    this.passwordBeans.id=this.dataUserService.getUserId();
    console.log(this.passwordBeans);
    
    this.customerService.customerUpdate(this.passwordBeans).subscribe(
      (next) => {
        alert("Password changed successfully")
        ; this.route.navigate(['/customer'])
      },
      (error) => { alert(error.error.message) }


    )

  }

}
