import { Component, OnInit } from '@angular/core';
import { DataUserService } from 'src/app/services/data-user.service';
import { CompanyService } from 'src/app/services/company.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {
  constructor(public dataUserService: DataUserService, public companyService: CompanyService, public route: Router ) { }

   ngOnInit() {

    
   }

   







  


}
