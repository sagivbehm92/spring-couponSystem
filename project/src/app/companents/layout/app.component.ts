import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { LogoutService } from 'src/app/services/logout.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'Coupon system';
  
  
  constructor(public dataUserService : DataUserService, public route: Router){}

  ngOnInit(): void {
  

  }

  



}

