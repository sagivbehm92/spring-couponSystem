import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/company';
import { DataUserService } from 'src/app/services/data-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-email',
  templateUrl: './update-email.component.html',
  styleUrls: ['./update-email.component.scss']
})
export class UpdateEmailComponent implements OnInit {
  company : Company = new Company();
  constructor(public companyService : CompanyService ,public route: Router, public dataUserService:DataUserService , ) { }

  ngOnInit() {
  }

  companyEmailUpdate( ){
    this.company.id = this.dataUserService.getUserId();
    // console.log(this.company);
    this.companyService.updateEmaildCompany(this.company).subscribe(

      (next) => {alert('Change email successfully'); this.route.navigate(['/company'])} ,
      (error) => {alert(error.error.message) }
    )
  }

}
