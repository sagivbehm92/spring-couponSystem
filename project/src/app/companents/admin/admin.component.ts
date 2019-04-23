import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  data: Array<any> = [];
  ifCompanyTable: boolean;
  userName;
  constructor(public adminService: AdminService,public route: Router, public dataUserService: DataUserService) { }

  ngOnInit() {

  }

  getCustomer(idCustomer) {
    this.adminService.getCustomer(idCustomer).subscribe(
      (next) => next,
      (error) => {console.log(error) ; alert(error.error.message)},
      () => console.log('finish'))
  }

  getAllCustomers() {
    this.adminService.getAllCustomer().subscribe(
      (next) => {
        this.data = Object.values(next);
        this.ifCompanyTable = false;

      },
      (error) => {console.log(error) ; alert(error.error.message)},
      () => console.log('finish'))
  }

  deleteCustomer(id: string) {
    this.adminService.deleteCustomer(id).subscribe(
      (next) => { next; this.getAllCustomers() },
      (error) => {console.log(error) ; alert(error.error.message)},
      () => console.log('finish'))
  }




  




 
}
