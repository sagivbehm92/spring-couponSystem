import { Component, OnInit } from '@angular/core';
import { LogoutService } from 'src/app/services/logout.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(public logoutService: LogoutService, public dateUserService: DataUserService, public route: Router) { }

  ngOnInit() {
  }

  logout() {
    this.logoutService.logout().subscribe(
      (next) => {
        localStorage.removeItem("rememberMe");
        sessionStorage.removeItem("userLogin")
        this.dateUserService.setIsLogin(false);
        this.dateUserService.setUserName(null);
        this.dateUserService.setType(null);
        this.dateUserService.setUserId(null);
        this.route.navigate([""]);
        console.log("logout Succeeded")
      },
      (error) => {
        console.log(error.error.message);
        this.dateUserService.setIsLogin(false);
        this.dateUserService.setUserName(null);
        this.dateUserService.setType(null);
        this.route.navigate([""]);
        localStorage.removeItem("rememberMe");
        sessionStorage.removeItem("userLogin");
      }
    )
  }

  moveToProducts() {
    this.dateUserService.setIsHome(false);
    this.route.navigate([""])
  }

  moveToHome() {
    this.dateUserService.setIsHome(true);
    this.dateUserService.routeClient();
  }
}
