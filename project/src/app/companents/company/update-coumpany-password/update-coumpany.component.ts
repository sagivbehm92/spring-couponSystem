import { Component, OnInit } from '@angular/core';
import { DataUserService } from 'src/app/services/data-user.service';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';
import { PasswordBeans } from 'src/app/models/passwordBeans';

@Component({
  selector: 'app-update-coumpany',
  templateUrl: './update-coumpany.component.html',
  styleUrls: ['./update-coumpany.component.scss']
})
export class UpdateCoumpanyComponent implements OnInit {

  passwordBeans :PasswordBeans = new PasswordBeans ();
  constructor(public dataUserService: DataUserService, public route: Router, public companyService: CompanyService) { }

  ngOnInit() {

  }


  updatePasswordCompany() {
    this.passwordBeans.id=this.dataUserService.getUserId();
    this.companyService.updatePasswordCompany(this.passwordBeans).subscribe(
      (next) => {
        alert("Password changed successfully")
          ; this.route.navigate(['/company'])
      },
      (error) => { alert(error.error.message) }


    )

  }

}