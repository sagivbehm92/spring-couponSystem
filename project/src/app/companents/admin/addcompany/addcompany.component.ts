import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Company } from 'src/app/models/company';

@Component({
  selector: 'app-addcompany',
  templateUrl: './addcompany.component.html',
  styleUrls: ['./addcompany.component.scss']
})
export class AddcompanyComponent implements OnInit {
  company : Company = new Company();
  constructor(public adminService: AdminService,) { }

  ngOnInit() {

  }

  addCompany(){

  
    this.adminService.createCompany(this.company).subscribe(

      (next) => {alert("Successfully added company") , this.company.name = this.company.password = this.company.email=null},
      (error)=>{alert(error.error.message)}

    )
   }

}
