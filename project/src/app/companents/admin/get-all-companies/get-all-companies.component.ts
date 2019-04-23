import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { Company } from 'src/app/models/company';
@Component({
  selector: 'app-get-all-companies',
  templateUrl: './get-all-companies.component.html',
  styleUrls: ['./get-all-companies.component.scss']
})
export class GetAllCompaniesComponent implements OnInit {
data:Array<Company> =[];
isUpdateCompany : boolean = false;
constructor(public adminService: AdminService ,public ngxSmartModalService: NgxSmartModalService ) { }

ngOnInit() {
  this.getAllCompanies();
}

getAllCompanies() {
  this.adminService.getAllCompany().subscribe(
      (next) => {
        this.isUpdateCompany = false;
        this.data = Object.values(next)
      },
      (error) =>  alert(error.error.message),
      () => console.log('finish')
    )
  }


  deleteCompany(id: string) {
    let isDelete = confirm("Are you sure you want to delete the company id : "+id);
    if(isDelete){
      this.adminService.deleteCompany(id).subscribe(
        (next) => { next; this.getAllCompanies() },
        (error) => alert(error.error.message),
        () => { console.log('finish'); }
        
        )
      }
  }

  updateCompany (company:Company){
    this.ngxSmartModalService.setModalData(company, 'myModal' , true);
    this.isUpdateCompany = true;
    this.ngxSmartModalService.getModal('myModal').open();
  }

  sendUpdate(companyId:number , name:string , password:string , email:string ){
    console.log(new Company(companyId , name , password , email))
    this.adminService.updateCompany(new Company(companyId , name , password , email)).subscribe(
      (next) => {alert("Update successfully passed") ;this.ngxSmartModalService.getModal('myModal').close(); this.getAllCompanies() },
      (error) => { alert(error.error.message)}
    )
  }
}


