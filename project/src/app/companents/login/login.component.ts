import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/models/loginUser';
import { DataUserService } from 'src/app/services/data-user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  titel = "Login";
  userLogin: LoginUser = new LoginUser();
  constructor(public loginService: LoginService, public route: Router, public dataUserService: DataUserService) { }

  ngOnInit() {
   

  }


  sendRememberMe(rememberMe : boolean){
    this.userLogin.rememberMe=rememberMe;
  }


  sendLogin() {

    console.log(this.userLogin)
    this.loginService.login(this.userLogin).subscribe(
      (next) => {
        this.userLogin.id = next;
        this.userLogin.password = "";
        sessionStorage.setItem("userLogin", JSON.stringify(this.userLogin)),
        localStorage.setItem("rememberMe", JSON.stringify(this.userLogin.rememberMe))
        this.dataUserService.setIsLogin(true);
        this.dataUserService.setUserName(this.userLogin.name);
        this.dataUserService.setType(this.userLogin.type);
        this.dataUserService.setUserId(this.userLogin.id)
        console.log(this.dataUserService.getType())
        console.log(this.dataUserService.getUserName())
        this.dataUserService.routeClient();
      },
      (error) => { console.log(error); 
        if(error.error.message){
          console.log(error)
          alert(error.error.message) 
        }
        // else{
        //   this.dataUserService.checkLogin()
        //   alert("We are renovating the app for you Please try later")
        // }
      },
      () => {
        return console.log('finish');
      })
  }


}
