import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { Customer } from 'src/app/models/customer';

@Component({
  selector: 'app-get-all-customer',
  templateUrl: './get-all-customer.component.html',
  styleUrls: ['./get-all-customer.component.scss']
})
export class GetAllCustomerComponent implements OnInit {
  data : Array<any> = [];
  isUpdateCustomer : boolean = false;
  constructor(public adminService: AdminService,public ngxSmartModalService: NgxSmartModalService ) { }

  ngOnInit() {
    this.getAllCustomers();
  }

  
  getAllCustomers() {
    this.adminService.getAllCustomer().subscribe(
      (next) => {
        this.data = Object.values(next);

      },
      //לגבי הארור אין לי רעיון איך להציג את זה בינתיים
      (error) => alert(error.error.message),
      () => console.log('finish'))
  }

  deleteCustomer(id: string) {
    let isDelete = confirm("Are you sure you want to delete the customer id : "+id);
    if(isDelete){
    this.adminService.deleteCustomer(id).subscribe(
      (next) => { next; this.getAllCustomers() },
      //לגבי הארור אין לי רעיון איך להציג את זה בינתיים
      (error) => alert(error.error.message),
      () => console.log('finish'))
    }
  }

  updateCustomer (customer:Customer){
    this.ngxSmartModalService.setModalData(customer, 'myModal' , true);
    this.isUpdateCustomer = true;
    this.ngxSmartModalService.getModal('myModal').open();
  }

  sendUpdate(customerId:number , name:string , password:string  ){
    console.log(new Customer(customerId , name , password ))
    this.adminService.updateCustomer(new Customer(customerId , name , password )).subscribe(
      (next) => {alert("Update successfully passed") ;this.ngxSmartModalService.getModal('myModal').close(); this.getAllCustomers() },
      (error) => {alert(error.error.message)}
    )
  }




  
}
