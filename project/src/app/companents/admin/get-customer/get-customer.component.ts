import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-get-customer',
  templateUrl: './get-customer.component.html',
  styleUrls: ['./get-customer.component.scss']
})
export class GetCustomerComponent implements OnInit {
  
  data :Array<Customer> = [ ];
  constructor(private adminService: AdminService) { }

  ngOnInit() {
  }

  sendCustomerId(customerId:number){
    this.adminService.getCustomer(customerId).subscribe(
      (next) => {this.data[0]=next ; console.log(this.data)},
      (error) => {alert(error.error.message)}

    )
  }


}
