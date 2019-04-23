import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Customer } from 'src/app/models/customer';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.scss']
})
export class AddCustomerComponent implements OnInit {
  customer : Customer = new Customer();
  constructor(public adminService: AdminService,) { }
  ngOnInit() {
  }


  addCustomer(){
    this.adminService.createCustomer(this.customer).subscribe(

      (next)=>{alert("Successfully added customer") , this.customer.name = this.customer.password = null},
      (error)=>{alert(error.error.message)}

    )
  }

}
