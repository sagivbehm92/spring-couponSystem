import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/company';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-get-company',
  templateUrl: './get-company.component.html',
  styleUrls: ['./get-company.component.scss']
})
export class GetCompanyComponent implements OnInit {
  data :Array<Company> = [];
  constructor(public adminService: AdminService) { }

  ngOnInit() {
  }

  sendCompanyId(idCompany:number){
    this.adminService.getCompany(idCompany).subscribe(
      (next) => {this.data[0]=(next) ; console.log(this.data)},
      (error) => {alert(error.error.message)}

    )
  }

}
